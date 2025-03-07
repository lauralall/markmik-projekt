package org.example.markmikprojekt;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Peaklass extends Application {
    private List<Kasutaja> kasutajad;
    private Kasutaja hetkeKasutaja;
    private Märkmik märkmik;
    private Stage peaLava;
    private TextArea tekstiVäli;
    private TextField pealkirjaVäli;
    private VBox märmeList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.peaLava = primaryStage;
        this.peaLava.setTitle("Märkmik");

        laeKasutaja();
        sisseLogimiseAken();
    }

    private void laeKasutaja() {
        try {
            kasutajad = FailiHaldur.laeKasutajad();
        } catch (IOException e) {
            kasutajad = new ArrayList<>();
            näitaTeadet("Error", "Kasutajate laadimine ebaõnnestus: " + e.getMessage());
        }
    }

    private void sisseLogimiseAken() {
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new Insets(10));

        Label kasutajaLabel = new Label("Kasutajanimi:");
        TextField kasutajaVäli = new TextField();
        Label paroolLabel = new Label("Parool:");
        PasswordField paroolVäli = new PasswordField();
        Button sisseLogimiseNupp = new Button("Logi sisse");
        Button uusKasutajaNupp = new Button("Loo uus kasutaja");

        sisseLogimiseNupp.setOnAction(e -> logiKasutajaSisse(kasutajaVäli.getText(), paroolVäli.getText()));
        uusKasutajaNupp.setOnAction(e -> uueKasutajaAken());

        loginLayout.getChildren().addAll(kasutajaLabel, kasutajaVäli, paroolLabel, paroolVäli, sisseLogimiseNupp, uusKasutajaNupp);

        Scene sisseLogimiseStseen = new Scene(loginLayout, 300, 200);
        peaLava.setScene(sisseLogimiseStseen);
        peaLava.show();

    }

    private void logiKasutajaSisse(String kasutajanimi, String parool) {
        for (Kasutaja kasutaja : kasutajad) {
            if (kasutaja.KasutajanimiParoolKontroll(kasutajanimi, parool)) {
                hetkeKasutaja = kasutaja;
                märkmik = new Märkmik(hetkeKasutaja);
                märkmik.loeMärkmedFailist();
                märkmikuAken();
                return;
            }
        }
        näitaTeadet("Error", "Vale kasutajanimi või parool.");
    }

    private void uueKasutajaAken() {
        VBox uusKasutajaLayout = new VBox(10);
        uusKasutajaLayout.setPadding(new Insets(10));

        Label kasutajaLabel = new Label("Uus kasutajanimi:");
        TextField kasutajaVäli = new TextField();
        Label paroolLabel = new Label("Parool:");
        PasswordField paroolVäli = new PasswordField();
        Button looKasutajaNUpp = new Button("Loo kasutaja");
        Button tühistaNupp = new Button("Tühista");

        looKasutajaNUpp.setOnAction(e -> looKasutaja(kasutajaVäli.getText(), paroolVäli.getText()));
        tühistaNupp.setOnAction(e -> sisseLogimiseAken());

        uusKasutajaLayout.getChildren().addAll(kasutajaLabel, kasutajaVäli, paroolLabel, paroolVäli, looKasutajaNUpp, tühistaNupp);

        Scene uusKasutajaStseen = new Scene(uusKasutajaLayout, 300, 200);
        peaLava.setScene(uusKasutajaStseen);
    }

    private void looKasutaja(String kasutajanimi, String parool) {
        for (Kasutaja kasutaja : kasutajad) {
            if (kasutaja.getKasutajanimi().equals(kasutajanimi)) {
                näitaTeadet("Error", "Kasutajanimi on juba olemas.");
                return;
            }
        }
        if (kasutajanimi.equals("")) {
            näitaTeadet("Error", "Kasutajanime väli ei tohi olla tühi.");
            return;
        }
        if (parool.equals("")) {
            näitaTeadet("Error", "Parooli väli ei tohi olla tühi.");
            return;
        }
        Kasutaja uusKasutaja = new Kasutaja(kasutajanimi, parool);
        kasutajad.add(uusKasutaja);
        salvestaKasutaja();
        näitaTeadet("Info", "Kasutaja loodud.");
        sisseLogimiseAken();
    }

    private void salvestaKasutaja() {
        try {
            FailiHaldur.salvestaKasutajad(kasutajad);
        } catch (IOException e) {
            näitaTeadet("Error", "Kasutaja salvestamine ebaõnnestus: " + e.getMessage());
        }
    }

    private void märkmikuAken() {
        BorderPane borderPane = new BorderPane();

        HBox menüü = new HBox(10);
        menüü.setPadding(new Insets(10));
        Button lisaMärgeNupp = new Button("Lisa märge");
        Button kustutaMärgeNupp = new Button("Kustuta märge");
        Button salvestaMärkmedNupp = new Button("Salvesta märkmed");
        Button logiVäljaNupp = new Button("Logi välja");

        lisaMärgeNupp.setOnAction(e -> lisaMärgeAken());
        kustutaMärgeNupp.setOnAction(e -> kustutaMärgeAken());
        salvestaMärkmedNupp.setOnAction(e -> salvestaMärkmed());
        logiVäljaNupp.setOnAction(e -> logiVälja());

        menüü.getChildren().addAll(lisaMärgeNupp, kustutaMärgeNupp, salvestaMärkmedNupp, logiVäljaNupp);
        borderPane.setTop(menüü);

        märmeList = new VBox(10);
        märmeList.setPadding(new Insets(10));
        värskendaMärkmeListi();

        ScrollPane scrollPane = new ScrollPane(märmeList);
        borderPane.setCenter(scrollPane);

        Scene notebookScene = new Scene(borderPane, 600, 400);
        peaLava.setScene(notebookScene);
    }

    private void lisaMärgeAken() {
        Stage märkmeAken = new Stage();
        märkmeAken.setTitle("Lisa märge");

        VBox märkmeLayout = new VBox(10);
        märkmeLayout.setPadding(new Insets(10));

        Label pealkiriLabel = new Label("Pealkiri:");
        pealkirjaVäli = new TextField();
        Label sisuLabel = new Label("Sisu:");
        tekstiVäli = new TextArea();
        Button salvestaNupp = new Button("Salvesta");

        salvestaNupp.setOnAction(e -> salvestaMärge(märkmeAken));

        märkmeLayout.getChildren().addAll(pealkiriLabel, pealkirjaVäli, sisuLabel, tekstiVäli, salvestaNupp);

        Scene märkmeStseen = new Scene(märkmeLayout, 400, 300);
        märkmeAken.setScene(märkmeStseen);
        märkmeAken.show();
    }

    private void salvestaMärge(Stage märkmeAken) {
        String pealkiri = pealkirjaVäli.getText();
        String sisu = tekstiVäli.getText();

        if (pealkiri.isEmpty() || sisu.isEmpty()) {
            näitaTeadet("Error", "Pealkiri ja sisu ei tohi olla tühjad");
            return;
        }
        märkmik.lisaMärge(new Märge(pealkiri, sisu));
        värskendaMärkmeListi();
        märkmeAken.close();
    }

    private void värskendaMärkmeListi() {
        märmeList.getChildren().clear();
        for (Märge märge : märkmik.getMärkmed()) {
            Label pealkiriLabel = new Label("Pealkiri: " + märge.getPealkiri());
            Label sisuLabel = new Label("Sisu: " + märge.getSisu());
            Separator eraldaja = new Separator();
            märmeList.getChildren().addAll(pealkiriLabel, sisuLabel, eraldaja);
        }
    }

    private void kustutaMärgeAken() {
        Stage kustutaLava = new Stage();
        kustutaLava.setTitle("Kustuta märge");

        VBox kustutaLayout = new VBox(10);
        kustutaLayout.setPadding(new Insets(10));

        Label kustutatavPealkiriLabel = new Label("Sisesta kustutatava märkme pealkiri:");
        TextField kustutaVäli = new TextField();
        Button kustutaMärgeNupp = new Button("Kustuta");

        kustutaMärgeNupp.setOnAction(e -> {
            String pealkiri = kustutaVäli.getText();
            try {
                märkmik.eemaldaMärge(pealkiri);
            } catch (Exception ex) {
                näitaTeadet("Error", "Märget ei eksisteeri.");
            }
            värskendaMärkmeListi();
            kustutaLava.close();
        });

        kustutaLayout.getChildren().addAll(kustutatavPealkiriLabel, kustutaVäli, kustutaMärgeNupp);

        Scene kustutaStseen = new Scene(kustutaLayout, 300, 200);
        kustutaLava.setScene(kustutaStseen);
        kustutaLava.show();
    }

    private void salvestaMärkmed() {
        märkmik.salvestaMärkmedFaili();
        näitaTeadet("Info", "Märkmed on salvestatud.");
    }

    private void logiVälja() {
        hetkeKasutaja = null;
        märkmik = null;
        sisseLogimiseAken();
    }

    private void näitaTeadet(String pealkir, String sisu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(pealkir);
        alert.setHeaderText(null);
        alert.setContentText(sisu);
        alert.showAndWait();
    }
}
