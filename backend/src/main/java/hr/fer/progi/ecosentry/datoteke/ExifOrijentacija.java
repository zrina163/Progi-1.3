package hr.fer.progi.ecosentry.datoteke;

/**
 * Čita samo EXIF oznaku orijentacije (tag 0x0112) iz JPEG datoteke, bez vanjskih biblioteka.
 *
 * Građa JPEG-a: niz segmenata oblika FF xx [duljina 2 bajta] [podaci].
 * EXIF je u segmentu APP1 (FF E1) koji počinje s "Exif\0\0", a iza toga slijedi
 * TIFF zaglavlje ("II" = little-endian ili "MM" = big-endian) i popis oznaka (IFD0).
 *
 * Ako bilo što nije u očekivanom obliku, vraća 1 (normalna orijentacija).
 */
final class ExifOrijentacija {

    private static final int TAG_ORIJENTACIJA = 0x0112;

    private ExifOrijentacija() {}

    static int procitaj(byte[] b) {
        try {
            int i = 2; // preskoči SOI (FF D8)
            while (i + 4 <= b.length) {
                if ((b[i] & 0xFF) != 0xFF) {
                    return 1;
                }
                int marker = b[i + 1] & 0xFF;
                if (marker == 0xFF) {           // bajt za popunu
                    i++;
                    continue;
                }
                if (marker == 0xDA || marker == 0xD9) {  // počinju podaci slike / kraj
                    return 1;
                }
                int duljina = ((b[i + 2] & 0xFF) << 8) | (b[i + 3] & 0xFF);
                if (duljina < 2) {
                    return 1;
                }
                int pocetak = i + 4;
                int kraj = Math.min(i + 2 + duljina, b.length);
                if (marker == 0xE1 && jeExif(b, pocetak, kraj)) {
                    return izTiffa(b, pocetak + 6, kraj);
                }
                i += 2 + duljina;
            }
        } catch (RuntimeException e) {
            // oštećeni EXIF – ignoriramo ga
        }
        return 1;
    }

    private static boolean jeExif(byte[] b, int pocetak, int kraj) {
        byte[] potpis = {'E', 'x', 'i', 'f', 0, 0};
        if (pocetak + potpis.length > kraj) {
            return false;
        }
        for (int k = 0; k < potpis.length; k++) {
            if (b[pocetak + k] != potpis[k]) {
                return false;
            }
        }
        return true;
    }

    private static int izTiffa(byte[] b, int tiff, int kraj) {
        boolean little;
        if (b[tiff] == 'I' && b[tiff + 1] == 'I') {
            little = true;
        } else if (b[tiff] == 'M' && b[tiff + 1] == 'M') {
            little = false;
        } else {
            return 1;
        }

        long pomak = u32(b, tiff + 4, kraj, little);
        int ifd = (int) (tiff + pomak);
        int brojOznaka = u16(b, ifd, kraj, little);

        for (int k = 0; k < brojOznaka; k++) {
            int zapis = ifd + 2 + 12 * k;
            if (u16(b, zapis, kraj, little) == TAG_ORIJENTACIJA) {
                int vrijednost = u16(b, zapis + 8, kraj, little);
                return (vrijednost >= 1 && vrijednost <= 8) ? vrijednost : 1;
            }
        }
        return 1;
    }

    private static int u16(byte[] b, int p, int kraj, boolean little) {
        if (p < 0 || p + 2 > kraj) {
            throw new IndexOutOfBoundsException();
        }
        int a = b[p] & 0xFF;
        int c = b[p + 1] & 0xFF;
        return little ? (c << 8) | a : (a << 8) | c;
    }

    private static long u32(byte[] b, int p, int kraj, boolean little) {
        if (p < 0 || p + 4 > kraj) {
            throw new IndexOutOfBoundsException();
        }
        long v = 0;
        for (int k = 0; k < 4; k++) {
            int bajt = b[little ? p + 3 - k : p + k] & 0xFF;
            v = (v << 8) | bajt;
        }
        return v;
    }
}
