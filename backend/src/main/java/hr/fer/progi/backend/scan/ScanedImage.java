package hr.fer.progi.backend.scan;
import java.awt.Graphics2D;
import net.sourceforge.tess4j.*;
import java.awt.Image;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class ScanedImage {
    public static void processImg(BufferedImage ipimage, float scaleFactor, float offset) throws IOException, TesseractException
    {
        // Making an empty image buffer to store image later ipimage is an image buffer of input image
        BufferedImage opimage = new BufferedImage(1050,1024, ipimage.getType());

        // creating a 2D platform on the buffer image for drawing the new image
        Graphics2D graphic = opimage.createGraphics();

        // drawing new image starting from 0 0 of size 1050 x 1024 null is the ImageObserver class object
        graphic.drawImage(ipimage, 0, 0, 1050, 1024, null);
        graphic.dispose();

        // rescale OP object for gray scaling images
        RescaleOp rescale = new RescaleOp(scaleFactor, offset, null);

        // performing scaling and writing on a .png file
        BufferedImage fopimage = rescale.filter(opimage, null);
        ImageIO .write(fopimage, "jpg", new File("pathname"));

        // Instantiating the Tesseract class which is used to perform OCR
        Tesseract it = new Tesseract();
        it.setDatapath("D:\\Program Files\\Workspace\\Tess4J");

        // doing OCR on the image and storing result in string str
        String str = it.doOCR(fopimage);
        System.out.println(str);
    }

    public static void main(String args[]) {
        //...
    }
}

