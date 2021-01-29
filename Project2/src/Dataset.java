import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*; 
import java.io.*;  

public class Dataset {

    Map<String, Integer> vocab;      //words of all reviews, count how many reviews contain the word
    Map<String, Integer> posVocab;  // words of positive reviews, count how many positive reviews contain the word
    int filesCount;
    int countAttributes=0;

    public Dataset ()
    {
        init();
    }

    public void init(){
        vocab = new LinkedHashMap<>();
        posVocab = new LinkedHashMap<>();
    }

    public void readVocab(String vocabPath,int m,int n)
    {
        
        BufferedReader reader;
        try 
        {
            reader = new BufferedReader(new FileReader(vocabPath));
            String line = reader.readLine().replaceAll("\\p{Punct}", "");
            while (line != null) {
                countAttributes ++;
                if(countAttributes >= n && countAttributes <= m && line.length()>0){
                    //System.out.println(line);
                    vocab.put(line, 0);
                    posVocab.put(line, 0);
                }
                else if(countAttributes > m)
                {
                    break;
                }
                line = reader.readLine();
                if (line  != null){
                    line = reader.readLine().replaceAll("\\p{Punct}", "");
                }
            }
            reader.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void readTraining(String trainingPath,String folder,int filesToRead){
        try
        {
            BufferedReader reader = null;
            String[] splitted = null;
            File dir=new File(trainingPath + folder);  
            ArrayList<File> files = new ArrayList<File>(Arrays.asList(dir.listFiles()));
            
            Map <String,Integer> uniqueWordsOfFile;
            int i = -1;
            for(File file:files)
            {
                i++;
                if(i==filesToRead){
                    break;
                }
                uniqueWordsOfFile = readWords(splitted,reader,file);
                for(String key : uniqueWordsOfFile.keySet()){
                    if(folder == "/pos"){
                        if(posVocab.containsKey(key)){
                            posVocab.replace(key,posVocab.get(key)+1);
                            //System.out.println("Key = " + splitted[i] + ", Value = " + posVocab.get(splitted[i]));
                        }
                        
                    }
                    if(vocab.containsKey(key)){
                        vocab.replace(key,vocab.get(key)+1);
                        //System.out.println("Key = " + key + ", Value = " + vocab.get(key));
                    }
                }
            }
            filesCount = i-1;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public Map<String,Integer> readTest(File file){
        try
        {
            BufferedReader reader = null;
            String[] splitted = null;
            Map <String,Integer> uniqueWordsOfFile;
            
            uniqueWordsOfFile = readWords(splitted,reader,file);
            return uniqueWordsOfFile;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String,Integer> readWords(String[] splitted,BufferedReader reader,File file){
        Map<String,Integer> uniqueWordsOfFile = new HashMap<>();
        try
        {
            reader = new BufferedReader(new FileReader(file));
            //System.out.println("File Name = " + file.getName() + "\n");
            String line = reader.readLine().replaceAll("\\p{Punct}", "").toLowerCase();
            //System.out.println("Contents = " + line + "\n");
            
            while(line != null)
            {
                splitted = line.split(" ");
                for(int i=0; i < splitted.length; i++)
                {
                    uniqueWordsOfFile.put(splitted[i],0);
                    //System.out.println(splitted[i]);
                }
                line = reader.readLine();
                if (line  != null){
                    line.replaceAll("\\p{Punct}", "").toLowerCase();
                }
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return uniqueWordsOfFile;
    }

    public int getFilesCount(){
        return filesCount;
    }

    public Map <String,Integer> getVocab(){
        return vocab;
    }

    public Map <String,Integer> getPosVocab(){
        return posVocab;
    }
}

