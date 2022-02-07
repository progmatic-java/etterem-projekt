package hu.progmatic.kozos.etterem.leltar;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import hu.progmatic.kozos.etterem.szamla.SzamlaRepository;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class EtteremTermekService implements InitializingBean {

  @Autowired
  SzamlaRepository szamlaRepository;

  public final List<EtteremTermek> initItems = List.of(
      new EtteremTermek(null, "Paradicsom leves", 1200, Tipus.LEVES),
      new EtteremTermek(null, "Gulyás leves", 1500, Tipus.LEVES),
      new EtteremTermek(null, "Tatárbeefsteak", 2200, Tipus.ELOETEL),
      new EtteremTermek(null, "Cézár saláta", 2000, Tipus.ELOETEL),
      new EtteremTermek(null, "Brassói aprópecsenye", 3200, Tipus.SERTESETEL),
      new EtteremTermek(null, "Milánói sertésborda", 2800, Tipus.SERTESETEL),
      new EtteremTermek(null, "Lazacsteak", 4200, Tipus.HALETEL),
      new EtteremTermek(null, "Fish and chips", 1200, Tipus.HALETEL),
      new EtteremTermek(null, "Rib-eye steak", 6200, Tipus.MARHAETEL),
      new EtteremTermek(null, "Vadasmarha", 4200, Tipus.MARHAETEL),
      new EtteremTermek(null, "Somlói", 1200, Tipus.DESSZERT),
      new EtteremTermek(null, "Gundelpalacsinta", 1200, Tipus.DESSZERT),
      new EtteremTermek(null, "Sör", 1200, Tipus.ALKOHOL),
      new EtteremTermek(null, "Bor", 1200, Tipus.ALKOHOL),
      new EtteremTermek(null, "Pálinka", 1800, Tipus.ROVIDITAL),
      new EtteremTermek(null, "Whiskey", 2200, Tipus.ROVIDITAL),
      new EtteremTermek(null, "Titanic", 600, Tipus.KOKTEL),
      new EtteremTermek(null, "Mojito", 3500, Tipus.KOKTEL),
      new EtteremTermek(null, "Fanta", 1200, Tipus.UDITO),
      new EtteremTermek(null, "Kóla", 1200, Tipus.UDITO),
      new EtteremTermek(null, "Tea", 1200, Tipus.FORROITAL),
      new EtteremTermek(null, "Forró csokoládé", 1200, Tipus.FORROITAL),
      new EtteremTermek(null, "Cappuccino", 1200, Tipus.KAVE),
      new EtteremTermek(null, "Espresso", 1200, Tipus.KAVE)
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
