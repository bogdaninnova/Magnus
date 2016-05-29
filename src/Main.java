public class Main {

    public static void main(String[] args) {


        int m = 1000000;
   //     m = 30;
        int n = 100000;
        Calculator calc = new Calculator();

        while (m --> 0)
            calc.iteration(false);

        while (n --> 0)
            calc.iteration(true);

        TextWriter.writeTraectorysCoordinates(calc.locationList, "location");
        TextWriter.writeTraectorysCoordinates(calc.magnetizationList, "magnetization");





    }



}
