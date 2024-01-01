import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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



