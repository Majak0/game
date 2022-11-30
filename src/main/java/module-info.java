module fr.devops.game {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.devops.game to javafx.fxml;
    exports fr.devops.game;
}