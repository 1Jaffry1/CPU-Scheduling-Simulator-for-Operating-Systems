import java.lang.invoke.MethodHandle;
import java.util.ArrayList;

public class Alg {

    public static void fcfs(ArrayList<Process> array) {
        StringBuilder toPrint = new StringBuilder();
        Methods.sortBy("priority");
        Methods.sortBy("arrival");
        int time = 0;
        float totalBurstTime = 0;
        float averageWaitingTimeTempSum = 0;
        float averageTurnAroundTimeTempSum = 0;
        float averageResponseTimeTempSum = 0;
        for (Process i : array) {
            if (i.getArrivalTime() > time) //checks if the next process has arrived yet
//                time+= i.getArrivalTime()-time;
                time = i.getArrivalTime();
            i.setStartTime(time);
            i.setWaiting(time - i.getArrivalTime());
            i.setTurnaround(time - i.getArrivalTime() + i.getBurst());
            i.setResponse(i.getWaiting());
            i.setRemainingBurstTime(0);
            i.setFinished(true);
            time = time + i.getBurst();
            totalBurstTime += i.getBurst();
            i.setFinishTime(time);
            toPrint.append("[").append(i.getName()).append("] [").append(i.getStartTime()).append("] [").append(i.getFinishTime()).append("]\n");
            averageWaitingTimeTempSum += i.getWaiting();
            averageTurnAroundTimeTempSum += i.getTurnaround();
            averageResponseTimeTempSum += i.getResponse();
        }
        float throughput = (float) array.size() / time;
        float util = totalBurstTime / time * 100;
        float averageWaitingTime = averageWaitingTimeTempSum / array.size();
        float averageTurnaroundTime = averageTurnAroundTimeTempSum / array.size();
        float averageResponseTime = averageResponseTimeTempSum / array.size();

        Methods.IO.writeToFile("FCFS", "Throughput: " + throughput + "\n" + "CPU Utilization: " + util + "%\n" + "Average Waiting Time: " + averageWaitingTime + "\n" + "Average Turnaround Time: " + averageTurnaroundTime + "\n" + "Average Response Time: " + averageResponseTime + "\n\n" + toPrint);
        Process.reset();
    }

//    public static void sjf(ArrayList<Process> array) {
//        Methods.sortBy("priority");
//        Methods.sortBy("arrival");
//        Methods.sortBy("remaining");
//        StringBuilder toPrint = new StringBuilder();
//        int finished = 0;
//
//        ArrayList<Process> arrived = new ArrayList<>();
//        int time = 0;
//        float totalBurstTime = 0;
//        float averageWaitingTimeTempSum = 0;
//        float averageTurnAroundTimeTempSum = 0;
//        float averageResponseTimeTempSum = 0;
//
//        while (finished < array.size()) {
//            int j = 0;
//            while (j<array.size()){
//                Process i = array.get(j);
//                if (i.getArrivalTime() > time | i.isFinished()) continue;
//                i.setStartTime(time);
//                i.setWaiting(time - i.getArrivalTime());
//                i.setTurnaround(time - i.getArrivalTime() + i.getBurst());
//                i.setResponse(i.getWaiting());
//                i.setRemainingBurstTime(0);
//                i.setFinished(true);
//                finished++;
//                time = time + i.getBurst();
//                totalBurstTime += i.getBurst();
//                i.setFinishTime(time);
//                toPrint.append("[").append(i.getName()).append("] [").append(i.getStartTime()).append("] [").append(i.getFinishTime()).append("]\n");
//                averageWaitingTimeTempSum += i.getWaiting();
//                averageTurnAroundTimeTempSum += i.getTurnaround();
//                averageResponseTimeTempSum += i.getResponse();
//            }
//        }
//        float throughput = (float) array.size() / time;
//        float util = totalBurstTime / time * 100;
//        float averageWaitingTime = averageWaitingTimeTempSum / array.size();
//        float averageTurnaroundTime = averageTurnAroundTimeTempSum / array.size();
//        float averageResponseTime = averageResponseTimeTempSum / array.size();
//
//        Methods.IO.writeToFile("SJF", "Throughput: " + throughput + "\n" + "CPU Utilization: " + util + "%\n" + "Average Waiting Time: " + averageWaitingTime + "\n" + "Average Turnaround Time: " + averageTurnaroundTime + "\n" + "Average Response Time: " + averageResponseTime + "\n\n" + toPrint);
//        Process.reset();
//
//    }


    public static void sjf2(ArrayList<Process> array){
        ArrayList<Process> finished = new ArrayList<>();
        Methods.sortBy("priority");
        Methods.sortBy("arrival");
        Methods.sortBy("remaining");
        int time = 0;

        Process i = array.get(0);
        if (i.getArrivalTime() <= time){

        }
    }




}
