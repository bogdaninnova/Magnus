import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        
    	
    	
    	Calculator calc = new Calculator();
    	    	
    	ArrayList<Double> listX = new ArrayList<Double>(1000);
    	ArrayList<Double> listY = new ArrayList<Double>(1000);
    	
    	for (int i = 0; i < 100000; i++) 
    		calc.iteration();
    	for (int i = 0; i < 1000; i++) {
    		calc.iteration();
			listX.add(calc.ux);
			listY.add(calc.uy);
    	}
    	

    	
    	writeDoubleList(listX, "listX");
    	writeDoubleList(listY, "listY");
    }
	/*
   	for (int i = 0; i < 100000; i++) {
	if (counter++ == 1000) {
		counter = 0;
		listX.add(calc.getAverrageUx());
		listY.add(calc.getAverrageUy());
		calc.ux = 0;
		calc.uy = 0;
		calc.counter = 0;
	}
	calc.iteration();

}
 * */
    
	@SuppressWarnings("resource")//damn
	public static void writeDoubleList(List<Double> averrageList, String name) {
		
		ListIterator<Double> iter = averrageList.listIterator();
		
		File file = new File(name + ".txt");
		try {
			FileWriter writer = new FileWriter(file);

			while (iter.hasNext()) writer.append(iter.next() + "\r\n");
			
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}






}
