package UI.ImageHandeling;

import javax.swing.*;

public class ImageIconManager{

    private ImageIcon[] imageIcons;

    public ImageIconManager(){
        imageIcons = new ImageIcon[12];
        loadImageIcons();
    }

    private void loadImageIcons(){
        for (int i=1;i<=12;i++){
            ImageLoader imageLoader=new ImageLoader();
            imageIcons[i-1]=new ImageIcon(imageLoader.getImage(i));
            try {
                imageLoader.close();
            }catch (Exception e){

            }
        }
    }

    public ImageIcon getImageIcons(int imageNr) {
        return imageIcons[imageNr];
    }
    //
}
