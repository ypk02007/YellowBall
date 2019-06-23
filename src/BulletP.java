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
	
	public void getEnemy() { // JPanel���� �� ��ü�� ã�� ������
		for(Component jl : board.getComponents()) {
			if(jl instanceof Enemy) {
				enemy = (Enemy)jl;
			}
		}
		if(enemy == null)
			handleError("Enemy�� �������� ����");
	}
	
	public boolean check() { // �Ѿ��� ������ ������ üũ
		int x = this.getX();
		int y = this.getY();
		int ex = enemy.enemyX();
		int ey = enemy.enemyY();
		if(y < 5) // ȭ�� ������ ����
			return true;
		else if((x > ex - 20) && (x < ex + 75) && (y > ey - 20) && (y < ey + 100)) { // ������ ����
			enemy.damaged();
			return true;
		} else if(player.isLifeZero()) { // life�� 0
			return true;
		} else
			return false;
	}
	
	public void deleteThis() { // �Ѿ� ����
		board.remove(this);
		board.revalidate();
		board.repaint();
		player.removeBullet(this);
	}
	public void bulletMove(int m) { // �Ѿ� �̵�
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
			int m = 15; // bulletMove ȣ�� �� �̵��Ÿ�
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
