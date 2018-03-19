import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
// to aggregate 2 min data to 10 min data 

public class Ten_Min_Granularity {
	private Scanner sc;
	private String name;
	private int sequence;
	private Map<Date, ArrayList<String>> mapWithDates;
	public Ten_Min_Granularity (Scanner sc, String name) {
		this.name=name;
		this.sc =sc;
		this.mapWithDates = new TreeMap<Date, ArrayList<String>>();
	}
	public Ten_Min_Granularity () {
		
	}
	
   public void execute() throws ParseException, IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(name +"appliance_group_10min_Granularity" + ".csv"));
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
    //DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss"); 
    //Map<Date, Integer> mapWithHours = new TreeMap<Date, Integer>();
//    int count=0;
//    int sequence =0;
	   String line = null;
	while (sc.hasNext()) {
		line=sc.next();
		String[] components = line.split(",");
		if(mapWithDates.isEmpty()==false) {
		Set<Date> keySet = mapWithDates.keySet();
		for(Date s: keySet){
			if(dateFormat.parse(components[2]).equals(s)) {
				ArrayList<String> key = mapWithDates.get(s);
				key.add(line);
				mapWithDates.put(s,key);
			}
			else {
				ArrayList<String> list = new ArrayList<String>();
				list.add(line);
				mapWithDates.put(dateFormat.parse(components[2]), list);
			}
		}
		} else {
			ArrayList<String> list = new ArrayList<String>();
			list.add(line);
			mapWithDates.put(dateFormat.parse(components[2]),list);
		}
	}
	  Set<Map.Entry<Date, ArrayList<String>>> set = mapWithDates.entrySet();
	  for(Map.Entry<Date, ArrayList<String>> s:set){
		  ArrayList<String> array =s.getValue();
		  int size = array.size();
		  if(size!=720) {
			  mapWithDates.remove(s.getKey());
			  }
//		  for(String lines:array) {
//			  String[] parts = lines.split(",");
//			  
//			  mapWithHours.put(hourFormat.parse(parts[4]), Integer.parseInt(parts[3]));
//			  }
//		  
//		  int count=0;
//		  Set<Map.Entry<Date, Integer>> naruto = mapWithHours.entrySet();
//		  for(Map.Entry<Date, Integer> boruto :naruto) {
//			  
//		  }
//		  
		 Map<Date, Integer> finalSort = SortWithTimes(array);
		 String line2 =array.get(0);
		 String[] rara = line2.split(",");
		 for(Map.Entry<Date, Integer> naruto:finalSort.entrySet()) {
			 String highLine = rara[0] + "," + rara[1] + "," + rara[3] + "," + naruto.getKey()  + "," + naruto.getValue(); 
		 }
		  }
	  
	  }
	
  public Map<Date, Integer> SortWithTimes(ArrayList<String> finalSort) throws NumberFormatException, ParseException {
	  DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss"); 
	  Map<Date, Integer> map = new TreeMap<Date, Integer>();
	  Map<Date, Integer> MAP = new TreeMap<Date, Integer>();
	  String time = "00:10:00";
	  Date date = hourFormat.parse(time);
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	  int count =0;
	  int sum =0;
	  for(String s: finalSort) {
		  String[] parts=s.split(",");
		  
		  map.put(hourFormat.parse(parts[4]), Integer.parseInt(parts[3]));
	  }
	  
	 Iterator<Entry<Date, Integer>> iterator= map.entrySet().iterator();
	  while(iterator.hasNext()) {
		  count++;
		  Map.Entry<Date, Integer> entry = iterator.next();
		  if(count==720) {
			  sum=sum + entry.getValue();
			  sequence =sum;
			  
		  }
		  else if((count%5)!=0) {
			   sum=sum + entry.getValue();
		   }
		  
		   else {
			   sum = sum + entry.getValue();
			   MAP.put(c.getTime(), sum);
			   c.add(Calendar.MINUTE, 10);
			   sum = 0;
		   }
		 
		  
	  }
	  return  MAP;
	  
  }
	
	
	public static void main(String[] args) throws IOException, ParseException {
		final File f= new File(args[0]);
		Ten_Min_Granularity k =new Ten_Min_Granularity();
		try {
		k.GetAllFiles(f);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	  public  void GetAllFiles(final File folder) throws IOException, ParseException {
		  File[] files= folder.listFiles();
		  try {
		  for(int i =0; i<files.length;i++) {
			  SortOutFiles(files[i]);
		  }
		  }  catch (java.io.IOException e) {
	          System.out.println("ne znaiu daje chto proishodit");
	      }
	   }
				
		public void SortOutFiles(File file) throws IOException, ParseException	 {
			FileInputStream fstream =null;
			Scanner sc= null;
//			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt",true));
			try {
			fstream = new FileInputStream(file);
			 sc= new Scanner(fstream);
			 Ten_Min_Granularity dataWith10minGranularity = new Ten_Min_Granularity(sc, file.getAbsolutePath().replaceAll(".csv", ""));
	      	dataWith10minGranularity.execute();
//	      	 writer.write(dataWith10minGranularity.execute());
//				writer.newLine();
				if (sc.ioException() != null) {
			        throw sc.ioException();
			    } 
			}  finally {
			    if ( fstream!= null) {
			        fstream.close();
//			        writer.close();
			    }
			    if (sc != null) {
			        sc.close();
			    }
			}
		}
			
	
}


