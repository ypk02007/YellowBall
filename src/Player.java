import javax.swing.*;

public class Player extends JLabel{
	private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;

    private int life = 3;
    private boolean fire = false;
    private boolean invincible = false; // true가 되면 적의 공격을 안 받음
    private boolean control = false; // true가 되어야 이동, 공격 가능
    
    private int[] moveMax = null; // 이동범위 제한 {up, down, left, right}
    private JPanel board = null;
    
    public Player(JPanel board) {
    	this.setIcon(new ImageIcon("graphics/player45.png"));
    	this.setLocation(275, 650);
    	this.setSize(45, 45);
    	this.board = board;
    	this.board.add(this);
    	movingLoop();
    	fireLoop();
    	restrictMove();
    }
    
    public void setControlFlag(boolean flag) {control = flag;}
    
    public void restrictMove() { // 이동범위 제한 설정
    	moveMax = new int[4];
    	moveMax[0] = 100;
    	moveMax[1] = 650;
    	moveMax[2] = 5;
    	moveMax[3] = 555;
    }
    
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
    
    void fireLoop() {
        FireThread th = new FireThread();
        th.start();
    }
    
    public void movingPosition() { // 상하좌우에 관련된 flag가 true가 되면 그 방향으로 m 만큼 이동
    	if(!control) // control이 false면 이동 불가
    		return;
    	int x = this.getX();
    	int y = this.getY();
    	int m = 5; // 메소드 호출 당 이동 거리
    	
        if(y > moveMax[0] && up) {
        	y -= m;
            this.setLocation(x, y);
        }
        if(y < moveMax[1] && down) {
            y += m;
            this.setLocation(x, y);
        }
        if(x > moveMax[2] && left) {
            x -= m;
            this.setLocation(x, y);
        }
        if(x < moveMax[3] && right) {
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
    
    public void newBullet() {
    	if(fire)
    		new BulletP(board, this);
    	//System.out.println("총알 발사");
    }
    
    class FireThread extends Thread { // z키가 눌려있으면 0.05초 마다 총알 발사
        public void run() {
        	int period = 0;
            while(life > 0) {
                try {
                	if(period == 10) {
                		newBullet();
                		period = 0;
                	}
                	sleep(10);
                	period++;
                } catch (Exception e) {
                    handleError(e.getMessage());
                }
            }
        }
    }
}
