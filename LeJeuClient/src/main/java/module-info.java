module fr.devops.game {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;


    opens fr.devops.game.menu to javafx.fxml;
    exports fr.devops.game;
    exports fr.devops.game.menu;
}