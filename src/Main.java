import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

    	Calculator calc = new Calculator();
		ArrayList<Double> listKsi = new ArrayList<Double>(10000);
		ArrayList<Double> listX = new ArrayList<Double>(10000);
		ArrayList<Double> listY = new ArrayList<Double>(10000);
		
		
		for (int i = 0; i < 100000; i++)
			calc.RungeKuttIteration();
		
    	for (int i = 0; i < 5000; i++) {
    		calc.RungeKuttIteration();
    		calc.ux += calc.getUx();
    		calc.uy += calc.getUy();
			listKsi.add(calc.ksi);
			listX.add(calc.ux);
			listY.add(calc.uy);
	   	}

		writeDoubleList(listKsi, "listKsi");
		writeDoubleList(listX, "listX");
		writeDoubleList(listY, "listY");
    }
    
	@SuppressWarnings("resource")
	public static void writeDoubleList(List<Double> averrageList, String name) {
		ListIterator<Double> iter = averrageList.listIterator();
		File file = new File(name + ".txt");
		try {
			FileWriter writer = new FileWriter(file);
			while (iter.hasNext())
				writer.append(iter.next() + "\r\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}