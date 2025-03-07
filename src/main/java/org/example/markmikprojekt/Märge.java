package org.example.markmikprojekt;

/**
 * Antud klassi kasutatakse selleks, et saada märkmiku sissekandesse kuuluvad pealkiri ja sisu.
 */
public class Märge {
    private String pealkiri;
    private String sisu;

    public Märge(String pealkiri, String sisu) {
        this.pealkiri = pealkiri;
        this.sisu = sisu;
    }

    public String getPealkiri() {
        return pealkiri;
    }

    public void setPealkiri(String pealkiri) {
        this.pealkiri = pealkiri;
    }

    public String getSisu() {
        return sisu;
    }

    public void setSisu(String sisu) {
        this.sisu = sisu;
    }
}

