package hr.fer.progi.ecosentry.sanacija;

import java.time.Instant;

public final class SanacijaDto {
    private SanacijaDto(){}

    public record Potvrda(
            Integer idPotvrda,
            Integer idPrijava,
            String urlFotografije,
            StatusPotvrde status,
            Instant datumZahtjeva
    ){
        public static Potvrda od(PotvrdaSanacije ps){
            return  new Potvrda(
                    ps.getIdPotvrda(),
                    ps.getPrijava().getIdPrijava(),
                    ps.getFotografija().getUrl(),
                    ps.getStatus(),
                    ps.getDatumZahtjeva()
            );
        }
    }

}
