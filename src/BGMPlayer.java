import java.io.*;
import javax.sound.sampled.*;

public class BGMPlayer {
	private Clip clip;
    private FloatControl volume;

    BGMPlayer(int bgmNum, float vol) {
        String fileName;
        switch(bgmNum) {
            case 0:
                fileName = "sound/title.wav";
                break;
            case 1:
                fileName = "sound/enemy1.wav";
                break;
            case 2:
                fileName = "sound/enemy2.wav";
                break;
            case 3:
                fileName = "sound/enemy3.wav";
                break;
            default:
                fileName = "";
        }
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
}
