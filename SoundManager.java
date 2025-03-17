import java.io.File;
import java.util.HashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundManager {
    HashMap<String, Clip> clips;

	private static SoundManager instance = null;

	private SoundManager () {
		clips = new HashMap<String, Clip>();

		Clip clip = loadClip("sounds/lose.wav");
		clips.put("lose", clip);

        Clip clip2 = loadClip("sounds/game.wav");
		clips.put("game", clip2);

		Clip clip3 = loadClip("sounds/orbHit.wav");
		clips.put("orbHit", clip3);

		Clip clip4 = loadClip("sounds/start.wav");
		clips.put("btnPress", clip4);


	}

    public static SoundManager getInstance() {
		if (instance == null)
			instance = new SoundManager();
		
		return instance;
	}		


    	public Clip loadClip (String fileName) {
 		AudioInputStream audioIn;
		Clip clip = null;

		try {
            File file = new File(fileName);
            audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL()); 
            clip = AudioSystem.getClip();
            clip.open(audioIn);
		}
		catch (Exception e) {
 			System.out.println ("Error opening sound files: " + e);
		}
    		return clip;
    	}


	public Clip getClip (String title) {

		return clips.get(title);
	}


    public void playClip(String title, boolean looping) {
        Clip clip = getClip(title);
        if (clip != null) {
            clip.setFramePosition(0);
            if (looping)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            else
                clip.start();
        }
    }

	public void stopClip(String title) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.stop();
		}
	}

	public void setVolume (String title, float volume) {
		Clip clip = getClip(title);

		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();

		gainControl.setValue(gain);
	}
}
