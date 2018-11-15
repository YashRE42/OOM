import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Panel extends JPanel {
    BufferedImage image;

    public Panel(){
//        try {
//            image = ImageIO.read(new File("C:/homestuck_wallpaper_thingy_by_inkandescent_khaos-d80b9ll"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void paintComponent(Graphics g){
//        g.drawImage(image,0,0,null);
        g.setColor(Color.red);
        g.fillRect(10,10,100,50);
    }
}
