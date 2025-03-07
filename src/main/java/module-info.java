module org.example.markmikprojekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.markmikprojekt to javafx.fxml;
    exports org.example.markmikprojekt;
}