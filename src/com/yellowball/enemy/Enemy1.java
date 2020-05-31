package com.yellowball.enemy;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.yellowball.BulletE;
import com.yellowball.Player;
import com.yellowball.sound.SEPlayer;

@SuppressWarnings("serial")
public class Enemy1 extends JLabel implements Enemy {
	private JPanel board = null;
	private int hp = 500;
	private JLabel hpBar = null;
	
	private CopyOnWriteArrayList<BulletE> bullets = null;
	private int pattern = 0;
	private boolean patternOn = false;
	private int patternCounter = 0;
	private int moveBulletsCounter = 0;
	
	private boolean[] noteCheck = {true, true, true, true, true};
	
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
    	bullets = new CopyOnWriteArrayList<BulletE>();
	}
	
	private void changeImage(int code) {
        switch(code) {
            case 0: // �⺻��
            	this.setIcon(new ImageIcon("graphics/enemy1/enemy1a.png"));
            	break;
            case 1: // ���� 1
            	this.setIcon(new ImageIcon("graphics/enemy1/enemy1b.png"));
            	break;
            case 2: // ���� 2
            	this.setIcon(new ImageIcon("graphics/enemy1/enemy1c.png"));
            	break;
            case 3: // ���� 3
            	this.setIcon(new ImageIcon("graphics/enemy1/enemy1d.png"));
            	break;
            case 4: // hp�� 0
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
		if(hp <= 0) {
			hp = -1;
			changeImage(5);
		}
	}
	
	private void randomPattern() { // ���� ������ �������� ���Ѵ�, ������ ������ �����Ѵ�.
        double ran;
        int pattern;
        while(true) {
            ran = Math.random();
            pattern = (int) (ran * 3) + 1; // 1~3�� �� ��������
            if(pattern != this.pattern) // ������ ���ϰ� �޶�� ���� ����
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
        	pattern1();
            break;
        case 2:
        	pattern2();
            break;
        case 3:
        	pattern1();
            break;
		}
	}
	
	private void pattern1() {
		if(patternCounter == 1) {
			changeImage(1);
			for(int i = 0; i < noteCheck.length; i++) {
				noteCheck[i] = true;
			}
		}
		
		if(patternCounter%30 == 0 && patternCounter <= 450) {
			int note;
			int temp = preventOverlap();
            if (temp % 2 == 0)
                note = 1;
            else
                note = 2;
            se.play(temp + 1);
            bullets.add(new BulletE(this, board, 10 + temp * 120, 100, 0, 15, note));
		}
		
		if(patternCounter == 451) {
			changeImage(0);
		}
		
		if(patternCounter == 650) {
			patternCounter = 0;
			patternOn = false;
		}
	}
    
	private int preventOverlap() { // ��Ʈ ��ġ �ߺ� ����
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
	
	private void pattern2() {
		if(patternCounter == 1) {
			changeImage(2);
		}
		
		if(patternCounter <= 400 && patternCounter%80 != 0 && patternCounter%40 == 0) {
			se.play(6);
            bullets.add(new BulletE(this, board, enemyX() + 100, enemyY() + 35, 3, 16, 5));
            bullets.add(new BulletE(this, board, enemyX() + 50, enemyY() + 65, 1, 16, 3));
            bullets.add(new BulletE(this, board, enemyX(), enemyY() + 65, -1, 16, 6));
            bullets.add(new BulletE(this, board, enemyX() - 50, enemyY() + 35, -3, 16, 4));
		} else if(patternCounter <= 400 && patternCounter%80 == 0 && patternCounter%40 == 0) {
			se.play(6);
			bullets.add(new BulletE(this, board, enemyX() - 75, enemyY() + 20, -4, 16, 4));
	        bullets.add(new BulletE(this, board, enemyX() - 25, enemyY() + 50, -2, 16, 5));
	        bullets.add(new BulletE(this, board, enemyX() + 25, enemyY() + 80, 0, 16, 3));
	        bullets.add(new BulletE(this, board, enemyX() + 75, enemyY() + 50, 2, 16, 6));
	        bullets.add(new BulletE(this, board, enemyX() + 125, enemyY() + 20, 4, 16, 4));
		}
		
		if(patternCounter == 400) {
			changeImage(0);
		}
		
		if(patternCounter == 600) {
			patternCounter = 0;
			patternOn = false;
		}
	}
	
	public void removeBullet(BulletE be) { // �Ѿ� ����
    	int i = bullets.indexOf(be);
    	if (i >= 0 && be != null)
            bullets.remove(i);
    }
	
	public void moveBullets(Player player) { // �Ѿ� �̵�
    	moveBulletsCounter++;
    	if(moveBulletsCounter == 2) {
	    	for(BulletE b : bullets) {
	    		b.moveBullet(player);
	    	}
	    	moveBulletsCounter = 0;
    	}
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
	
	/*
	
	class FireThread3 extends Thread {
		public void run() {
			changeImage(3);
			try {
				se.play(7);
				sleep(500);
				bullets.add(new Circle(getThis(), board));
				sleep(500);
				bullets.add(new Circle(getThis(), board));
				sleep(500);
				bullets.add(new Circle(getThis(), board));
				sleep(500);
				bullets.add(new Circle(getThis(), board));
				sleep(500);
				bullets.add(new Circle(getThis(), board));
				sleep(500);
				changeImage(0);
                Thread.sleep(2000);
                fireLoop(3);
			} catch (Exception e) {
                handleError(e.getMessage());
            }
		}
	}
	
	class Circle extends BulletE {
		public Circle(Enemy enemy, JPanel board) {
            super(enemy, board, 0, 0, 0, 0, 7);
        }
		
		public void launch(int mx, int my) {
			Player player = getPlayer();
        	int px = player.getX();
        	int py = player.getY();
        	this.setLocation(px - 5, py - 5);
        	LaunchThread th = new LaunchThread();
        	th.start();
		}
		class LaunchThread extends Thread {
			public void run() {
				JLabel outterCircle = new JLabel(new ImageIcon("graphics/bulletE/circle2.png"));
				outterCircle.setSize(120, 120);
				outterCircle.setLocation(getX() - 30, getY() - 30);
				board.add(outterCircle);
				try {
					sleep(80);
					board.remove(outterCircle);
					outterCircle = new JLabel(new ImageIcon("graphics/bulletE/circle3.png"));
					outterCircle.setSize(100, 100);
					outterCircle.setLocation(getX() - 20, getY() - 20);
					board.add(outterCircle);
					board.revalidate();
					board.repaint();
					sleep(80);
					board.remove(outterCircle);
					outterCircle = new JLabel(new ImageIcon("graphics/bulletE/circle4.png"));
					outterCircle.setSize(80, 80);
					outterCircle.setLocation(getX() - 10, getY() - 10);
					board.add(outterCircle);
					board.revalidate();
					board.repaint();
					sleep(80);
					board.remove(outterCircle);
					outterCircle = new JLabel(new ImageIcon("graphics/bulletE/circle5.png"));
					outterCircle.setSize(60, 60);
					outterCircle.setLocation(getX(), getY());
					board.add(outterCircle);
					board.revalidate();
					board.repaint();
					se.play(8);
					boolean chk = check();
					sleep(80);
					board.remove(outterCircle);
					board.revalidate();
					board.repaint();
				} catch (InterruptedException e) {
					handleError(e.getMessage());
				}
				deleteThis();
			}
		}
	}*/
}
