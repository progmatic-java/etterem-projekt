package hu.progmatic.kozos.etterem.szamla;

import hu.progmatic.kozos.etterem.rendeles.RendelesDto;
import hu.progmatic.kozos.etterem.rendeles.RendelesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import hu.progmatic.kozos.etterem.asztal.Asztal;
import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.rendeles.Rendeles;

import java.util.List;

@Service
@Transactional
public class SzamlaService {

    @Autowired
    private RendelesService rendelesService;
    @Autowired
    private SzamlaRepository szamlaRepository;
    @Autowired
    private AsztalService asztalService;

    public Szamla create(Szamla szamla) {
        return szamlaRepository.save(szamla);
    }

    public Szamla getById(Integer id) {
        return szamlaRepository.getById(id);
    }

    public void delete(Integer id) {
        szamlaRepository.deleteById(id);
    }

    public void saveAll(List<Szamla> szamlaList) {
        szamlaRepository.saveAllAndFlush(szamlaList);
    }

    public Szamla createSzamlaForAsztal(Integer asztalId) {
        Asztal asztal = asztalService.getById(asztalId);
        Szamla szamla = Szamla.builder()
                .asztal(asztal)
                .build();
        asztal.setSzamla(szamla);
        return szamlaRepository.save(szamla);
    }

    public void update(Integer id, List<Rendeles> etteremTermekek, Asztal asztal) {
        Szamla entity = szamlaRepository.getById(id);
        entity.setEtteremTermekek(etteremTermekek);
        entity.setAsztal(asztal);
    }
    public Szamla findSzamlaByAsztalId(Integer asztalId){
        return szamlaRepository.findByAsztal_Id(asztalId);
    }

    public SzamlaDto szamlaDtoBuilder(Szamla szamla) {
        return SzamlaDto.builder()
                .id(szamla.getId())
                .asztalId(szamla.getAsztal().getId())
                .rendelesek(rendelesService.rendelesDtoList(szamla.getAsztal().getRendelesek()))
                .build();
    }

    public Double getVegosszeg(SzamlaDto dto){
        Double osszeg=0.0;
        List<RendelesDto> rendelesek= dto.getRendelesek();
        for(RendelesDto rendeles: rendelesek){
            osszeg+=rendeles.getTermekAr()*rendeles.getMennyiseg()*1.15;
        }
        return osszeg;
    }

}
