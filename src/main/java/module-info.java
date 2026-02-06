module dk.easv.eventticketsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens dk.easv.eventticketsystem to javafx.fxml;
    exports dk.easv.eventticketsystem;
}