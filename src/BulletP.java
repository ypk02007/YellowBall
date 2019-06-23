import javax.swing.*;
import java.awt.*;

public class BulletP extends JLabel {
	private JPanel board = null;
	private Player player = null;
	private Enemy enemy = null;
	
	public BulletP(JPanel board, Player player) {
		this.player = player;
		int x = player.getX() + 12;
		int y = player.getY() - 10;
		this.setIcon(new ImageIcon("graphics/bullet.png"));
    	this.setLocation(x, y);
    	this.setSize(20, 20);
    	this.board = board;
    	board.add(this);
    	getEnemy();
    	LaunchThread th = new LaunchThread();
    	th.start();
	}
	
	public void getEnemy() { // JPanel에서 적 객체를 찾아 가져옴
		for(Component jl : board.getComponents()) {
			if(jl instanceof Enemy) {
				enemy = (Enemy)jl;
			}
		}
		if(enemy == null)
			handleError("Enemy가 감지되지 않음");
	}
	
	public boolean check() { // 총알이 삭제될 조건을 체크
		int x = this.getX();
		int y = this.getY();
		int ex = enemy.enemyX();
		int ey = enemy.enemyY();
		if(y < 5) // 화면 끝까지 도달
			return true;
		else if((x > ex - 20) && (x < ex + 75) && (y > ey - 20) && (y < ey + 100)) { // 적에게 맞음
			enemy.damaged();
			return true;
		} else if(player.isLifeZero()) { // life가 0
			return true;
		} else
			return false;
	}
	
	public void deleteThis() { // 총알 삭제
		board.remove(this);
		board.revalidate();
		board.repaint();
		player.removeBullet(this);
	}
	public void bulletMove(int m) { // 총알 이동
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
			int m = 15; // bulletMove 호출 당 이동거리
			while(true) {
				if(check())
					break;
				try {
					bulletMove(m);
					sleep(20);
				} catch (InterruptedException e) {
					handleError(e.getMessage());
				}
			}
			deleteThis();
		}
	}
}
