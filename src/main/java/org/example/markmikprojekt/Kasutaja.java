package org.example.markmikprojekt;


/**
 * Antud klassi kasutatakse kasutajanime ja parooli seotud väljakutsumistega
 */

public class Kasutaja {
    private String kasutajanimi;
    private String parool;

    public Kasutaja(String kasutajanimi, String parool) {
        this.kasutajanimi = kasutajanimi;
        this.parool = parool;
    }

    public String getKasutajanimi() {
        return kasutajanimi;
    }

    public String getParool() {
        return parool;
    }

    public boolean KasutajanimiParoolKontroll(String sisendKasutajanimi, String sisendParool) {
        return kasutajanimi.equals(sisendKasutajanimi) && parool.equals(sisendParool);
    }
}

