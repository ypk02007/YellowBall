import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Main extends JFrame {
	private JPanel board = null; // �پ��� JLabel���� ���� JPanel
	private ImageIcon background = null;
	private BGMPlayer bgm = null;
	private HashMap<String, JLabel> sprites = new HashMap<String, JLabel>(); // ������ �׷��� ��ҵ�
	private Player player = null;
	private Enemy enemy = null;
	
	public Main() {
		setTitle("�г��� �����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon("graphics/icon.png").getImage());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // ����â�� ȭ�� ���߾ӿ� ������ ��ġ��Ű�� ���� �ڵ�
        int mw = dim.width / 2 - 328;
        int mh = (int) (dim.height * 0.05f);
        setSize(655, 729);
        setLocation(mw, mh);
		setResizable(false);
		
		background = new ImageIcon("graphics/bg/title.png");
		
		board = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(background.getImage(), 0, 0, null);
			}
		};
		board.setLayout(null);
		setContentPane(board);
		
		setSprites();
		
		bgm = new BGMPlayer(0);
		bgm.loop();
		
		setVisible(true);
		
		requestFocus(true);
		addKeyListener(new EnterEvent());
	}
	
	public void setSprites() { // ������ �׷��� ��ҵ��� ����
		JLabel face1 = new JLabel(new ImageIcon("graphics/face/enemy1.png"));
		JLabel face2 = new JLabel(new ImageIcon("graphics/face/enemy2.png"));
		JLabel face3 = new JLabel(new ImageIcon("graphics/face/enemy3.png"));
		JLabel face4 = new JLabel(new ImageIcon("graphics/face/enemy4.png"));
		JLabel select = new JLabel(new ImageIcon("graphics/select.png"));
		JLabel intro = new JLabel(new ImageIcon());
		JLabel frame = new JLabel(new ImageIcon("graphics/frame.png"));
		int horizon = 450;
		int gap = 150;
		
		face1.setSize(100, 100);
		face2.setSize(100, 100);
		face3.setSize(100, 100);
		face4.setSize(100, 100);
		select.setSize(110, 110);
		intro.setSize(550, 300);
		frame.setSize(650, 700);
		
		face1.setLocation(50, horizon);
		face2.setLocation(50 + gap * 1, horizon);
		face3.setLocation(50 + gap * 2, horizon);
		face4.setLocation(50 + gap * 3, horizon);
		intro.setLocation(50, 100);
		frame.setLocation(0, 0);
		
		board.add(face1);
		board.add(face2);
		board.add(face3);
		board.add(face4);
		board.add(select);
		board.add(intro);
		board.add(frame);
		
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
		frame.setVisible(false);
		
		sprites.put("face1", face1);
		sprites.put("face2", face2);
		sprites.put("face3", face3);
		sprites.put("face4", face4);
		sprites.put("select", select);
		sprites.put("intro", intro);
		sprites.put("frame", frame);
	}
	
	public void changeBackground(int code) { // ��� �̹��� ��ȯ
		switch(code) {
		case 0:
			background = new ImageIcon("graphics/bg/title.png");
			board.repaint();
			break;
		case 1:
			background = new ImageIcon("graphics/bg/select.png");
			board.repaint();
			sprites.get("face1").setVisible(true);
			sprites.get("face2").setVisible(true);
			sprites.get("face3").setVisible(true);
			sprites.get("face4").setVisible(true);
			break;
		case 2:
			background = new ImageIcon("graphics/bg/bg1.png");
			board.repaint();
			break;
		case 3:
			background = new ImageIcon("graphics/bg/bg2.png");
			board.repaint();
			break;
		case 4:
			background = new ImageIcon("graphics/bg/bg3.png");
			board.repaint();
			break;
		case 6:
			background = new ImageIcon("graphics/bg/gameover.png");
			board.repaint();
			break;
		}
	}
	
	public void readyForBattle(int backgroundCode) { // ������ ������ �غ�, �Ķ���ʹ� backgroundCode�̴�
		sprites.get("face1").setVisible(false);
		sprites.get("face2").setVisible(false);
		sprites.get("face3").setVisible(false);
		sprites.get("face4").setVisible(false);
		sprites.get("select").setVisible(false);
		sprites.get("intro").setVisible(false);
		sprites.get("frame").setVisible(true);
		
		player = new Player(board, this); // �÷��̾� ��ü ����
		player.addKeyListener(new Controller());
		player.requestFocus(true);
		
		switch(backgroundCode) {
		case 2: // ���� ���̸� ����
			enemy = new Enemy1(board);
			break;
		case 3: // ������ ����
			enemy = new Enemy2(board);
			break;
		}
		
		Config.getInstance().setBackgroundCode(backgroundCode); // ������ ���� �´� ��� �̹����� ��ȯ
		changeBackground(Config.getInstance().getBackgroundCode());
		
		ReadyGo rg = new ReadyGo(true); //ReadyGo ������ ����
		rg.start();
	}
	
	public void handleError(String msg) { // ���� ó��
		System.out.println(msg);
		System.exit(1);
	}
	
	class ReadyGo extends Thread { // READY�� GO!�� ���̳����ϰ� ǥ��
		private boolean go;
		public ReadyGo(boolean go) { // go�� true�̸� �� �����带 �ϳ� �� ����
			this.go = go;
		}
		public void run() {
			JLabel jl = null;
			if(go)
				jl = new JLabel(new ImageIcon("graphics/intro/ready.png"));
			else
				jl = new JLabel(new ImageIcon("graphics/intro/go.png"));
			jl.setSize(400, 150);
			int horizon = 250;
			jl.setLocation(0, horizon);
			board.add(jl);
			
			try {
				sleep(20);
				jl.setLocation(50, horizon);
				sleep(20);
				jl.setLocation(80, horizon);
				sleep(20);
				jl.setLocation(100, horizon);
				sleep(600);
				jl.setLocation(120, horizon);
				sleep(20);
				jl.setLocation(150, horizon);
				sleep(20);
				jl.setLocation(200, horizon);
				sleep(20);
				board.remove(jl);
				board.revalidate();
				board.repaint();
				sleep(300);
				if(go) {
					ReadyGo rg = new ReadyGo(false);
					rg.start();
				} else {
					battleStart(); // ���� ����
				}
			} catch (InterruptedException e) {
				handleError(e.getMessage());
			}
		}
	}
	
	public void battleStart() {
		player.setControlFlag(true);
		enemy.fireLoop(0);
	}
	
	public void playerLose() {
		Config.getInstance().setBackgroundCode(6); // ������ ���� �´� ��� �̹����� ��ȯ
		changeBackground(Config.getInstance().getBackgroundCode());
	}
	
	class SelectEnemyEvent extends MouseAdapter { // �� ���ð� ���õ� ���콺 �̺�Ʈ��
		public void mouseEntered(MouseEvent e) { // �� �����ϱ� ���� ���콺 �ø�
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
		public void mouseExited(MouseEvent e) { // ���콺 ��������
			int code = Config.getInstance().getBackgroundCode();
			
			if(code == 1) {
				JLabel select = sprites.get("select");
				JLabel intro = sprites.get("intro");
				select.setVisible(false);
				intro.setVisible(false);
			}
		}
		public void mouseClicked(MouseEvent e) { // Ŭ������ �� ����, ��� �̹����� ������� ��ȯ
			int code = Config.getInstance().getBackgroundCode();
			
			if(code == 1) {
				JLabel jl = (JLabel)e.getSource();
				int x = jl.getX();
				
				switch(x) {
				case 50:
					bgm.changeBGM(1);
					readyForBattle(2);
					break;
				case 200:
					bgm.changeBGM(2);
                    readyForBattle(3);
					break;
				case 350:
					bgm.changeBGM(3);
                    readyForBattle(4);
					break;
				}
			}
		}
	}
	
	class EnterEvent extends KeyAdapter { // Ÿ��Ʋ����, ���ӿ����� ����Ű �̺�Ʈ
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch(keyCode) {
			case KeyEvent.VK_ENTER:
				Config.getInstance().changeBackgroundCode();
				changeBackground(Config.getInstance().getBackgroundCode());
				break;
			}
		}
	}
	
	class Controller extends KeyAdapter { // �÷��̾� ��ü ���� �̺�Ʈ
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch(keyCode) {
			case KeyEvent.VK_UP:
                player.keyDown(1);
                break;
            case KeyEvent.VK_DOWN:
            	player.keyDown(2);
                break;
            case KeyEvent.VK_LEFT:
            	player.keyDown(3);
                break;
            case KeyEvent.VK_RIGHT:
            	player.keyDown(4);
                break;
            case KeyEvent.VK_Z:
            	player.keyDown(5);
                break;
			}
		}
		public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch(keyCode) {
            case KeyEvent.VK_UP:
            	player.keyUp(1);
                break;
            case KeyEvent.VK_DOWN:
            	player.keyUp(2);
                break;
            case KeyEvent.VK_LEFT:
            	player.keyUp(3);
                break;
            case KeyEvent.VK_RIGHT:
            	player.keyUp(4);
                break;
            case KeyEvent.VK_Z:
            	player.keyUp(5);
                break;
            }
        }
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
