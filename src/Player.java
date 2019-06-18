import javax.swing.*;

public class Player extends JLabel{
	private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;

    private int life = 3;
    private boolean fire = false;
    private boolean invincible = false; // true�� �Ǹ� ���� ������ �� ����
    private boolean control = false; // true�� �Ǿ�� �̵�, ���� ����
    
    private int[] moveMax = null; // �̵����� ���� {up, down, left, right}
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
    
    public void restrictMove() { // �̵����� ���� ����
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
    
    public void movingPosition() { // �����¿쿡 ���õ� flag�� true�� �Ǹ� �� �������� m ��ŭ �̵�
    	if(!control) // control�� false�� �̵� �Ұ�
    		return;
    	int x = this.getX();
    	int y = this.getY();
    	int m = 5; // �޼ҵ� ȣ�� �� �̵� �Ÿ�
    	
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
    
    public void handleError(String msg) { // ���� ó��
		System.out.println(msg);
		System.exit(1);
	}
    
    class MovingThread extends Thread { // 0.01�� ���� �������� �����ؼ� �÷��̾� ��ü�� �̵�
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
    	//System.out.println("�Ѿ� �߻�");
    }
    
    class FireThread extends Thread { // zŰ�� ���������� 0.05�� ���� �Ѿ� �߻�
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
