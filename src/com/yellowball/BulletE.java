package com.yellowball;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.yellowball.enemy.Enemy;

@SuppressWarnings("serial")
public class BulletE extends JLabel {
	private JPanel board = null;
	private Enemy enemy = null;
	private int width = 0;
	private int height = 0;
	private int hitBoxWidth = 0;
	private int hitBoxHeight = 0;
	protected int mx = 0;
	protected int my = 0;
	
	public BulletE(Enemy enemy, JPanel board, int x, int y, int mx, int my, int code) {
		this.board = board;
		this.enemy = enemy;
		setBulletImage(code);
		setBulletSize(code);
		this.setSize(width, height);
		this.setLocation(x, y);
		board.add(this);
		this.mx = mx;
		this.my = my;
	}
	
	public void setBulletImage(int code) {
        switch(code) {
            case 1: // Enemy1의 패턴1-1
            	this.setIcon(new ImageIcon("graphics/bulletE/note1.png"));
            	break;
            case 2: // Enemy1의 패턴1-2
            	this.setIcon(new ImageIcon("graphics/bulletE/note2.png"));
            	break;
            case 3: // Enemy1의 패턴2-1
            	this.setIcon(new ImageIcon("graphics/bulletE/arrow1.png"));
            	break;
            case 4: // Enemy1의 패턴2-2
            	this.setIcon(new ImageIcon("graphics/bulletE/arrow2.png"));
            	break;
            case 5: // Enemy1의 패턴2-3
            	this.setIcon(new ImageIcon("graphics/bulletE/arrow3.png"));
            	break;
            case 6: // Enemy1의 패턴2-4
            	this.setIcon(new ImageIcon("graphics/bulletE/arrow4.png"));
            	break;
            case 7: // Enemy1의 패턴3-1
            	this.setIcon(new ImageIcon("graphics/bulletE/drum1.png"));
            	break;
            case 8: // Enemy1의 패턴3-2
            	this.setIcon(new ImageIcon("graphics/bulletE/drum2.png"));
            	break;
            case 14: // Enemy2의 패턴1
            	this.setIcon(new ImageIcon("graphics/bulletE/rapid.png"));
            	break;
            case 15: // Enemy2의 패턴2
            	this.setIcon(new ImageIcon("graphics/bulletE/fireball.png"));
            	break;
            case 16: // Enemy3의 패턴3
            	this.setIcon(new ImageIcon("graphics/bulletE/whirl.png"));
            	break;
            default:
            	this.setIcon(new ImageIcon("graphics/bulletE/bulletE.png"));
        }
    }
	
	public void setBulletSize(int code) {
		if(code >= 1 && code <= 2) { // 노트
            width = 110; height = 20; hitBoxWidth = 110; hitBoxHeight = 20;
        } else if (code >= 3 && code <= 6) { // 화살표
            width = 45; height = 45; hitBoxWidth = 45; hitBoxHeight = 45;
        } else if (code == 7 || code == 8) { // 원
        	width = 60; height = 60; hitBoxWidth = 60; hitBoxHeight = 60;
        } else if(code == 14) { // 기관총
            width = 20; height = 20; hitBoxWidth = 20; hitBoxHeight = 20;
        } else if(code == 15) { // 파이어볼
            width = 40; height = 40; hitBoxWidth = 40; hitBoxHeight = 40;
        } else if(code == 16) { // 회오리난사
            width = 30; height = 30; hitBoxWidth = 30; hitBoxHeight = 30;
        }
    }
	
	public boolean check(Player player) { // 총알이 삭제될 조건을 체크
		int x = this.getX();
		int y = this.getY();
		int px = player.getX();
		int py = player.getY();
		if(y > 700 || y < 0 || x > 600 || x < (-width)) // 화면 밖으로 나감
			return true;
		else if(calculateCollision(x, y, px, py)) { // 플레이어에게 맞음
			player.damaged();
			return true;
		} else
			return false;
	}
	
	public boolean calculateCollision(int x, int y, int px, int py) {
		if(hitBoxWidth != hitBoxHeight) { // 사각형 판정
			return (x > px - hitBoxWidth) && (x < px + 45) && (y > py - hitBoxHeight) && (y < py + 45);
		} else { // 원형 판정
			double xCenter = x + hitBoxWidth/2;
			double yCenter = y + hitBoxHeight/2;
			double pxCenter = px + 45/2;
			double pyCenter = py + 45/2;
			double colDist = (45 + hitBoxWidth)/2;
			
			double dist = Math.sqrt(Math.pow(xCenter - pxCenter, 2) + Math.pow(yCenter - pyCenter, 2));
			
			return (dist < colDist);
		}
	}
	
	public void deleteThis() { // 총알 삭제
		board.remove(this);
		board.revalidate();
		board.repaint();
		enemy.removeBullet(this);
	}
	
	public void moveBullet(Player player) { // 총알 이동
		if(check(player)) {
			deleteThis();
		} else {
			int x = this.getX();
			int y = this.getY();
			this.setLocation(x + mx, y + my);
		}
	}
}
