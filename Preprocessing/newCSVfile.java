 import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
public class newCSVfile {
	// prepare 224x3024 matrix of labels as household ids and columns as normalized values
		final static String[][] matrix=new String[10000][2];
		Scanner sc;
		static int count =0;
		String name;
		public newCSVfile(Scanner sc, String name) {
			this.sc=sc;
			this.name=name;
			}
		public newCSVfile() {
			
		}
		public void execute() {
			int i=1;
			matrix[0][count] = name;
			while(sc.hasNext()) {
				
				String[] lineComponents = sc.next().split(",");
				matrix[i][count]=lineComponents[3];
				
				i++;
				
			}
		}
		
	public static void main(String[] args) throws IOException, ParseException {
		         // path where your source will be taken
				final File f= new File("/home/aserikova/EnergyResearch/DataHidingNewVersionWithEveryWeek/WeeklyFilesNormalizedWholeWeek/");
				BufferedWriter write = new BufferedWriter(new FileWriter("generatedCSVfile" + ".tsv"));
				for(File file: f.listFiles()) {
					Scanner sc = new Scanner(file);
					newCSVfile k = new newCSVfile(sc, file.getName().replaceAll("aggregated_10min_Granularity", "_").replaceAll("_Normalized", ""));
					k.execute();
					count++;
					}	
				
				 for (int i = 0; i < matrix.length; i++) {
				        for (int j = 0; j<matrix[0].length; j++)
				        { 
				        	if(i==0) {
				        		 write.write(matrix[i][j] + "\t\t");
				               } else {
				        write.write(matrix[i][j] + "\t\t");
				               }
				        }
				        write.write("\n");
				    }
				    write.close();	
	}

	}

