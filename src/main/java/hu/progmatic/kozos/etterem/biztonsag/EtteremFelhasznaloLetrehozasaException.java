package hu.progmatic.kozos.etterem.biztonsag;

public class EtteremFelhasznaloLetrehozasaException extends RuntimeException{
    public EtteremFelhasznaloLetrehozasaException(String uzenet){
        super(uzenet);
    }
}
