package daybreaksnow.uma.search.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import daybreaksnow.uma.search.UmaFriendListScraping;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * 
 * @author daybreaksnow
 *
 */
public class UmaFriendSearchController implements Initializable {

	@FXML
	private TextField searchUmaNameText;

	@FXML
	private ListView<String> searchBlueFactorCombo;

	@FXML
	private ListView<String> searchRedFactorCombo;

	@FXML
	private TextField nextNumText;

	@FXML
	private Button searchUmaButton;

	@FXML
	private TextArea searchUmaResultTextArea;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> searchBlueFactors = FXCollections.observableArrayList("スピード", "パワー", "スタミナ", "根性", "賢さ");
		ObservableList<String> searchRedFactors = FXCollections.observableArrayList("短距離", "マイル", "中距離", "長距離", "芝",
				"ダート");
		searchBlueFactorCombo.setItems(searchBlueFactors);
		searchBlueFactorCombo.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		searchRedFactorCombo.setItems(searchRedFactors);
		searchRedFactorCombo.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		nextNumText.setText("0");
		nextNumText.setMinWidth(0);
		nextNumText.setAlignment(Pos.CENTER_RIGHT);

	}

	@FXML
	public void search(ActionEvent event) {
		String umaName = getTextValue(searchUmaNameText.getText());
		Collection<String> factors = new ArrayList<>();
		factors.addAll(searchBlueFactorCombo.getSelectionModel().getSelectedItems());
		factors.addAll(searchRedFactorCombo.getSelectionModel().getSelectedItems());
		String nextNumStr = getTextValue(nextNumText.getText());
		int nextNum;
		try {
			if (nextNumStr != null) {
				nextNum = Integer.parseInt(nextNumStr);
			} else {
				nextNum = 0;
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("もっと見る回数が数字ではありません");
			alert.showAndWait();
			return;
		}
		UmaFriendListScraping scraping = new UmaFriendListScraping();
		// TODO 重いので別スレッドで実行し、プログレスバーを出す
		String result = scraping.scraping(umaName, factors, nextNum);
		searchUmaResultTextArea.setText(result);
	}

	public static String getTextValue(String text) {
		return (text == null || text.trim().isEmpty()) ? null : text;
	}
}
