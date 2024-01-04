import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Methods.IO.readFromFile("src/list.txt");
        Alg.fcfs(Process.allProcesses);
        Alg.sjf(Process.allProcesses);
//        Alg.srjf(Process.allProcesses);
        Alg.SRJF(Process.allProcesses);
        Alg.priorityScheduling(Process.allProcesses);
        Alg.preemtivePriority(Process.allProcesses);
        Alg.roundRobin(Process.allProcesses, 2);
    }

}
