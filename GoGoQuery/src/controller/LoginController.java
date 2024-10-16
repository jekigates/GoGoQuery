package controller;

import entity.User;
import javafx.stage.Stage;
import model.UserModel;
import util.StageManager;
import view.LoginView;

public class LoginController {
	private final StageManager sm = StageManager.getInstance();
	private Stage stage = sm.getStage();
	
	public void index() {
		LoginView lv = new LoginView();
		stage.setScene(lv.getScene());
	}
	
	public boolean authenticate(String email, String password) {
		User user = UserModel.findUser(email, password);
		
		return user == null ? false : true;
	}
}
