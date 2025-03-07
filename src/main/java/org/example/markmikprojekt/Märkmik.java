package org.example.markmikprojekt;


/**
 * Antud klassi kasutatakse selleks, et märkmeid salvestada, kustutada, lugeda.
 */

import org.example.markmikprojekt.Kasutaja;
import org.example.markmikprojekt.Märge;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Märkmik {
    private List<Märge> märkmed;
    private Kasutaja kasutaja;

    public Märkmik(Kasutaja kasutaja) {
        this.märkmed = new ArrayList<>();
        this.kasutaja = kasutaja;
    }

    public List<Märge> getMärkmed() {
        return märkmed;
    }

    public void lisaMärge(Märge märge) { //Lisab märkme märkmete järjendisse
        märkmed.add(märge);
    }

    public void eemaldaMärge(String pealkiri) throws Exception { // Eemaldab märkme
        boolean onEemaldatud = märkmed.removeIf(märge -> märge.getPealkiri().equalsIgnoreCase(pealkiri));
        if (!onEemaldatud) {
            throw new Exception();
        }
    }

    public void salvestaMärkmedFaili() { //salvestab märmed kasutaja nimelisse faili
        try (BufferedWriter kirjutaja = new BufferedWriter(new FileWriter(kasutaja.getKasutajanimi() + "_märkmed.txt"))) {
            for (Märge märge : märkmed) {
                kirjutaja.write("Pealkiri: " + märge.getPealkiri());
                kirjutaja.newLine();
                kirjutaja.write("Sisu: " + märge.getSisu());
                kirjutaja.newLine();
                kirjutaja.write("--------------------");
                kirjutaja.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loeMärkmedFailist() { // Loeb märkmed sealt failist, millesse kasutaja sisselogitud on
        String failinimi = kasutaja.getKasutajanimi() + "_märkmed.txt";
        try (BufferedReader lugeja = new BufferedReader(new FileReader(failinimi))) {
            String rida;
            while ((rida = lugeja.readLine()) != null) {
                if (rida.startsWith("Pealkiri: ")) {
                    String pealkiri = rida.substring(10);
                    String sisu = lugeja.readLine().substring(6);
                    lugeja.readLine();
                    märkmed.add(new Märge(pealkiri, sisu));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

