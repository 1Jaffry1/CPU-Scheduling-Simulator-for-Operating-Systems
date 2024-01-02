import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Methods {
    public static void sortBy(String type, ArrayList<Process> array) { // n=0: arrival, n=1: priority, n=2 burst time
        int n = -1;
        switch (type) {
            case "arrival" -> n = 0;
            case "priority" -> n = 1;
            case "remaining" -> n = 2;
        }
        for (int i = 0; i < array.size(); i++)
            for (int j = 0; j < array.size() - i - 1; j++)
                if (array.get(j).attrs[n] > array.get(j + 1).attrs[n]) {
                    //swap a[j] and a[j+1]
                    Process temp = array.get(j);
                    array.set(j, array.get(j + 1));
                    array.set(j + 1, temp);
                }
    }

    public class IO {
        public static void readFromFile(String filename) throws FileNotFoundException {
            File myObj = new File(filename);
            Scanner scanner = new Scanner(myObj);
            do {
                String info = scanner.nextLine();
                var process = info.split(", ");
                new Process(process[0], Integer.parseInt(process[1]), Integer.parseInt(process[2]), Integer.parseInt(process[3]));
            } while (scanner.hasNext());
        }

        public static void writeToFile(String AlgNameForFile, String n) {
            String FileName = AlgNameForFile + ".txt";
            new File(FileName);
            try {
                try (FileWriter writer = new FileWriter(FileName)) {
                    writer.write(n);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}



