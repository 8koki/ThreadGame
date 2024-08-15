package kadai6;

public class NumMonitoring extends Thread {
	
	private NumGenerate NumGenerate;
	private NumWriting NumWriting;
	//取得
	//private LinkedList<Integer> queue = NumGenerate.getQueue();
	
	public NumMonitoring(NumGenerate NumGenerate, NumWriting NumWriting) {
		this.NumGenerate = NumGenerate;
		this.NumWriting = NumWriting;
	}
	
	//boolean CreatedNumber = NumGenerate.CreatedNumber();
	//NumGenerateのキューのサイズが8を超えるまで
	public void run() {
		while(NumGenerate.getQueue().size()<=8) {
			//sleep(500);
			if(NumGenerate.CreatedNumber()) {
				NumWriting.Notify();
				//System.out.println("num確認");
			}
		/*	if(8 < queue.size()) {
				break;
			}*/
		}
		//System.out.println("game Over");
	}
	
}