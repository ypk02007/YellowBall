import javax.swing.*;

public class Enemy2 extends JLabel implements Enemy {
	private JPanel board = null;
	private int hp = 500;
	private JLabel hpBar = null;
	private SEPlayer se = null;
	
	public Enemy2(JPanel board) {
		this.setIcon(new ImageIcon("graphics/enemy2/enemy2a.png"));
    	this.setLocation(275, 10);
    	this.setSize(75, 100);
    	this.board = board;
    	this.board.add(this);
    	hpBar = new JLabel(new ImageIcon("graphics/hpBar.png"));
    	hpBar.setLocation(610, 45);
    	hpBar.setSize(30, 500);
    	this.board.add(hpBar);
    	se = new SEPlayer();
	}
	
	public void changeImage(int code) {
        switch(code) {
            case 0: // �⺻��
            	this.setIcon(new ImageIcon("graphics/enemy2/enemy2a.png"));
            	break;
            case 1: // ���� 1
            	this.setIcon(new ImageIcon("graphics/enemy2/enemy2b.png"));
            	break;
            case 2: // ���� 2
            	this.setIcon(new ImageIcon("graphics/enemy2/enemy2c.png"));
            	break;
            case 3: // ���� 3
            	this.setIcon(new ImageIcon("graphics/enemy2/enemy2d.png"));
            	break;
            case 4: // hp�� 0
            	this.setIcon(new ImageIcon("graphics/enemy2/enemy2a.png"));
            	break;
        }
    }
	
	public int enemyX() {return this.getX();}
	public int enemyY() {return this.getY();}
	
	public void damaged() {
		if(hp > 0) {
			hp--;
			int d = 500 - hp;
			hpBar.setLocation(610, 45 + d);
	    	hpBar.setSize(30, hp);
		}
		if(hp == 0) {
			hp = -1;
			changeImage(5);
		}
	}
	
	public int randomPattern(int prevPattern) { // ���� ������ �������� ���Ѵ�, ������ ������ �����Ѵ�.
        double ran;
        int pattern;
        while(true) {
            ran = Math.random();
            pattern = (int) (ran * 3) + 1; // 1~3�� �� ��������
            if(pattern != prevPattern) // ������ ���ϰ� �޶�� ���� ����
                break;
        }
        return pattern;
    }
	
	public void fireLoop(int code) { // ���� ���ϵ� �� �������� �ϳ� ����
        int pattern = randomPattern(code);
		//int pattern = 2;
        switch(pattern) {
            case 1:
            	FireThread1 th1 = new FireThread1();
            	th1.start();
                break;
            case 2:
            	FireThread2 th2 = new FireThread2();
            	th2.start();
                break;
            case 3:
            	FireThread1 th3 = new FireThread1();
            	th3.start();
                break;
        }
    }
	
	public void handleError(String msg) { // ���� ó��
		System.out.println(msg);
		System.exit(1);
	}
	
	class FireThread1 extends Thread {
        public void run() {
        	int x = 0;
        	int y = 0;
        	changeImage(1);
            try {
                se.play(9);
                Thread.sleep(700);
                MovingThread th = new MovingThread();
                th.start();
                for(int i = 0; i < 5; i++) {
                	x = enemyX(); y = enemyY();
                    new BulletE(board, x - 25, y + 40, -20 + i * 4,  i * 4, 16);
                    Thread.sleep(1);
                    new BulletE(board, x + 50, y + 40, 20 - i * 4, i * (-4), 16);
                    se.play(10);
                    Thread.sleep(100);
                }
                for(int i = 0; i < 5; i++) {
                	x = enemyX(); y = enemyY();
                    new BulletE(board, x - 25, y + 40, i * 4,  20 + i * (-4), 16);
                    Thread.sleep(1);
                    new BulletE(board, x + 50, y + 40, i * (-4), -20 + i * 4, 16);
                    se.play(10);
                    Thread.sleep(100);
                }
                for(int i = 0; i < 5; i++) {
                	x = enemyX(); y = enemyY();
                    new BulletE(board, x + 50, y + 40, 20 - i * 4, i * (-4), 16);
                    Thread.sleep(1);
                    new BulletE(board, x - 25, y + 40, -20 + i * 4,  i * 4, 16);
                    se.play(10);
                    Thread.sleep(100);
                }
                for(int i = 0; i < 5; i++) {
                	x = enemyX(); y = enemyY();
                    new BulletE(board, x + 50, y + 40, i * (-4), -20 + i * 4, 16);
                    Thread.sleep(1);
                    new BulletE(board, x - 25, y + 40, i * 4,  20 + i * (-4), 16);
                    se.play(10);
                    Thread.sleep(100);
                }
                
                changeImage(0);
                Thread.sleep(2000);
                fireLoop(1);
            } catch (Exception e) {
            	handleError(e.getMessage());
            }
        }
    }
	
	public void setLocationForThread(int x, int y) {this.setLocation(x, y);}
	
    class MovingThread extends Thread {
        public void run() {
            try {
                for(int i = 0; i < 100; i++) {
                    if(i < 50)
                    	setLocationForThread(enemyX(), enemyY() + 10);
                    else
                    	setLocationForThread(enemyX(), enemyY() - 10);
                    Thread.sleep(20);
                }
            } catch (Exception e) {
                handleError(e.getMessage());
            }
        }
    }
    
    class FireThread2 extends Thread {
        public void run() {
        	int x = 0;
        	int y = 0;
        	changeImage(2);
            try {
                se.play(13);
                Thread.sleep(1000);
                se.play(14);
                for(int i = 0; i < 50; i++) {
                	x = enemyX();
                	y = enemyY();
                    new BulletE(board, x + 10, y + 40, randomRapid(), 16, 14);
                    Thread.sleep(50);
                    if(i % 2 == 0)
                    	setLocationForThread(x + 4, y);
                    else
                    	setLocationForThread(x - 4, y);
                }
                
                changeImage(0);
                Thread.sleep(2000);
                fireLoop(2);
            } catch (Exception e) {
                handleError(e.getMessage());
            }
        }
        
        int randomRapid() {
            double ran = Math.random();
            int n = (int) (ran * 24) - 12;
            return n;
        }
    }
}
