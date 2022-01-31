package java.hu.progmatic.kozos.etterem.biztonsag;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EtteremFelhasznaloAdataimService implements UserDetailsService {
    private final EtteremFelhasznaloRepository felhasznaloRepository;

    public EtteremFelhasznaloAdataimService(EtteremFelhasznaloRepository felhasznaloRepository) {
        this.felhasznaloRepository = felhasznaloRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String felhasznaloNev) {
        Optional<EtteremFelhasznalo> felhasznalo = felhasznaloRepository.findByNev(felhasznaloNev);
        if(felhasznalo.isEmpty()){
            throw new UsernameNotFoundException(felhasznaloNev);
        }
        return new EtteremFelhasznaloAdataim(felhasznalo.get());
    }
}