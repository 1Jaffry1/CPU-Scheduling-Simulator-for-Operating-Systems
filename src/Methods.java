import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Methods {
    public static void sortBy(String type) { // n=0: arrival, n=1: priority, n=2 burst time
        int n=-1;
        switch (type) {
            case "arrival" -> n = 0;
            case "priority" -> n = 1;
            case "remaining" -> n = 2;
        }
        for (int i = 0; i < Process.allProcesses.size(); i++)
            for (int j = 0; j < Process.allProcesses.size() - i - 1; j++)
                if (Process.allProcesses.get(j).attrs[n] > Process.allProcesses.get(j + 1).attrs[n]) {
                    //swap a[j] and a[j+1]
                    Process temp = Process.allProcesses.get(j);
                    Process.allProcesses.set(j, Process.allProcesses.get(j + 1));
                    Process.allProcesses.set(j + 1, temp);
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



