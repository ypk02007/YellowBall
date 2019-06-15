import java.awt.*;
import javax.swing.*;

public class Player extends JLabel{
	private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;

    private int life = 3;
    private boolean fire = false;
    private boolean invincible = false;
    private boolean move = false;
    
    public Player() {
    	this.setIcon(new ImageIcon("graphics/player45.png"));
    	this.setLocation(250, 650);
    	this.setSize(45, 45);
    	movingLoop();
    }
    
    public void setMoveFlag(boolean flag) {move = flag;}
    
    public void keyDown(int code) {
        switch(code) {
            case 1:
                up = true;
                break;
            case 2:
                down = true;
                break;
            case 3:
                left = true;
                break;
            case 4:
                right = true;
                break;
            case 5:
                fire = true;
                break;
        }
    }

    public void keyUp(int code) {
        switch(code) {
            case 1:
                up = false;
                break;
            case 2:
                down = false;
                break;
            case 3:
                left = false;
                break;
            case 4:
                right = false;
                break;
            case 5:
                fire = false;
                break;
        }
    }
    
    void movingLoop() {
        MovingThread th = new MovingThread();
        th.start();
    }
    
    public void movingPosition() { // 상하좌우에 관련된 flag가 true가 되면 그 방향으로 m 만큼 이동
    	if(!move)
    		return;
    	int x = this.getX();
    	int y = this.getY();
    	int m = 5; // 메소드 호출 당 이동 거리
    	
        if(y > 0 && up) {
        	y -= m;
            this.setLocation(x, y);
        }
        if(y < 680 && down) {
            y += m;
            this.setLocation(x, y);
        }
        if(x > 0 && left) {
            x -= m;
            this.setLocation(x, y);
        }
        if(x < 550 && right) {
            x += m;
            this.setLocation(x, y);
        }
    }
    
    public void handleError(String msg) { // 오류 처리
		System.out.println(msg);
		System.exit(1);
	}
    
    class MovingThread extends Thread { // 0.01초 마다 움직임을 감지해서 플레이어 기체를 이동
        public void run() {
            while(life > 0) {
                try {
                    movingPosition();
                    Thread.sleep(10);
                } catch (Exception e) {
                    handleError(e.getMessage());
                }
            }
        }
    }
}
