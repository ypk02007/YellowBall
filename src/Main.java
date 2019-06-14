import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class Main extends JFrame {
	private Container c = null;
	private JLabel background = null;
	private BGMPlayer bgm = null;
	private HashMap<String, JLabel> sprites = new HashMap<String, JLabel>();
	
	public Main() {
		setTitle("분노의 노란공");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon("graphics/icon.png").getImage());
		setSize(650, 730);
		setResizable(false);
		
		c = getContentPane();
		c.setLayout(null);
		
		background = new JLabel(new ImageIcon("graphics/bg/title.png"));
		background.setSize(650, 700);
		background.setLocation(0, 0);
		c.add(background);
		
		setSelectIcons();
		
		bgm = new BGMPlayer(0, Config.getInstance().getbgmVolume());
		bgm.loop();
		
		setVisible(true);
		
		requestFocus(true);
		addKeyListener(new Controller());
	}
	
	public void setSelectIcons() {
		JLabel face1 = new JLabel(new ImageIcon("graphics/face/enemy1.png"));
		JLabel face2 = new JLabel(new ImageIcon("graphics/face/enemy2.png"));
		JLabel face3 = new JLabel(new ImageIcon("graphics/face/enemy3.png"));
		JLabel face4 = new JLabel(new ImageIcon("graphics/face/enemy4.png"));
		JLabel select = new JLabel(new ImageIcon("graphics/select.png"));
		JLabel intro = new JLabel(new ImageIcon());
		int horizon = 450;
		int gap = 150;
		
		face1.setSize(100, 100);
		face2.setSize(100, 100);
		face3.setSize(100, 100);
		face4.setSize(100, 100);
		select.setSize(110, 110);
		intro.setSize(550, 300);
		
		face1.setLocation(50, horizon);
		face2.setLocation(50 + gap * 1, horizon);
		face3.setLocation(50 + gap * 2, horizon);
		face4.setLocation(50 + gap * 3, horizon);
		intro.setLocation(50, 100);
		
		background.add(face1);
		background.add(face2);
		background.add(face3);
		background.add(face4);
		background.add(select);
		background.add(intro);
		
		face1.addMouseListener(new SelectEnemyEvent());
		face2.addMouseListener(new SelectEnemyEvent());
		face3.addMouseListener(new SelectEnemyEvent());
		face4.addMouseListener(new SelectEnemyEvent());
		
		face1.setVisible(false);
		face2.setVisible(false);
		face3.setVisible(false);
		face4.setVisible(false);
		select.setVisible(false);
		intro.setVisible(false);
		
		sprites.put("face1", face1);
		sprites.put("face2", face2);
		sprites.put("face3", face3);
		sprites.put("face4", face4);
		sprites.put("select", select);
		sprites.put("intro", intro);
	}
	
	class SelectEnemyEvent extends MouseAdapter {
		public void mouseEntered(MouseEvent e) {
			int code = Config.getInstance().getBackgroundCode();
			
			if(code == 1) {
				JLabel jl = (JLabel)e.getSource();
				int x = jl.getX();
				int y = jl.getY();
				JLabel select = sprites.get("select");
				JLabel intro = sprites.get("intro");
				
				select.setLocation(x - 5, y - 5);
				select.setVisible(true);
				switch(x) {
				case 50:
					intro.setIcon(new ImageIcon("graphics/intro/intro1.png"));
					intro.setVisible(true);
					break;
				case 200:
					intro.setIcon(new ImageIcon("graphics/intro/intro2.png"));
					intro.setVisible(true);
					break;
				case 350:
					intro.setIcon(new ImageIcon("graphics/intro/intro3.png"));
					intro.setVisible(true);
					break;
				}
			}
		}
		public void mouseExited(MouseEvent e) {
			int code = Config.getInstance().getBackgroundCode();
			
			if(code == 1) {
				JLabel select = sprites.get("select");
				JLabel intro = sprites.get("intro");
				select.setVisible(false);
				intro.setVisible(false);
			}
		}
	}
	
	public void changeBackground(int code) {
		switch(code) {
		case 0:
			background.setIcon(new ImageIcon("graphics/bg/title.png"));
			break;
		case 1:
			background.setIcon(new ImageIcon("graphics/bg/select.png"));
			sprites.get("face1").setVisible(true);
			sprites.get("face2").setVisible(true);
			sprites.get("face3").setVisible(true);
			sprites.get("face4").setVisible(true);
			break;
		}
	}
	
	class Controller extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch(keyCode) {
			case KeyEvent.VK_ENTER:
				Config.getInstance().setBackgroundCode();
				changeBackground(Config.getInstance().getBackgroundCode());
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
