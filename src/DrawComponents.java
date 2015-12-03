import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

public class DrawComponents {

    private final static int sizeW = 2000;
    private final static int sizeH = 1000;

    public static void wright(List<Double> list, String name) {
        BufferedImage bi = new BufferedImage(sizeW, sizeH,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = getBackgroundedGraphics2D(bi, Color.white);
        g.setStroke(new BasicStroke(3));
        wright(list, g, Color.BLACK);
        save(bi, new File(name + ".png"));
    }

    private static void wright(List<Double> list, Graphics2D g, Color color) {

        ListIterator<Double> iter = list.listIterator();
        double y1 = iter.next();
        double y2 = iter.next();

        double step = (double) sizeW / (double) list.size();

        double x1 = 0;
        double x2 = step;

        g.setColor(color);
        while (iter.hasNext()) {
            g.drawLine(
                    (int) x1, (int) Math.abs(sizeH /2 * (y1 - 1)),
                    (int) x2, (int) Math.abs(sizeH /2 * (y2 - 1)));

            x1 = x2;
            x2 += step;

            y1 = y2;
            y2 = iter.next();

        }
    }

    private static double getMax(List<Double> list) {
        double max = - 8888;
        for (double e : list)
            if (e > max) max = e;
        return max;
    }

    private static double getMin(List<Double> list) {
        double min = 8888;
        for (double e : list)
            if (e < min) min = e;
        return min;
    }

    public static Graphics2D getBackgroundedGraphics2D(BufferedImage bi, Color color) {
        Graphics2D g = bi.createGraphics();
        g.setColor(color);
        g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        return g;
    }

    public static void save(BufferedImage bi, File file) {
        try {
            ImageIO.write(bi, "PNG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
