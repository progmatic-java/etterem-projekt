package hu.progmatic.kozos.etterem.biztonsag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EtteremFelhasznaloServiceTest {


    @Autowired
    private EtteremFelhasznaloService etteremFelhasznaloService;

    @Test
    @DisplayName("Összes felhasználó listázása")
    @WithMockUser(roles = EtteremFelhasznaloTipus.Jogosultsag.USER_READ_ROLE)
    void findAll() {
        assertFelhasznaloLetezik("1111");
    }

    @Test
    @DisplayName("Felhasználó hozzáadása")
    @WithMockUser(roles = {EtteremFelhasznaloTipus.Jogosultsag.USER_WRITE_ROLE, EtteremFelhasznaloTipus.Jogosultsag.USER_READ_ROLE})
    void add() {
        EtteremUjFelhasznaloCommand command = new EtteremUjFelhasznaloCommand("ujtesztfelhasznalo", "x", EtteremFelhasznaloTipus.ADMIN);
        etteremFelhasznaloService.add(command);
        assertFelhasznaloLetezik("ujtesztfelhasznalo");
    }

    @Test
    @DisplayName("Felhasználó létezik hibaüzenet")
    @WithMockUser(roles = EtteremFelhasznaloTipus.Jogosultsag.USER_WRITE_ROLE)
    void felhasznaloLetezikHiba() {
        EtteremUjFelhasznaloCommand command = new EtteremUjFelhasznaloCommand("1111", "8575", EtteremFelhasznaloTipus.ADMIN);
        EtteremFelhasznaloLetrehozasaException e = null;
        try {
            etteremFelhasznaloService.add(command);
        } catch (EtteremFelhasznaloLetrehozasaException ex) {
            e = ex;
        }
        assertThat(e)
                .isNotNull()
                .extracting(EtteremFelhasznaloLetrehozasaException::getMessage)
                .isEqualTo("Ilyen névvel már létezik felhasználó!");
    }

    @Test
    @DisplayName("Felhasználó törlése")
    @WithMockUser(roles = {EtteremFelhasznaloTipus.Jogosultsag.USER_WRITE_ROLE, EtteremFelhasznaloTipus.Jogosultsag.USER_READ_ROLE})
    void torles() {
        String tesztFelhasznaloNev = "ujtesztfelhasznalotorleshez";
        EtteremUjFelhasznaloCommand command = new EtteremUjFelhasznaloCommand(tesztFelhasznaloNev, "x", EtteremFelhasznaloTipus.ADMIN);
        etteremFelhasznaloService.add(command);
        Optional<EtteremFelhasznalo> elmentett = etteremFelhasznaloService.findByName(tesztFelhasznaloNev);
        assertThat(elmentett).isPresent();
        etteremFelhasznaloService.delete(elmentett.get().getId());
        assertThat(etteremFelhasznaloService.findByName(tesztFelhasznaloNev)).isEmpty();
    }

    @Test
    @DisplayName("Felhasználó jogosultságok lekérése - manager")
    @WithUserDetails(value = "2222", userDetailsServiceBeanName = "etteremFelhasznaloAdataimService")
    void managerHasJogosultsag() {
        assertTrue(etteremFelhasznaloService.hasJogosultsag(EtteremFelhasznaloTipus.Jogosultsag.USER_READ_ROLE));
        assertTrue(etteremFelhasznaloService.hasJogosultsag(EtteremFelhasznaloTipus.Jogosultsag.USER_WRITE_ROLE));
        assertTrue(etteremFelhasznaloService.hasJogosultsag(EtteremFelhasznaloTipus.Jogosultsag.USER_DELETE_ROLE));
        assertTrue(etteremFelhasznaloService.hasJogosultsag(EtteremFelhasznaloTipus.Jogosultsag.USER_ADD_ITEM_TO_INVENTORY_ROLE));
    }

    @Test
    @DisplayName("Felhasználó jogosultságok lekérése - admin")
    @WithUserDetails(value = "1111", userDetailsServiceBeanName = "etteremFelhasznaloAdataimService")
    void adminHasJogosultsag() {
        assertTrue(etteremFelhasznaloService.hasJogosultsag(EtteremFelhasznaloTipus.Jogosultsag.USER_READ_ROLE));
        assertTrue(etteremFelhasznaloService.hasJogosultsag(EtteremFelhasznaloTipus.Jogosultsag.USER_WRITE_ROLE));
        assertTrue(etteremFelhasznaloService.hasJogosultsag(EtteremFelhasznaloTipus.Jogosultsag.USER_DELETE_ROLE));
        assertTrue(etteremFelhasznaloService.hasJogosultsag(EtteremFelhasznaloTipus.Jogosultsag.USER_ADD_ITEM_TO_INVENTORY_ROLE));
    }

    @Test
    @DisplayName("Felhasználó jogosultságok lekérése - waiter")
    @WithUserDetails(value = "3333", userDetailsServiceBeanName = "etteremFelhasznaloAdataimService")
    void waiterHasJogosultsag() {
        assertTrue(etteremFelhasznaloService.hasJogosultsag(EtteremFelhasznaloTipus.Jogosultsag.USER_READ_ROLE));
        assertTrue(etteremFelhasznaloService.hasJogosultsag(EtteremFelhasznaloTipus.Jogosultsag.USER_WRITE_ROLE));
        assertFalse(etteremFelhasznaloService.hasJogosultsag(EtteremFelhasznaloTipus.Jogosultsag.USER_DELETE_ROLE));
        assertFalse(etteremFelhasznaloService.hasJogosultsag(EtteremFelhasznaloTipus.Jogosultsag.USER_ADD_ITEM_TO_INVENTORY_ROLE));
    }

    @Test
    @DisplayName("Felhasználó id lekérése")
    @WithUserDetails(value = "3333", userDetailsServiceBeanName = "etteremFelhasznaloAdataimService")
    void waiterId() {
        Long felhasznaloId = etteremFelhasznaloService.getFelhasznaloId();
        assertThat(felhasznaloId)
                .isNotNull()
                .isPositive();
    }

    private void assertFelhasznaloLetezik(String felhasznalonev) {
        assertThat(etteremFelhasznaloService.findAll())
                .isNotEmpty()
                .extracting(EtteremFelhasznalo::getNev)
                .contains(felhasznalonev);
    }
}