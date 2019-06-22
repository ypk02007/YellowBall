import javax.swing.*;

public class Enemy1 extends JLabel implements Enemy {
	private JPanel board = null;
	private int hp = 500;
	private JLabel hpBar = null;
	private SEPlayer se = null;
	
	public Enemy1(JPanel board) {
		this.setIcon(new ImageIcon("graphics/enemy1/enemy1a.png"));
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
            case 0: // 기본값
            	this.setIcon(new ImageIcon("graphics/enemy1/enemy1a.png"));
            	break;
            case 1: // 패턴 1
            	this.setIcon(new ImageIcon("graphics/enemy1/enemy1b.png"));
            	break;
            case 2: // 패턴 2
            	this.setIcon(new ImageIcon("graphics/enemy1/enemy1c.png"));
            	break;
            case 3: // 패턴 3
            	this.setIcon(new ImageIcon("graphics/enemy1/enemy1d.png"));
            	break;
            case 4: // hp가 0
            	this.setIcon(new ImageIcon("graphics/enemy1/enemy1e.png"));
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
	
	public int randomPattern(int prevPattern) { // 공격 패턴을 무작위로 정한다, 직전의 패턴은 제외한다.
        double ran;
        int pattern;
        while(true) {
            ran = Math.random();
            pattern = (int) (ran * 3) + 1; // 1~3의 수 무작위로
            if(pattern != prevPattern) // 이전의 패턴과 달라야 리턴 가능
                break;
        }
        return pattern;
    }
	
	public void fireLoop(int code) { // 공격 패턴들 중 무작위로 하나 시작
        int pattern = randomPattern(code);
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
	
	public void handleError(String msg) { // 오류 처리
		System.out.println(msg);
		System.exit(1);
	}
	
	class FireThread1 extends Thread {
		private boolean[] noteCheck = {true, true, true, true, true};
		
        public void run() {
            int note;
            changeImage(1);
            try {
                for(int i = 0; i < 15; i++) {
                    int temp = preventOverlap();
                    if (temp % 2 == 0)
                        note = 1;
                    else
                        note = 2;
                    se.play(temp + 1);
                    new BulletE(board, 10 + temp * 120, 100, 0, 15, note);
                    Thread.sleep(300);
                }
                changeImage(0);
                Thread.sleep(2000);
                fireLoop(1);
            } catch (Exception e) {
                handleError(e.getMessage());
            }
        }
        
        public int preventOverlap() { // 노트 위치 중복 방지
            double ran;
            int n = 0;

            for(int i = 0; i < 5; i++) {
                if(noteCheck[i])
                    break;
                n++;
            }
            if(n == 5) {
                for(int i = 0; i < 5; i++) {
                	noteCheck[i] = true;
                }
            }

            while(true) {
                ran = Math.random();
                n = (int) (ran * 5);
                if(noteCheck[n]) {
                	noteCheck[n] = false;
                    break;
                }
            }
            return n;
        }
    }
	
	class FireThread2 extends Thread {
        public void run() {
            boolean straight = false;
            int x = enemyX();
            int y = enemyY();
            changeImage(2);
            try {
                for(int i = 0; i < 10; i++) {
                    if(straight) {
                        se.play(6);
                        new BulletE(board, x - 75, y + 20, -4, 16, 4);
                        Thread.sleep(50);
                        new BulletE(board, x - 25, y + 50, -2, 16, 5);
                        Thread.sleep(50);
                        new BulletE(board, x + 25, y + 80, 0, 16, 3);
                        Thread.sleep(50);
                        new BulletE(board, x + 75, y + 50, 2, 16, 6);
                        Thread.sleep(50);
                        new BulletE(board, x + 125, y + 20, 4, 16, 4);
                        Thread.sleep(50);
                        straight = false;
                    } else {
                        se.play(6);
                        new BulletE(board, x + 100, y + 35, 3, 16, 5);
                        Thread.sleep(50);
                        new BulletE(board, x + 50, y + 65, 1, 16, 3);
                        Thread.sleep(50);
                        new BulletE(board, x, y + 65, -1, 16, 6);
                        Thread.sleep(50);
                        new BulletE(board, x - 50, y + 35, -3, 16, 4);
                        Thread.sleep(50);
                        straight = true;
                    }
                    Thread.sleep(300);
                }
                changeImage(0);
                Thread.sleep(2000);
                fireLoop(2);
            } catch (Exception e) {
                handleError(e.getMessage());
            }
        }
    }
}
