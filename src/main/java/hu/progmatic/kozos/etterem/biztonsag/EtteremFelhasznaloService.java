package hu.progmatic.kozos.etterem.biztonsag;

import lombok.extern.java.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Log
@Transactional
public class EtteremFelhasznaloService implements InitializingBean {

    private final EtteremFelhasznaloRepository etteremFelhasznaloRepository;
    private final PasswordEncoder encoder;

    public EtteremFelhasznaloService(EtteremFelhasznaloRepository etteremFelhasznaloRepository, PasswordEncoder encoder) {
        this.etteremFelhasznaloRepository = etteremFelhasznaloRepository;
        this.encoder = encoder;
    }

    @RolesAllowed(EtteremFelhasznaloTipus.Jogosultsag.USER_READ_ROLE)
    public List<EtteremFelhasznalo> findAll() {
        return etteremFelhasznaloRepository.findAll();
    }

    @RolesAllowed(EtteremFelhasznaloTipus.Jogosultsag.USER_WRITE_ROLE)
    public void add(EtteremUjFelhasznaloCommand command){
        if(etteremFelhasznaloRepository.findByNev(command.getNev()).isPresent()){
            throw new EtteremFelhasznaloLetrehozasaException("Ilyen névvel már létezik felhasználó!");
        }
        EtteremFelhasznalo felhasznalo = EtteremFelhasznalo.builder()
                .nev(command.getNev())
                .jelszo(encoder.encode(command.getJelszo()))
                .jogosultsag(command.getJogosultsag())
                .build();
        etteremFelhasznaloRepository.save(felhasznalo);
    }

    @RolesAllowed(EtteremFelhasznaloTipus.Jogosultsag.USER_WRITE_ROLE)
    public void delete(Long id) {
        etteremFelhasznaloRepository.deleteById(id);
    }

    @RolesAllowed(EtteremFelhasznaloTipus.Jogosultsag.USER_READ_ROLE)
    public Optional<EtteremFelhasznalo> findByName(String nev) {
        return etteremFelhasznaloRepository.findByNev(nev);
    }


    @Override
    public void afterPropertiesSet() {
        if (findAll().isEmpty()) {
            add(new EtteremUjFelhasznaloCommand("1111", "1111", EtteremFelhasznaloTipus.ADMIN));

            add(new EtteremUjFelhasznaloCommand("3333", "3333", EtteremFelhasznaloTipus.WAITER));
        }
    }

    public boolean hasJogosultsag(String jogosultsag) {
        EtteremFelhasznaloAdataim etteremFelhasznaloAdataim = getFelhasznaloAdataim();
        return etteremFelhasznaloAdataim.getJogosultsag().hasJogosultsag(jogosultsag);
    }

    public Long getFelhasznaloId() {
        EtteremFelhasznaloAdataim etteremFelhasznaloAdataim = getFelhasznaloAdataim();
        return etteremFelhasznaloAdataim.getFelhasznaloId();
    }
    private EtteremFelhasznaloAdataim getFelhasznaloAdataim() {
        return (EtteremFelhasznaloAdataim) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
