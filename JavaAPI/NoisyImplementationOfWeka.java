import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
// java implementation of weka 
public class NoisyImplementationOfWeka {
	private static float accuracy=0;
	public static void main(String[] args)throws IOException, Exception {
		int seed=5;
		int fold=5;
		Random rand= new Random(seed);
		DataSource dataTn = new DataSource("C:/Users/HP/Desktop/FilesTested/OriginalData2Normalized.arff");
		BufferedReader file = new BufferedReader(new FileReader("C:/Users/HP/Desktop/FilesTested/TEN_758Normalized.arff"));
		Instances train = dataTn.getDataSet();
		 train.setClassIndex(train.numAttributes()-4);
		 System.out.println(train.classAttribute());
		
	    train.randomize(rand);
		train.stratify(fold);
		//each time peviously created testS.file need to be deleted , before the running the code make sure test file doesn't contain id's label, otherwise you get an error
	   BufferedWriter  RTest = new BufferedWriter(new FileWriter("C:/Users/HP/Desktop/testS.arff", true));
//		RTrain.write(train.toString());
//		RTrain.close();
		ArrayList<Integer> Attributes = new ArrayList<Integer>();
		ArrayList<String> lines= new ArrayList<String>();
	    for(int i=0; i<train.numInstances(); i++) {
	    	
	    		Attributes.add((int)train.get(i).value(0));
	    	
	    }
	    String line=null;
	    while ((line=file.readLine())!=null) {
	    	lines.add(line);
	    }
	    file.close();
	    RTest.write("@RELATION Energy_Consumption_Data_Attributes\n");
        RTest.write("@attribute ID numeric\n" +
        		"@attribute Kurtosis numeric\n"+
        		"@attribute Skewness numeric\n"+
        		"@attribute FFT numeric\n"+
        		"@attribute AR numeric\n"+
        		"@attribute I numeric\n"+
        		"@attribute MA numeric\n"+
        		"@attribute Total_Persentile_25 numeric\n"+
        		"@attribute Total_Persentile_75 numeric\n"+
        		"@attribute Total_Min numeric\n"+
        		"@attribute Total_Max numeric\n"+
        		"@attribute Total_Median numeric\n"+
        		"@attribute TotalAverage numeric\n"+
        		"@attribute Total_Variance numeric\n"+
        		"@attribute Day_Persentile_25 numeric\n"+
        		"@attribute Day_Persentile_75 numeric\n"+
        		"@attribute Day_Min numeric\n"+
        		"@attribute Day_Max numeric\n"+
        		"@attribute Day_Median numeric\n"+
        		"@attribute DayAverage numeric\n"+
        		"@attribute Day_Variance numeric\n"+
        		"@attribute Evening_Persentile_25 numeric\n"+
        		"@attribute Evening_Persentile_75 numeric\n"+
        		"@attribute Evening_Min numeric\n"+
        		"@attribute Evening_Max numeric\n"+
        		"@attribute Evening_Median numeric\n"+
        		"@attribute EveningAverage numeric\n"+
        		"@attribute Evening_Variance numeric\n"+
        		"@attribute Morning_Persentile_25 numeric\n"+
        		"@attribute Morning_Persentile_75 numeric\n"+
        		"@attribute Morning_Min numeric\n"+
        		"@attribute Morning_Max numeric\n"+
        		"@attribute Morning_Median numeric\n"+
        		"@attribute MorningAverage numeric\n"+
        		"@attribute Morning_Variance numeric\n"+
        		"@attribute Night_Persentile_25 numeric\n"+
        		"@attribute Night_Persentile_75 numeric\n"+
        		"@attribute Night_Min numeric\n"+
        		"@attribute Night_Max numeric\n"+
        		"@attribute Night_Median numeric\n"+
        		"@attribute NightAverage numeric\n"+
        		"@attribute Night_Variance numeric\n"+
        		"@attribute Noon_Persentile_25 numeric\n"+
        		"@attribute Noon_Persentile_75 numeric\n"+
        		"@attribute Noon_Min numeric\n"+
        		"@attribute Noon_Max numeric\n"+
        		"@attribute Noon_Median numeric\n"+
        		"@attribute NoonAverage numeric\n"+
        		"@attribute Noon_Variance numeric\n"+
        		"@attribute Morning_Over_Noon numeric\n"+
        		"@attribute Evening_Over_Noon numeric\n"+
        		"@attribute Noon_Over_Total numeric\n"+
        		"@attribute Night_Over_Day numeric\n"+
        		"@attribute Max numeric\n"+
        		"@attribute Min numeric\n"+
        		"@attribute Average numeric\n"+
        		"@attribute Morning_Average numeric\n"+
        		"@attribute Evening_Average numeric\n"+
        		"@attribute Night_Average numeric\n"+
        		"@attribute Noon_Average numeric\n"+
        		"@attribute Day_Average numeric\n"+
        		"@attribute Total_Average numeric\n"+
        		"@attribute Evening_Noon numeric\n"+
        		"@attribute Min_Average numeric\n"+
        		"@attribute Morning_Noon numeric\n"+
        		"@attribute Night_Day numeric\n"+
        		"@attribute over_1_0_kWh numeric\n"+
        		"@attribute over_1_5_kWh numeric\n"+
        		"@attribute over_2_kWh numeric\n"+
        		"@attribute Percentile_25 numeric\n"+
        		"@attribute Percentile_75 numeric\n"+
        		"@attribute Median numeric\n"+
        		"@attribute Variance numeric\n"+
        		"@attribute Weekdaysaverage numeric\n"+
        		"@attribute Weekendsaverage numeric\n"+
        		"@attribute Weekdaysaverage_Weekendsaverage numeric\n"+
        		"@attribute Autocorrelation_Daily numeric\n"+
        		"@attribute Autocorrelation_Weekly numeric\n"+
        		"@attribute Variance_Average numeric\n"+
        		"@attribute Variance_Of_Variance numeric\n"+
        		"@attribute Max_Over_Average numeric\n"+
        		"@attribute Cons_wd_day numeric\n"+
        		"@attribute Cons_wd_morning numeric\n"+
        		"@attribute Cons_wd_noon numeric\n"+
        		"@attribute cons_wd_evening numeric\n"+
        		"@attribute cons_wd_night numeric\n"+
        		"@attribute cons_wd_max numeric\n"+
        		"@attribute cons_wd_min numeric\n"+
        		"@attribute cons_we_day numeric\n"+
        		"@attribute cons_we_morning numeric\n"+
        		"@attribute Cons_we_noon numeric\n"+
        		"@attribute cons_we_evening numeric\n"+
        		"@attribute cons_we_night numeric\n"+
        		"@attribute cons_we_max numeric\n"+
        		"@attribute cons_we_min numeric\n"+
        		"@attribute var_week_avg_countweekdays numeric\n"+
        		"@attribute var_wd_avg_countweekdays numeric\n"+
        		"@attribute cons_wd_night_cons_wd_day numeric\n"+
        		"@attribute cons_wd_max_weekendsaverage numeric\n"+
        		"@attribute cons_wd_min_weekendsaverage numeric\n"+
        		"@attribute cons_wd_max_cons_wd_min numeric\n"+
        		"@attribute cons_wd_morning_cons_wd_noon numeric\n"+
        		"@attribute cons_wd_evening_cons_wd_noon numeric\n"+
        		"@attribute cons_wd_noon_cons_wd_day numeric\n"+
        		"@attribute cons_we_night_cons_we_day numeric\n"+
        		"@attribute cons_we_max_weekdaysaverage numeric\n"+
        		"@attribute cons_we_min_weekdaysaverage numeric\n"+
        		"@attribute cons_we_max_cons_we_min numeric\n"+
        		"@attribute cons_we_morning_cons_we_noon numeric\n"+
        		"@attribute cons_we_evening_cons_we_noon numeric\n"+
        		"@attribute var_wd_avg_var_week_avg numeric\n"+
        		"@attribute cons_wd_max_cons_we_max numeric\n"+
        		"@attribute cons_wd_min_cons_we_min numeric\n"+
        		"@attribute cons_wd_noon_cons_we_noon numeric\n"+
        		"@attribute cons_wd_evening_cons_we_evening numeric\n"+
        		"@attribute HouseHoldOccupancy {0,1}\n"+
        		"@attribute Single {0,1}\n"+
        		"@attribute HouseholdWithChildren {0,1}\n"+
        		"@attribute Working_status {0,1}\n"+
        		"@attribute Social_Grade {0,1}\n"
);

        RTest.write("@DATA\n");
	    for(int j=0; j<Attributes.size(); j++) {
	    	RTest.write((lines.get(Attributes.get(j)-1)));
	    	RTest.newLine();
	    }
	    RTest.close();
	    DataSource dataTt = new DataSource("C:/Users/HP/Desktop/testS.arff");
	    Instances test = dataTt.getDataSet();
	    System.out.println("*****************************Delete Unnecessary ATtributes****************************************");
		Integer[] nessAtt = {17,10,13,3,20,42,114,34,1,2,116};
		int[] deleteAtt = new int[ train.numAttributes() - nessAtt.length];
		int num=0;
		int initialNumAtt = train.numAttributes();
		List <Integer> nessAttributes = new ArrayList<Integer>(Arrays.asList(nessAtt));
		List<Integer> allAttributes = new ArrayList<Integer>();
		for(int j=0; j<initialNumAtt; j++) {
			allAttributes.add(j);
		}
		for(Integer att: allAttributes) {
			if(!nessAttributes.contains(att)) {
				deleteAtt[num] = att;
				num++;
			}
		}

	    Instances newTrain = Remove(train, deleteAtt);
	    System.out.println(newTrain.attribute(0));
	    Instances newTest = Remove(test, deleteAtt);
		 Instances trainingInstances = new Instances(newTrain);
		 Instances testingInstances = new Instances(newTest);
		 trainingInstances.setClassIndex(trainingInstances.numAttributes()-1);
		 testingInstances.setClassIndex(testingInstances.numAttributes()-1);	
		 System.out.println(trainingInstances.classAttribute());
		 for(int n =0; n<fold; n++) {
			Instances trainingData = trainingInstances.trainCV(fold, n, rand);
			Instances testData = testingInstances.testCV(fold, n); 
			// classifier part 
			AdaBoostM1 RF = new AdaBoostM1();
		    RF.buildClassifier(trainingData);
			 Evaluation evaluation = new Evaluation(testData);
			 evaluation.evaluateModel(RF, testData);
			 System.out.println("\nResults:");
			 System.out.println(evaluation.pctCorrect());
			 System.out.println(evaluation.correct());
			 System.out.println(evaluation.numInstances());
	         accuracy+=evaluation.pctCorrect();
		//trainingDatas.close();	
		//testingDatas.close();
		}
		 System.out.println("Accuracy is " + accuracy/5);
	}
	public static Instances Remove(Instances inst, int[] deleteAtt) {
		 Remove remove=new Remove();
			Instances newInst=null;
			
			try {
				remove.setAttributeIndicesArray(deleteAtt);
				remove.setInvertSelection(false);
				remove.setInputFormat(inst);
				newInst= Filter.useFilter(inst, remove);
			} catch (Exception e ) {
				e.printStackTrace();
			}
			return newInst;
	 }
}