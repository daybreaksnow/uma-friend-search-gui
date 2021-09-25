package daybreaksnow.uma.search.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 * @author daybreaksnow
 *
 */
public class UmaFriendSearchView extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			ScrollPane root = (ScrollPane) FXMLLoader.load(getClass().getResource("UmaFriendSearch.fxml"));
			Scene scene = new Scene(root, 800, 640);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("ウマ娘フレンド検索");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void launch(String[] args) {
		Application.launch(args);
	}
}
