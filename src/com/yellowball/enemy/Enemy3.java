package com.yellowball.enemy;

import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.yellowball.BulletE;
import com.yellowball.Player;
import com.yellowball.sound.SEPlayer;

@SuppressWarnings("serial")
public class Enemy3 extends JLabel implements Enemy {
	private JPanel board = null;
	private int hp = 500;
	private JLabel hpBar = null;

	private Player player = null;
	
	private CopyOnWriteArrayList<BulletE> bullets = null;
	private int pattern = 0;
	private boolean patternOn = false;
	private int patternCounter = 0;
	private int moveBulletsCounter = 0;
	
	private int safeCard = -1;
	
	private SEPlayer se = null;
	
	public Enemy3(JPanel board, Player player) {
		this.setIcon(new ImageIcon("graphics/enemy3/enemy3.png"));
    	this.setLocation(275, 10);
    	this.setSize(75, 100);
    	this.board = board;
    	this.board.add(this);
    	this.player = player;
    	hpBar = new JLabel(new ImageIcon("graphics/hpBar.png"));
    	hpBar.setLocation(610, 45);
    	hpBar.setSize(30, 500);
    	this.board.add(hpBar);
    	se = new SEPlayer();
    	bullets = new CopyOnWriteArrayList<BulletE>();
	}
	
	/*
	 * private void changeImage(int code) { switch(code) { case 0: // 기본값
	 * this.setIcon(new ImageIcon("graphics/enemy2/enemy2a.png")); break; case 1: //
	 * 패턴 1 this.setIcon(new ImageIcon("graphics/enemy2/enemy2b.png")); break; case
	 * 2: // 패턴 2 this.setIcon(new ImageIcon("graphics/enemy2/enemy2c.png")); break;
	 * case 3: // 패턴 3 this.setIcon(new ImageIcon("graphics/enemy2/enemy2d.png"));
	 * break; case 4: // hp가 0 this.setIcon(new
	 * ImageIcon("graphics/enemy2/enemy2e.png")); break; } }
	 */
	
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
			//changeImage(5);
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
    }
	
	public void executePattern() {
		patternCounter++;
		if(!patternOn) {
			randomPattern();
		}
		switch(pattern) {
        case 1:
        	pattern2();
            break;
        case 2:
        	pattern2();
            break;
        case 3:
        	pattern2();
            break;
		}
	}
	
	private void pattern1() {
		if(patternCounter == 1) {
			//changeImage(1);
			se.play(15);
		}
		
		if(patternCounter%60 == 0 && patternCounter <= 360) {
			setSafeCard();
			se.play(16);
			for(int i = 0; i < 9; i++) {
				if(i == safeCard) {
					bullets.add(new BulletE(this, board, 10 + i * 66, 100, 0, 15, randomSafeCardImg()));
				} else {
					bullets.add(new BulletE(this, board, 10 + i * 66, 100, 0, 15, 21));
				}
			}
		}
		
        if(patternCounter == 361) {
        	//changeImage(0);
        } else if(patternCounter == 560) {
        	patternCounter = 0;
			patternOn = false;
        }
	}
	
	private void setSafeCard() { // 안전한 카드의 위치 설정
		int prevPosition = safeCard;
		switch(prevPosition) {
		case -1:
			double ran = Math.random();
	        safeCard = (int) (ran * 5) + 1;
	        break;
		case 0:
			safeCard = 1;
			break;
		case 9:
			safeCard = 8;
			break;
		default:
			safeCard = leftOrRight(prevPosition);
		}
	}
	
	private int leftOrRight(int position) { // 다음 안전한 카드의 위치를 가까운 곳으로 유도
		double ran = Math.random();
        int n = (int) (ran * 2);
        
        return (n == 0) ? (position - 1) : (position + 1);
	}
	
	private int randomSafeCardImg() { // 무작위 안전한 카드 이미지
        double ran = Math.random();
        int n = (int) (ran * 4);
        n += 17;

        return n;
    }
	
	private void pattern2() {
		if(patternCounter == 1) {
			//changeImage(2);
			se.play(17);
		}
		
		if(patternCounter <= 320 && patternCounter%80 != 0 && patternCounter%40 == 0) {
			se.play(18);
			// 좌측에서
            bullets.add(new BulletE(this, board, 30, 100, 12, 6, 23));
            bullets.add(new BulletE(this, board, 30, 100, 10, 8, 23));
            bullets.add(new BulletE(this, board, 30, 100, 8, 10, 23));
            bullets.add(new BulletE(this, board, 30, 100, 6, 12, 23));
            // 우측에서
			bullets.add(new BulletE(this, board, 540, 100, -12, 6, 23));
	        bullets.add(new BulletE(this, board, 540, 100, -10, 8, 23));
	        bullets.add(new BulletE(this, board, 540, 100, -8, 10, 23));
	        bullets.add(new BulletE(this, board, 540, 100, -6, 12, 23));
		} else if(patternCounter <= 320 && patternCounter%80 == 0 && patternCounter%40 == 0) {
			se.play(18);
			// 좌측에서
            bullets.add(new BulletE(this, board, 30, 100, 11, 7, 23));
            bullets.add(new BulletE(this, board, 30, 100, 9, 9, 23));
            bullets.add(new BulletE(this, board, 30, 100, 7, 11, 23));
            // 우측에서
			bullets.add(new BulletE(this, board, 540, 100, -11, 7, 23));
	        bullets.add(new BulletE(this, board, 540, 100, -9, 9, 23));
	        bullets.add(new BulletE(this, board, 540, 100, -7, 11, 23));
		}
		
		if(patternCounter == 320) {
			//changeImage(0);
		} else if(patternCounter == 520) {
			patternCounter = 0;
			patternOn = false;
		}
	}
	
	public void moveBullets() { // 총알 이동
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