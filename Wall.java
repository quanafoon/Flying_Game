import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JPanel;

public class Wall {

    private JPanel panel;
    private int height;
    private int width = 20;
    private int gap = 60;
    private int x = 0;
    private Image wall;

    public Wall(JPanel panel){
        this.panel = panel;
        this.wall = ImageManager.get_image("images/wall.jpg");
    }

    public void create_pair(){
        Random rand = new Random();
        height = rand.nextInt(panel.getHeight() - 100);
    }

    public void update_pair(Graphics2D g2, int x){
        this.x = x;
        int y = height + gap;
        draw(x, 0, width, height, g2);
        draw(x, y, width, panel.getHeight()-y, g2);
        handle_gap(x, height, width, gap, g2);
    }

    public void draw(int x, int y, int width, int height, Graphics2D g2){
        g2.drawImage(wall, x, y, width, height, null);
    }

    public void handle_gap(int x, int y, int width, int height, Graphics2D g2){
        g2.setColor(new Color(166, 198, 237));
        g2.fill (new Rectangle2D.Double (x, y, width, height));  
    }

    public LinkedList<HashMap<String, Integer>> getDimensions(){
        LinkedList<HashMap<String, Integer>> wallsPos = new LinkedList<>();
        HashMap<String, Integer> wall1 = new HashMap<>();
        HashMap<String, Integer> wall2 = new HashMap<>();
        wall1.put("x", x);
        wall1.put("y", 0);
        wall1.put("height", height);
        wall1.put("width", width);
        wallsPos.add(wall1);

        wall2.put("x", x);
        wall2.put("y", height + gap);
        wall2.put("height", panel.getHeight() - (height+gap));
        wall2.put("width", width);
        wallsPos.add(wall2);
        
        return wallsPos;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

}
