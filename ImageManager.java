import java.awt.Image;
import javax.swing.ImageIcon;

/**
 Utlity class for dealing with images
*/
public class ImageManager{

    /**
      Retrieves an image from a given filepath
      * @return an {@code Image} object
     */
    public static Image get_image(String filepath){
        return new ImageIcon(filepath).getImage();
    }
}