import javax.swing.JPanel;

public class Pad extends BulletE {
    Pad(JPanel board, int x, int y, int code) {
        super(board, x, y, 0, 0, code);
        threadSelect(code);
    }
    
    public void launch(int mx, int my) {}
    
    public void threadSelect(int code) {
    	if(code != 7) { // ¹öÆ°
            LaunchThread th = new LaunchThread();
            th.start();
        } else { // Æ²
            PadThread th = new PadThread();
            th.start();
        }
    }
    
    public void handleError(String msg) {
		System.out.println(msg);
		System.exit(1);
	}
    
    class LaunchThread extends Thread {
        public void run() {
        	boolean b;
        	try {
                for(int i = 9; i < 14; i++) {
                    Thread.sleep(100);
                    setBulletImage(i);
                }
                setBulletSize(13);
                b = check();
                Thread.sleep(100);
            } catch (Exception e) {
                handleError(e.getMessage());
            }
        	deleteThis();
        }
    }

    class PadThread extends Thread {
        public void run() {
        	Player player = getPlayer();
            try {
            	player.setLocation(280, 440);
                player.restrictMove(2);
                Thread.sleep(50);
                bulletMove(0, 20);
                Thread.sleep(50);
                bulletMove(0, 10);
                Thread.sleep(50);
                bulletMove(0, 5);
                Thread.sleep(50);
                bulletMove(0, 5);
                Thread.sleep(2700);
                player.restrictMove(1);
            } catch (Exception e) {
            	handleError(e.getMessage());
            }
        	deleteThis();
        }
    }
}
