import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Summerenergy {
	private BufferedReader reader;
	private List<Energy> k;
	private List<String> output;
	private int[] intdays;
	
	public Summerenergy(BufferedReader reader) {
		this.reader =reader;
		this.k=new ArrayList<Energy>();
		this.output = new ArrayList<String>();
		}
	public void execute() throws IOException {
		String line =null;
		while((line=reader.readLine())!= null) {
			String[] parts =line.split(","); 
		     String[] days=parts[2].split("-");
			 for(int i=0; i<days.length; i++){
					 intdays[i]= Integer.parseInt(days[i]);
					 }
					 for(int j=6; j<=8; j++) {
						 for(int m=1; m<=31; m++) {
							 if(intdays[2]==m && intdays[1]==j && intdays[0]==2010) {
								 addEnergy(new Energy(parts[0], parts[1], parts[2],Integer.parseInt(parts[3]), parts[4]));
							 }
							 }
					 }
			//addEnergy(new Energy(parts[0], parts[1], parts[2],Integer.parseInt(parts[3]), parts[4]));
			Energy energy = getEnergy(parts[4]);
			energy.setValue(energy.getValue()+Integer.parseInt(parts[4]));
			
			
			
		}
		
	
	for(Energy a :k) {
		output.add(a.getEnergy());
	}
}
 private void addEnergy (Energy energy) {
//	 String[] days=energy.getDate().split(Pattern.quote("/"));
//	 intdays = null;
	 for(Energy a: k) {
			if(a.getDate().equals(energy.getDate()) && a.getTime().equals(energy.getTime())) {
				return;
			}
//	 for(int i=0; i<days.length; i++){
//	 intdays[i]= Integer.parseInt(days[i]);
//	 }
//	 for(int j=6; j<=8; j++) {
//		 for(int m=1; m<=31; m++) {
//			 if(intdays[0]==m && intdays[1]==j && intdays[2]==2010) {
//				 k.add(energy);
//			 }
//			 }
//	 }
	 }
	
}
public Energy getEnergy(String b) {
	
	for(Energy a : k) {
		if( a.getTime().equals(b)) {
			int n=a.getNum()+1;
			a.setNum(n);
			return a;
		}
	}
	return null;
}
public List<String> getOutput(){
	return output;
}
public static void main(String[] args) throws IOException {
	String input =  "3,101006,2010-06-12,50,00:00:00\n"+
                    "3,101006,2010-06-13,153,00:00:00\n"+
                    "3,101006,2010-05-12,144,00:20:00\n"+
                    "3,101006,2010-05-12,78,00:20:00" ;
	
BufferedReader reader= new BufferedReader(new StringReader(input));
//System.out.println(reader.readLine());
Summerenergy recordParser = new Summerenergy(reader);
try {
 recordParser.execute();
 for(String s : recordParser.getOutput()) {
	 System.out.println(s);
 }
} catch(IOException e) {
 e.printStackTrace();
}
try {
 reader.close();
} catch(IOException e) {
 
 e.printStackTrace();
}
}
public class Energy {
	private String folderNum;
	private String HouseholderID;
	private String date;
	private int value;
	private String time;
	private int num=0;
	public Energy (String folderNum,String HouseholderID,String date,int value,String time ) {
		this.folderNum=folderNum;
		this.HouseholderID=HouseholderID;
		this.date=date;
		this.value=value;
		this.time=time;
	}
	
	public void setDate(String date) {
		this.date=date;
	}
	public String getDate() {
		return date;
	}
	public void setValue(int value){
		this.value=value;
	}
	public int getValue() {
		return value;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTime() {
		return time;
	}
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	public String getEnergy() {
		return folderNum + "," + HouseholderID + "," + date + "," + value/(num+1) + "," +time;
//		StringBuilder builder = new StringBuilder();
//		builder.append(folderNum);
//		builder.append(",");
//		builder.append(HouseholderID);
//		builder.append(",");
//		//builder.append(Appliance);
//		//builder.append(",");
//		builder.append(date);
//		builder.append(",");
//		builder.append(value);
//		builder.append(",");
//		builder.append(time);
//		return builder.toString();
	}

}
}

