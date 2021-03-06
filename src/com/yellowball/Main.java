package com.yellowball;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.yellowball.enemy.Enemy;
import com.yellowball.enemy.Enemy1;
import com.yellowball.enemy.Enemy2;
import com.yellowball.enemy.Enemy3;
import com.yellowball.sound.BGMPlayer;

@SuppressWarnings("serial")
public class Main extends JFrame {
	private JPanel board = null; // 다양한 JLabel들을 붙일 JPanel
	private ImageIcon background = null;
	private int backgroundCode = 0;
	private boolean returnToSelectFlag = false;
	private BGMPlayer bgm = null;
	private HashMap<String, JLabel> sprites = null; // 정적인 그래픽 요소들
	private Player player = null;
	private Enemy enemy = null;
	
	public Main() {
		setTitle("분노의 노란공");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon("graphics/icon.png").getImage());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // 게임창을 화면 정중앙에 가깝게 위치시키기 위한 코드
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
		
		sprites = new HashMap<String, JLabel>();
		setSprites();
		
		bgm = new BGMPlayer(0);
		bgm.loop();
		
		setVisible(true);
		
		requestFocus(true);
		addKeyListener(new EnterEvent());
	}
	
	public void setSprites() { // 정적인 그래픽 요소들을 세팅
		JLabel face1 = new JLabel(new ImageIcon("graphics/face/enemy1.png"));
		JLabel face2 = new JLabel(new ImageIcon("graphics/face/enemy2.png"));
		JLabel face3 = new JLabel(new ImageIcon("graphics/face/enemy3.png"));
		JLabel face4 = new JLabel(new ImageIcon("graphics/face/enemy4.png"));
		JLabel select = new JLabel(new ImageIcon("graphics/select.png"));
		JLabel intro = new JLabel(new ImageIcon());
		JLabel frame = new JLabel(new ImageIcon("graphics/frame.png"));
		JLabel win = new JLabel("YOU WIN!");
		win.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		win.setForeground(Color.ORANGE);
		win.setSize(300, 100);
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
		win.setLocation(50, 200);
		
		board.add(face1);
		board.add(face2);
		board.add(face3);
		board.add(face4);
		board.add(select);
		board.add(intro);
		board.add(frame);
		board.add(win);
		
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
		win.setVisible(false);
		
		sprites.put("face1", face1);
		sprites.put("face2", face2);
		sprites.put("face3", face3);
		sprites.put("face4", face4);
		sprites.put("select", select);
		sprites.put("intro", intro);
		sprites.put("frame", frame);
		sprites.put("win", win);
	}
	
	public void changeBackground(int code) { // 배경 이미지 전환
		switch(code) {
		case 0:
			background = new ImageIcon("graphics/bg/title.png");
			board.repaint();
			break;
		case 1:
			background = new ImageIcon("graphics/bg/select.png");
			sprites.get("face1").setVisible(true);
			sprites.get("face2").setVisible(true);
			sprites.get("face3").setVisible(true);
			sprites.get("face4").setVisible(true);
			board.repaint();
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
	
	public void readyForBattle(int backgroundCode) { // 적과의 전투를 준비, 파라미터는 backgroundCode이다
		sprites.get("face1").setVisible(false);
		sprites.get("face2").setVisible(false);
		sprites.get("face3").setVisible(false);
		sprites.get("face4").setVisible(false);
		sprites.get("select").setVisible(false);
		sprites.get("intro").setVisible(false);
		sprites.get("frame").setVisible(true);
		
		player = new Player(board); // 플레이어 기체 생성
		player.addKeyListener(new Controller());
		player.requestFocus(true);
		
		switch(backgroundCode) {
		case 2: // 리듬 게이머 생성
			enemy = new Enemy1(board, player);
			break;
		case 3: // 총잡이 생성
			enemy = new Enemy2(board, player);
			break;
		case 4: // 도박꾼 생성
			enemy = new Enemy3(board, player);
			break;
		}
		
		this.backgroundCode = backgroundCode; // 선택한 적에 맞는 배경 이미지로 전환
		changeBackground(backgroundCode);
		
		ReadyGo rg = new ReadyGo(true); //ReadyGo 스레드 시작
		rg.start();
	}
	
	public void handleError(String msg) { // 오류 처리
		System.out.println(msg);
		System.exit(1);
	}
	
	class ReadyGo extends Thread { // READY와 GO!를 다이나믹하게 표시
		private boolean go;
		public ReadyGo(boolean go) { // go가 true이면 이 스레드를 하나 더 실행
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
					battleStart(); // 전투 시작
				}
			} catch (InterruptedException e) {
				handleError(e.getMessage());
			}
		}
	}
	
	public void battleStart() {
		player.setControlFlag(true);
		DrawingLoop loop = new DrawingLoop();
		loop.start();
	}
	
	class DrawingLoop extends Thread { // 동적인 그래픽을 그리기 위한 루프(게임 플레이)
		public void run() {
			while(!player.isLifeZero() && !enemy.isHpZero()) {
				try {
					player.movePosition();
					player.newBullet(enemy);
					player.moveBullets();
					
					enemy.executePattern();
					enemy.moveBullets();
					sleep(10);
				} catch (InterruptedException e) {
					handleError(e.getMessage());
				}
			}
			
			player.setControlFlag(false);
			requestFocus(true);
			
			if(player.isLifeZero()) {
				player.removeAllGraphics();
				enemy.removeAllGraphics();
				playerLose();
			} else if(enemy.isHpZero()) {
				playerWin();
			}
		}
	}
	
	public void playerLose() {
		sprites.get("frame").setVisible(false);
		backgroundCode = 6; // 게임 오버 배경으로 전환
		changeBackground(backgroundCode);
		returnToSelectFlag = true;
	}
	
	public void playerWin() {
		if(enemy != null) {
			if(enemy instanceof Enemy1) {
				sprites.get("face1").setIcon(new ImageIcon("graphics/face/enemy1clear.png"));
				System.out.println("Enemy1 cleared");
			} else if(enemy instanceof Enemy2) {
				sprites.get("face2").setIcon(new ImageIcon("graphics/face/enemy2clear.png"));
				System.out.println("Enemy2 cleared");
			}
		}
		sprites.get("win").setVisible(true);
		returnToSelectFlag = true;
	}
	
	class SelectEnemyEvent extends MouseAdapter { // 적 선택과 관련된 마우스 이벤트들
		public void mouseEntered(MouseEvent e) { // 적 선택하기 위해 마우스 올림
			int code = backgroundCode;
			
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
		public void mouseExited(MouseEvent e) { // 마우스 빠져나옴
			int code = backgroundCode;
			
			if(code == 1) {
				JLabel select = sprites.get("select");
				JLabel intro = sprites.get("intro");
				select.setVisible(false);
				intro.setVisible(false);
			}
		}
		public void mouseClicked(MouseEvent e) { // 클릭으로 적 선택, 배경 이미지와 배경음악 전환
			int code = backgroundCode;
			
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
	
	class EnterEvent extends KeyAdapter { // 엔터키 이벤트
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if(keyCode == KeyEvent.VK_ENTER) {
				if(backgroundCode == 0) {
					backgroundCode = 1;
					changeBackground(backgroundCode);
				} else if(returnToSelectFlag) {
					if(backgroundCode != 6) {
						player.removeAllGraphics();
						enemy.removeAllGraphics();
						sprites.get("win").setVisible(false);
						sprites.get("frame").setVisible(false);
					}
					backgroundCode = 1;
					changeBackground(backgroundCode);
					returnToSelectFlag = false;
					bgm.changeBGM(0);
				}
			}
		}
	}
	
	class Controller extends KeyAdapter { // 플레이어 기체 조종 이벤트
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
