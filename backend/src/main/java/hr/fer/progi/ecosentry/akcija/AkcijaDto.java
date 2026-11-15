package hr.fer.progi.ecosentry.akcija;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public final class AkcijaDto {
    private AkcijaDto() {}

    public record NovaAkcija(
            @NotEmpty List<Integer> idPrijave,
            @NotBlank @Size(max = 200) String naziv,
            @Size(max = 400) String opis,
            @NotNull @Positive Integer maxVolontera,
            @NotNull @Future Instant pocetak,
            @NotNull @DecimalMin("-90") @DecimalMax("90")BigDecimal sastanakSirina,
            @NotNull @DecimalMin("-180") @DecimalMax("180")BigDecimal sastanakDuzina,
            @Size(max = 400) String sastanakAdresa
            ){}

    public record Detalji(
            Integer idAkcija,
            String naziv,
            String opis,
            Integer maxVolontera,
            Instant pocetak,
            BigDecimal sastanakSirina,
            BigDecimal sastanakDuzina,
            String sastanakAdresa,
            StatusAkcije status,
            String nazivUdruge,
            List<Integer> idPrijave
    ){
        public static Detalji od(AkcijaCiscenja a, List<Integer> idPrijave){
            return new Detalji(
                    a.getIdAkcija(),
                    a.getNaziv(),
                    a.getOpis(),
                    a.getMaxVolontera(),
                    a.getPocetak(),
                    a.getSastanakSirina(),
                    a.getSastanakDuzina(),
                    a.getSastanakAdresa(),
                    a.getStatus(),
                    a.getUdruga().getNaziv(),
                    idPrijave
            );
        }
    }


}
