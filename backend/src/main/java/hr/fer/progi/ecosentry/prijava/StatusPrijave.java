package hr.fer.progi.ecosentry.prijava;

/**
 * Status prijave otpada (CHECK u tablici PRIJAVA).
 * Boje na karti: PRIJAVLJENO = crveno, AKCIJA_U_TIJEKU / SLUZBENA_SANACIJA = žuto, OCISCENO = zeleno.
 */
public enum StatusPrijave {
    PRIJAVLJENO,
    AKCIJA_U_TIJEKU,
    OCISCENO,
    SLUZBENA_SANACIJA,
    ODBACENO
}
