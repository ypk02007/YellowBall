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
            case 1: // 기본값
            	this.setIcon(new ImageIcon("graphics/enemy1/enemy1a.png"));
            	break;
            case 2: // 패턴 1
            	this.setIcon(new ImageIcon("graphics/enemy1/enemy1b.png"));
            	break;
            case 3: // 패턴 2
            	this.setIcon(new ImageIcon("graphics/enemy1/enemy1c.png"));
            	break;
            case 4: // 패턴 3
            	this.setIcon(new ImageIcon("graphics/enemy1/enemy1d.png"));
            	break;
            case 5: // hp가 0
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
	
	public void fireLoop(int code) {
        //int pattern = randomPattern(code);
		int pattern = 1;
        switch(pattern) {
            case 1:
            	FireThread1 th = new FireThread1();
            	th.start();
                break;
        }
    }
	
	class FireThread1 extends Thread {
		private boolean[] noteCheck = {true, true, true, true, true};
		
        public void run() {
            int note;
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
                Thread.sleep(2000);
                fireLoop(1);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Enemy1의 FireThread1");
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
}
