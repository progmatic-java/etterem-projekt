package hu.progmatic.kozos.etterem.leltar;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtteremTermekRepository extends JpaRepository<EtteremTermek, Integer> {
    List<EtteremTermek> findAllByTipus(Tipus tipus);
    EtteremTermek findAllByNev(String nev);
}
