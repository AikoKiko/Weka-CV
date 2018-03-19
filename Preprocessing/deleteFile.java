package deleteFile;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class deleteFile {
	private Scanner sc;
	private Set <String> sortedTimes;
	public deleteFile(Scanner sc) {
		this.sc =sc;
		
	}
	public deleteFile() {
		
	}
	
   public void execute() throws ParseException, IOException {
	   BufferedWriter writer = new BufferedWriter(new FileWriter("appliance_group_10min_Granularity", true));
	   String line = null;
	   while (sc.hasNextLine()) {
		   line=sc.nextLine();
		   System.out.println(line);
		   String[] columns = line.split(",");
		   sortedTimes = sortOutTimes ();
		   if(sortedTimes.contains(columns[5])) {
		   writer.write(line);
		   writer.newLine();
		   }	
	   }
		   writer.close();

   }
   
//   public String toString(String line) {
//	   return line;
//   }
   static Set<String> sortOutTimes() throws ParseException {
        String time = "00:00:00";
	    Set<String> sortedTimes = new TreeSet<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		sortedTimes.add(time);
	    Date date = sdf.parse("00:00:00");
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    for(int i = 0; i<144; i++) {
	    	c.add(Calendar.MINUTE, 10);
	    	sortedTimes.add(sdf.format(c.getTime()));
	    }
//	    	} while (c.getTime().after(date));
	 
	    return sortedTimes;
   }
	   
   public  void GetAllFiles(final File folder) throws IOException, ParseException {
	  File[] files= folder.listFiles();
	  try {
	  for(int i =0; i<files.length;i++) {
		  SortOutFiles(files[i]);
	  }
	  }  catch (java.io.IOException e) {
          System.out.println("Error");
      }
   }
			
	public void SortOutFiles(File file) throws IOException, ParseException	 {
		FileInputStream fstream =null;
		Scanner sc= null;
//		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt",true));
		try {
		fstream = new FileInputStream(file);
		 sc= new Scanner(fstream);
		deleteFile dataWith10minGranularity = new deleteFile(sc);
      	dataWith10minGranularity.execute();
//      	 writer.write(dataWith10minGranularity.execute());
//			writer.newLine();
			if (sc.ioException() != null) {
		        throw sc.ioException();
		    } 
		}  finally {
		    if ( fstream!= null) {
		        fstream.close();
//		        writer.close();
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
	}
		
	public static void main(String[] args) throws IOException, ParseException {
		final File f= new File(args[0]);
		deleteFile k =new deleteFile();
		try {
		k.GetAllFiles(f);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   }
