module fr.devops.game {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	
	requires LeJeuShared;


    opens fr.devops.game.menu to javafx.fxml;
    opens fr.devops.game.ingame to javafx.fxml;
    opens fr.devops.game.render to javafx.fxml;
    exports fr.devops.game;
}