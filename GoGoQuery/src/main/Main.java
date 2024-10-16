package main;

import controller.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.StageManager;

public class Main extends Application {
	private final StageManager sm = StageManager.getInstance();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		sm.setStage(primaryStage);
//		Set scene dengan border pane polos sebagai root biar nanti bisa swap scenes
		sm.getStage().setScene(new Scene(new BorderPane()));
		
		LoginController lc = new LoginController();
		lc.index();
		
        // Load the CSS stylesheet
        String css = this.getClass().getResource("/css/styles.css").toExternalForm();
        sm.getStage().getScene().getStylesheets().add(css);
		sm.getStage().show();
	}

}
