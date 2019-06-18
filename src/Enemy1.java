import javax.swing.*;

public class Enemy1 extends JLabel implements Enemy{
	private JPanel board = null;
	private int hp = 500;
	private JLabel hpBar = null;
	
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
	}
	
	public void damaged() {
		hp--;
		int d = 500 - hp;
		hpBar.setLocation(610, 45 + d);
    	hpBar.setSize(30, hp);
	}
	
	public int enemyX() {return this.getX();}
	public int enemyY() {return this.getY();}
}
