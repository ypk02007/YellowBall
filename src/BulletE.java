import java.awt.Component;

import javax.swing.*;

public class BulletE extends JLabel {
	private JPanel board = null;
	private Enemy enemy = null;
	private Player player = null;
	
	public BulletE(JPanel board, int x, int y, int mx, int my, int code) {
		this.board = board;
		changeImage(code);
		this.setSize(110, 20);
		this.setLocation(x, y);
		board.add(this);
		LaunchThread th = new LaunchThread(mx, my);
    	th.start();
	}
	
	public void changeImage(int code) {
        switch(code) {
            case 1: // Enemy1의 패턴1-1
            	this.setIcon(new ImageIcon("graphics/bulletE/note1.png"));
            	break;
            case 2: // Enemy1의 패턴1-2
            	this.setIcon(new ImageIcon("graphics/bulletE/note2.png"));
            	break;
        }
    }
	
	public void getPlayer() { // JPanel에서 플레이어 객체를 찾아 가져옴
		for(Component jl : board.getComponents()) {
			if(jl instanceof Player) {
				player = (Player)jl;
			}
		}
		if(player == null)
			handleError("Player가 감지되지 않음");
	}
	
	public boolean check() { // 총알이 삭제될 조건을 체크
		int x = this.getX();
		int y = this.getY();
		if(y > 700) // 화면 끝까지 도달
			return true;
		else
			return false;
	}
	
	public void deleteThis() { // 총알 삭제
		board.remove(this);
		board.revalidate();
		board.repaint();
	}
	public void bulletMove(int mx, int my) { // 총알 이동
		int x = this.getX();
		int y = this.getY();
		this.setLocation(x + mx, y + my);
	}
	
	public void handleError(String msg) {
		System.out.println(msg);
		System.exit(1);
	}
	
	class LaunchThread extends Thread {
		private int mx;
		private int my;
		
		public LaunchThread(int mx, int my) {
			this.mx = mx; this.my = my; // bulletMove 호출 당 x축, y축 이동거리
		}
		
		public void run() {
			while(true) {
				if(check()) {
					break;
				}
				try {
					bulletMove(mx, my);
					sleep(10);
				} catch (InterruptedException e) {
					handleError(e.getMessage());
				}
			}
			deleteThis();
		}
	}
}
