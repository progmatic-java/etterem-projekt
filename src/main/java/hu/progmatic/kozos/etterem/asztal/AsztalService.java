package hu.progmatic.kozos.etterem.asztal;

import hu.progmatic.kozos.etterem.leltar.EtteremTermekDto;
import hu.progmatic.kozos.etterem.leltar.Tipus;
import hu.progmatic.kozos.etterem.rendeles.Rendeles;
import hu.progmatic.kozos.etterem.rendeles.RendelesDto;
import hu.progmatic.kozos.etterem.rendeles.RendelesRepository;
import hu.progmatic.kozos.etterem.termekfooldal.AsztalFeluletTipus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class AsztalService implements InitializingBean {
  public final List<Asztal> asztalok = List.of(
      Asztal.builder().nev("1. asztal").build(),
      Asztal.builder().nev("2. asztal").build(),
      Asztal.builder().nev("3. asztal").build(),
      Asztal.builder().nev("4. asztal").build(),
      Asztal.builder().nev("5. asztal").build(),
      Asztal.builder().nev("6. asztal").build(),
      Asztal.builder().nev("7. asztal").build(),
      Asztal.builder().nev("8. asztal").build(),
      Asztal.builder().nev("9. asztal").build()
  );

  @Autowired
  private AsztalRepository asztalRepository;

  public void createAsztal(String nev) {
    asztalRepository.save(Asztal.builder()
        .nev(nev)
        .build());
  }

  public Asztal getById(Integer id) {
    return asztalRepository.getById(id);
  }

  public void delete(Integer id) {
    asztalRepository.deleteById(id);
  }

  @Override
  public void afterPropertiesSet() {
    if (asztalRepository.count() == 0) {
      asztalRepository.saveAll(asztalok);
    }
  }

  public Integer getIdByNev(String asztalNev) {
    return asztalRepository.getAsztalByNevEquals(asztalNev).getId();
  }

  public Integer getIdByAsztalSzam(Integer asztalSzam) {
    String asztalNev = asztalSzam + ". asztal";
    return getIdByNev(asztalNev);
  }

  public AsztalDto buildAsztalDto(Asztal asztal) {
    return AsztalDto.builder()
        .id(asztal.getId())
        .nev(asztal.getNev())
        .rendelesDtoLista(asztal.getRendelesek().stream()
            .map(rendeles -> RendelesDto.builder()
                .id(rendeles.getId())
                .mennyiseg(rendeles.getMennyiseg())
                .etteremTermekDto(EtteremTermekDto.builder()
                    .id(rendeles.getEtteremTermek().getId())
                    .nev(rendeles.getEtteremTermek().getNev())
                    .ar(rendeles.getEtteremTermek().getAr())
                    .build())
                .build())
            .toList())
        .build();
  }

  public TableViewDto getTableViewDto(Integer asztalId, Tipus tipus) {
    Asztal asztal = asztalRepository.getById(asztalId);
    List<TableViewDto.RendelesDto> rendelesek = getRendelesek(asztal);
    return TableViewDto.builder()
        .id(asztal.getId())
        .nev(asztal.getNev())
        .rendelesDtoList(rendelesek)
        .termekTipus(tipus)
        .build();
  }

  public TableViewDto getTableViewDto(Integer asztalId, AsztalFeluletTipus asztalFeluletTipus) {
    Asztal asztal = asztalRepository.getById(asztalId);
    List<TableViewDto.RendelesDto> rendelesek = getRendelesek(asztal);
    return TableViewDto.builder()
        .id(asztal.getId())
        .nev(asztal.getNev())
        .rendelesDtoList(rendelesek)
        .asztalFeluletTipus(asztalFeluletTipus)
        .build();
  }

  public TableViewDto getTableViewDto(Integer asztalId, Tipus tipus, AsztalFeluletTipus asztalFeluletTipus) {
    Asztal asztal = asztalRepository.getById(asztalId);
    List<TableViewDto.RendelesDto> rendelesek = getRendelesek(asztal);
    return TableViewDto.builder()
        .id(asztal.getId())
        .nev(asztal.getNev())
        .rendelesDtoList(rendelesek)
        .termekTipus(tipus)
        .asztalFeluletTipus(asztalFeluletTipus)
        .build();
  }

  private List<TableViewDto.RendelesDto> getRendelesek(Asztal asztal) {
    return asztal.getRendelesek()
        .stream()
        .map(
            rendeles -> TableViewDto.RendelesDto.builder()
                .mennyiseg(rendeles.getMennyiseg())
                .rendelesId(rendeles.getId())
                .etteremTermekNev(rendeles.getEtteremTermek().getNev())
                .build()
        )
        .toList();
  }

}
