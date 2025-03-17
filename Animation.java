import java.awt.Image;
import java.util.ArrayList;


/**
    The Animation class manages a series of images (frames) and
    the amount of time to display each frame.
*/
public class Animation {

    private ArrayList<AnimFrame> frames;
    private int currFrameIndex;
    private long animTime;
    private long startTime;
    private long totalDuration;

    private boolean loop;
    private boolean isActive;

    /**
        Creates a new, empty Animation.
    */
    public Animation(boolean loop) {
        frames = new ArrayList<AnimFrame>();
        totalDuration = 0;
        this.loop = loop;
        isActive = false;
    }


    /**
        Adds an image to the animation with the specified
        duration (time to display the image).
    */
    public synchronized void addFrame(Image image, long duration)
    {
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }


    /**
        Starts this animation over from the beginning.
    */
    public synchronized void start() {
	    isActive = true;
        animTime = 0;
        currFrameIndex = 0;	
	    startTime = System.currentTimeMillis();
    }


    /**
        Terminates this animation.
    */
    public synchronized void stop() {
	    isActive = false;
    }


    /**
        Updates this animation's current image (frame), if
        neccesary.
    */
    public synchronized void update() {

	if (!isActive)
	    return;

        long currTime = System.currentTimeMillis();
        long elapsedTime = currTime - startTime;
        startTime = currTime;

        if (frames.size() > 1) {
            animTime += elapsedTime;
            if (animTime >= totalDuration) {
		if (loop) {
                    animTime = animTime % totalDuration;
                    currFrameIndex = 0;
		}
		else { 
	            isActive = false;
		}
            }

	    if (!isActive)
	       return;

            while (animTime > getFrame(currFrameIndex).endTime) {
                currFrameIndex++;
            }
        }
	
    }


    /**
        Gets this Animation's current image. Returns null if this
        animation has no images.
    */
    public synchronized Image getImage() {
        if (frames.size() == 0) {
            return null;
        }
        else {
            return getFrame(currFrameIndex).image;
        }
    }


    public int getNumFrames() {
	    return frames.size();
    }


    private AnimFrame getFrame(int i) {
        return frames.get(i);
    }


    public boolean isStillActive () {
	    return isActive;
    }

    public long getTotalDuration(){
        return this.totalDuration;
    }


    private class AnimFrame {

        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }

}
