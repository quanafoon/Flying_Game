import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

public class Orb {

    private JPanel panel;
    private int x;
    private int y;
    private int firstX;
    private int offset;
    private Image orb;

    public Orb(JPanel panel, int offset){
        this.panel = panel;
        this.offset = offset;
        this.x = newX();
        this.y = newY();
        this.firstX = this.x;
    }

    public void draw(Graphics2D g2){
        orb = ImageManager.get_image("images/orb.jpg");
        g2.drawImage(orb, x, y, 20, 20, null);
    }

    public int newX(){
        Random rand = new Random();
        return rand.nextInt(offset + panel.getWidth()/2, offset + (panel.getWidth() - (panel.getWidth()/10)));
    }

    public int newY(){
        Random rand = new Random();
        return rand.nextInt(panel.getHeight()/10, panel.getHeight() - (panel.getHeight()/10));
    }
    
    public void update_orb(int x, Graphics2D g2){
        this.x = (this.firstX - x);
        draw(g2);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getFirstX() {
        return firstX;
    }

    public void setFirstX(int firstX) {
        this.firstX = firstX;
    }
    

}
