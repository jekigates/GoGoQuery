package controller;

import model.UserModel;
import util.StageManager;
import view.RoleView;

public class RoleController {
	private final StageManager sm = StageManager.getInstance();

	public void index(String email, String password, String gender, String dob) {
		RoleView view = new RoleView(email, password, gender, dob);
		sm.getStage().getScene().setRoot(view.getPane());
	}

	public void register(String email, String password, String gender, String role, String dob) {
		UserModel.register(email, password, gender, role, dob);
	}
}
