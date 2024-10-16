package controller;

import javafx.stage.Stage;
import util.StageManager;
import view.LoginView;

public class LoginController {
	private StageManager sm = StageManager.getInstance();
	private Stage stage = sm.getStage();
	
	public void index() {
		LoginView lv = new LoginView();
		stage.setScene(lv.getScene());
	}
}
