import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

public class Main {




    public static void main(String[] args) {
        ZipUtils.saveCodeInHistory();
        ExcelWriter ew = new ExcelWriter();
        for (double phase = 0; phase <= 2; phase = round(phase + 0.1, 1)) {
            ArrayList<Double> arrayList = new ArrayList<>();
            for (double alpha = 0; alpha <= 9.9; alpha = round(alpha + 0.1, 1)) {
                System.out.println("phase = " + phase);
                System.out.println("alpha = " + alpha);
                System.out.println("Date = " + new Date() + "\n");
                CalculatorLimited calculator0 = new CalculatorLimited(alpha, 0.25 * alpha, phase);
                double t0 = 5;
                while (calculator0.t < t0)
                    calculator0.iteration(false);
                while (calculator0.t < t0 + 1)
                    calculator0.iteration(true);
                arrayList.add(calculator0.sGamma);
            }
            for (double alpha = 10; alpha <= 99; alpha++) {
                System.out.println("phase = " + phase);
                System.out.println("alpha = " + alpha);
                System.out.println("Date = " + new Date() + "\n");
                CalculatorLimited calculator0 = new CalculatorLimited(alpha, 0.25 * alpha, phase);
                double t0 = 5;
                while (calculator0.t < t0)
                    calculator0.iteration(false);
                while (calculator0.t < t0 + 1)
                    calculator0.iteration(true);
                arrayList.add(calculator0.sGamma);
            }
            for (double alpha = 100; alpha <= 204; alpha += 5) {
                System.out.println("phase = " + phase);
                System.out.println("alpha = " + alpha);
                System.out.println("Date = " + new Date() + "\n");
                CalculatorLimited calculator0 = new CalculatorLimited(alpha, 0.25 * alpha, phase);
                double t0 = 5;
                while (calculator0.t < t0)
                    calculator0.iteration(false);
                while (calculator0.t < t0 + 1)
                    calculator0.iteration(true);
                arrayList.add(calculator0.sGamma);
            }
            ew.addColumn("0 10 100 200", arrayList);
            TextWriter.writeDoubleList(arrayList, "s(alpha) " + phase);
        }
        ew.write("4PSI");
        System.exit(0);

//        ArrayList<Double> arrayList = new ArrayList<>();
//        ArrayList<Double> arrayList2 = new ArrayList<>();
//        for (double alpha = Math.pow(10, -5); alpha < Math.pow(10, 5); alpha *= 10) {
//            Calculator4 calculator = new Calculator4(alpha, 0.25 * alpha, 0 * Math.PI);
//            double t = 5;
//            while (calculator.t < t)
//                calculator.iteration(false);
//            while (calculator.t < t + 0.5)
//                calculator.iteration(true);
//            arrayList.add(calculator.sGamma);
//            arrayList2.add(calculator.sTheorGamma);
//            System.out.println(alpha);
//        }
//        TextWriter.writeDoubleList(arrayList, "array4Psi");
//        TextWriter.writeDoubleList(arrayList2, "array4Psi2teor");
//
////         TextWriter.writeDoubleList(c.ksiListTheor, "ksiListTheor");
////         TextWriter.writeDoubleList(c.ksiList, "ksiList");
////         TextWriter.writeDoubleList(c.sList, "sList");
////         TextWriter.writeDoubleList(c.sTheorList, "sTheorList");
//
//        System.exit(0);

        double phase = 0;
        for (double alpha = 0.01; alpha <= 0.01; alpha = round(alpha + 0.1, 3)) {
            ArrayList<Double> listS = new ArrayList<>();
            for (double psi = 0; psi <= 500; psi += 10) {
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
                System.out.println(psi);
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
