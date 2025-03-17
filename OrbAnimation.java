import java.awt.Graphics2D;
import java.awt.Image;

public class OrbAnimation {
    
    private Animation animation;

    public OrbAnimation(){

        Image animImage = ImageManager.get_image("images/smoke1.jpg");
		Image animImage2 = ImageManager.get_image("images/smoke2.jpg");
		Image animImage3 = ImageManager.get_image("images/smoke3.jpg");
		Image animImage4 = ImageManager.get_image("images/smoke4.jpg");
		Image animImage5 = ImageManager.get_image("images/smoke5.jpg");
		Image animImage6 = ImageManager.get_image("images/smoke6.jpg");
		animation = new Animation(true);

        animation.addFrame(animImage, 100);
        animation.addFrame(animImage2, 100);
        animation.addFrame(animImage3, 100);
		animation.addFrame(animImage4, 100);
        animation.addFrame(animImage5, 500);
        animation.addFrame(animImage6, 500);

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

		g2.drawImage(animation.getImage(), x, y, 30, 30, null);
	}
}
