import java.util.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class newFeaturesCalculation {
		private Scanner sc;
		static String name;
		int lineNumbers=0;
		private Map<Date, ArrayList<String>> days;
		static Map<Date, DaysParameters> weekdays;
		static Map<Date, DaysParameters> weekends;
		static Map<Date, DaysParameters> everydays;
		static ArrayList<Float> allValues;
		static ArrayList<Float> variances;
		public void init () {
			everydays=new HashMap<Date, DaysParameters>();
			weekdays=new HashMap<Date,DaysParameters>();
			weekends=new HashMap<Date,DaysParameters>();
			allValues = new ArrayList<Float>();
			variances=new ArrayList<Float>();
		}
		public newFeaturesCalculation (Scanner sc, String name) {
			this.sc=sc;
			this.days=new HashMap<Date,ArrayList<String>>();
		}
		
	    public void readAllLines () throws ParseException {
	    	SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    	while(sc.hasNext() && lineNumbers<1008) {
	    		lineNumbers++;
	    		String[] parts = sc.next().split(",");
	    		float num = Float.parseFloat((parts[3]));
	    		name=parts[1];
	    		allValues.add(num);
	    		if(days.containsKey(spf.parse(parts[2]))) {
	    			days.get(spf.parse(parts[2])).add(parts[3]+","+parts[4]);
	    			//System.out.println(days);
	    		}
	    		else {
	    			ArrayList<String> day= new ArrayList<String>();
	    			day.add(parts[3]+"," + parts[4]);
	    			days.put(spf.parse(parts[2]), day);
	    			
	    		}
	    		}
	    	//System.out.println(lineNumbers);
	    	for(Date d : days.keySet()) {
	    		DaysParameters daysParam = new DaysParameters(d);
	            float sume =0, summ=0, sumn=0, sumno=0, sumda=0, sumt=0;
	            float  e=0,m=0,n=0, no=0,da=0,t=0,j =0, k=0, u=0;
	            float max=0, min=50000;
	            
	    		for(String s: days.get(d)) {
	    			String[] components = s.split(",");
	    			float value = Float.parseFloat(components[0]);
	    			//System.out.println(value);
	    			if((eveningTime(sdf.parse(components[1]))!=null)) {
	    				sume =sume+value;
	    				e++;
	    			}
	    			if((morningTime(sdf.parse(components[1]))!=null)) {
	    				summ =summ+value;
	    				m++;
	    			}
	    			if((nightTime(sdf.parse(components[1]))!=null)) {
	    				sumn =sumn+value;
	    				n++;
	    			}
	    			if((noonTime(sdf.parse(components[1]))!=null)) {
	    				sumno =sumno+ value;
	    				no++;
	    			}
	    			if((dayTime(sdf.parse(components[1]))!=null)) {
	    				sumda =sumda+value;
	    				da++;
	    			}
	    			sumt= sumt+value;
	    			t++;
	    			if(value>max) max=value;
	    			if(value<min) min=value;
	    			if(value>1500) j++;
	    			if(value>1000) k++;
	    			if(value>2000) u++;
	    			
	    			
	    		}
	    		//System.out.println(sumt);
	    		//System.out.println(j + "," + k + "," + u + "," +t);
	    		daysParam.evening=(float) sume/e;
	    		daysParam.morning = (float) summ/m;
	    		daysParam.night =(float) sumn/n;
	    		daysParam.noon = (float) sumno/no;
	    		daysParam.day = (float) sumda/da;
	    		daysParam.average=(float) sumt/t;
	    		daysParam.total =(float) sumt;
	    		daysParam.setMax(max);
	    		daysParam.setMin(min);
	    		daysParam.over_1_0_KWh=(float)k/(float)t;
	    		daysParam.over_1_5_KWh=(float)j/(float)t;
	    		daysParam.over_2_0_KWh=(float)u/(float)t; 
	    		daysParam.calculation();
	    		float sumOfsquares=0;
	    		for(int p=0; p<days.get(d).size(); p++) {
					float dif = Float.parseFloat((days.get(d).get(p).split(",")[0]))-daysParam.average;
					sumOfsquares = sumOfsquares + (float)Math.pow(dif, 2);
	    		}
	    		daysParam.variance=sumOfsquares/(float)(days.get(d).size());
	    		everydays.put(d, daysParam);	
	    		
	    	}
	    	
	    }
	    static String dayTime(Date genDate) throws ParseException {

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		    Date fromDate = sdf.parse("05:59:00");
		    Date toDate = sdf.parse("22:00:01");
		    Calendar c = Calendar.getInstance();
		    c.setTime(genDate);
		    if(c.getTime().before(toDate) && c.getTime().after(fromDate)) {
		    String data = sdf.format(genDate);
		    return data;
		    }
		    return null;
		    }
		static String eveningTime(Date genDate) throws ParseException {

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		    Date fromDate = sdf.parse("17:59:00");
		    Date toDate = sdf.parse("22:00:01");
		    Calendar c = Calendar.getInstance();
		    c.setTime(genDate);
		    if(c.getTime().before(toDate) && c.getTime().after(fromDate)) {
		    String data = sdf.format(genDate);
		    return data;
		    }
		    return null;
		    }
		static String morningTime(Date genDate) throws ParseException {

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		    Date fromDate = sdf.parse("05:59:00");
		    Date toDate = sdf.parse("10:00:01");
		    Calendar c = Calendar.getInstance();
		    c.setTime(genDate);
		    if(c.getTime().before(toDate) && c.getTime().after(fromDate)) {
		    String data = sdf.format(genDate);
		    return data;
		    }
		    return null;
		    }
		static String nightTime(Date genDate) throws ParseException {

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		    Date fromDate = sdf.parse("00:59:00");
		    Date toDate = sdf.parse("05:00:01");
		    Calendar c = Calendar.getInstance();
		    c.setTime(genDate);
		    if(c.getTime().before(toDate) && c.getTime().after(fromDate)) {
		    String data = sdf.format(genDate);
		    return data;
		    }
		    return null;
		    }
		static String noonTime(Date genDate) throws ParseException {

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		    Date fromDate = sdf.parse("09:59:00");
		    Date toDate = sdf.parse("14:00:01");
		    Calendar c = Calendar.getInstance();
		    c.setTime(genDate);
		    if(c.getTime().before(toDate) && c.getTime().after(fromDate)) {
		    String data = sdf.format(genDate);
		    return data;
		    }
		    return null;
		    }
	    public static void main(String[] args) throws IOException, ParseException {
			final File f= new File("C:/Users/HP/Desktop/iknow");
			BufferedWriter writer = new BufferedWriter(new FileWriter("featureCollection.csv", true));
			for(File file: f.listFiles()) {
				float max=0, min=0, average=0,evening=0, morning=0, night=0, noon=0,day=0, total=0,variance=0;
				float average_max=0, min_average=0, night_day=0, morning_noon=0, evening_noon=0, max_over_average=0;
				float over_1_5_KWh=0,over_1_0_KWh=0,over_2_0_KWh=0;
				int countOfDays=0;
					Scanner sc= new Scanner(file);
					newFeaturesCalculation input = new newFeaturesCalculation(sc, file.getAbsolutePath().replaceAll(".csv",""));
					Calendar c1 = Calendar.getInstance();
					
					input.init();
					input.readAllLines();
					for(Date d : everydays.keySet()) {
						c1.setTime(d);
						if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || 
							    c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
							weekends.put(d, everydays.get(d));
						}
						else {
							weekdays.put(d, everydays.get(d));
						}
						DaysParameters k = everydays.get(d);
						max=max+k.getMax();
						min=min+k.getMin();
						average=average+k.average;
					
						evening= evening+k.evening;
						morning=morning+k.morning;
						night=night+k.night;
						noon=noon+k.noon;
						day=day+k.day;
						total=total+k.total;
						average_max=average_max+k.average_max;
						//System.out.println(k.average_max);
						evening_noon = evening_noon +k.evening_noon;
						min_average = min_average + k.min_average;
						morning_noon = morning_noon +k.morning_noon;
						night_day = night_day +k.night_day;
						over_1_0_KWh=over_1_0_KWh+k.over_1_0_KWh;
						over_1_5_KWh=over_1_5_KWh+k.over_1_5_KWh;
						over_2_0_KWh=over_2_0_KWh+k.over_2_0_KWh;
						variance = variance+k.variance;
						max_over_average = max_over_average+k.max_over_average;
						
						countOfDays++;
						variances.add(k.variance);
						
						}
					
					float po=0, sumon=0;
					for(int g=0; g<variances.size(); g++) {
						sumon=sumon+variances.get(g);
		    		}
					for(int g=0; g<variances.size(); g++) {
						float dif = variances.get(g)-sumon/variances.size();
						po = po + (float)Math.pow(dif, 2);
		    		}
					Collections.sort(allValues);
						float percentile25,percentile75,median,sum=0,squaredsums=0,difference, weekendsaverage=0, weekdaysaverage=0, countweekends=0, countweekdays=0;
						int size = allValues.size();
						if ((size*0.25) == (int)(size*0.25)) {
							percentile25=(allValues.get((int)(0.25*size-1))+allValues.get((int) (0.25*size)))/2;
						} else {
							percentile25=allValues.get((int)Math.round(0.25*size)-1);
						}
					
						if ((size*0.75) == (int)(size*0.75)) {
							percentile75=(allValues.get((int)(0.75*size-1))+allValues.get((int) (0.75*size)))/2;
						} else {
							percentile75=allValues.get((int) Math.round(0.75*size-1));
						}
						
						int middle = size/2;
						if(allValues.size()%2==1) {
							median=allValues.get(middle);
						} else {
							median = (allValues.get(middle-1)+allValues.get(middle))/2;
						}
						
						for(int i= 0; i<allValues.size(); i++) {
							sum = sum+allValues.get(i);
						}
						
						for(int j=0; j<allValues.size(); j++) {
							difference = allValues.get(j)-sum/allValues.size();
							squaredsums=squaredsums+(float)Math.pow(difference,2);
							}	
						float cons_wd_day=0, cons_wd_morning=0, cons_wd_noon=0,cons_wd_evening=0, cons_wd_night=0,cons_wd_max=0, cons_wd_min=0, cons_we_day=0, cons_we_morning=0, cons_we_noon=0,cons_we_evening=0, cons_we_night=0, cons_we_max=0, cons_we_min=0;
						float var_week_avg=0, var_wd_avg=0;
						for(Date weekedDays : weekends.keySet()) {
							DaysParameters daysP = weekends.get(weekedDays);
							weekendsaverage = weekendsaverage+daysP.average;
							cons_wd_day = cons_wd_day+daysP.day;
							cons_wd_morning=cons_wd_morning+daysP.morning;
							cons_wd_noon=cons_wd_noon+daysP.noon;
							cons_wd_evening=cons_wd_evening+daysP.evening;
							cons_wd_night=cons_wd_night+daysP.night;
							cons_wd_max=cons_wd_max+daysP.max;
							cons_wd_min=cons_wd_min+daysP.min;
							var_wd_avg = var_wd_avg+daysP.variance;
							countweekends++;
							}
						for(Date weekedDays : weekdays.keySet()) {
							DaysParameters daysP = weekdays.get(weekedDays);
							weekdaysaverage = weekdaysaverage+daysP.average;
							cons_we_day = cons_we_day+daysP.day;
							cons_we_morning=cons_we_morning+daysP.morning;
							cons_we_noon=cons_we_noon+daysP.noon;
							cons_we_evening=cons_we_evening+daysP.evening;
							cons_we_night=cons_we_night+daysP.night;
							cons_we_max=cons_we_max+daysP.max;
							cons_we_min=cons_we_min+daysP.min;
							var_week_avg =var_week_avg +daysP.variance;
							countweekdays++;
							}
						
						 Autocorrelation auto = new  Autocorrelation(allValues); 
						String output = name +"," + Math.round((max/countOfDays)*100.0)/100.0 +","+Math.round((min/countOfDays)*100.0)/100.0 +","+ Math.round(average/countOfDays*100.0)/100.0 + "," + Math.round((morning/countOfDays)*100.0)/100.0 +"," + Math.round((evening/countOfDays)*100.0)/100.0 + ","+ Math.round((night/countOfDays)*100.0)/100+ "," + Math.round((noon/countOfDays)*100.0)/100.0 +","+Math.round((day/countOfDays)*100.0)/100.0 + "," + Math.round((total/countOfDays/6/24)*100.0)/100.0 +"," + Math.round((evening_noon/countOfDays)*100.0)/100.0 +"," + Math.round(( min_average/countOfDays)*100.0)/100.0
								+"," + Math.round((morning_noon/countOfDays)*100.0)/100.0+"," + Math.round((night_day/countOfDays)*100.0)/100.0 +"," + 
								Math.round(over_1_0_KWh/countOfDays*100.0)/100.0 + "," + Math.round(over_1_5_KWh/countOfDays*100.0)/100.0 +"," + Math.round(over_2_0_KWh/countOfDays*100.0)/100.0 + "," + Math.round((percentile25)*100.0)/100.0 +"," + Math.round((percentile75)*100.0)/100.0 + ","+ Math.round((median)*100.0)/100.0 +
								"," + Math.round(Math.sqrt(Math.sqrt((squaredsums/(float)(allValues.size()))))*100.0)/100.0 + "," + Math.round(( weekdaysaverage/countweekdays)*100.0)/100.0 + "," + Math.round((weekendsaverage/countweekends)*100.0)/100.0 + "," +
								Math.round(((weekdaysaverage/countweekdays)/(weekendsaverage/countweekends))*100.0)/100.0 +"," + Math.round(auto.process(144)*100.0)/100.0 + "," +  Math.round(auto.process(144*7)*100.0)/100.0 + "," + Math.round( Math.sqrt(Math.sqrt((variance/countOfDays)))*100.0)/100.0 + "," + Math.round(Math.sqrt(Math.sqrt((po/(variances.size()))))*100.0)/100.0 + "," + Math.round(max_over_average/countOfDays*100.0)/100.0 +
								"," + Math.round(cons_wd_day/countweekends*100.0)/100.0 + "," +
								Math.round(cons_wd_morning/countweekends*100.0)/100.0 
								+ "," + Math.round(cons_wd_noon/countweekends*100.0)/100.0
								+ "," + Math.round(cons_wd_evening/countweekends*100.0)/100.0
								+"," + Math.round(cons_wd_night/countweekends*100.0)/100.0
								+"," + Math.round(cons_wd_max/countweekends*100.0)/100.0
								+"," + Math.round(cons_wd_min/countweekends*100.0)/100.0
								+ "," + Math.round(cons_we_day/countweekdays*100.0)/100.0 + 
								"," + Math.round(cons_we_morning/countweekdays*100.0)/100.0 
								+ "," + Math.round(cons_we_noon/countweekdays*100.0)/100.0
								+ "," + Math.round(cons_we_evening/countweekdays*100.0)/100.0
								+"," + Math.round(cons_we_night/countweekdays*100.0)/100.0
								+"," + Math.round(cons_we_max/countweekdays*100.0)/100.0
								+"," + Math.round(cons_we_min/countweekdays*100.0)/100.0
						         +"," + Math.round(var_week_avg/countweekdays*100.0)/100.0
						         +"," + Math.round(var_wd_avg/countweekdays*100.0)/100.0 
						         +"," + Math.round(cons_wd_night/cons_wd_day*100.0)/100.0 
						         +"," + Math.round(cons_wd_max/weekendsaverage*100.0)/100.0 
						         +"," + Math.round(cons_wd_min/weekendsaverage*100.0)/100.0 
						         +"," + Math.round(cons_wd_max/cons_wd_min*100.0)/100.0 
						         +"," + Math.round(cons_wd_morning/cons_wd_noon*100.0)/100.0 
						         +"," + Math.round(cons_wd_evening/cons_wd_noon*100.0)/100.0 
						         +"," + Math.round(cons_wd_noon/cons_wd_day*100.0)/100.0 
						         +"," + Math.round(cons_we_night/cons_we_day*100.0)/100.0 
						         +"," + Math.round(cons_we_max/weekdaysaverage*100.0)/100.0 
						         +"," + Math.round(cons_we_min/weekdaysaverage*100.0)/100.0
						         +"," + Math.round(cons_we_max/cons_we_min*100.0)/100.0
						         +"," + Math.round(cons_we_morning/cons_we_noon*100.0)/100.0
						         +"," + Math.round(cons_we_evening/cons_we_noon*100.0)/100.0
						         +"," + Math.round((var_wd_avg/countweekends)/ (var_week_avg/countweekdays)*100.0)/100.0
						         +"," + Math.round((cons_wd_max/countweekends)/cons_we_max/countweekdays*100.0)/100.0
						         +"," + Math.round((cons_wd_min/countweekends)/ (cons_we_min/countweekdays)*100.0)/100.0
						         +"," + Math.round((cons_wd_noon/countweekends)/(cons_we_noon/countweekdays)*100.0)/100.0
						         +"," + Math.round((cons_wd_evening/countweekends)/(cons_we_evening/countweekdays)*100.0)/100.0
						         ;
								System.out.println(output);	
								writer.write(output);
								writer.newLine();
								//float r_wd_night_day,r_wd_max_mean, r_wd_min_mean,r_wd_max_min,r_wd_morning_noon,r_wd_evening_noon,r_wd_noon_day,r_we_night_day,r_we_max_mean, r_we_min_mean,r_we_max_min,r_we_morning_noon,r_we_evening_noon,r_we_noon_day;

					}
			writer.close();
		}
	   

public class DaysParameters{
	     Date date;
		 private float max;
		private float min;
		float average;
		float evening;
		float morning;
		float night;
		float noon;
		float day;
		float total;
		float variance;
		 float average_max, min_average, night_day, morning_noon, evening_noon;
		 float over_1_5_KWh,over_1_0_KWh,over_2_0_KWh;
		 float max_over_average;
		 public DaysParameters(Date date) {
			 this.date=date;
		 }
		 public void calculation() {
		 min_average = getMin()/average;
		 night_day = night/day;
		 morning_noon= morning/noon;
		 evening_noon = evening/noon;
		 max_over_average=getMax()/average;
		 }
		  
		 public String toString () {
			 return over_1_5_KWh + "," + over_1_0_KWh + "," + over_2_0_KWh + "," + getMax() + "," + getMin();
		 }
		public float getMax() {
			return max;
		}
		public void setMax(float max) {
			this.max = max;
		}
		float getMin() {
			return min;
		}
		void setMin(float min) {
			this.min = min;
		}
		 }
}
