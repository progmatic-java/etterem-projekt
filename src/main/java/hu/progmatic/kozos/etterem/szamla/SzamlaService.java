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
    public void szamlaAdd(Szamla szamla, Rendeles rendeles){
        szamla.getAsztal().getRendelesek().add(rendeles);
    }

    public Szamla findSzamlaByAsztalId(Integer asztalId){
        return szamlaRepository.findByAsztal_Id(asztalId);
    }

    public SzamlaDto szamlaDtoBuilder(Szamla szamla) {
        return SzamlaDto.builder()
                .id(szamla.getId())
                .asztalId(szamla.getAsztal().getId())
                .szamlaTetelek(szamlaDtoList(szamla.getAsztal().getRendelesek()))
                .build();
    }

    public Integer getVegosszeg(SzamlaDto dto){
        Integer osszeg=0;
        List<SzamlaTetelDto> rendelesek= dto.getSzamlaTetelek();
        for(SzamlaTetelDto rendeles: rendelesek){
            osszeg+=rendeles.getTermekAr()*rendeles.getMennyiseg();
        }
        Integer szerviz= osszeg/100*15;
        return osszeg+szerviz;
    }

    public SzamlaTetelDto szamlaDtoBuilder(Rendeles rendeles){
        return SzamlaTetelDto.builder()
                .asztalId(rendeles.getAsztal().getId())
                .termekAr(rendeles.getEtteremTermek().getAr())
                .mennyiseg(rendeles.getMennyiseg())
                .termekNev(rendeles.getEtteremTermek().getNev())
                .build();
    }
    public List<SzamlaTetelDto> szamlaDtoList(List<Rendeles> rendelesek){
        List<SzamlaTetelDto> rendelesekDto=new ArrayList<>();
        for(Rendeles rendeles: rendelesek){
            rendelesekDto.add(szamlaDtoBuilder(rendeles));
        }
        return rendelesekDto;
    }

}
