package hu.progmatic.kozos.etterem.szamla;

import hu.progmatic.kozos.etterem.rendeles.RendelesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import hu.progmatic.kozos.etterem.asztal.Asztal;
import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.rendeles.Rendeles;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SzamlaService {
    @Autowired
    private SzamlaRepository szamlaRepository;
    @Autowired
    private AsztalService asztalService;

    public Szamla create(Szamla szamla) {
        return szamlaRepository.save(szamla);
    }

    public void delete(Integer id) {
        szamlaRepository.deleteById(id);
    }

    public SzamlaDto createSzamlaForAsztal(Integer asztalId) {
        Asztal asztal = asztalService.getById(asztalId);
        Szamla szamla = Szamla.builder()
                .asztal(asztal)
                .build();
        asztal.setSzamla(szamla);
        szamlaRepository.save(szamla);
        return szamlaDtoBuilder(szamla);
    }

    public Szamla findSzamlaByAsztalId(Integer asztalId){
        return szamlaRepository.findByAsztal_Id(asztalId);
    }

    public SzamlaDto szamlaDtoBuilder(Szamla szamla) {
        return SzamlaDto.builder()
                .id(szamla.getId())
                .asztalDto(asztalService.buildAsztalDto(szamla.getAsztal()))
                .vegosszeg(getVegosszeg(szamla))
                .build();
    }

    private Integer getVegosszeg(Szamla szamla) {
        return szamla.getAsztal().getRendelesek().stream()
            .mapToInt(rendeles -> rendeles.getMennyiseg() * rendeles.getEtteremTermek().getAr())
            .sum();
    }
}
