package kadai6;

import java.util.LinkedList;
import java.util.Random;

import javafx.application.Platform;

public class NumGenerate extends Thread{
	
	private Kadai6 kadai6;
	
	//ランダム,キュー,リストの作成
	private Random rnd = new Random();
	private LinkedList<Integer> queue = new LinkedList<>();
	//private ArrayList<Integer> list = new ArrayList<>();
	int Level;
	int ms;
	//Getter
	private boolean CreatedNumber;
	
	public NumGenerate(Kadai6 kadai6) {
		this.kadai6 = kadai6;
		//Level = kadai6.getLevel();
		ms = 2000 - (Level - 1) * 200;
	}
	
	public void run() {
		while(queue.size()<=8) {
			try {
				//スレッドの一時停止(sleep)
				sleep(ms);
				//ランダムな値の作成(0~9)
				int num = rnd.nextInt(10);
				//キュー,リストに数値を挿入
				queue.add(num);
				//list.add(num);
				//System.out.println(queue);
				CreatedNumber = true;
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
		}
		//System.out.println("game Over");
		kadai6.setGame(false);
		Platform.runLater(()->kadai6.gameEnd());
	}
	
	public synchronized boolean CreatedNumber() {
		if(CreatedNumber) {
			CreatedNumber = false;
			return true;
		}else {
		return this.CreatedNumber;
		}
	}

	public synchronized LinkedList<Integer> getQueue() {//Getter
		return queue;
	}

	public void setQueue(LinkedList<Integer> queue) {//Setter
		this.queue = queue;
	}
	
	public synchronized void setMs(int ms) {//Kadai6.msの取得
		this.ms = ms;
	}
}