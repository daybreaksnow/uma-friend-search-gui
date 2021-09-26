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

import daybreaksnow.uma.search.Factor;
import daybreaksnow.uma.search.FriendInfo;
import daybreaksnow.uma.search.UmaFriendExtractor;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

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

	@FXML
	private Button loadUmaResultButton;

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
	private ComboBox<String> extractRepresentBlueFactorCombo;

	@FXML
	private ComboBox<Integer> extractRepresentBlueFactorMinNumCombo;

	// 代表赤因子
	@FXML
	private ComboBox<String> extractRepresentRedFactorCombo;

	@FXML
	private ComboBox<Integer> extractRepresentRedFactorMinNumCombo;

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

	/**
	 * 抽出結果モデル
	 */
	public static class ExtractResult {
		private String trainerId;
		private String allFactors;
		private String representFactors;

		public ExtractResult(String trainerId, String allFactors, String representFactors) {
			this.trainerId = trainerId;
			this.allFactors = allFactors;
			this.representFactors = representFactors;
		}

		public String getTrainerId() {
			return trainerId;
		}

		public String getAllFactors() {
			return allFactors;
		}

		public String getRepresentFactors() {
			return representFactors;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((allFactors == null) ? 0 : allFactors.hashCode());
			result = prime * result + ((representFactors == null) ? 0 : representFactors.hashCode());
			result = prime * result + ((trainerId == null) ? 0 : trainerId.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ExtractResult other = (ExtractResult) obj;
			if (allFactors == null) {
				if (other.allFactors != null)
					return false;
			} else if (!allFactors.equals(other.allFactors))
				return false;
			if (representFactors == null) {
				if (other.representFactors != null)
					return false;
			} else if (!representFactors.equals(other.representFactors))
				return false;
			if (trainerId == null) {
				if (other.trainerId != null)
					return false;
			} else if (!trainerId.equals(other.trainerId))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "ExtractResult [trainerId=" + trainerId + ", allFactors=" + allFactors + ", representFactors="
					+ representFactors + "]";
		}

	}

	// 抽出結果
	@FXML
	private TableView<ExtractResult> resultTable;

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
		extractRepresentBlueFactorCombo.setItems(searchBlueFactors);
		ObservableList<Integer> representFactorMinNumItems = FXCollections.observableArrayList(1, 2, 3);
		extractRepresentBlueFactorMinNumCombo.setItems(representFactorMinNumItems);
		// 合計赤
		extractRepresentRedFactorCombo.setItems(searchRedFactors);
		extractRepresentRedFactorMinNumCombo.setItems(representFactorMinNumItems);
		// 合計その他
		extractRepresentOtherFactorMinNumCombo1.setItems(representFactorMinNumItems);
		extractRepresentOtherFactorMinNumCombo2.setItems(representFactorMinNumItems);
		extractRepresentOtherFactorMinNumCombo3.setItems(representFactorMinNumItems);
		// 抽出結果
		// トレーナーIDや因子内容をコピー可能にするため、編集可能に
		resultTable.setEditable(true);
		TableColumn<ExtractResult, String> col;
		col = new TableColumn<ExtractResult, String>("トレーナーID");
		col.setMinWidth(100);
		col.setMaxWidth(100);
		col.setCellValueFactory(new PropertyValueFactory<ExtractResult, String>("trainerId"));
		col.setCellFactory(new TextFieldCellCallback());
		resultTable.getColumns().add(col);

		col = new TableColumn<ExtractResult, String>("合計因子");
		col.setMinWidth(300);
		col.setCellValueFactory(new PropertyValueFactory<ExtractResult, String>("allFactors"));
		col.setCellFactory(new TextFieldCellCallback());
		resultTable.getColumns().add(col);

		col = new TableColumn<ExtractResult, String>("代表因子");
		col.setMinWidth(300);
		col.setCellValueFactory(new PropertyValueFactory<ExtractResult, String>("representFactors"));
		col.setCellFactory(new TextFieldCellCallback());
		resultTable.getColumns().add(col);
	}

	/** テーブルセルを編集可能にするためのクラス */
	private final class TextFieldCellCallback
			implements Callback<TableColumn<ExtractResult, String>, TableCell<ExtractResult, String>> {
		@Override
		public TableCell<ExtractResult, String> call(TableColumn<ExtractResult, String> arg0) {
			return new TextFieldTableCell<ExtractResult, String>(new DefaultStringConverter());
		}
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
				try {
					log("start scraping");
					final String result = scraping.scraping(umaName, factors, nextNum);
					log("end scraping");
					Platform.runLater(
							() -> {
								log("update TextArea  start");
								searchUmaResultTextArea.setText(result);
								log("update TextArea  end");
								searchUmaResultTextArea.setDisable(false);
								searchUmaButton.setDisable(false);
								searchUmaButton.setText("検索");
							});
				} catch (Throwable e) {
					e.printStackTrace();
					Platform.runLater(
							() -> {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setHeaderText("検索エラー発生");
								alert.setContentText(getStackTrace(e));
								alert.showAndWait();

								searchUmaResultTextArea.setDisable(false);
								searchUmaButton.setDisable(false);
								searchUmaButton.setText("検索");
							});
				}

				return null;
			}
		};
		searchUmaButton.setDisable(true);
		searchUmaButton.setText("検索中");
		searchUmaResultTextArea.setDisable(true);
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(searchTask);
	}

	private void log(String message) {
		System.out.println(message);
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

	@FXML
	public void loadResult(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("テキストファイル", "*.txt"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File inputFile = fileChooser.showOpenDialog(null);
		if (inputFile != null) {
			String html;
			try {
				html = Files.readString(inputFile.toPath());
			} catch (IOException e) {
				e.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("読み込みエラー発生");
				alert.setContentText(getStackTrace(e));
				alert.showAndWait();
				return;
			}
			// テキスト設定が重いので、一旦ボタンの文字を変えておいて、別スレッドでテキストエリアを更新
			loadUmaResultButton.setText("読込中");
			loadUmaResultButton.setDisable(true);
			Platform.runLater(
					() -> {
						searchUmaResultTextArea.setText(html);
						loadUmaResultButton.setText("読込");
						loadUmaResultButton.setDisable(false);
					});
		}
	}

	@FXML
	public void extract(ActionEvent event) {
		String html = getTextValue(searchUmaResultTextArea.getText());
		if (html == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("抽出元HTMLが空です");
			alert.showAndWait();
			return;
		}
		UmaFriendExtractor extractor = new UmaFriendExtractor();
		Collection<Factor> representNeedFactors = getRepresentNeedFactors();
		Collection<Factor> allNeedFactors = getAllNeedFactors();
		Collection<String> representExcludeFactors = getRepresentExcludeFactors();
		Collection<String> allExcludeFactors = getAllExcludeFactors();
		Collection<FriendInfo> friendInfos = extractor.extract(html, representNeedFactors, allNeedFactors,
				representExcludeFactors, allExcludeFactors);
		if (friendInfos.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("抽出結果が空です");
			alert.showAndWait();
			return;
		}
		resultTable.getItems().clear();
		for (FriendInfo friendInfo : friendInfos) {
			resultTable.getItems().add(new ExtractResult(friendInfo.getTrainderId(),
					friendInfo.getAllFactors().toString(), friendInfo.getRepresentFactors().toString()));
		}
	}

	private Collection<Factor> getRepresentNeedFactors() {
		Collection<Factor> factors = new ArrayList<>();
		addFactor(factors, extractRepresentBlueFactorCombo, extractRepresentBlueFactorMinNumCombo);
		addFactor(factors, extractRepresentRedFactorCombo, extractRepresentRedFactorMinNumCombo);
		addFactor(factors, extractRepresentOtherFactorText1, extractRepresentOtherFactorMinNumCombo1);
		addFactor(factors, extractRepresentOtherFactorText2, extractRepresentOtherFactorMinNumCombo2);
		addFactor(factors, extractRepresentOtherFactorText3, extractRepresentOtherFactorMinNumCombo3);
		return factors;
	}

	private Collection<Factor> getAllNeedFactors() {
		Collection<Factor> factors = new ArrayList<>();
		addFactor(factors, extractAllBlueFactorCombo1, extractAllBlueFactorMinNumCombo1);
		addFactor(factors, extractAllBlueFactorCombo2, extractAllBlueFactorMinNumCombo2);
		addFactor(factors, extractAllBlueFactorCombo3, extractAllBlueFactorMinNumCombo3);
		addFactor(factors, extractAllRedFactorCombo1, extractAllRedFactorMinNumCombo1);
		addFactor(factors, extractAllRedFactorCombo2, extractAllRedFactorMinNumCombo2);
		addFactor(factors, extractAllRedFactorCombo3, extractAllRedFactorMinNumCombo3);
		addFactor(factors, extractAllOtherFactorText1, extractAllOtherFactorMinNumCombo1);
		addFactor(factors, extractAllOtherFactorText2, extractAllOtherFactorMinNumCombo2);
		addFactor(factors, extractAllOtherFactorText3, extractAllOtherFactorMinNumCombo3);
		return factors;
	}

	private Collection<String> getRepresentExcludeFactors() {
		return getExludeFactors(extractRepresentExcludeFactorTextArea);
	}

	private Collection<String> getAllExcludeFactors() {
		return getExludeFactors(extractAllExcludeFactorTextArea);
	}

	private Collection<String> getExludeFactors(TextArea textArea) {
		Collection<String> factors = new ArrayList<>();
		String allText = textArea.getText();
		String[] split = allText.split("\n");
		for (String factorText : split) {
			if (getTextValue(factorText) != null) {
				factors.add(getTextValue(factorText));
			}
		}
		return factors;
	}

	private void addFactor(Collection<Factor> factors, ComboBox<String> factorNameCombo,
			ComboBox<Integer> minNumCombo) {
		if (factorNameCombo.getValue() != null) {
			String factorName = factorNameCombo.getValue();
			Integer minNum = minNumCombo.getValue();
			if (minNum == null) {
				minNum = 1;
			}
			factors.add(new Factor(factorName, minNum));
		}
	}

	private void addFactor(Collection<Factor> factors, TextField factorNameText,
			ComboBox<Integer> minNumCombo) {
		if (getTextValue(factorNameText.getText()) != null) {
			String factorName = getTextValue(factorNameText.getText());
			Integer minNum = minNumCombo.getValue();
			if (minNum == null) {
				minNum = 1;
			}
			factors.add(new Factor(factorName, minNum));
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
