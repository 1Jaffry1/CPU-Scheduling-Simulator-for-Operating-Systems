import java.io.FileNotFoundException;
import java.util.prefs.PreferenceChangeEvent;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Methods.IO.readFromFile("src/list.txt");
        Alg.fcfs(Process.allProcesses);
        Alg.sjf(Process.allProcesses);
//        Alg.srjf(Process.allProcesses);
        Alg.SRJF(Process.allProcesses);
        Alg.PriorityScheduling(Process.allProcesses);
        Alg.PreemtivePriority(Process.allProcesses);
    }

}
