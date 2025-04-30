module CMSC_137_Project {
	requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;

    opens application;
    opens application.game_menu;
    opens application.multi_player_lobby;
	
}
