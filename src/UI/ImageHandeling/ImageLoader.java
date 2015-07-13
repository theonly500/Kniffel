package UI.ImageHandeling;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ImageLoader implements AutoCloseable {

    public ImageLoader(){}

    public Image getImage(int imageNr) {
        Image imageTmp;
        String filepath= "Icons/" + imageNr + ".png";
        try {
            imageTmp = ImageIO.read(getClass().getResource(filepath));
        }
        catch (IOException IOE){
            imageTmp=null;
        }
        Image image=imageTmp.getScaledInstance(100,100,Image.SCALE_SMOOTH);
        return image;
    }

    @Override
    public void close() throws Exception {

    }
}
