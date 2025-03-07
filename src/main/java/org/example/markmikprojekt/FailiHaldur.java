package org.example.markmikprojekt;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FailiHaldur {

    private static final String kasutajaFail = "kasutajad.txt";

    /**
     * Salvestan kasutajatega seotud info ehk parool ja kasutajatunnus faili "kasutajad.txt"
     */
    public static void salvestaKasutajad(List<Kasutaja> kasutajad) throws IOException {
        try (BufferedWriter kasutajaKirjutaja = new BufferedWriter(new FileWriter(kasutajaFail))) {
            for (Kasutaja kasutaja : kasutajad) {
                kasutajaKirjutaja.write(kasutaja.getKasutajanimi() + "," + kasutaja.getParool());
                kasutajaKirjutaja.newLine();
            }
        }
    }

    /**
     * Kasutan "kasutajad.txt" faili, et lugeda seal uuesti kasutajate andmed nagu parool ja kasutajatunnus, et seda programmi taaskäivitades saaks ligi salvestatud märkmetele
     */
    public static List<Kasutaja> laeKasutajad() throws IOException {
        List<Kasutaja> kasutajad = new ArrayList<>();
        try (BufferedReader kasutajaLugeja = new BufferedReader(new FileReader(kasutajaFail))) {
            String rida;
            while ((rida = kasutajaLugeja.readLine()) != null) {
                String[] osad = rida.split(",");
                if (osad.length == 2) {
                    kasutajad.add(new Kasutaja(osad[0], osad[1]));
                }
            }
        }
        return kasutajad;
    }
    /**
     * Salvestab kõikide kasutajate märkmed faili, kus iga kasutaja märkmed salvestatakse tema nime järgi
     */
    public static void salvestaMärkmed(List<Märkmik> märkmed) throws IOException {
        for (Märkmik märkmik : märkmed) {
            märkmik.salvestaMärkmedFaili();
        }
    }

    /**
     * Laeb kõikide kasutajate märkmed vastavatest failidest, kus iga kasutaja märkmed on salvestatud tema nime järgi
     */
    public static void laeMärkmed(List<Märkmik> märkmed) throws IOException {
        for (Märkmik märkmik : märkmed) {
            märkmik.loeMärkmedFailist();
        }
    }
}


