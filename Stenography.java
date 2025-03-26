import java.awt.*;
import java.util.ArrayList;

public class Stenography {
    public static void main(String[] args) {       
        Picture beach = new Picture("beach.jpg");
        Picture motorcycle = new Picture("blueMotorcycle.jpg");
        Picture hidden = hidePicture(beach, motorcycle);
        hidden.explore();
        Picture robot = new Picture("robot.jpg");
        Picture flower1 = new Picture("flower1.jpg");
        beach.explore();
        Picture hidden1 = hidePicture(beach, robot, 65, 208);
        Picture hidden2 = hidePicture(hidden1, flower1, 280, 110);
        hidden2.explore();
        Picture unhidden = revealPicture(hidden2);
        unhidden.explore(); 
        Picture swan = new Picture("swan.jpg");
        Picture swan2 = new Picture("swan.jpg");
        System.out.println("Swan and swan2 are the same: " +
        isSame(swan, swan2));
        swan = testClearLow(swan);
        System.out.println("Swan and swan2 are the same (after clearLow run on swan): "
        + isSame(swan, swan2)); 
        Picture arch = new Picture("arch.jpg");
        Picture arch2 = new Picture("arch.jpg");
        Picture koala = new Picture("koala.jpg") ;
        Picture robot1 = new Picture("robot.jpg");
        ArrayList<Point> pointList = findDifferences(arch, arch2);
        System.out.println("PointList after comparing two identical pictures " +
        "has a size of " + pointList.size());
        pointList = findDifferences(arch, koala);
        System.out.println("PointList after comparing two different sized pictures " +
        "has a size of " + pointList.size());
        arch2 = hidePicture(arch, robot1, 65, 102);
        pointList = findDifferences(arch, arch2);
        System.out.println("Pointlist after hiding a picture has a size of "
        + pointList.size());
        arch.show();
        arch2.show();   
        Picture hall = new Picture("femaleLionAndHall.jpg");
        Picture robot2 = new Picture("robot.jpg");
        Picture flower2 = new Picture("flower1.jpg");
        Picture hall2 = hidePicture(hall, robot2, 50, 300);
        Picture hall3 = hidePicture(hall2, flower2, 115, 275);
        hall3.explore();
        if(!isSame(hall, hall3)) {
        Picture hall4 = showDifferentArea(hall,
            findDifferences(hall, hall3));
        hall4.show();
        Picture unhiddenHall3 = revealPicture(hall3);
        unhiddenHall3.show();   
        }

        // Add code to test hiding and revealing a message here
    }

    // Activity 1

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

    // Activity 2

    public static boolean canHide(Picture source, Picture secret) {
        return source.getWidth() >= secret.getWidth() && source.getHeight() >= secret.getHeight();
    }

    public static Picture hidePicture(Picture source, Picture secret) {
        Picture hidden = new Picture(source);
        Pixel[][] pixels = hidden.getPixels2D();
        Pixel[][] secretPixels = secret.getPixels2D();
        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[0].length; c++) {
                setLow(pixels[r][c], secretPixels[r][c].getColor());
            }
        }
        return hidden;
    }

    // Activity 3

    public static Picture hidePicture(Picture source, Picture secret, int startRow, int startColumn) {
        Picture hidden = new Picture(source);
        Pixel[][] sourcePixels = hidden.getPixels2D();
        Pixel[][] secretPixels = secret.getPixels2D();
        for (int r = 0; r < secretPixels.length; r++) {
            for (int c = 0; c < secretPixels[0].length; c++) {
                if (r + startRow < sourcePixels.length && c + startColumn < sourcePixels[0].length) {
                    setLow(sourcePixels[r + startRow][c + startColumn], secretPixels[r][c].getColor());
                }
            }
        }
        return hidden;
    }
  
    public static boolean isSame(Picture p1, Picture p2) {
        if (p1.getWidth() != p2.getWidth() || p1.getHeight() != p2.getHeight()) {
            return false;
        }
        Pixel[][] pixels1 = p1.getPixels2D();
        Pixel[][] pixels2 = p2.getPixels2D();
        for (int r = 0; r < pixels1.length; r++) {
            for (int c = 0; c < pixels1[0].length; c++) {
                if (!pixels1[r][c].getColor().equals(pixels2[r][c].getColor())) {
                    return false;
                }
            }
        }
        return true;
    }


    public static ArrayList<Point> findDifferences(Picture p1, Picture p2) {
        ArrayList<Point> differences = new ArrayList<>();
        if (p1.getWidth() != p2.getWidth() || p1.getHeight() != p2.getHeight()) {
            return differences;
        }
        Pixel[][] pixels1 = p1.getPixels2D();
        Pixel[][] pixels2 = p2.getPixels2D();
        for (int r = 0; r < pixels1.length; r++) {
            for (int c = 0; c < pixels1[0].length; c++) {
                if (!pixels1[r][c].getColor().equals(pixels2[r][c].getColor())) {
                    differences.add(new Point(r, c));
                }
            }
        }
        return differences;
    }   

    public static Picture showDifferentArea(Picture p, ArrayList<Point> differences) {
        if (differences.isEmpty()) {
            return p;
        }
        int minRow = Integer.MAX_VALUE, maxRow = Integer.MIN_VALUE;
        int minCol = Integer.MAX_VALUE, maxCol = Integer.MIN_VALUE;
        for (Point pnt : differences) {
            int row = pnt.x;
            int col = pnt.y;
            if (row < minRow) minRow = row;
            if (row > maxRow) maxRow = row;
            if (col < minCol) minCol = col;
            if (col > maxCol) maxCol = col;
        }
        Picture result = new Picture(p);
        Color rectColor = Color.RED;
        for (int col = minCol; col <= maxCol; col++) {
            result.getPixel(minRow, col).setColor(rectColor);
            result.getPixel(maxRow, col).setColor(rectColor);
        }
        for (int row = minRow; row <= maxRow; row++) {
            result.getPixel(row, minCol).setColor(rectColor);
            result.getPixel(row, maxCol).setColor(rectColor);
        }
        return result;
    }

    // Activity 4

    public static ArrayList<Integer> encodeString(String s) {
        s = s.toUpperCase();
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < s.length(); i++) {
            if (s.substring(i,i+1).equals(" ")) {
                result.add(27);
            }
            else {
                result.add(alpha.indexOf(s.substring(i,i+1))+1);
            }
        }
        result.add(0);
        return result;
    }

    public static String decodeString(ArrayList<Integer> codes) {
        String result = "";
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i=0; i < codes.size(); i++) {
            if (codes.get(i) == 27) {
                result = result + " ";
            }
            else {
                result = result +
                    alpha.substring(codes.get(i)-1,codes.get(i));
            }
        }
        return result;
    }

    private static int[] getBitPairs(int num) {
        int[] bits = new int[3];
        int code = num;
        for (int i = 0; i < 3; i++) {
            bits[i] = code % 4;
            code = code / 4;
        }
        return bits;
    }

    /**
    * Hide a string (must be only capital letters and spaces) in a
    * picture.
    * The string always starts in the upper left corner.
    * @param source picture to hide string in
    * @param s string to hide
    * @return picture with hidden string
    */
    public static void hideText(Picture source, String s) {
        // Complete this method
    }

    /**
    * Returns a string hidden in the picture
    * @param source picture with hidden string
    * @return revealed string
    */
    public static String revealText(Picture source) {
        // Complete this method
        return ""; // << Temporary return
    }
}
