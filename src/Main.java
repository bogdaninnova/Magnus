import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        ExcelWriter ew = new ExcelWriter();

//        for (int a = -5; a <= 5; a++) {
//            double alpha = Math.pow(10, a);
//            System.out.println(Math.atan(alpha/4));
//        }

        for (int al = -5; al <= 0; al++) {
            System.out.println(new Date());
            System.out.println(al);
            double alpha = Math.pow(10, al);
            Calculator4 c0 = new Calculator4(alpha, 0.25 * alpha, 0);
            double t = 0;
            while (c0.t < t)
                c0.iteration(false);
            while (c0.t < t + 1000)
                c0.iteration(true);

            ew.addColumn("ksiList" + alpha, c0.ksiList);
            ew.addColumn("ksiList" + alpha, c0.ksiListTheor);
            ew.addColumn("ksiList" + alpha, minus(c0.ksiListTheor, c0.ksiList));
            ew.addColumn("sList" + alpha, c0.sList);
            ew.addColumn("sList" + alpha, c0.sTheorList);

            ew.write("gl");
        }
        System.exit(0);



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


    private static ArrayList<Double> minus(ArrayList<Double> list2, ArrayList<Double> list1) {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < list2.size(); i++)
            list.add(list2.get(i) - list1.get(i));
        return list;
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
