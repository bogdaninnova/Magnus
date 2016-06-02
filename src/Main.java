public class Main {

    public static void main(String[] args) {


        int m = 10;
   //     m = 30;
        int n = 10;
        Calculator calc = new Calculator();

        while (calc.t < m)
            calc.iteration(false);

        while (calc.t < m + n)
            calc.iteration(true);

        System.out.println(calc.L.getX());

        TextWriter.writeTraectorysCoordinates(calc.locationList, "location");
        TextWriter.writeTraectorysCoordinates(calc.magnetizationList, "magnetization");





    }



}
