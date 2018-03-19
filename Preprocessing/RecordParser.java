
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class RecordParser {
	private BufferedReader reader;
	private List<Energy> k;
	private List<String> output;
	
	public RecordParser(BufferedReader reader) {
		this.reader =reader;
		this.k=new ArrayList<Energy>();
		this.output = new ArrayList<String>();
		}
	public void execute() throws IOException {
		String line =null;
		while((line=reader.readLine())!= null) {
			String[] parts =line.split(",");
			switch(parts[2]) {
			case "251" : break;
			case "252" : break;
			case "253" : break;
			case  "254" : break;
			default: 
			addEnergy(new Energy(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), parts[5]));
			Energy energy = getEnergy(parts[3], parts[5]);
			if(!energy.getAppliance().equals(parts[2])) {
			energy.setValue(energy.getValue()+Integer.parseInt(parts[4]));
			}
			break;
			}
			
		}
		
	
	for(Energy a :k) {
		output.add(a.getEnergy());
	}
}
 private void addEnergy (Energy energy) {
	for(Energy a: k) {
		if(a.getDate().equals(energy.getDate()) && a.getTime().equals(energy.getTime())) {
			return;
		}
	}
	k.add(energy);
}
private Energy getEnergy(String b, String c) {
	for(Energy a : k) {
		if(a.getDate().equals(b) && a.getTime().equals(c)) {
			return a;
		}
	}
	return null;
}
public List<String> getOutput(){
	return output;
}
public static void main(String[] args) throws IOException {
	String input =  "3,101006,2,2010-05-12,50,00:00:00\n"+
                    "3,101006,3,2010-05-12,153,00:00:00\n"+
                    "3,101006,251,2010-05-12,144,00:20:00\n"+
                    "3,101006,252,2010-05-12,78,00:20:00" ;
	
BufferedReader reader= new BufferedReader(new StringReader(input));
//System.out.println(reader.readLine());
RecordParser recordParser = new RecordParser(reader);
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
	private String Appliance;
	private String date;
	private int value;
	private String time;
	public Energy (String folderNum,String HouseholderID,String Appliance,String date,int value,String time ) {
		this.folderNum=folderNum;
		this.HouseholderID=HouseholderID;
		this.setAppliance(Appliance);
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
	public String getAppliance() {
		return Appliance;
	}

	public void setAppliance(String appliance) {
		Appliance = appliance;
	}
	public String getEnergy() {
		return folderNum + "," + HouseholderID + "," + getAppliance() + "," + date + "," + value + "," +time;
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
