package java.hu.progmatic.kozos.etterem.leltar;

import hu.progmatic.kozos.etterem.szamla.SzamlaRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static hu.progmatic.kozos.etterem.leltar.Tipus.*;

@Service
@Transactional
public class EtteremTermekService implements InitializingBean {

  @Autowired
  SzamlaRepository szamlaRepository;

  public final List<EtteremTermek> initItems = List.of(
      new EtteremTermek(null, "Paradicsom leves", 1200, LEVES),
      new EtteremTermek(null, "Gulyás leves", 1500, LEVES),
      new EtteremTermek(null, "Tatárbeefsteak", 2200, ELOETEL),
      new EtteremTermek(null, "Cézár saláta", 2000, ELOETEL),
      new EtteremTermek(null, "Brassói aprópecsenye", 3200, SERTESETEL),
      new EtteremTermek(null, "Milánói sertésborda", 2800, SERTESETEL),
      new EtteremTermek(null, "Lazacsteak", 4200, HALETEL),
      new EtteremTermek(null, "Fish and chips", 1200, HALETEL),
      new EtteremTermek(null, "Rib-eye steak", 6200, MARHAETEL),
      new EtteremTermek(null, "Vadasmarha", 4200, MARHAETEL),
      new EtteremTermek(null, "Somlói", 1200, DESSZERT),
      new EtteremTermek(null, "Gundelpalacsinta", 1200, DESSZERT),
      new EtteremTermek(null, "Sör", 1200, ALKOHOL),
      new EtteremTermek(null, "Bor", 1200, ALKOHOL),
      new EtteremTermek(null, "Pálinka", 1800, ROVIDITAL),
      new EtteremTermek(null, "Whiskey", 2200, ROVIDITAL),
      new EtteremTermek(null, "Titanic", 600, KOKTEL),
      new EtteremTermek(null, "Mojito", 3500, KOKTEL),
      new EtteremTermek(null, "Fanta", 1200, UDITO),
      new EtteremTermek(null, "Kóla", 1200, UDITO),
      new EtteremTermek(null, "Tea", 1200, FORROITAL),
      new EtteremTermek(null, "Forró csokoládé", 1200, FORROITAL),
      new EtteremTermek(null, "Cappuccino", 1200, KAVE),
      new EtteremTermek(null, "Espresso", 1200, KAVE)
  );

  private final EtteremTermekRepository etteremTermekRepository;

  public EtteremTermekDto getDto(Integer id) {
    EtteremTermek entity = etteremTermekRepository.getById(id);
    return EtteremTermekDto.builder()
        .id(entity.getId())
        .nev(entity.getNev())
        .ar(entity.getAr())
        .build();
  }

  public EtteremTermekService(EtteremTermekRepository etteremTermekRepository) {
    this.etteremTermekRepository = etteremTermekRepository;
  }

  public List<EtteremTermek> findAll() {
    return etteremTermekRepository.findAll();
  }

  public EtteremTermek create(EtteremTermek item) {
    item.setId(null);
    return etteremTermekRepository.saveAndFlush(item);
  }

  public EtteremTermek save(EtteremTermek item) {
    return etteremTermekRepository.saveAndFlush(item);
  }

  public void deleteById(Integer id) {
    etteremTermekRepository.deleteById(id);
  }

  public EtteremTermek getById(Integer id) {
    return etteremTermekRepository.getById(id);
  }

  public EtteremTermek getByName(String name){
    return etteremTermekRepository.findAllByNev(name);
  }

  public List<EtteremTermek> findAllByTipus(Tipus tipus) {
    return etteremTermekRepository.findAllByTipus(tipus);
  }

  public List<EtteremTermekDto> findAllByTipusDto(Tipus tipus) {
    List<EtteremTermek> allTermekByTipus = findAllByTipus(tipus);
    return allTermekByTipus.stream()
        .map(item -> getDto(item.getId()))
        .collect(Collectors.toList());
  }

  public EtteremTermek empty() {
    return new EtteremTermek();
  }

  @Override
  public void afterPropertiesSet() {
    if (etteremTermekRepository.count() == 0) {
      etteremTermekRepository.saveAll(initItems);
    }
  }

  public boolean isExists(Integer id) {
    return etteremTermekRepository.existsById(id);
  }

  public void update(Integer id, String ujNev, Integer ujAr, Tipus ujTipus) {
    EtteremTermek entity = etteremTermekRepository.getById(id);
    entity.setNev(ujNev);
    entity.setAr(ujAr);
    entity.setTipus(ujTipus);
  }
}
