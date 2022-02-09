package hu.progmatic.kozos.etterem.rendeles;

import hu.progmatic.kozos.etterem.asztal.Asztal;
import hu.progmatic.kozos.etterem.asztal.AsztalRepository;
import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.leltar.EtteremTermek;
import hu.progmatic.kozos.etterem.leltar.EtteremTermekDto;
import hu.progmatic.kozos.etterem.leltar.EtteremTermekService;
import hu.progmatic.kozos.etterem.szamla.SzamlaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
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
  private EtteremTermekService etteremTermekService;
  @Autowired
  private AsztalRepository asztalRepository;

  public Rendeles create(@Valid CreateRendelesCommand command) {
    Asztal asztal = asztalService.getById(command.getAsztalId());
    EtteremTermek etteremTermek = etteremTermekService.getById(command.getEtteremTermekId());
    Rendeles rendeles = Rendeles.builder()
        .asztal(asztal)
        .etteremTermek(etteremTermek)
        .mennyiseg(command.getMennyiseg())
        .build();
    asztal.getRendelesek().add(rendeles);
      szamlaService.createSzamlaForAsztal(asztal.getId());
    return rendelesRepository.save(rendeles);
  }

  public void delete(Integer rendelesId) {
    rendelesRepository.delete(rendelesRepository.getById(rendelesId));
  }

  public List<EtteremTermekDto> findAll() {
    List<Rendeles> rendelesek = rendelesRepository.findAll();
    List<EtteremTermekDto> etteremTermekDtoList = new ArrayList<>();
    for (Rendeles rendeles : rendelesek) {
      etteremTermekDtoList.add(
          EtteremTermekDto.builder()
              .id(rendeles.getId())
              .nev(rendeles.getEtteremTermek().getNev())
              .ar(rendeles.getEtteremTermek().getAr())
              .build()
      );
    }
    return etteremTermekDtoList;
  }

  public void mennyisegNovelese(Integer asztalId, String termekNeve) {
    Asztal asztal = asztalRepository.getById(asztalId);
    Rendeles aktualisRendeles = asztal.getRendelesek().stream()
        .filter(rendeles -> rendeles.getEtteremTermek().getNev().equals(termekNeve))
        .findFirst()
        .orElseThrow();
    aktualisRendeles.setMennyiseg(aktualisRendeles.getMennyiseg() + 1);
  }

  public void mennyisegNovelese(Integer asztalId, Integer termekId) {
    Asztal asztal = asztalRepository.getById(asztalId);
    Rendeles aktualisRendeles = asztal.getRendelesek().stream()
        .filter(rendeles -> Objects.equals(rendeles.getEtteremTermek().getId(), termekId))
        .findFirst()
        .orElseThrow();
    aktualisRendeles.setMennyiseg(aktualisRendeles.getMennyiseg() + 1);
  }

  public void mennyisegCsokkentese(Integer asztalId, String termekNeve) {
    Asztal asztal = asztalRepository.getById(asztalId);
    Rendeles aktualisRendeles = asztal.getRendelesek().stream()
        .filter(rendeles -> rendeles.getEtteremTermek().getNev().equals(termekNeve))
        .findFirst()
        .orElseThrow();
    aktualisRendeles.setMennyiseg(aktualisRendeles.getMennyiseg() - 1);
    if (aktualisRendeles.getMennyiseg() == 0) {
      asztal.getRendelesek().remove(aktualisRendeles);
      rendelesRepository.delete(aktualisRendeles);
    }
  }

  public boolean rendelesTartalmazzaATermeket(CreateRendelesCommand command) {
    Asztal asztal = asztalRepository.getById(command.getAsztalId());
    EtteremTermek termek = etteremTermekService.getById(command.getEtteremTermekId());
    return asztal.getRendelesek().stream()
        .map(rendeles -> rendeles.getEtteremTermek().getNev())
        .toList().contains(termek.getNev());
  }

  public List<Rendeles> findAllByAsztal(Asztal asztal) {
    return rendelesRepository.findAllByAsztal(asztal);
  }

}
