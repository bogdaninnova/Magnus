import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

//        double al = Math.pow(10, 0);
//        Calculator4 c = new Calculator4(al, 0.25 * al, 0);
//        double t = 15;
//        while (c.t < t)
//            c.iteration(false);
//        while (c.t < t + 1)
//            c.iteration(true);
//        TextWriter.writeDoubleList(c.ksiListTheor, "ksiListTheor");
//        TextWriter.writeDoubleList(c.ksiList, "ksiList");
//        TextWriter.writeDoubleList(c.sList, "sList");
//        TextWriter.writeDoubleList(c.sTheorList, "sTheorList");
//
//        System.exit(0);


        ExcelWriter ew = new ExcelWriter();
        String name = "alpha = 10^-5 - 10^5";
        String nameTheor = "(th)alpha = 10^-5 - 10^5";
        for (int alp = 5; alp <= 5; alp++) {
            double alpha = Math.pow(10, alp);
            ArrayList<Double> listS = new ArrayList<>();
            ArrayList<Double> listSTheor = new ArrayList<>();
            for (double phase = 0; phase <= 2; phase = round(phase + 0.1, 1)) {
                System.out.println("alpha = " + Math.pow(10, alp) + "; phase = " + phase + "; " + new Date());
                Calculator4 c = new Calculator4(alpha, 0.25 * alpha, phase * Math.PI);
                double time = 5;
                while (c.t < time)
                    c.iteration(false);
                while (c.t < time+1)
                    c.iteration(true);
                listS.add(c.sGamma);
                listSTheor.add(c.sTheorGamma);
            }
            ew.addColumn(name, listS);
            ew.addColumn(name, listSTheor);
            //ew.addColumn(nameTheor, listSTheor);
        }

        ew.write("S_lists");

    }


    private static void calc() {
        ExcelWriter ew = new ExcelWriter();

        for (int alp = -5; alp <= 5; alp++) {
            String name = "alpha = 10^" + alp;
            double alpha = Math.pow(10, alp);

            System.out.println("Alpha = " + alpha);

            Calculator4 c0 = new Calculator4(alpha, 0.25 * alpha, 0);
            double tim = 50;
            while (c0.t < tim)
                c0.iteration(false);
            while (c0.t < tim + 1)
                c0.iteration(true);
            ew.addColumn(name, c0.sList);
            ew.addColumn(name, c0.sTheorList);
        }
        ew.write("sTheor");

    }


    private static ArrayList<Double> calculate42(double alpha) {

        ArrayList<Double> list = new ArrayList<>();
        for (double phase = 0; phase <= 2; phase = round(phase + 0.1, 1)) {
            Calculator4 c = new Calculator4(alpha, 0.25 * alpha, phase * Math.PI);
            double time = 5;
            while (c.t < time)
                c.iteration(false);
            while (c.t < time+1)
                c.iteration(true);
            list.add(c.sGamma);
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
