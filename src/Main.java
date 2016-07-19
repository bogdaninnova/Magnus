import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        for (double psi = 0.1; psi <= 10; psi = round(psi + 0.1, 1)) {
            System.out.println(psi);
            System.out.println(new Date());
            System.out.println();
            Draw2DGraphic.draw2dDouble(calculate42(1, psi * Math.PI), "1\\psi = " + psi + " pi");
        }
        System.exit(0);

        Calculator4 c = new Calculator4(0.1, 80*Math.PI / 8, Math.PI + Math.PI /4);
        double time = 5;
        while (c.t < time)
            c.iteration(false);
        while (c.t < time+3)
            c.iteration(true);
        Draw2DGraphic.draw2D(c.track, "track3");
    }


    private static ArrayList<Double> calculate4(double alpha, double PSI) {

        Calculator4 c = new Calculator4(alpha, PSI, 0);
        while (c.t < 10)
            c.iteration(false);
        while (c.t < 11)
            c.iteration(true);
        ArrayList<Double> list = new ArrayList<>();
        for (double phase = 0; phase <= 2 * Math.PI; phase += Math.PI / 100) {
            list.add((Math.cos(phase) * c.Is - Math.sin(phase) * c.Ic));
        }
        return list;
    }

    private static ArrayList<Double> calculate42(double alpha, double PSI) {

        ArrayList<Double> list = new ArrayList<>();
        for (double phase = 0; phase <= 2 * Math.PI; phase += Math.PI / 20) {
            Calculator4 c = new Calculator4(alpha, PSI, phase);
            double time = 5;
            while (c.t < time)
                c.iteration(false);
            while (c.t < time+1)
                c.iteration(true);
            list.add(c.Is);
        }
        return list;
    }


    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
