package com.yellowball.sound;
import java.io.*;
import javax.sound.sampled.*;

public class SEPlayer {
    private Clip clip = null;
    private float vol = -15.0f;
    
    public void initializeSE(int seNum) {
    	String fileName = getPathString(seNum);
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
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
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
    
    public String getPathString(int seNum) {
    	if(seNum != 8) {
    		vol = -15.0f;
    	}
    	switch(seNum) {
    	case 0:
            return "sound/se/attacked.wav";
        case 1:
        	return "sound/se/note1.wav";
        case 2:
        	return "sound/se/note2.wav";
        case 3:
        	return "sound/se/note3.wav";
        case 4:
        	return "sound/se/note4.wav";
        case 5:
        	return "sound/se/note5.wav";
        case 6:
        	return "sound/se/arrow.wav";
        case 7:
        	return "sound/se/circleReady.wav";
        case 8:
        	vol = 0.0f;
        	return "sound/se/circle.wav";
        case 9:
        	return "sound/se/reload.wav";
        case 10:
        	return "sound/se/fire1.wav";
        case 11:
        	return "sound/se/preheating.wav";
        case 12:
        	return "sound/se/fire2.wav";
        case 13:
        	return "sound/se/fireReady.wav";
        case 14:
        	return "sound/se/rapidFire.wav";
        case 15:
        	return "sound/se/shuffle.wav";
        case 16:
        	return "sound/se/cardThrowing.wav";
        default:
        	return "";
    	} 
    }

    public void play(int seNum){
    	initializeSE(seNum);
        clip.setFramePosition(0);
        clip.start();
    }
}