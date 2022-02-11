package hu.progmatic.kozos.etterem.asztal;

import hu.progmatic.kozos.etterem.leltar.TermekDto;
import hu.progmatic.kozos.etterem.leltar.Tipus;
import hu.progmatic.kozos.etterem.rendeles.RendelesDto;
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
      Asztal.builder().nev("1. ASZTAL").build(),
      Asztal.builder().nev("2. ASZTAL").build(),
      Asztal.builder().nev("3. ASZTAL").build(),
      Asztal.builder().nev("4. ASZTAL").build(),
      Asztal.builder().nev("5. ASZTAL").build(),
      Asztal.builder().nev("6. ASZTAL").build(),
      Asztal.builder().nev("7. ASZTAL").build(),
      Asztal.builder().nev("8. ASZTAL").build(),
      Asztal.builder().nev("9. ASZTAL").build()
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
    String asztalNev = asztalSzam + ". ASZTAL";
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
                .termekDto(TermekDto.builder()
                    .id(rendeles.getTermek().getId())
                    .nev(rendeles.getTermek().getNev())
                    .ar(rendeles.getTermek().getAr())
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
                .etteremTermekNev(rendeles.getTermek().getNev())
                .build()
        )
        .toList();
  }

  public AsztalDto getAsztalDtoById(Integer id) {
    return buildAsztalDto(asztalRepository.getById(id));
  }
}
