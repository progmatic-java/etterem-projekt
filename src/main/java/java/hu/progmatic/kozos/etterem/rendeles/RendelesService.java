package java.hu.progmatic.kozos.etterem.rendeles;

import hu.progmatic.kozos.etterem.asztal.Asztal;
import hu.progmatic.kozos.etterem.asztal.AsztalService;
import hu.progmatic.kozos.etterem.leltar.EtteremTermek;
import hu.progmatic.kozos.etterem.leltar.EtteremTermekDto;
import hu.progmatic.kozos.etterem.leltar.EtteremTermekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RendelesService {
  @Autowired
  RendelesRepository rendelesRepository;
  @Autowired
  AsztalService asztalService;
  @Autowired
  private EtteremTermekService etteremTermekService;

  public Rendeles create(@Valid CreateRendelesCommand command) {
    Asztal asztal = asztalService.getById(command.getAsztalId());
    EtteremTermek etteremTermek = etteremTermekService.getById(command.getEtteremTermekId());
    Rendeles rendeles = Rendeles.builder()
            .asztal(asztal)
            .etteremTermek(etteremTermek)
            .mennyiseg(command.getMennyiseg())
            .build();
    return rendelesRepository.save(rendeles);
  }

  public Rendeles empty() {
    return new Rendeles();
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
                  .mennyiseg(rendeles.getMennyiseg())
                  .build()
      );
    }
    return etteremTermekDtoList;
  }

  public List<Rendeles> findAllByAsztal() {
    return rendelesRepository.findAll();
  }

  public List<Rendeles> findAllByAsztal(Asztal asztal) {
    return rendelesRepository.findAllByAsztal(asztal);
  }
}
