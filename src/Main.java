public class Main {

    public static void main(String[] args) {

        System.out.println(Calculator.v0T * Calculator.dt * 0.3627554017749045 / 0.00628 * 100000) ;
        System.exit(0);

        int m = 5;
   //     m = 30;
        int n = 1;
        Calculator calc = new Calculator();

        while (calc.t < m)
            calc.iteration(false);

        while (calc.t < m + n)
            calc.iteration(true);

        System.out.println(calc.L.getX());

        System.out.println(calc.getAverU().getX());
        System.out.println(calc.getAverU().getY());
        System.out.println(calc.getAverU().getZ());

        TextWriter.writeTraectorysCoordinates(calc.locationList, "location");
        TextWriter.writeTraectorysCoordinates(calc.magnetizationList, "magnetization");





    }



}
