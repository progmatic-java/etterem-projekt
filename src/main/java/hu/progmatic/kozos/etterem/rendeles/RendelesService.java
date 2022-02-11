package hu.progmatic.kozos.etterem.rendeles;

import hu.progmatic.kozos.etterem.asztal.Asztal;
import hu.progmatic.kozos.etterem.asztal.AsztalRepository;
import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.leltar.Termek;
import hu.progmatic.kozos.etterem.leltar.TermekService;
import hu.progmatic.kozos.etterem.szamla.SzamlaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class RendelesService {
    @Autowired
    private SzamlaService szamlaService;
    @Autowired
    private RendelesRepository rendelesRepository;
    @Autowired
    private AsztalService asztalService;
    @Autowired
    private TermekService termekService;
    @Autowired
    private AsztalRepository asztalRepository;

    public Rendeles create(@Valid CreateRendelesCommand command) {
        Asztal asztal = asztalService.getById(command.getAsztalId());
        Termek termek = termekService.getById(command.getEtteremTermekId());
        Rendeles rendeles = Rendeles.builder()
                .asztal(asztal)
                .termek(termek)
                .mennyiseg(command.getMennyiseg())
                .build();
        asztal.getRendelesek().add(rendeles);
        szamlaService.createSzamlaForAsztal(asztal.getId());
        return rendelesRepository.save(rendeles);
    }


    public void mennyisegNovelese(Integer asztalId, String termekNeve) {
        Asztal asztal = asztalRepository.getById(asztalId);
        Rendeles aktualisRendeles = asztal.getRendelesek().stream()
                .filter(rendeles -> rendeles.getTermek().getNev().equals(termekNeve))
                .findFirst()
                .orElseThrow();
        aktualisRendeles.setMennyiseg(aktualisRendeles.getMennyiseg() + 1);
        szamlaService.createSzamlaForAsztal(asztalId);
    }

    public void mennyisegNovelese(Integer asztalId, Integer termekId) {
        Asztal asztal = asztalRepository.getById(asztalId);
        Rendeles aktualisRendeles = asztal.getRendelesek().stream()
                .filter(rendeles -> Objects.equals(rendeles.getTermek().getId(), termekId))
                .findFirst()
                .orElseThrow();
        aktualisRendeles.setMennyiseg(aktualisRendeles.getMennyiseg() + 1);
        szamlaService.createSzamlaForAsztal(asztalId);
    }

    public void mennyisegCsokkentese(Integer asztalId, String termekNeve) {
        Asztal asztal = asztalRepository.getById(asztalId);
        Rendeles aktualisRendeles = asztal.getRendelesek().stream()
                .filter(rendeles -> rendeles.getTermek().getNev().equals(termekNeve))
                .findFirst()
                .orElseThrow();
        aktualisRendeles.setMennyiseg(aktualisRendeles.getMennyiseg() - 1);
        if (aktualisRendeles.getMennyiseg() == 0) {
            szamlaService.szamlaTetelEltavolitasa(asztalId, aktualisRendeles);
            asztal.getRendelesek().remove(aktualisRendeles);
            rendelesRepository.delete(aktualisRendeles);
        }
        szamlaService.createSzamlaForAsztal(asztalId);
    }

    public boolean rendelesTartalmazzaATermeket(CreateRendelesCommand command) {
        Asztal asztal = asztalRepository.getById(command.getAsztalId());
        Termek termek = termekService.getById(command.getEtteremTermekId());
        return asztal.getRendelesek().stream()
                .map(rendeles -> rendeles.getTermek().getNev())
                .toList().contains(termek.getNev());
    }

    public List<Rendeles> findAllByAsztal(Asztal asztal) {
        return rendelesRepository.findAllByAsztal(asztal);
    }

}
