package hu.progmatic.kozos.etterem.biztonsag;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class EtteremFelhasznaloAdataim implements UserDetails {
    private static final String ROLE_PREFIX = "ROLE_";

    private final Long felhasznaloId;
    private final String jelszo;
    private final String nev;
    private final EtteremFelhasznaloTipus jogosultsag;

    public EtteremFelhasznaloAdataim(EtteremFelhasznalo felhasznalo) {
        jelszo = felhasznalo.getJelszo();
        nev = felhasznalo.getNev();
        jogosultsag = felhasznalo.getJogosultsag();
        felhasznaloId = felhasznalo.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(jogosultsag.getJogosultsagok()).map(s -> new SimpleGrantedAuthority(ROLE_PREFIX + s)).toList();
    }

    @Override
    public String getPassword() {
        return jelszo;
    }

    @Override
    public String getUsername() {
        return nev;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public EtteremFelhasznaloTipus getJogosultsag() {
        return jogosultsag;
    }

    public Long getFelhasznaloId() {
        return felhasznaloId;
    }
}
