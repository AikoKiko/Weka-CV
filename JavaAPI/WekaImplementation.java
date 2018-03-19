import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.meta.Bagging;

public class WekaImplementation {
	 private static float accuracy=0;
	 
	 public static void main(String[] args)  throws IOException, Exception {
	System.out.println("**********Preprocess Part******************");
	DataSource sourceTrain = new DataSource("C:/Users/HP/Desktop/train.arff");
	DataSource sourceTest = new DataSource("C:/Users/HP/Desktop/test.arff");
	Instances train = sourceTrain.getDataSet();
	Instances test = sourceTest.getDataSet();
	int size = train.numInstances() / 5;
	int begin=0;
	int end = size-1;
	System.out.println("*****************************Delete Unnecessary ATtributes****************************************");
	Integer[] nessAtt = {0,1,2,3,4,115};
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
    Instances newTest =  Remove(test, deleteAtt);	
    System.out.println(newTrain);
	System.out.println(newTest);
	for (int i=1; i<=5; i++) {

		System.out.println("Iteration" + i);
		Instances trainingInstances = new Instances(newTrain);
		Instances testingInstances = new Instances(newTest, begin, (end-begin));
		trainingInstances.setClassIndex(trainingInstances.numAttributes()-1);
		testingInstances.setClassIndex(testingInstances.numAttributes()-1);
		for(int j=0; j<(end-begin); j++) {
			trainingInstances.delete(begin);
		}
		System.out.println("Trying to predict:" + trainingInstances.classAttribute().name());
		AdaBoost(trainingInstances, testingInstances);
		System.out.println("Classifier Chosen is:" + args[0]);
		switch(args[0]) {
		case "Ada_Boost" : AdaBoost(trainingInstances, testingInstances);
		     break;
		case "kNN" : kNN(trainingInstances, testingInstances, args[1]);
		     break;
		case "libSVM" :  
		     libSVM(trainingInstances, testingInstances, args[2], args[3], args[4], args[5]); 
		     break;
		case "RandForest": RandomForest(trainingInstances, testingInstances);
		        break;
		case "Bagging" : Bagging(trainingInstances, testingInstances);
		        break;
		case "BayesNet" : BayesNet(trainingInstances, testingInstances);
		        break;
		}
      begin = end+1;
      end+=size;
      if(i==(4)) {
   	   end = train.numInstances();
      }	
	}
	System.out.println("Total result of 5 folds with " + accuracy/5 );
	
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
	 
	 public static void AdaBoost(Instances trainingInstances, Instances testingInstances) throws Exception {
		 AdaBoostM1 Ada_Boost = new AdaBoostM1();
		 Ada_Boost.buildClassifier(trainingInstances);
		 Evaluation evaluation = new Evaluation(testingInstances);
		 evaluation.evaluateModel(Ada_Boost, testingInstances);
		 System.out.println("\nResults:");
		 System.out.println(evaluation.pctCorrect());
        accuracy+=evaluation.pctCorrect();
         
	 }
	 public static void kNN(Instances trainingInstances, Instances testingInstances, String k) throws Exception {
		 IBk kNN = new IBk();
		 kNN.setKNN(5);
		 kNN.buildClassifier(trainingInstances);
		 Evaluation evaluation = new Evaluation(testingInstances);
		 evaluation.evaluateModel(kNN, testingInstances);
		 System.out.println("\nResults:");
		 System.out.println(evaluation.pctCorrect());
        accuracy+=evaluation.pctCorrect();		 
	 }
	 public static void RandomForest(Instances trainingInstances, Instances testingInstances) throws Exception {
		RandomForest RF = new RandomForest();
		 RF.buildClassifier(trainingInstances);
		 Evaluation evaluation = new Evaluation(testingInstances);
		 evaluation.evaluateModel(RF, testingInstances);
		 System.out.println("\nResults:");
		 System.out.println(evaluation.pctCorrect());
        accuracy+=evaluation.pctCorrect();		 
	 }
	 public static void Bagging(Instances trainingInstances, Instances testingInstances) throws Exception {
		Bagging bagging = new Bagging();
		 bagging.buildClassifier(trainingInstances);
		 Evaluation evaluation = new Evaluation(testingInstances);
		 evaluation.evaluateModel(bagging, testingInstances);
		 System.out.println("\nResults:");
		 System.out.println(evaluation.pctCorrect());
        accuracy+=evaluation.pctCorrect();		 
	 }
	 public static void BayesNet(Instances trainingInstances, Instances testingInstances) throws Exception {
		 BayesNet bayesNet = new BayesNet();
		 bayesNet.buildClassifier(trainingInstances);
		 Evaluation evaluation = new Evaluation(testingInstances);
		 evaluation.evaluateModel(bayesNet, testingInstances);
		 System.out.println("\nResults:");
		 System.out.println(evaluation.pctCorrect());
         accuracy+=evaluation.pctCorrect();		 
	 }
	 public static void libSVM(Instances trainingInstances, Instances testingInstances, String cost, String gamma, String weight1, String weight2) throws Exception {
		LibSVM libSVM = new LibSVM();
		libSVM.setCost(Double.parseDouble(cost));
		libSVM.setGamma(Double.parseDouble(gamma));
		if(weight1!=null && weight2!=null) {
			String weight = weight1 + "\t" + weight2;
			libSVM.setWeights(weight);
		}
		libSVM.buildClassifier(trainingInstances);
		 Evaluation evaluation = new Evaluation(testingInstances);
		 evaluation.evaluateModel(libSVM, testingInstances);
		 System.out.println("\nResults:");
		 System.out.println(evaluation.pctCorrect());
		 System.out.println(evaluation.toMatrixString());
         accuracy+=evaluation.pctCorrect();		 
	 }
	 
}

