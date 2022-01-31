package java.hu.progmatic.kozos.etterem.szamla;

import hu.progmatic.kozos.etterem.asztal.Asztal;
import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.rendeles.Rendeles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SzamlaService {

    @Autowired
    private SzamlaRepository szamlaRepository;
    @Autowired
    private AsztalService asztalService;

    public Szamla create(Szamla szamla){
        return szamlaRepository.save(szamla);
    }
    public Szamla getById(Integer id){
        return szamlaRepository.getById(id);
    }
    public void delete(Integer id){
        szamlaRepository.deleteById(id);
    }
    public void saveAll(List<Szamla> szamlaList){
        szamlaRepository.saveAllAndFlush(szamlaList);
    }

    public Szamla createSzamlaForAsztal(Integer asztalId){
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

}
