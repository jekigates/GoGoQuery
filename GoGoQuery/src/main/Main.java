package main;

import javafx.application.Application;
import javafx.stage.Stage;
import util.StageManager;
import view.LoginView;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		StageManager sm = StageManager.getInstance();
		sm.setStage(primaryStage);
		
		LoginView lv = new LoginView();
		sm.getStage().setTitle("GoGoQuery - Login");
		sm.getStage().setScene(lv.getScene());
		
        // Load the CSS stylesheet
        String css = this.getClass().getResource("/css/styles.css").toExternalForm();
        sm.getStage().getScene().getStylesheets().add(css);
		sm.getStage().show();
	}

}
