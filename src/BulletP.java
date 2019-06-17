import javax.swing.*;

public class BulletP extends JLabel {
	private JPanel board = null;
	
	public BulletP(JPanel board, Player player) {
		int x = player.getX() + 12;
		int y = player.getY() - 10;
		this.setIcon(new ImageIcon("graphics/bullet.png"));
    	this.setLocation(x, y);
    	this.setSize(20, 20);
    	this.board = board;
    	this.board.add(this);
    	LaunchThread th = new LaunchThread();
    	th.start();
	}
	
	public int getThisY() {return this.getY();}
	public void deleteThis() {
		board.remove(this);
		board.revalidate();
		board.repaint();
		System.out.println("총알 제거됨");
	}
	public void bulletMove(int m) {
		int x = this.getX();
		int y = this.getY() - m;
		this.setLocation(x, y);
	}
	
	public void handleError(String msg) {
		System.out.println(msg);
		System.exit(1);
	}
	
	class LaunchThread extends Thread {
		public void run() {
			int m = 10; // 메소드 호출 당 이동거리
			while(true) {
				if(getThisY() < 100) {
					break;
				}
				try {
					bulletMove(m);
					sleep(10);
				} catch (InterruptedException e) {
					handleError(e.getMessage());
				}
			}
			deleteThis();
		}
	}
}
