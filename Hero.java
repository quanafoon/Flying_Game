import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;

public class Hero {
    
    private int x;
    private int y;
    private Image hero;
    private boolean lockUp = false;
    private boolean lockDown = false;
    private int height = 50;
    private int width = 88;
    int dy = 20;

    public Hero(int x, int y){
        this.x = x;
        this.y = y;
        hero = ImageManager.get_image("images/hero.jpg");
    }

    public void draw(Graphics2D g2){
        g2.drawImage(hero, x, y,null);
    }

    public void move(String direction){
        if(direction == "up" && !lockUp){
            y-= dy;
        }
        if(direction == "down" && !lockDown){
            y+= dy;
        }
    }

    public HashMap<Character, Integer> getPos(){
        HashMap<Character, Integer> pos = new HashMap<>();
        pos.put('x', x);
        pos.put('y', y);
        return pos;
    }

    public boolean isLockUp() {
        return lockUp;
    }

    public void setLockUp(boolean lockUp) {
        this.lockUp = lockUp;
    }

    public boolean isLockDown() {
        return lockDown;
    }

    public void setLockDown(boolean lockDown) {
        this.lockDown = lockDown;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getDy(){
        return this.dy;
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

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

}
