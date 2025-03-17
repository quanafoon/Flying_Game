import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Gameover{

    public void draw(int x, int y, Graphics2D g2){
        Rectangle2D rect = new Rectangle2D.Double(x, y, 200, 100);
        g2.setColor(Color.RED);
        g2.fill(rect);
        g2.setColor(Color.WHITE);
        g2.drawString("GAME OVER", x + 10, y + 50);
        g2.drawString("SCORE - " + GameWindow.score.getText(), x + 10, y + 70);

    }
}