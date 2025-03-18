import java.awt.Color;

public class Stenography {
    public static void main(String[] args) {        
        Picture beach2 = new Picture ("beach.jpg");
        beach2.explore();
        Picture copy2 = testSetLow(beach2, Color.PINK);
        copy2.explore();
        Picture copy3 = revealPicture(copy2);
        copy3.explore();

        Picture motorcycle = new Picture("blueMotorcycle.jpg");
        Picture caterpillar = new Picture("caterpillar.jpg");
        System.out.println(canHide(beach2, motorcycle)); // Should return true
        System.out.println(canHide(motorcycle, caterpillar)); // Should return false
    }

    public static void clearLow(Pixel p) {
        p.setRed(p.getRed()/4*4);
        p.setGreen(p.getGreen()/4*4);
        p.setBlue(p.getBlue()/4*4);
    }

    public static Picture testClearLow(Picture p) {
        Picture result = new Picture(p);
        Pixel[][] pixels = result.getPixels2D();
        for (Pixel[] row : pixels) {
            for (Pixel obj : row) {
                clearLow(obj);
            }
        }
        return result;
    }

    public static void setLow(Pixel p, Color c) {
        p.setRed((p.getRed()/4*4) + c.getRed()/64);
        p.setGreen((p.getGreen()/4*4) + c.getGreen()/64);
        p.setBlue((p.getBlue()/4*4) + c.getBlue()/64);
    }

    public static Picture testSetLow(Picture p, Color c) {
        Picture result = new Picture(p);
        Pixel[][] pixels = result.getPixels2D();
        for (Pixel[] row : pixels) {
            for (Pixel obj : row) {
                setLow(obj, c);
            }
        }
        return result;
    }

    public static Picture revealPicture(Picture hidden) {
        Picture copy = new Picture(hidden);
        Pixel[][] pixels = copy.getPixels2D();
        Pixel[][] source = hidden.getPixels2D();
        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[0].length; c++) {
                Color col = source[r][c].getColor();
                Pixel p = pixels[r][c];
                p.setRed((p.getRed()%4)*64);
                p.setGreen((p.getGreen()%4)*64);
                p.setBlue((p.getBlue()%4)*64);
            }
        }
        return copy;
    }

    public static boolean canHide(Picture source, Picture secret) {
        return (source.getWidth() == secret.getWidth()) && (source.getHeight() == secret.getHeight());
    }

    public static Picture hidePicture(Picture source, Picture secret) {
        Picture result = new Picture(source);
        // CONTINUE HERE
        return result;
    }
}