package kadai6;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Kadai6 extends Application{
	
	//インスタンス化
	NumGenerate t1 = new NumGenerate(this);
	NumWriting t3 = new NumWriting(this);
	//同じインスタンスを他で共有する
	NumMonitoring t2 = new NumMonitoring(t1, t3);
	
	//ラベルの作成(配列)
	private Label[] numLabel = new Label[8];
	//「足して10になりません」の表示
	private Label l8 = new Label("");
	//回答部分
	private TextField answer = new TextField("");
	//ゲームの開始判定
	private boolean game = false;
	//表示(Level,Score)
	private Label l1 = new Label("Level : 0");
	private Label l2 = new Label("Score : 0");
	//計算(Level,Score,resultScore)
	private int Level = 1;
	private int Score = 0;
	private int resultScore = 0;
	//5回カウント
	private int count = 0;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		AnchorPane ap = new AnchorPane();
		//起動時Windowの大きさ
		Scene scene = new Scene(ap,500,250);
		primaryStage.setScene(scene);
		//サイズ固定
		primaryStage.setResizable(false);
		//Windowのタイトル
		primaryStage.setTitle("スレッドゲーム");
		//ラベルの作成
		//空白部分
		Label l0 = new Label("");
		Label l3 = new Label("");
		Label l4 = new Label("");
		Label l5 = new Label("");
		Label l6 = new Label("");
		Label l7 = new Label("");
		//ボタンの作成
		Button start = new Button("Start");
		//最終的なまとめ//
		VBox LevelScore = new VBox(2);
		HBox number = new HBox(20);
		HBox numberlist = new HBox();
		VBox show = new VBox(20);
		//境界線の作成
		Border border = new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderWidths.DEFAULT));
		//背景色の作成
		answer.setBackground(new Background(new BackgroundFill(Color.WHITE,new CornerRadii(0),Insets.EMPTY)));
		//幅と高さの指定
		LevelScore.setPrefWidth(450);
		l3.setPrefWidth(30);
		l3.setPrefHeight(40);
		answer.setPrefWidth(40);
		answer.setPrefHeight(40);
		l4.setPrefWidth(10);
		l4.setPrefHeight(40);
		numberlist.setPrefWidth(350);
		numberlist.setPrefHeight(40);
		start.setPrefWidth(80);
		start.setPrefHeight(30);
		//境界線の作成
		answer.setBorder(border);
		//配置位置指定
		LevelScore.setAlignment(Pos.TOP_RIGHT);
		answer.setAlignment(Pos.CENTER);
		number.setAlignment(Pos.CENTER);
		start.setAlignment(Pos.CENTER);
		show.setAlignment(Pos.CENTER);
		//配列numの境界線,幅,高さ,背景色,配置位置指定,挿入部分
		for(int i = numLabel.length - 1;i >= 0; i--) {
			numLabel[i] = new Label("");
			//境界線
			numLabel[i].setBorder(border);
			//幅と高さ
			numLabel[i].setPrefHeight(40);
			numLabel[i].setPrefWidth(40);
			//背景色の作成
			numLabel[i].setBackground(new Background(new BackgroundFill(Color.WHITE,new CornerRadii(0),Insets.EMPTY)));
			//配置位置指定
			numLabel[i].setAlignment(Pos.CENTER);
			//挿入部分
			numberlist.getChildren().add(numLabel[i]);
		}
		//挿入部分
		LevelScore.getChildren().addAll(l0,l1,l2);
		ap.getChildren().add(LevelScore);
		//numberlist.getChildren().addAll(num);
		number.getChildren().addAll(l3,answer,l4,numberlist);
		show.getChildren().addAll(l5,l6,number,l7,l8,start);
		ap.getChildren().addAll(show);
		primaryStage.show();
		//キーが押される時
		scene.setOnKeyPressed(this::keyPressed);
		//Start押された時
		start.setOnAction((ActionEvent)->{
			t3.start();
			t2.start();
			t1.start();
			start.setDisable(true);
			game = true;
			l1.setText("Level : 1  ");
		});
		
	}
	//numLabelを書き換える
	public void changeLabel() {
		//int count = 0;
		//String text=String.valueOf(t1.getQueue().get(count));
		//num[count].setText(text);
		for(int i = 0; i < numLabel.length; i++) {
		if(i<t1.getQueue().size()) {
			numLabel[t1.getQueue().size()- i - 1].setText(String.valueOf(t1.getQueue().get(i)));
		}
		}
	//	count++;
	}
//キーイベントの作成
public void keyPressed(KeyEvent e) {
	//int queue = 0;
		switch(e.getCode()) {
		case ENTER:
			if(game == true) {
				decision(answer);
			}
			break;
			//default:
			//break;
		default:
			break;
		}
	}
//判定部分(計算)
private void decision(TextField answer) {
	int ans = 0;
	int queue = 0;
	
	String str = answer.getText();
	boolean flag = true;
	try{
		ans = Integer.parseInt(str);
	}catch(NumberFormatException e){
		l8.setText("足して10になりません");
		answer.setText("");
		flag = false;
	}
	if(flag == true) {
		l8.setText("");
		queue = t1.getQueue().get(0);
		Score = queue * Level;
		resultScore = resultScore + Score;
		if(ans + queue == 10) {
			numLabel[t1.getQueue().size() - 1].setText("");
			t1.getQueue().remove(0);
			answer.setText("");
			count++;
			String StrScore = String.valueOf(resultScore);
			l2.setText("Score :  " + StrScore);
		}else if(ans == 0 && queue == 0) {
			for(int i = 0; i < 8; i++) {
				numLabel[i].setText("");
			}
			int result = 0;
			for(int i = 0; i < t1.getQueue().size(); i++) {
				result = result + t1.getQueue().get(i);
			}
			count=count+t1.getQueue().size();
			t1.getQueue().clear();
			Score = result * Level;
			resultScore = resultScore + Score;
			answer.setText("");
		    // count++;
			String StrScore = String.valueOf(resultScore);
			l2.setText("Score :  " + StrScore);
		}else {
			l8.setText("足して10になりません");
			answer.setText("");
		}
		//if(count == 5) {
			
			Level = 1+(count/5);
			int ms = 2000 - (Level - 1) * 200;
			t1.setMs(ms);
			//System.out.println(count);
			String StrLevel = String.valueOf(Level);
			l1.setText("Level : " + StrLevel);
		//}
	}
}
//ゲーム終了の際にラベルの数字を書き換える
public void gameEnd() {
	numLabel[7].setText("G");
	numLabel[6].setText("A");
	numLabel[5].setText("M");
	numLabel[4].setText("E");
	numLabel[3].setText("S");
	numLabel[2].setText("E");
	numLabel[1].setText("T");
	numLabel[0].setText("!");
}
//ゲーム開始判定
public void setGame(boolean game) {
	this.game = game;
}
public int getLevel() {//Getter 
	return Level;
}
public void setLevel(int Level) {//Setter
	this.Level = Level;
}
public int getScore() {//Getter
	return Score;
}
public void setScore(int Score) {//Setter
	this.Score = Score;
}

}