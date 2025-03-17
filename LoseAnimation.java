import java.awt.Graphics2D;
import java.awt.Image;

public class LoseAnimation {
    
    private Animation animation;

    public LoseAnimation(){

        Image animImage = ImageManager.get_image("images/fire1.jpg");
		Image animImage2 = ImageManager.get_image("images/fire2.jpg");
		Image animImage3 = ImageManager.get_image("images/fire3.jpg");
        Image animImage4 = ImageManager.get_image("images/fire4.jpg");
		Image animImage5 = ImageManager.get_image("images/fire5.jpg");

		animation = new Animation(true);

        animation.addFrame(animImage, 200);
        animation.addFrame(animImage2, 200);
        animation.addFrame(animImage3, 200);
        animation.addFrame(animImage4, 200);
        animation.addFrame(animImage5, 200);


    }

    public void start() {
		animation.start();
	}

	public void stop(){
		animation.stop();
	}

    public void update() {
		if (!animation.isStillActive())
			return;

		animation.update();
	}

    public void draw(Graphics2D g2, int x, int y) {
		if (!animation.isStillActive())
			return;

		g2.drawImage(animation.getImage(), x, y, 30, 20, null);
	}

    public long getTotalDuration(){
        return animation.getTotalDuration();
    }

}