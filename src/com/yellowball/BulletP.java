package com.yellowball;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.yellowball.enemy.Enemy;

@SuppressWarnings("serial")
public class BulletP extends JLabel {
	private JPanel board = null;
	private Player player = null;
	private Enemy enemy = null;
	
	public BulletP(JPanel board, Player player, Enemy enemy) {
		this.player = player;
		this.enemy = enemy;
		int x = player.getX() + 12;
		int y = player.getY() - 10;
		this.setIcon(new ImageIcon("graphics/bullet.png"));
    	this.setLocation(x, y);
    	this.setSize(20, 20);
    	this.board = board;
    	board.add(this);
	}
	
	public boolean check() { // 총알이 삭제될 조건을 체크
		int x = this.getX();
		int y = this.getY();
		int ex = enemy.enemyX();
		int ey = enemy.enemyY();
		if(y < 5) // 화면 끝까지 도달
			return true;
		else if((x > ex - 20) && (x < ex + 75) && (y > ey - 20) && (y < ey + 100)) { // 적에게 맞음
			enemy.damaged();
			return true;
		} else if(player.isLifeZero()) { // life가 0
			return true;
		} else
			return false;
	}
	
	public void deleteThis() { // 총알 삭제
		board.remove(this);
		board.revalidate();
		board.repaint();
		player.removeBullet(this);
	}
	
	public void moveBullet() { // 총알 이동
		if(check()) {
			deleteThis();
		} else {
			int m = 15; // 총알 이동 거리
			int x = this.getX();
			int y = this.getY() - m;
			this.setLocation(x, y);
		}
	}
}
