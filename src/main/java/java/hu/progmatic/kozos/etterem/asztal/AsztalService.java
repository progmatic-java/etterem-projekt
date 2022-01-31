package java.hu.progmatic.kozos.etterem.asztal;

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

  public Asztal save(Asztal table) {
    return asztalRepository.saveAndFlush(table);
  }

  public Asztal getById(Integer id) {
    return asztalRepository.getById(id);
  }

  public void delete(Integer id) {
    asztalRepository.deleteById(id);
  }

  public void saveAll(List<Asztal> tableList) {
    asztalRepository.saveAllAndFlush(tableList);
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

  public TableViewDto getTableViewDto(Integer asztalId) {
    Asztal asztal = asztalRepository.getById(asztalId);
    List<TableViewDto.RendelesDto> rendelesek = asztal.getRendelesek()
        .stream()
        .map(
            rendeles -> TableViewDto.RendelesDto.builder()
                .mennyiseg(rendeles.getMennyiseg())
                .rendelesId(rendeles.getId())
                .etteremTermekNev(rendeles.getEtteremTermek().getNev())
                .build()
        )
        .toList();

    return TableViewDto.builder()
        .id(asztal.getId())
        .nev(asztal.getNev())
        .rendelesDtoList(rendelesek)
        .build();
  }
}
