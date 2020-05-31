package com.yellowball.enemy;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.yellowball.BulletE;
import com.yellowball.Player;
import com.yellowball.sound.SEPlayer;

@SuppressWarnings("serial")
public class Enemy2 extends JLabel implements Enemy {
	private JPanel board = null;
	private int hp = 500;
	private JLabel hpBar = null;
	
	private CopyOnWriteArrayList<BulletE> bullets = null;
	private int pattern = 0;
	private boolean patternOn = false;
	private int patternCounter = 0;
	private int moveBulletsCounter = 0;
	
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
    	bullets = new CopyOnWriteArrayList<BulletE>();
	}
	
	private void changeImage(int code) {
        switch(code) {
            case 0: // 기본값
            	this.setIcon(new ImageIcon("graphics/enemy2/enemy2a.png"));
            	break;
            case 1: // 패턴 1
            	this.setIcon(new ImageIcon("graphics/enemy2/enemy2b.png"));
            	break;
            case 2: // 패턴 2
            	this.setIcon(new ImageIcon("graphics/enemy2/enemy2c.png"));
            	break;
            case 3: // 패턴 3
            	this.setIcon(new ImageIcon("graphics/enemy2/enemy2d.png"));
            	break;
            case 4: // hp가 0
            	this.setIcon(new ImageIcon("graphics/enemy2/enemy2e.png"));
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
	
	public boolean isHpZero() {
		return hp <= 0;
	}
	
	private void randomPattern() { // 공격 패턴을 무작위로 정한다, 직전의 패턴은 제외한다.
		double ran;
        int pattern;
        while(true) {
            ran = Math.random();
            pattern = (int) (ran * 3) + 1; // 1~3의 수 무작위로
            if(pattern != this.pattern) // 이전의 패턴과 달라야 리턴 가능
                break;
        }
        
        patternOn = true;
        this.pattern = pattern;
        this.pattern = 3;
    }
	
	public void executePattern() {
		patternCounter++;
		if(!patternOn) {
			randomPattern();
		}
		switch(pattern) {
        case 1:
        	pattern1();
            break;
        case 2:
        	pattern2();
            break;
        case 3:
        	pattern3();
            break;
		}
	}
	
	private void pattern1() {
		if(patternCounter == 1) {
			changeImage(1);
			se.play(9);
		}
		
		// 총알 발사
		if(patternCounter >= 70 && patternCounter <= 110 && patternCounter%10 == 0) {
			int i = (patternCounter - 70)/10;
			bullets.add(new BulletE(this, board, enemyX() - 25, enemyY() + 40, -20 + i * 4,  i * 4, 16));
			bullets.add(new BulletE(this, board, enemyX() + 50, enemyY() + 40, 20 - i * 4, i * (-4), 16));
			se.play(10);
		} else if(patternCounter >= 120 && patternCounter <= 160 && patternCounter%10 == 0) {
			int i = (patternCounter - 120)/10;
			bullets.add(new BulletE(this, board, enemyX() - 25, enemyY() + 40, i * 4,  20 + i * (-4), 16));
            bullets.add(new BulletE(this, board, enemyX() + 50, enemyY() + 40, i * (-4), -20 + i * 4, 16));
			se.play(10);
		} else if(patternCounter >= 170 && patternCounter <= 210 && patternCounter%10 == 0) {
			int i = (patternCounter - 170)/10;
			bullets.add(new BulletE(this, board, enemyX() + 50, enemyY() + 40, 20 - i * 4, i * (-4), 16));
            bullets.add(new BulletE(this, board, enemyX() - 25, enemyY() + 40, -20 + i * 4,  i * 4, 16));
			se.play(10);
		} else if(patternCounter >= 220 && patternCounter <= 260 && patternCounter%10 == 0) {
			int i = (patternCounter - 220)/10;
			bullets.add(new BulletE(this, board, enemyX() + 50, enemyY() + 40, i * (-4), -20 + i * 4, 16));
            bullets.add(new BulletE(this, board, enemyX() - 25, enemyY() + 40, i * 4,  20 + i * (-4), 16));
			se.play(10);
		}
		
		// 이동
		if(patternCounter >= 70 && patternCounter < 170 && patternCounter%2 == 0) {
			this.setLocation(enemyX(), enemyY() + 10);
		} else if(patternCounter >= 170 && patternCounter < 270 && patternCounter%2 == 0) {
			this.setLocation(enemyX(), enemyY() - 10);
		}
		
        if(patternCounter == 270) {
        	changeImage(0);
        } else if(patternCounter == 470) {
        	patternCounter = 0;
			patternOn = false;
        }
	}
	
	private void pattern2() {
		if(patternCounter == 1) {
			changeImage(2);
			se.play(13);
		} else if(patternCounter == 100) {
        	se.play(14);
        }
        
        if(patternCounter >= 100 && patternCounter < 350 && patternCounter%10 == 0 && patternCounter%5 == 0) {
        	bullets.add(new BulletE(this, board, enemyX() + 10, enemyY() + 40, randomRapid(), 16, 14));
        	setLocation(enemyX() + 4, enemyY());
        } else if(patternCounter >= 100 && patternCounter < 350 && patternCounter%10 != 0 && patternCounter%5 == 0) {
        	bullets.add(new BulletE(this, board, enemyX() + 10, enemyY() + 40, randomRapid(), 16, 14));
        	setLocation(enemyX() - 4, enemyY());
        }
        
        if(patternCounter == 350) {
        	changeImage(0);
        } else if(patternCounter == 550) {
        	patternCounter = 0;
			patternOn = false;
        }
	}
	
	private void pattern3() {
		if(patternCounter == 1) {
			changeImage(3);
			se.play(11);
		}
        
		if(patternCounter >= 120 && patternCounter <= 417 && (patternCounter-120)%33 == 0) {
			se.play(12);
            bullets.add(new Fireball(this, board, enemyX() - 20, enemyY() + 30));
            bullets.add(new Fireball(this, board, enemyX() + 55, enemyY() + 30));
		}
		
        if(patternCounter == 417) {
        	changeImage(0);
        } else if(patternCounter == 617) {
        	patternCounter = 0;
			patternOn = false;
        }
	}
	
	private int randomRapid() {
        double ran = Math.random();
        int n = (int) (ran * 24) - 12;
        return n;
    }

	public class Fireball extends BulletE {
		private boolean targetPoint = false;
		
        Fireball(Enemy enemy, JPanel board, int x, int y) {
            super(enemy, board, x, y, 0, 16, 15);
        }
        
        public void moveBullet(Player player) {
        	if(check(player)) {
    			deleteThis();
    		} else {
    			if(!targetPoint) {
            		mx = target(player);
            		targetPoint = true;
            	}
    			int x = this.getX();
    			int y = this.getY();
    			this.setLocation(x + mx, y + my);
    		}
        }

        int target(Player player) {
        	int px = player.getX();
        	int py = player.getY();
        	int y = this.getY();
            if(px < 300)
                return (-1) * (300 - px) * 12 / (py - y);
            else
                return (px - 300) * 12 / (py - y);
        }
	}
	
	public void moveBullets(Player player) { // 총알 이동
		moveBulletsCounter++;
    	if(moveBulletsCounter == 2) {
	    	for(BulletE b : bullets) {
	    		b.moveBullet(player);
	    	}
	    	moveBulletsCounter = 0;
    	}
	}

	public void removeBullet(BulletE be) { // 총알 제거
		int i = bullets.indexOf(be);
    	if (i >= 0 && be != null)
            bullets.remove(i);
	}
	
	public void removeAllGraphics() {
    	for(BulletE b : bullets) {
    		board.remove(b);
    		removeBullet(b);
    	}
    	board.remove(hpBar);
    	board.remove(this);
		board.revalidate();
		board.repaint();
    }
}
