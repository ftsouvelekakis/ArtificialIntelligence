import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.*;
import java.lang.Math;

public class NaiveBayes {

    Dataset dataset;
    int countPosFiles;
    int countNegFiles;
    int truePos=0;
    int trueNeg=0;
    int falsePos=0;
    int falseNeg=0;
    public NaiveBayes(){
         dataset = new Dataset();
    }

    public void train(String vocabPath,int m,int n,String trainingPath,String posFolder,String negFolder,int filesToRead){
        
        // second variable (m) indicates how many words will look at (will erase least used words of vocab)
        // trird variable (n) indicates the most used words that will be ignored (will erased top used words of vocab)
        dataset.readVocab(vocabPath,m,n);
        
        // second parameter indicates the folder incide training from where files will be read.
        dataset.readTraining(trainingPath,posFolder,filesToRead);
        countPosFiles = dataset.getFilesCount();
        dataset.readTraining(trainingPath,negFolder,filesToRead);
        countNegFiles = dataset.getFilesCount();
    }

    public void infoGain(int topWords){
        double c0=(countNegFiles+2)/(double)(countNegFiles+countPosFiles+4);
        double c1=(countPosFiles+2)/(double)(countNegFiles+countPosFiles+4);
        double hC = -(c1*Math.log(c1))-(c0*Math.log(c0));
        double x0;
        double x1;
        double c0x1;
        double c1x1;
        double c0x0;
        double c1x0;
        double hCX1;
        double hCX0;
        double infoGain;
        Map<String,Double> infoGainMap = new LinkedHashMap<>();
        for (String key : dataset.getVocab().keySet()){
            x0 = (double)((countPosFiles+countNegFiles+4)-(dataset.getVocab().get(key)+2))/(countPosFiles+countNegFiles+4);
            x1 = (double)(dataset.getVocab().get(key)+2)/(countPosFiles+countNegFiles+4);
            c0x1 = (double)(dataset.getVocab().get(key)+2-(dataset.getPosVocab().get(key)+1))/(dataset.getVocab().get(key)+2);
            c1x1 = (double)(dataset.getPosVocab().get(key)+1)/(dataset.getVocab().get(key)+2);
            c0x0 = (double)(countNegFiles+2-((dataset.getVocab().get(key)+2)-(dataset.getPosVocab().get(key)+1)))/((countPosFiles+countNegFiles+4)-(dataset.getVocab().get(key)+2));
            c1x0 = (double)((countPosFiles+2)-(dataset.getPosVocab().get(key)+1))/((countPosFiles+countNegFiles+4)-(dataset.getVocab().get(key)+2));
            hCX1 = -(c0x1*Math.log(c0x1)+c1x1*Math.log(c1x1));
            hCX0 = -(c0x0*Math.log(c0x0)+c1x0*Math.log(c1x0));
            infoGain = (hC - (x0*hCX0 + x1*hCX1));
            infoGainMap.put(key, infoGain); 
        }
        Map<String,Double> sortedInfoGain;
        sortedInfoGain = sortMap(infoGainMap);
        int m = 0;
        for (String key1 : sortedInfoGain.keySet()){
            m++;
            if(m>topWords){
                if(dataset.getVocab().containsKey(key1)){
                    dataset.getVocab().remove(key1);
                }
            }
        }
    }

    public void test(String testPath,String folder,int filesToTest){
        File dir=new File(testPath + folder);  
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(dir.listFiles()));
        Map<String,Integer> uniqueWordsOfFile;
        int count = -1;
        for(File file:files)
        {
            count++;
            if(count==filesToTest){
                break;
            }
            uniqueWordsOfFile = dataset.readTest(file);
            if(uniqueWordsOfFile!=null){
                int result = classify(uniqueWordsOfFile);
                if(folder=="/pos" &&  result == 1){
                    truePos++;
                }else if(folder=="/pos" && result == 0){
                    falseNeg++;
                }else if(folder=="/neg" && result == 0){
                    trueNeg++;
                }else if(folder=="/neg" && result == 1){
                    falsePos++;
                }
                
            }
        }
        
    }

    private int classify(Map<String,Integer> uniqueWordsOfFile){
        
        double c0=(countNegFiles+2)/(double)(countNegFiles+countPosFiles+4);
        double c1=(countPosFiles+2)/(double)(countNegFiles+countPosFiles+4);
        double mulXiC1=0;
        double mulXiC0=0;
        double totXiC1;
        double totXiC0;
        double mulXC1;
        for (String key : dataset.getVocab().keySet()){
            if (uniqueWordsOfFile.containsKey(key)){
                mulXC1 = (double)(dataset.getPosVocab().get(key)+1)/(countPosFiles+2);
                mulXiC1 = mulXiC1 + Math.log(mulXC1);
                mulXiC0 = mulXiC0 + Math.log((double)(dataset.getVocab().get(key)+2-(dataset.getPosVocab().get(key)+1))/(countNegFiles+2));
            }else{
                mulXiC1 = mulXiC1 + Math.log((double)(countPosFiles+2-(dataset.getPosVocab().get(key)+1))/(countPosFiles+2));    
                mulXiC0 = mulXiC0 + Math.log((double)(countNegFiles+2-((dataset.getVocab().get(key)+2)-(dataset.getPosVocab().get(key)+1)))/(countNegFiles+2));
            }
        }
        totXiC1 = c1 * mulXiC1;
        totXiC0 = c0 * mulXiC0;
        
        if(totXiC0<totXiC1){
            return 1;
        }else{
            return 0;
        }
    }

    //sort hash map by value
    private static Map<String, Double> sortMap(Map<String, Double> unsortMap)
    {

        List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Double>>()
        {
            public int compare(Entry<String, Double> o1,Entry<String, Double> o2)
            {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Entry<String, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public void printResults(){
        System.out.println("Accuracy = " + getAccuracy());
        System.out.println("F1 = " + getF1());
        System.out.println("Recall = " + getRecall());
        System.out.println("Presicion = " + getPrecision());
    }

    public double getF1(){
        if((2*truePos+falsePos+falseNeg)==0){
            return 0.0;
        }else{
            return ((double)(2*truePos)/(2*truePos+falsePos+falseNeg)); 
        }
    }
    public double getRecall(){ 
        if((truePos+falseNeg)==0){
            return 0.0;
        }else{
            return ((double)truePos/(truePos+falseNeg)); 
        }
    }
    public double getPrecision(){ 
        if((truePos+falsePos)==0){
            return 0.0;
        }else{
            return ((double)truePos/(truePos+falsePos)); 
        }
    }
    public double getAccuracy(){ 
        if((truePos+trueNeg+falsePos+falseNeg)==0){
            return 1.0;
        }else{
            return ((double)(truePos+trueNeg)/(truePos+trueNeg+falsePos+falseNeg));
        }
    }

    public void reset(){
        truePos=0;
        trueNeg=0;
        falsePos=0;
        falseNeg=0;
    }
}
