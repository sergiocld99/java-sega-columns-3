package cs10.apps.columns.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundUtils {

    public static void playMusic(String resourceName){
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                File file = new File("src/main/resources/"+resourceName+".wav");
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
                clip.open(inputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void playSound(String resourceName){
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                File file = new File("src/main/resources/"+resourceName+".wav");
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
