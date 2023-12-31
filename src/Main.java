import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        IO.readFromFile("src/list.txt");
        Alg.fcfs(Process.allProcesses);
    }
}
