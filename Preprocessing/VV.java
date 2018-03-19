import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
// to do normalization 
public class VV {
	Scanner sc;
	String name;
	public VV (Scanner sc, String nameOfFile) {
		this.sc=sc;
		this.name=nameOfFile;
		}
	public SumIteration execute() {
		int sum=0;
		int it=0;
		ArrayList<String> Lines =new ArrayList<String>();
		while(sc.hasNext()) {
			String line = sc.next();
			String[] parts = line.split(",");
			Lines.add(line);
			sum=sum+Integer.parseInt(parts[3]);
			it++;
		}
		
		SumIteration sumIt = new SumIteration(sum, it,name,Lines);
		return sumIt;
	}
	
	

	public static void main(String[] args) throws IOException, ParseException {
		final File f= new File(args[0]); 
		List<SumIteration> listOfClass = new ArrayList<SumIteration>();
		List<Float> SquaresOfVariance = new ArrayList<Float>();
		int totalSum=0;
		int it =0;
		float average;
		try {
			for(File file: f.listFiles()) {
				Scanner sc= new Scanner(file);
				VV vv= new VV(sc, file.getAbsolutePath().replaceAll(".csv",""));
				SumIteration lala = vv.execute();
				listOfClass.add(lala);  
				
				}
			System.out.println(listOfClass);
			for(SumIteration sumit: listOfClass) {
				totalSum = totalSum + sumit.sum;
				it=it+sumit.it;
			}
			average = (float)totalSum/(float)it;
			for(SumIteration su: listOfClass) {
				ArrayList<String> listOfnewClass = new ArrayList<String>();
				for(String line:su.getLines()) {
					String[] comp = line.split(",");
					float newValue =Integer.parseInt(comp[3])-average;
					
//					String newLine = comp[0] + "," + comp[1] + "," + comp[2] + "," + newValue + "," + comp[4];
//					listOfnewClass.add(newLine);
				}
				su.setLines(listOfnewClass);
			}
			System.out.println(listOfClass);
			for(SumIteration sumi: listOfClass) {
				BufferedWriter writer = new BufferedWriter(new FileWriter(sumi.name+"_Normalized" + ".csv"));
				for(String line : sumi.getLines()) {
					writer.write(line);
					writer.newLine();
				}
				writer.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
		
		public class SumIteration {
			int sum;
			int it;
			String name;
			private ArrayList<String> lines;
			public SumIteration(int sum, int it, String name, ArrayList<String> lines) {
				this.sum=sum;
				this.it=it;
				this.name=name;
				this.setLines(lines);
			}
			public String toString() {
				return "[" + sum+ "," + it +"," +name + "," + lines + "," + "]";
			}
			public ArrayList<String> getLines() {
				return lines;
			}
			public void setLines(ArrayList<String> lines) {
				this.lines = lines;
			}
		}
//		public float StandardDeviation(float average) {
//			
//			for(SumIteration classes : listOfClass) {
//				for(String hochuspat :classes.lines) {
//					String[] components = hochuspat.split(",");
//					float yawaspat = Integer.parseInt(components[3]);
//				}
//			}
//		}
}
