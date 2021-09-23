package daybreaksnow.uma.search.gui;

/**
 * 
 * @author daybreaksnow
 *
 */
public class EntryPoint{

	public static void main(String[] args) {
		// NOTE Applicationを継承したクラスのmainを呼ぶと以下のエラーが出るため、ここから実行する
		// 「エラー: JavaFXランタイム・コンポーネントが不足しており、このアプリケーションの実行に必要です」
		// 参考：https://torutk.hatenablog.jp/entry/2018/12/01/215113
		UmaFriendSearchView.launch(args);
	}

}

