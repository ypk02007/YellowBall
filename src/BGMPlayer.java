import java.io.*;
import javax.sound.sampled.*;

public class BGMPlayer {
	private Clip clip = null;
    private FloatControl volume = null;
    private float vol;

    BGMPlayer(int bgmNum) {
    	vol = -30.0f;
        initializeBGM(bgmNum);
    }
    
    public void initializeBGM(int bgmNum) {
    	String fileName = getBGMString(bgmNum);
        try {
            File file = new File(fileName);
            if (file.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(sound);
            }
            else {
                throw new RuntimeException("Sound: file not found: " + fileName);
            }
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(vol);
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }
    }
    
    public String getBGMString(int bgmNum) {
    	switch(bgmNum) {
        case 0:
        	return "sound/title.wav";
        case 1:
        	return "sound/enemy1.wav";
        case 2:
        	return "sound/enemy2.wav";
        case 3:
        	return "sound/enemy3.wav";
        default:
        	return "";
    	} 
    }
    
    public void play(){
        clip.setFramePosition(0);
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }

    public void changeVolume(float vol) {
        volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(vol);
    }
    
    public void changeBGM(int bgmNum) {
    	stop();
    	initializeBGM(bgmNum);
    	loop();
    }
}
