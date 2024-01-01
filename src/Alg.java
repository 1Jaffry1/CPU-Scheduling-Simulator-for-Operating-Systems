import java.util.ArrayList;

public class Alg {
    public static void sortBy(int n) {
        for (int i = 0; i < Process.allProcesses.size(); i++)
            for (int j = 0; j < Process.allProcesses.size() - i - 1; j++)
                if (Process.allProcesses.get(j).attrs[n] > Process.allProcesses.get(j + 1).attrs[n]) {
                    //swap a[j] and a[j+1]
                    Process temp = Process.allProcesses.get(j);
                    Process.allProcesses.set(j, Process.allProcesses.get(j + 1));
                    Process.allProcesses.set(j + 1, temp);
                }
    }

    public static void fcfs(ArrayList<Process> array) {
        StringBuilder toPrint = new StringBuilder();
        sortBy(1);
        sortBy(0);
        int time = 0;
        float util = 100;
        float averageWaitingTimeTempSum = 0;
        float averageTurnAroundTimeTempSum = 0;
        float averageResponseTimeTempSum = 0;
        for (Process i : array) {
            i.setStartTime(time);
            i.setWaiting(time - i.getArrivalTime());
            i.setTurnaround(time - i.getArrivalTime() + i.getBurst());
            i.setResponse(i.getWaiting());
            i.setRemainingBurstTime(0);
            i.setFinished(true);
            time = time + i.getBurst();
            i.setFinishTime(time);
            toPrint.append("[").append(i.getName()).append("] [").append(i.getStartTime()).append("] [").append(i.getFinishTime()).append("]\n");
            averageWaitingTimeTempSum += i.getWaiting();
            averageTurnAroundTimeTempSum += i.getTurnaround();
            averageResponseTimeTempSum += i.getResponse();
        }
        float throughput = (float) array.size() / time;
        float averageWaitingTime = averageWaitingTimeTempSum / array.size();
        float averageTurnaroundTime = averageTurnAroundTimeTempSum / array.size();
        float averageResponseTime = averageResponseTimeTempSum / array.size();

        IO.writeToFile("FCFS",
                "Throughput: " + throughput + "\n"
                        + "CPU Utilization: " + util + "%\n"
                        + "Average Waiting Time: " + averageWaitingTime + "\n"
                        + "Average Turnaround Time: " + averageTurnaroundTime + "\n"
                        + "Average Response Time: " + averageResponseTime + "\n\n"+toPrint)
        ;
        Process.reset();
    }

}
