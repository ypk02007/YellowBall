import java.awt.Component;
import javax.swing.*;

public class BulletE extends JLabel {
	private JPanel board = null;
	private Player player = null;
	private int width = 0;
	private int height = 0;
	private int hitBoxWidth = 0;
	private int hitBoxHeight = 0;
	
	public BulletE(JPanel board, int x, int y, int mx, int my, int code) {
		this.board = board;
		setBulletImage(code);
		setBulletSize(code);
		this.setSize(width, height);
		this.setLocation(x, y);
		board.add(this);
		this.player = getPlayer();
		launch(mx, my);
	}
	
	public void launch(int mx, int my) {
		LaunchThread th = new LaunchThread(mx, my);
    	th.start();
	}
	
	public void setBulletImage(int code) {
        switch(code) {
            case 1: // Enemy1의 패턴1-1
            	this.setIcon(new ImageIcon("graphics/bulletE/note1.png"));
            	break;
            case 2: // Enemy1의 패턴1-2
            	this.setIcon(new ImageIcon("graphics/bulletE/note2.png"));
            	break;
            case 3: // Enemy1의 패턴2-1
            	this.setIcon(new ImageIcon("graphics/bulletE/arrow1.png"));
            	break;
            case 4: // Enemy1의 패턴2-2
            	this.setIcon(new ImageIcon("graphics/bulletE/arrow2.png"));
            	break;
            case 5: // Enemy1의 패턴2-3
            	this.setIcon(new ImageIcon("graphics/bulletE/arrow3.png"));
            	break;
            case 6: // Enemy1의 패턴2-4
            	this.setIcon(new ImageIcon("graphics/bulletE/arrow4.png"));
            	break;
            case 7: // Enemy1의 패턴3-1
            	this.setIcon(new ImageIcon("graphics/bulletE/pad1.png"));
            	break;
            case 8: // Enemy1의 패턴3-2
            	this.setIcon(new ImageIcon("graphics/bulletE/pad2.png"));
            	break;
            case 9: // Enemy1의 패턴3-3
            	this.setIcon(new ImageIcon("graphics/bulletE/pad3.png"));
            	break;
            case 10: // Enemy1의 패턴3-4
            	this.setIcon(new ImageIcon("graphics/bulletE/pad4.png"));
            	break;
            case 11: // Enemy1의 패턴3-5
            	this.setIcon(new ImageIcon("graphics/bulletE/pad5.png"));
            	break;
            case 12: // Enemy1의 패턴3-6
            	this.setIcon(new ImageIcon("graphics/bulletE/pad6.png"));
            	break;
            case 13: // Enemy1의 패턴3-7
            	this.setIcon(new ImageIcon("graphics/bulletE/pad7.png"));
            	break;
            case 14: // Enemy2의 패턴1
            	this.setIcon(new ImageIcon("graphics/bulletE/rapid.png"));
            	break;
            case 15: // Enemy2의 패턴2
            	this.setIcon(new ImageIcon("graphics/bulletE/fireball.png"));
            	break;
            case 16: // Enemy3의 패턴3
            	this.setIcon(new ImageIcon("graphics/bulletE/whirl.png"));
            	break;
            default:
            	this.setIcon(new ImageIcon("graphics/bulletE/bulletE.png"));
        }
    }
	
	public void setBulletSize(int code) {
		if(code >= 1 && code <= 2) {
            width = 110; height = 20; hitBoxWidth = 110; hitBoxHeight = 20;
        } else if (code >= 3 && code <= 6) {
            width = 45; height = 45; hitBoxWidth = 45; hitBoxHeight = 45;
        } else if (code == 7) {
        	width = 330; height = 330; hitBoxWidth = 0; hitBoxHeight = 0;
        } else if(code >= 8 && code <= 12) {
            width = 80; height = 80; hitBoxWidth = 0; hitBoxHeight = 0;
        } else if(code == 13) {
            width = 80; height = 80; hitBoxWidth = 80; hitBoxHeight = 80;
        } else if(code == 14) {
            width = 20; height = 20; hitBoxWidth = 20; hitBoxHeight = 20;
        } else if(code == 15) {
            width = 40; height = 40; hitBoxWidth = 40; hitBoxHeight = 40;
        } else if(code == 16) {
            width = 30; height = 30; hitBoxWidth = 30; hitBoxHeight = 30;
        }
    }
	
	public Player getPlayer() { // JPanel에서 플레이어 객체를 찾아 가져옴
		Player pl = null;
		for(Component jl : board.getComponents()) {
			if(jl instanceof Player) {
				pl = (Player)jl;
			}
		}
		if(pl == null)
			handleError("Player가 감지되지 않음");
		return pl;
	}
	
	public boolean check() { // 총알이 삭제될 조건을 체크
		int x = this.getX();
		int y = this.getY();
		int px = player.getX();
		int py = player.getY();
		if(y > 700) // 화면 끝까지 도달
			return true;
		else if((x > px - hitBoxWidth) && (x < px + 45) && (y > py - hitBoxHeight) && (y < py + 45)) { // 플레이어에게 맞음
			player.damaged();
			return true;
		} else
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
