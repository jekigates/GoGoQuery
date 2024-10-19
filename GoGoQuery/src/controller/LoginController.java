package controller;

import entity.User;
import model.UserModel;
import util.StageManager;
import view.LoginView;

public class LoginController {
	private final StageManager sm = StageManager.getInstance();
	
	public void index() {
		LoginView view = new LoginView();
		sm.getStage().getScene().setRoot(view.getPane());
	}
	
	public User authenticate(String email, String password) {
		User user = UserModel.findUser(email, password);
		
		return user == null ? null : user;
	}
}
