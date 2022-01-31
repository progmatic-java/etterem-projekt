package hu.progmatic.kozos.etterem.biztonsag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtteremFelhasznaloRepository extends JpaRepository<EtteremFelhasznalo, Long> {
    Optional<EtteremFelhasznalo> findByNev(String nev);
}
