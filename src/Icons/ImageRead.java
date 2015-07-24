package Icons;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageRead{
    Image img;

    public Image getImage(int imageNr) {
        img = null;
        BufferedImage tempImg;
        String filepath = imageNr + ".png";
        try {
            tempImg = ImageIO.read(getClass().getResource(filepath));
        } catch (IOException iOE) {
            tempImg = null;
        }
        //setImg(tempImg.getScaledInstance(tempImg.getWidth()*2,tempImg.getHeight()*2,Image.SCALE_DEFAULT));
        setImg(tempImg);

        return img;
    }

    private void setImg(Image img) {
        this.img = img;
    }

}