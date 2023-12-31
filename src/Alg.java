import java.util.ArrayList;

public class Alg {
    public static void sortBy(int n) {
        for (int i = 0; i < Process.allProcesses.size(); i++)
            for (int j = 0; j < Process.allProcesses.size() - i - 1; j++)
                if (Process.allProcesses.get(j).attrs[n] > Process.allProcesses.get(j + 1).attrs[n]) {   //swap a[j] and a[j+1]
                    Process temp = Process.allProcesses.get(j);
                    Process.allProcesses.set(j, Process.allProcesses.get(j + 1));
                    Process.allProcesses.set(j + 1, temp);
                }
    }

    public static void fcfs(ArrayList<Process> array) {
        sortBy(1);
        sortBy(0);
        for (Process i : array) {
            System.out.println(i.toString());
        }
        int time = 0;
        for (Process i : array) {
            i.setWaiting(time - i.getArrivalTime());
            i.setTurnaround(time - i.getArrivalTime() + i.getBurst());
            i.setResponse(i.getWaiting());
            i.setRemainingBurstTime(0);
            i.setFinished(true);
            time = time + i.getBurst();
        }
//        for (Process i : array) {
//            System.out.println(i.toString());
//        }
        float throughput = (float) Process.allProcesses.size() / time;
        Process.reset();
    }
}
