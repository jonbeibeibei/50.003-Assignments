import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jonathanbeiqiyang on 11/2/17.
 */
public class FileLinesReader {

    public static void readFile(File file) throws IOException{
        int lineCounter = 0;
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                lineCounter += 1;
            }
            bufferedReader.close();
        }
        catch (IOException e){
            System.out.println("Error!");
        }

        System.out.println(lineCounter);
    }



    public static void main(String[] args) throws IOException {
        ArrayList<File> fileNames = new ArrayList<File>();
        File file1 = new File("/Users/jonathanbeiqiyang/IdeaProjects/ESCWeek3/FileNumberofLines/src/LoremIpsum.txt");
        File file2 = new File("/Users/jonathanbeiqiyang/IdeaProjects/ESCWeek3/FileNumberofLines/src/LoremIpsumLonger.txt");
        fileNames.add(file1);
        fileNames.add(file2);
        for (File i : fileNames){
            readFile(i);
        }
    }
}
