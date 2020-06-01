package com.yellowball.enemy;

import com.yellowball.BulletE;

public interface Enemy {
	public int enemyX();
	public int enemyY();
	public void damaged();
	public void executePattern();
	public void moveBullets();
	public void removeBullet(BulletE be);
	public boolean isHpZero();
	public void removeAllGraphics();
}
