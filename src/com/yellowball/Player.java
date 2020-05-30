package com.yellowball;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.yellowball.enemy.Enemy;
import com.yellowball.sound.SEPlayer;

@SuppressWarnings("serial")
public class Player extends JLabel{
	private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    private int[] moveMax = null; // �̵����� ���� {up, down, left, right}

    private int life = 3;
    private boolean fire = false;
    private boolean invincible = false; // true�� �Ǹ� ���� ������ �� ����
    private boolean control = false; // true�� �Ǿ�� �̵�, ���� ����
    
    private JPanel board = null;
    private JLabel[] heart = null;
    private CopyOnWriteArrayList<BulletP> bullets = null;
    
    private int newBulletCounter = 0;
    private int moveBulletsCounter = 0;
    
    private SEPlayer se = null;
    
    public Player(JPanel board) {
    	this.setIcon(new ImageIcon("graphics/player45.png"));
    	this.setLocation(275, 650);
    	this.setSize(45, 45);
    	this.board = board;
    	board.add(this);
    	setHeart();
    	restrictMove(1);
    	se = new SEPlayer();
    	bullets = new CopyOnWriteArrayList<BulletP>();
    }
    
    public void setControlFlag(boolean flag) {control = flag;}
    
    public boolean isLifeZero() {return (life == 0);}
    
    public void setHeart() {
    	heart = new JLabel[3];
    	for(int i = 0; i < 3; i++) {
    		heart[i] = new JLabel(new ImageIcon("graphics/life.png"));
    		heart[i].setSize(30, 30);
    		heart[i].setLocation(610, 660 - i * 36);
    		board.add(heart[i]);
    	}
    }
    
    public void restrictMove(int code) { // �̵����� ���� ����
    	switch(code) {
    	case 1: // �⺻��
	    	moveMax = new int[4];
	    	moveMax[0] = 100;
	    	moveMax[1] = 650;
	    	moveMax[2] = 5;
	    	moveMax[3] = 555;
	    	break;
    	case 2: // Enemy1�� ����3
	    	moveMax = new int[4];
	    	moveMax[0] = 305;
	    	moveMax[1] = 575;
	    	moveMax[2] = 145;
	    	moveMax[3] = 415;
	    	break;
    	}
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
    
    public void damaged() {
    	if(life > 0 && !invincible) {
    		invincible = true;
            life--;
            loseHeart();

            se.play(0);

            InvincibleTimer th = new InvincibleTimer();
            th.start();
    	}
    	/*if(life == 0) {
    		m.playerLose();
    		board.remove(this);
    		board.revalidate();
    		board.repaint();
    	}*/
    }
    
    public void loseHeart() {
    	board.remove(heart[life]);
		board.revalidate();
		board.repaint();
		heart[life] = null;
    }
    
    public void movePosition() { // �����¿쿡 ���õ� flag�� true�� �Ǹ� �� �������� m ��ŭ �̵�
    	if(!control) // control�� false�� �̵� �Ұ�
    		return;
    	int x = this.getX();
    	int y = this.getY();
    	int m = 3; // �޼ҵ� ȣ�� �� �̵� �Ÿ�
    	
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
    
    public void newBullet(Enemy enemy) { // �Ѿ� ����
    	if(fire) {
    		newBulletCounter++;
    	}
    	if(newBulletCounter == 10) {
    		BulletP bp = new BulletP(board, this, enemy);
    		bullets.add(bp);
    		newBulletCounter = 0;
    	}
    }
    
    public void moveBullets() {
    	moveBulletsCounter++;
    	if(moveBulletsCounter == 2) {
	    	for(BulletP b : bullets) {
	    		b.moveBullet();
	    	}
	    	moveBulletsCounter = 0;
    	}
    }
    
    public void removeBullet(BulletP bp) { // �Ѿ� ����
    	int i = bullets.indexOf(bp);
    	if (i >= 0 && bp != null)
            bullets.remove(i);
    }
    
    public void showInvincible(boolean inv) {
    	if(inv)
    		this.setIcon(new ImageIcon("graphics/player45Invincible.png"));
    	else
    		this.setIcon(new ImageIcon("graphics/player45.png"));
    }
    
    class InvincibleTimer extends Thread { // ������ ������ 1.5�ʰ� ��������
        public void run() {
            try {
            	showInvincible(true);
                Thread.sleep(1500);
                showInvincible(false);
                invincible = false;
            } catch (Exception e) {
                handleError(e.getMessage());
            }
        }
    }
}
