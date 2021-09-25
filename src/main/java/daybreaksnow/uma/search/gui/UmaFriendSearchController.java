package daybreaksnow.uma.search.gui;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import daybreaksnow.uma.search.UmaFriendListScraping;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * 
 * @author daybreaksnow
 *
 */
public class UmaFriendSearchController implements Initializable {

	// 検索部
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

	//抽出部
	// 合計部
	//合計青因子
	@FXML
	private ComboBox<String> extractAllBlueFactorCombo1;

	@FXML
	private ComboBox<Integer> extractAllBlueFactorMinNumCombo1;

	@FXML
	private ComboBox<String> extractAllBlueFactorCombo2;

	@FXML
	private ComboBox<Integer> extractAllBlueFactorMinNumCombo2;

	@FXML
	private ComboBox<String> extractAllBlueFactorCombo3;

	@FXML
	private ComboBox<Integer> extractAllBlueFactorMinNumCombo3;

	//合計赤因子
	@FXML
	private ComboBox<String> extractAllRedFactorCombo1;

	@FXML
	private ComboBox<Integer> extractAllRedFactorMinNumCombo1;

	@FXML
	private ComboBox<String> extractAllRedFactorCombo2;

	@FXML
	private ComboBox<Integer> extractAllRedFactorMinNumCombo2;

	@FXML
	private ComboBox<String> extractAllRedFactorCombo3;

	@FXML
	private ComboBox<Integer> extractAllRedFactorMinNumCombo3;

	// 合計その他因子
	@FXML
	private TextField extractAllOtherFactorText1;

	@FXML
	private ComboBox<Integer> extractAllOtherFactorMinNumCombo1;

	@FXML
	private TextField extractAllOtherFactorText2;

	@FXML
	private ComboBox<Integer> extractAllOtherFactorMinNumCombo2;

	@FXML
	private TextField extractAllOtherFactorText3;

	@FXML
	private ComboBox<Integer> extractAllOtherFactorMinNumCombo3;

	// 全体除外因子
	@FXML
	private TextArea extractAllExcludeFactorTextArea;

	// 代表部
	//代表青因子
	@FXML
	private ComboBox<String> extractRepresentBlueFactorCombo1;

	@FXML
	private ComboBox<Integer> extractRepresentBlueFactorMinNumCombo1;

	@FXML
	private ComboBox<String> extractRepresentBlueFactorCombo2;

	@FXML
	private ComboBox<Integer> extractRepresentBlueFactorMinNumCombo2;

	@FXML
	private ComboBox<String> extractRepresentBlueFactorCombo3;

	@FXML
	private ComboBox<Integer> extractRepresentBlueFactorMinNumCombo3;

	// 代表赤因子
	@FXML
	private ComboBox<String> extractRepresentRedFactorCombo1;

	@FXML
	private ComboBox<Integer> extractRepresentRedFactorMinNumCombo1;

	@FXML
	private ComboBox<String> extractRepresentRedFactorCombo2;

	@FXML
	private ComboBox<Integer> extractRepresentRedFactorMinNumCombo2;

	@FXML
	private ComboBox<String> extractRepresentRedFactorCombo3;

	@FXML
	private ComboBox<Integer> extractRepresentRedFactorMinNumCombo3;

	// 代表その他因子
	@FXML
	private TextField extractRepresentOtherFactorText1;

	@FXML
	private ComboBox<Integer> extractRepresentOtherFactorMinNumCombo1;

	@FXML
	private TextField extractRepresentOtherFactorText2;

	@FXML
	private ComboBox<Integer> extractRepresentOtherFactorMinNumCombo2;

	@FXML
	private TextField extractRepresentOtherFactorText3;

	@FXML
	private ComboBox<Integer> extractRepresentOtherFactorMinNumCombo3;

	// 代表除外因子
	@FXML
	private TextArea extractRepresentExcludeFactorTextArea;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//検索部
		ObservableList<String> searchBlueFactors = FXCollections.observableArrayList("スピード", "パワー", "スタミナ", "根性", "賢さ");
		ObservableList<String> searchRedFactors = FXCollections.observableArrayList("短距離", "マイル", "中距離", "長距離", "芝",
				"ダート");
		searchBlueFactorCombo.setItems(searchBlueFactors);
		searchBlueFactorCombo.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		searchRedFactorCombo.setItems(searchRedFactors);
		searchRedFactorCombo.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		nextNumText.setText("0");
		nextNumText.setAlignment(Pos.CENTER_RIGHT);
		//抽出部
		// 合計青
		extractAllBlueFactorCombo1.setItems(searchBlueFactors);
		extractAllBlueFactorCombo2.setItems(searchBlueFactors);
		extractAllBlueFactorCombo3.setItems(searchBlueFactors);
		ObservableList<Integer> allFactorMinNumItems = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		extractAllBlueFactorMinNumCombo1.setItems(allFactorMinNumItems);
		extractAllBlueFactorMinNumCombo2.setItems(allFactorMinNumItems);
		extractAllBlueFactorMinNumCombo3.setItems(allFactorMinNumItems);
		// 合計赤
		extractAllRedFactorCombo1.setItems(searchRedFactors);
		extractAllRedFactorCombo2.setItems(searchRedFactors);
		extractAllRedFactorCombo3.setItems(searchRedFactors);
		extractAllRedFactorMinNumCombo1.setItems(allFactorMinNumItems);
		extractAllRedFactorMinNumCombo2.setItems(allFactorMinNumItems);
		extractAllRedFactorMinNumCombo3.setItems(allFactorMinNumItems);
		// 合計その他
		extractAllOtherFactorMinNumCombo1.setItems(allFactorMinNumItems);
		extractAllOtherFactorMinNumCombo2.setItems(allFactorMinNumItems);
		extractAllOtherFactorMinNumCombo3.setItems(allFactorMinNumItems);
		// 代表青
		extractRepresentBlueFactorCombo1.setItems(searchBlueFactors);
		extractRepresentBlueFactorCombo2.setItems(searchBlueFactors);
		extractRepresentBlueFactorCombo3.setItems(searchBlueFactors);
		ObservableList<Integer> representFactorMinNumItems = FXCollections.observableArrayList(1, 2, 3);
		extractRepresentBlueFactorMinNumCombo1.setItems(representFactorMinNumItems);
		extractRepresentBlueFactorMinNumCombo2.setItems(representFactorMinNumItems);
		extractRepresentBlueFactorMinNumCombo3.setItems(representFactorMinNumItems);
		// 合計赤
		extractRepresentRedFactorCombo1.setItems(searchRedFactors);
		extractRepresentRedFactorCombo2.setItems(searchRedFactors);
		extractRepresentRedFactorCombo3.setItems(searchRedFactors);
		extractRepresentRedFactorMinNumCombo1.setItems(representFactorMinNumItems);
		extractRepresentRedFactorMinNumCombo2.setItems(representFactorMinNumItems);
		extractRepresentRedFactorMinNumCombo3.setItems(representFactorMinNumItems);
		// 合計その他
		extractRepresentOtherFactorMinNumCombo1.setItems(representFactorMinNumItems);
		extractRepresentOtherFactorMinNumCombo2.setItems(representFactorMinNumItems);
		extractRepresentOtherFactorMinNumCombo3.setItems(representFactorMinNumItems);
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
		// 検索は重いので別スレッドで実行
		Task<Void> searchTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				final String result = scraping.scraping(umaName, factors, nextNum);
				Platform.runLater(
						() -> {
							searchUmaResultTextArea.setText(result);
							searchUmaResultTextArea.setDisable(false);
							searchUmaButton.setDisable(false);
							searchUmaButton.setText("検索");
						});
				return null;
			}
		};
		searchUmaButton.setDisable(true);
		searchUmaButton.setText("検索中");
		searchUmaResultTextArea.setDisable(true);
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(searchTask);
	}

	@FXML
	public void saveResult(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("テキストファイル", "*.txt"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File outputFile = fileChooser.showSaveDialog(null);
		if (outputFile != null) {
			String saveText = searchUmaResultTextArea.getText();
			Path dest = outputFile.toPath();
			try {
				Files.write(dest, Arrays.asList(saveText));
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("書き込みに成功しました");
				alert.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("書き込みエラー発生");
				alert.setContentText(getStackTrace(e));
				alert.showAndWait();
			}
		}
	}

	public static String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}

	public static String getTextValue(String text) {
		return (text == null || text.trim().isEmpty()) ? null : text;
	}
}
