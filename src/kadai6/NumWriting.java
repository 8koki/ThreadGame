package kadai6;

import javafx.application.Platform;

public class NumWriting extends Thread {
	
	Kadai6 kadai6;
	
	public NumWriting(Kadai6 kadai6) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.kadai6=kadai6;
	}

	public synchronized void run() {
		while(true) {
		try {
			wait();
			//System.out.println("Numかきこめ");
			Platform.runLater(()->kadai6.changeLabel());
		}catch(InterruptedException e) {
			//例外処理
		}
		}
	}
	
	public synchronized void Notify() {
		notify();
	}
	
}