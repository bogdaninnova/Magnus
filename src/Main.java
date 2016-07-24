import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {


//        Calculator4 c = new Calculator4(1, 0.25 * 1, 0);
//        double t = 15;
//        while (c.t < t)
//            c.iteration(false);
//        while (c.t < t + 1)
//            c.iteration(true);
//        TextWriter.writeDoubleList(c.ksiListTheor, "ksiListTheor");
//        TextWriter.writeDoubleList(c.ksiList, "ksiList");
//
//        System.exit(0);
        ExcelWriter ew = new ExcelWriter();

        for (int alp = -5; alp <= 5; alp++) {
            String name = "alpha = 10^" + alp;
            double alpha = Math.pow(10, alp);

            System.out.println("Alpha = " + alpha);

            Calculator4 c0 = new Calculator4(alpha, 0.25 * alpha, 0);
            double tim = 3;
            while (c0.t < tim)
                c0.iteration(false);
            while (c0.t < tim + 1)
                c0.iteration(true);
            ew.addColumn(name, c0.ksiList);
            ew.addColumn(name, c0.ksiListTheor);
        }
        ew.write("ksiTheor");
    }



    private static ArrayList<Double> calculate42(double alpha, double PSI) {

        ArrayList<Double> list = new ArrayList<>();
        for (double phase = 0; phase <= 2 * Math.PI; phase += round(phase + 0.1, 1)) {
            Calculator4 c = new Calculator4(alpha, PSI, phase * Math.PI);
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
