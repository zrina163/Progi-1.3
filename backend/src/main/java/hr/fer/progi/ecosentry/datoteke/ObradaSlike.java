package hr.fer.progi.ecosentry.datoteke;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Provjera i "čišćenje" učitanih fotografija (NF-2.5, NF-2.6).
 *
 * 1. Format se prepoznaje po prvim bajtovima datoteke (magic bytes), a ne po
 *    Content-Type zaglavlju koje šalje klijent i koje se može lažirati.
 * 2. Dopušteni su samo JPEG i PNG.
 * 3. Slika se dekodira (ako to ne uspije, datoteka nije prava slika) i ponovno
 *    kodira bez metapodataka – time se uklanjaju EXIF podaci (GPS lokacija,
 *    model uređaja, vrijeme snimanja).
 * 4. Prije uklanjanja EXIF-a čita se oznaka orijentacije i slika se okreće,
 *    da fotografije s mobitela ne ostanu polegnute.
 */
final class ObradaSlike {

    /** Zaštita od "dekompresijskih bombi": mala datoteka, ogromna slika. */
    static final long MAX_PIKSELA = 40_000_000L;

    enum Format {
        JPEG(".jpg"), PNG(".png");

        final String ekstenzija;

        Format(String ekstenzija) { this.ekstenzija = ekstenzija; }
    }

    record Rezultat(byte[] bajtovi, String ekstenzija) {}

    private ObradaSlike() {}

    static Rezultat ocisti(byte[] ulaz) {
        Format format = prepoznaj(ulaz);
        if (format == null) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    "Dopuštene su samo JPEG i PNG slike");
        }

        int orijentacija = format == Format.JPEG ? ExifOrijentacija.procitaj(ulaz) : 1;

        BufferedImage slika = dekodiraj(ulaz);
        slika = ispraviOrijentaciju(slika, orijentacija);

        return new Rezultat(kodiraj(slika, format), format.ekstenzija);
    }

    static Format prepoznaj(byte[] b) {
        if (b == null) {
            return null;
        }
        if (b.length >= 3
                && (b[0] & 0xFF) == 0xFF && (b[1] & 0xFF) == 0xD8 && (b[2] & 0xFF) == 0xFF) {
            return Format.JPEG;
        }
        byte[] png = {(byte) 0x89, 'P', 'N', 'G', '\r', '\n', 0x1A, '\n'};
        if (b.length >= png.length) {
            for (int i = 0; i < png.length; i++) {
                if (b[i] != png[i]) {
                    return null;
                }
            }
            return Format.PNG;
        }
        return null;
    }

    private static BufferedImage dekodiraj(byte[] ulaz) {
        try (ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(ulaz))) {
            Iterator<ImageReader> citaci = ImageIO.getImageReaders(iis);
            if (!citaci.hasNext()) {
                throw neispravna();
            }
            ImageReader citac = citaci.next();
            try {
                citac.setInput(iis, true, true); // ignoriraj metapodatke
                long piksela = (long) citac.getWidth(0) * citac.getHeight(0);
                if (piksela > MAX_PIKSELA) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Slika ima prevelike dimenzije");
                }
                BufferedImage slika = citac.read(0);
                if (slika == null) {
                    throw neispravna();
                }
                return slika;
            } finally {
                citac.dispose();
            }
        } catch (ResponseStatusException e) {
            throw e;
        } catch (IOException | RuntimeException e) {
            // npr. oštećena datoteka ili CMYK JPEG koji Java ne zna pročitati
            throw neispravna();
        }
    }

    private static byte[] kodiraj(BufferedImage slika, Format format) {
        ByteArrayOutputStream izlaz = new ByteArrayOutputStream();
        try {
            if (format == Format.PNG) {
                ImageIO.write(slika, "png", izlaz);
            } else {
                ImageWriter pisac = ImageIO.getImageWritersByFormatName("jpeg").next();
                ImageWriteParam param = pisac.getDefaultWriteParam();
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.9f);
                try (ImageOutputStream ios = ImageIO.createImageOutputStream(izlaz)) {
                    pisac.setOutput(ios);
                    // metadata = null -> zapisuje se samo osnovno JFIF zaglavlje, bez EXIF-a
                    pisac.write(null, new IIOImage(slika, null, null), param);
                } finally {
                    pisac.dispose();
                }
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Obrada slike nije uspjela", e);
        }
        return izlaz.toByteArray();
    }

    /**
     * Okreće sliku prema EXIF oznaci orijentacije (1 = normalno, 6 = zakrenuto 90° u smjeru kazaljke, ...).
     * AffineTransform(m00, m10, m01, m11, m02, m12): x' = m00*x + m01*y + m02, y' = m10*x + m11*y + m12.
     */
    static BufferedImage ispraviOrijentaciju(BufferedImage src, int orijentacija) {
        if (orijentacija < 2 || orijentacija > 8) {
            return src;
        }
        int w = src.getWidth();
        int h = src.getHeight();

        AffineTransform t = switch (orijentacija) {
            case 2 -> new AffineTransform(-1, 0, 0, 1, w, 0);   // zrcaljenje vodoravno
            case 3 -> new AffineTransform(-1, 0, 0, -1, w, h);  // 180°
            case 4 -> new AffineTransform(1, 0, 0, -1, 0, h);   // zrcaljenje okomito
            case 5 -> new AffineTransform(0, 1, 1, 0, 0, 0);    // transpozicija
            case 6 -> new AffineTransform(0, 1, -1, 0, h, 0);   // 90° u smjeru kazaljke
            case 7 -> new AffineTransform(0, -1, -1, 0, h, w);  // transverzala
            default -> new AffineTransform(0, -1, 1, 0, 0, w);  // 8: 90° suprotno od kazaljke
        };

        boolean zamjenaDimenzija = orijentacija >= 5;
        int novaSirina = zamjenaDimenzija ? h : w;
        int novaVisina = zamjenaDimenzija ? w : h;
        int tip = src.getColorModel().hasAlpha() ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;

        BufferedImage dst = new BufferedImage(novaSirina, novaVisina, tip);
        Graphics2D g = dst.createGraphics();
        try {
            g.drawImage(src, t, null);
        } finally {
            g.dispose();
        }
        return dst;
    }

    private static ResponseStatusException neispravna() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datoteka nije ispravna slika");
    }
}
