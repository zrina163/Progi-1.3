package hr.fer.progi.ecosentry.datoteke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PohranaSlikaTest {

    @TempDir
    Path dir;

    PohranaSlika pohrana;

    @BeforeEach
    void setUp() throws IOException {
        pohrana = new PohranaSlika(dir.toString());
    }

    @Test
    void odbijaTekstPrerusenUSliku() {
        var lazna = new MockMultipartFile("fotografija", "otpad.jpg", "image/jpeg",
                "ovo nije slika".getBytes(StandardCharsets.UTF_8));

        var e = assertThrows(ResponseStatusException.class, () -> pohrana.spremi(lazna));
        assertEquals(415, e.getStatusCode().value());
    }

    @Test
    void odbijaWebp() {
        byte[] webp = "RIFF\0\0\0\0WEBPVP8 ".getBytes(StandardCharsets.ISO_8859_1);
        var f = new MockMultipartFile("fotografija", "otpad.webp", "image/webp", webp);

        var e = assertThrows(ResponseStatusException.class, () -> pohrana.spremi(f));
        assertEquals(415, e.getStatusCode().value());
    }

    @Test
    void odbijaJpegZaglavljeSaSmecem() {
        byte[] smece = {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, 1, 2, 3, 4, 5};
        var f = new MockMultipartFile("fotografija", "otpad.jpg", "image/jpeg", smece);

        var e = assertThrows(ResponseStatusException.class, () -> pohrana.spremi(f));
        assertEquals(400, e.getStatusCode().value());
    }

    @Test
    void spremaPng() throws IOException {
        var f = new MockMultipartFile("fotografija", "otpad.png", "image/png", kodiraj(slika(30, 10), "png"));

        String url = pohrana.spremi(f);

        assertTrue(url.startsWith(PohranaSlika.URL_PREFIKS));
        assertTrue(url.endsWith(".png"));
        BufferedImage spremljena = ImageIO.read(new ByteArrayInputStream(procitajSpremljenu(url)));
        assertEquals(30, spremljena.getWidth());
    }

    @Test
    void uklanjaExifIOkreceSlikuPremaOrijentaciji() throws IOException {
        // 40x20 slika s EXIF orijentacijom 6 (mobitel držan uspravno)
        byte[] jpeg = umetniExif(kodiraj(slika(40, 20), "jpg"), 6);
        assertTrue(sadrzi(jpeg, "TajniGPS"), "test podaci moraju imati EXIF");

        String url = pohrana.spremi(new MockMultipartFile("fotografija", "otpad.jpg", "image/jpeg", jpeg));
        byte[] spremljeno = procitajSpremljenu(url);

        assertFalse(sadrzi(spremljeno, "Exif"), "EXIF segment mora biti uklonjen");
        assertFalse(sadrzi(spremljeno, "TajniGPS"), "metapodaci ne smiju ostati u datoteci");

        BufferedImage s = ImageIO.read(new ByteArrayInputStream(spremljeno));
        assertEquals(20, s.getWidth(), "slika mora biti okrenuta (širina i visina zamijenjene)");
        assertEquals(40, s.getHeight());
    }

    // ---------- pomoćne metode ----------

    private byte[] procitajSpremljenu(String url) throws IOException {
        return Files.readAllBytes(dir.resolve(url.substring(PohranaSlika.URL_PREFIKS.length())));
    }

    private static BufferedImage slika(int w, int h) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.RED);
        g.fillRect(0, 0, w / 2, h);
        g.setColor(Color.BLUE);
        g.fillRect(w / 2, 0, w - w / 2, h);
        g.dispose();
        return img;
    }

    private static byte[] kodiraj(BufferedImage img, String format) throws IOException {
        var out = new ByteArrayOutputStream();
        ImageIO.write(img, format, out);
        return out.toByteArray();
    }

    /** Umeće APP1 (EXIF) segment s oznakom orijentacije i lažnim "GPS" tekstom odmah iza SOI. */
    static byte[] umetniExif(byte[] jpeg, int orijentacija) throws IOException {
        var tiff = new ByteArrayOutputStream();
        tiff.write(new byte[]{'M', 'M', 0, 42, 0, 0, 0, 8});        // big-endian, IFD0 na pomaku 8
        tiff.write(new byte[]{0, 1});                                  // 1 oznaka
        tiff.write(new byte[]{0x01, 0x12, 0, 3, 0, 0, 0, 1, 0, (byte) orijentacija, 0, 0});
        tiff.write(new byte[]{0, 0, 0, 0});                            // nema sljedećeg IFD-a
        tiff.write("TajniGPS".getBytes(StandardCharsets.US_ASCII));

        byte[] podaci = tiff.toByteArray();
        int duljina = 2 + 6 + podaci.length;

        var out = new ByteArrayOutputStream();
        out.write(jpeg, 0, 2);                                         // SOI
        out.write(new byte[]{(byte) 0xFF, (byte) 0xE1, (byte) (duljina >> 8), (byte) duljina});
        out.write(new byte[]{'E', 'x', 'i', 'f', 0, 0});
        out.write(podaci);
        out.write(jpeg, 2, jpeg.length - 2);
        return out.toByteArray();
    }

    private static boolean sadrzi(byte[] b, String tekst) {
        return new String(b, StandardCharsets.ISO_8859_1).contains(tekst);
    }
}
