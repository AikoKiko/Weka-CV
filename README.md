# Weka-CV
We randomly form 5 groups of samples in the stratified way (i.e., ratio of samples of both classes are roughly the same in all group), and for each round, we use 4 of them for training and the other for testing. The difference from the typical 5-fold cross validation is that, while we use the original energy consumption data for training, for testing we use preprocessed data. 
