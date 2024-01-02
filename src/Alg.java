import java.util.ArrayList;

@SuppressWarnings("DuplicatedCode")
public class Alg {

    public static void fcfs(ArrayList<Process> array) {
        StringBuilder toPrint = new StringBuilder();
        Methods.sortBy("priority", array);
        Methods.sortBy("arrival", array);
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
            toPrint.append("[").append(i.getName()).append("]" + " [").append(i.getStartTime()).append("]" + " [").append(i.getFinishTime()).append("]\n");
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

    public static void sjf(ArrayList<Process> array) {
        int minArrivalTime = array.get(0).getArrivalTime();
        Methods.sortBy("priority", array);
        Methods.sortBy("arrival", array);
        Methods.sortBy("remaining", array);
        for (Process i:array)
            System.out.println(i.getName()+" "+i.getArrivalTime()+" "+ i.getBurst()+ " "+i.getPriority());
        StringBuilder toPrint = new StringBuilder();
        int time = 0;
        float totalBurstTime = 0;
        float averageWaitingTimeTempSum = 0;
        float averageTurnAroundTimeTempSum = 0;
        float averageResponseTimeTempSum = 0;
        int finished = 0;

        while (finished < array.size()) {
            for (int j = 0; j < array.size(); ) {
                Process i = array.get(j);
                if (i.isFinished()) {
                    j++;
                    continue;
                }
                if (i.getArrivalTime() > time) {
                    if (!i.isFinished() && i.getArrivalTime() < minArrivalTime)
                        minArrivalTime = i.getArrivalTime();
                    if (j== array.size()-1)
                        time = minArrivalTime;
                } else if (!i.isFinished() && i.getArrivalTime() <= time) {
                    i.setStartTime(time);
                    i.setWaiting(time - i.getArrivalTime());
                    i.setResponse(i.getWaiting());
                    i.setRemainingBurstTime(0);
                    i.setFinished(true);
                    time += i.getBurst();
                    i.setTurnaround(time - i.getArrivalTime());
                    i.setFinishTime(time);
                    toPrint.append("[").append(i.getName()).append("] " + "[").append(i.getStartTime()).append("] " + "[").append(i.getFinishTime()).append("]\n");
                    totalBurstTime += i.getBurst();
                    averageWaitingTimeTempSum += i.getWaiting();
                    averageTurnAroundTimeTempSum += i.getTurnaround();
                    averageResponseTimeTempSum += i.getResponse();
                    finished++;
                    try{
                        j=0;
                        while (array.get(j++).isFinished());
                    }catch (IndexOutOfBoundsException E) {
                        continue;
                    };
                    minArrivalTime = array.get(j).getArrivalTime();
                    j = 0;
                    break;
                }
                j++;
            }
        }
        float throughput = (float) array.size() / time;
        float util = totalBurstTime / time * 100;
        float averageWaitingTime = averageWaitingTimeTempSum / array.size();
        float averageTurnaroundTime = averageTurnAroundTimeTempSum / array.size();
        float averageResponseTime = averageResponseTimeTempSum / array.size();
        Methods.IO.writeToFile("SJF", "Throughput: " + throughput + "\n" + "CPU Utilization: " + util + "%\n" + "Average Waiting Time: " + averageWaitingTime + "\n" + "Average Turnaround Time: " + averageTurnaroundTime + "\n" + "Average Response Time: " + averageResponseTime + "\n\n" + toPrint);
        Process.reset();
    }

}




