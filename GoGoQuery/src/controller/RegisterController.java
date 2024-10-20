package controller;

import entity.User;
import model.UserModel;
import util.StageManager;
import view.RegisterView;

public class RegisterController {
	private final StageManager sm = StageManager.getInstance();
	
	public void index() {
		RegisterView view = new RegisterView();
		sm.getStage().getScene().setRoot(view.getPane());
	}
	
	public boolean isEmailTaken(String email) {
		return UserModel.isEmailTaken(email);
	}
	
	public boolean authenticate(String email, String password) {
		User user = UserModel.findUser(email, password);
		
		return user == null ? false : true;
	}
}
