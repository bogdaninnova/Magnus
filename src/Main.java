import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        ArrayList<Double> arrayList = new ArrayList<>();
        ArrayList<Double> arrayList2 = new ArrayList<>();
        for (double psi = 0; psi <= 250; psi += 10) {
            Calculator4 calculator = new Calculator4(4 * psi, psi, 0);
            double t = 5;
            while (calculator.t < t)
                calculator.iteration(false);
            while (calculator.t < t + 0.5)
                calculator.iteration(true);
            arrayList.add(calculator.sGamma);
            arrayList2.add(calculator.sTheorGamma);
            System.out.println(psi);
        }
        TextWriter.writeDoubleList(arrayList, "arrayList4Psi");
        TextWriter.writeDoubleList(arrayList2, "arrayList4Psi2");

        System.exit(0);

        double phase = 0;
        for (double alpha = 490; alpha <= 1000; alpha = round(alpha + 10, 1)) {
            ArrayList<Double> listS = new ArrayList<>();
            for (double psi = 0; psi <= 1000; psi = round(psi + 10, 1)) {
                System.out.println(new Date());
                System.out.println("alpha = " + alpha);
                System.out.println("psi = " + psi);
                Calculator4 calc = new Calculator4(alpha, psi, phase);
                double time = 5;
                while (calc.t < time)
                    calc.iteration(false);
                while (calc.t < time+0.5)
                    calc.iteration(true);
                listS.add(calc.sGamma);
            }
            //ew.addColumn(name, listS);
            TextWriter.writeDoubleList(listS, "alpha = " + alpha);
        }

        //ew.write("phase = " + phase);

    }


    private static ArrayList<Double> minus(ArrayList<Double> list2, ArrayList<Double> list1) {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < list2.size(); i++)
            list.add(list2.get(i) - list1.get(i));
        return list;
    }


    private static void calc() {
        ExcelWriter ew = new ExcelWriter();

        double phase = 0;
        String name = "10 - 1000";
        for (double alpha = 10; alpha <= 1000; alpha += 10) {
            ArrayList<Double> listS = new ArrayList<>();
            for (double psi = 10; psi <= 1000; psi += 10) {
                System.out.println("alpha = " + alpha + "; psi = " + psi + "; " + new Date());
                Calculator4 c = new Calculator4(alpha, psi, phase);
                double time = 5;
                while (c.t < time)
                    c.iteration(false);
                while (c.t < time+0.5)
                    c.iteration(true);
                listS.add(c.sGamma);
            }
            ew.addColumn(name, listS);
        }

        ew.write("phase = " + phase);

    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
