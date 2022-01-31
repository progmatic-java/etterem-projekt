package java.hu.progmatic.kozos.etterem.biztonsag;


import java.util.Arrays;

public enum EtteremFelhasznaloTipus {
    ADMIN(Jogosultsag.USER_WRITE_ROLE, Jogosultsag.USER_READ_ROLE, Jogosultsag.USER_DELETE_ROLE, Jogosultsag.USER_ADD_ITEM_TO_INVENTORY_ROLE),
    MANAGER(Jogosultsag.USER_WRITE_ROLE, Jogosultsag.USER_READ_ROLE, Jogosultsag.USER_DELETE_ROLE, Jogosultsag.USER_ADD_ITEM_TO_INVENTORY_ROLE),
    WAITER(Jogosultsag.USER_READ_ROLE, Jogosultsag.USER_WRITE_ROLE);
    private final String[] jogosultsagok;


    EtteremFelhasznaloTipus(String... jogosultsagok) {
        this.jogosultsagok = jogosultsagok;
    }

    public String[] getJogosultsagok() {
        return jogosultsagok;
    }

    public boolean hasJogosultsag(String jogosultsag) {
        return Arrays.asList(jogosultsagok).contains(jogosultsag);
    }

    public static class Jogosultsag {
        public static final String USER_WRITE_ROLE = "USER_WRITE";
        public static final String USER_READ_ROLE = "USER_READ";
        public static final String USER_DELETE_ROLE = "USER_DELETE";
        public static final String USER_ADD_ITEM_TO_INVENTORY_ROLE = "USER_ADD_ITEM_TO_INVENTORY_ROLE";
    }
}
