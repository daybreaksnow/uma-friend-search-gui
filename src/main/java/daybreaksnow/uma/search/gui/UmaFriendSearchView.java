package daybreaksnow.uma.search.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author daybreaksnow
 *
 */
public class UmaFriendSearchView extends Application{

	@Override
	public void start(Stage primaryStage) {
		try { 
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("UmaFriendSearch.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void launch(String[] args) {
		Application.launch(args);
	}
}

