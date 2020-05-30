package com.yellowball.enemy;

import com.yellowball.BulletE;
import com.yellowball.Player;

public interface Enemy {
	public int enemyX();
	public int enemyY();
	public void damaged();
	public void executePattern();
	public void moveBullets(Player player);
	public void removeBullet(BulletE be);
}
