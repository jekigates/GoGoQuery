package util;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StageManager {
    private static volatile StageManager sm;
    private Stage stage;

    private StageManager() {
        stage = new Stage();
    }

    public static StageManager getInstance() {
        if (sm == null) { // First check (no locking)
            synchronized (StageManager.class) {
                if (sm == null) { // Second check (with locking)
                    sm = new StageManager();
                }
            }
        }
        return sm;
    }

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		stage.setMaximized(true);
        stage.setResizable(false);
	}
	
	public void setRoot(Pane pane) {
		this.stage.getScene().setRoot(pane);
	}
}
