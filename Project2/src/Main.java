public class Main {
    public static void main(String[] args) throws Exception {
        String path = System.getProperty("user.dir");
        final String vocabPath = path.substring(0,path.length() - 4) + "/Extras/aclImdb/imdb.vocab";
        final String trainingPath = path.substring(0,path.length() - 4) + "/Extras/aclImdb/train";
        final String devPath = path.substring(0,path.length() - 4) + "/Extras/aclImdb/dev";
        //final String testPath = path.substring(0,path.length() - 4) + "/Extras/aclImdb/test";

        //hyper parameters
        //best parameter for IG is selected acoording to IG chart
        //if you dont want to use IG, best parameters for vocab are: M=2995 and N:9
        //By default all vocabulary is selected because using IG  is giving a bit better results
        int vocabM = 89527;
        int vocabN = 1;
        int igM = 600;


        boolean flagIG = true;                      //if true info gain is enabled 
        boolean createInfoGainChart = false;         //if true best parameter for IG will be calculated (false by default cause it will take time)

        double[] f1=new double[11];                 // array for keeping f1 values
        double[] recall=new double[11];             //  -\\-  -\\- -\\-  recall values
        double[] precision=new double[11];          //  -\\-  -\\- -\\-  precision values
        double[] accuracy=new double[11];           //  -\\-  -\\- -\\-  accuracy values with dev as test
        double[] accuracyTrain=new double[11];      //  -\\-  -\\- -\\-  accuracy values with training as test
        double[] totalSamplesArray=new double[11];  //  -\\-  -\\- -\\-  training sample value for each loop

        double[] accuracyIG= new double[31];        //  -\\-  -\\- -\\-  accuracy according to IG parameter
        double[] igValueArray = new double[31];     //  -\\-  -\\- -\\-  ig current selected parameter value for each loop

        int igValue = 0;

        int totalSamples=0;
        int step = 1125;

        if (flagIG){
            System.out.println("\n----Info Gain: Enabled----\n");
        }

        //running naybayes with different traing samples and test it with traing samples and dev
        for(int i = 0; i<=10; i++){
            System.out.println("----Run " + (i+1) + "----\n");
            System.out.println("----Training Samples = " + (totalSamples*2) + "----\n");
            NaiveBayes nb = new NaiveBayes();

            nb.train(vocabPath,vocabM,vocabN,trainingPath,"/pos","/neg",totalSamples);

            if (flagIG){
            nb.infoGain(igM);
            }

            System.out.println("----Testing with training samples----\n");
            nb.test(trainingPath,"/pos",totalSamples);
            nb.test(trainingPath,"/neg",totalSamples);
            System.out.println("Accuracy = " + nb.getAccuracy() + "\n");
            accuracyTrain[i]=nb.getAccuracy()*100;
            nb.reset();

            System.out.println("----Testing with dev samples----\n");
            nb.test(devPath,"/pos",-1);
            nb.test(devPath,"/neg",-1);

            nb.printResults();
            f1[i]=nb.getF1()*100;
            recall[i]=nb.getRecall()*100;
            precision[i]=nb.getPrecision()*100;
            accuracy[i]=nb.getAccuracy()*100;
            nb.reset();
            System.out.println();

            totalSamplesArray[i]=(double)totalSamples*2;
            totalSamples += step;
        }
        if(createInfoGainChart){
            System.out.println("----Calculating values for IG chart----");
            igValue = 0;
            step = 600;
            for(int i = 0; i<=30; i++){
                NaiveBayes nb = new NaiveBayes();

                nb.train(vocabPath,89527,1,trainingPath,"/pos","/neg",-1);

                if (flagIG){
                    nb.infoGain(igValue);
                }
                nb.test(devPath,"/pos",-1);
                nb.test(devPath,"/neg",-1);
                accuracyIG[i]=nb.getAccuracy()*100;
                igValueArray[i] = (double)igValue; 
                igValue += step;
                nb.reset();
            }
        }

        //creating charts

        MatlabChart precisionChart = new MatlabChart(); 
        precisionChart.plot(totalSamplesArray, precision, "-b", 2.0f, "Presicion");
        precisionChart.RenderPlot();
        precisionChart.title("Precision");   
        precisionChart.xlim(0, totalSamples*2);  
        precisionChart.ylim(0, 100);
        precisionChart.xlabel("Training Samples"); 
        precisionChart.ylabel("Precision %");
        precisionChart.grid("on","on"); 
        precisionChart.legend("northeast"); 
        precisionChart.font("Helvetica",15);
        precisionChart.saveas("Precision.jpeg",640,480);   

        MatlabChart recallChart = new MatlabChart(); 
        recallChart.plot(totalSamplesArray, recall, "-g", 2.0f, "Recall");
        recallChart.RenderPlot();
        recallChart.title("Recall");   
        recallChart.xlim(0, totalSamples*2);  
        recallChart.ylim(0, 100);
        recallChart.xlabel("Training Samples"); 
        recallChart.ylabel("Recall %");
        recallChart.grid("on","on"); 
        recallChart.legend("northeast"); 
        recallChart.font("Helvetica",15);
        recallChart.saveas("Recall.jpeg",640,480);   

        MatlabChart f1Chart = new MatlabChart(); 
        f1Chart.plot(totalSamplesArray, f1, "-r", 2.0f, "F1"); // plot(x,y1,'-r','LineWidth',2);
        f1Chart.RenderPlot();
        f1Chart.title("F1");   
        f1Chart.xlim(0, totalSamples*2);  
        f1Chart.ylim(0, 100);
        f1Chart.xlabel("Training gSamples"); 
        f1Chart.ylabel("F1 %");
        f1Chart.grid("on","on"); 
        f1Chart.legend("northeast"); 
        f1Chart.font("Helvetica",15);
        f1Chart.saveas("F1.jpeg",640,480);   

        MatlabChart accuracyChart = new MatlabChart(); 
        accuracyChart.plot(totalSamplesArray, accuracy, "-r", 2.0f, "Test");
        accuracyChart.plot(totalSamplesArray, accuracyTrain, "-c", 2.0f, "Train");
        accuracyChart.RenderPlot();
        accuracyChart.title("Learning Curve");
        accuracyChart.xlim(0, totalSamples*2);  
        accuracyChart.ylim(40, 110);
        accuracyChart.xlabel("Training Samples"); 
        accuracyChart.ylabel("Accuracy %");
        accuracyChart.grid("on","on"); 
        accuracyChart.legend("northeast"); 
        accuracyChart.font("Helvetica",15);
        accuracyChart.saveas("Accuracy.jpeg",640,480);   

        if(createInfoGainChart){
            MatlabChart igChart = new MatlabChart(); 
            igChart.plot(igValueArray, accuracyIG, "-b", 2.0f, "IG");
            igChart.RenderPlot();
            igChart.title("IG");   
            igChart.xlim(0, 19000);  
            igChart.ylim(40, 85);
            igChart.xlabel("Selected Attributes"); 
            igChart.ylabel("Accuracy");
            igChart.grid("on","on"); 
            igChart.legend("northeast"); 
            igChart.font("Helvetica",15);
            igChart.saveas("IG.jpeg",640,480);   
        }
    }
}
