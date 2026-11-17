package hr.fer.progi.ecosentry.akcija;

import hr.fer.progi.ecosentry.korisnik.Korisnik;
import hr.fer.progi.ecosentry.prijava.Prijava;
import hr.fer.progi.ecosentry.prijava.PrijavaService;
import hr.fer.progi.ecosentry.prijava.StatusPrijave;
import hr.fer.progi.ecosentry.udruga.Udruga;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class AkcijaService {
    private final AkcijaCiscenjaRepository akcije;
    private final PrijavaService prijavaService;

    public AkcijaService(AkcijaCiscenjaRepository akcije, PrijavaService prijavaService) {
        this.akcije = akcije;
        this.prijavaService = prijavaService;
    }


    @Transactional
    public AkcijaDto.Detalji kreiraj(AkcijaDto.NovaAkcija z, Udruga udruga, Korisnik k){
        if (new HashSet<>(z.idPrijave()).size() != z.idPrijave().size()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ista prijava je navedena više puta");
        }
        List<Prijava> odabrane = new ArrayList<>();
        for (Integer id: z.idPrijave().stream().sorted().toList()){
            Prijava p = prijavaService.nadjiZaAzuriranje(id);
            if(p.getStatus() != StatusPrijave.PRIJAVLJENO){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Prijava #" + id + " nije u statusu Prijavljeno");
            }
            if(!udruga.getRegija().equalsIgnoreCase(p.getGrad())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Prijava #" + id + " nije u regiji vaše udruge");
            }
            odabrane.add(p);
        }

        AkcijaCiscenja akcija = new AkcijaCiscenja();
        akcija.setUdruga(udruga);
        akcija.setNaziv(z.naziv());
        akcija.setMaxVolontera(z.maxVolontera());
        akcija.setPocetak(z.pocetak());
        akcija.setSastanakAdresa(z.sastanakAdresa());
        akcija.setSastanakDuzina(z.sastanakDuzina());
        akcija.setSastanakSirina((z.sastanakSirina()));
        akcija.setOpis(z.opis());
        akcije.save(akcija);

        for (Prijava prijava: odabrane){
            prijava.setIdAkcija(akcija.getIdAkcija());
            prijavaService.promijeniStatus(prijava, StatusPrijave.AKCIJA_U_TIJEKU, k, "Uvrštena u akciju: " + akcija.getNaziv());
        }

        return AkcijaDto.Detalji.od(akcija, z.idPrijave());

    }

}
