import java.util.*;

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
            toPrint.append(i.getName()).append(", ").append(i.getStartTime()).append(", ").append(i.getFinishTime()).append("\n");
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
                    if (!i.isFinished() && i.getArrivalTime() < minArrivalTime) minArrivalTime = i.getArrivalTime();
                    if (j == array.size() - 1) time = minArrivalTime;
                } else if (!i.isFinished() && i.getArrivalTime() <= time) {
                    i.setStartTime(time);
                    i.setWaiting(time - i.getArrivalTime());
                    i.setResponse(i.getWaiting());
                    i.setRemainingBurstTime(0);
                    i.setFinished(true);
                    time += i.getBurst();
                    i.setTurnaround(time - i.getArrivalTime());
                    i.setFinishTime(time);
                    toPrint.append(i.getName()).append(", ").append(i.getStartTime()).append(", ").append(i.getFinishTime()).append("\n");
                    totalBurstTime += i.getBurst();
                    averageWaitingTimeTempSum += i.getWaiting();
                    averageTurnAroundTimeTempSum += i.getTurnaround();
                    averageResponseTimeTempSum += i.getResponse();
                    finished++;
                    try {
                        j = 0;
                        while (array.get(j).isFinished()) j++;
                    } catch (IndexOutOfBoundsException E) {
                        continue;
                    }
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


    public static void SRJF(ArrayList<Process> array) {
        StringBuilder toPrint = new StringBuilder();
        StringBuilder toPrint2 = new StringBuilder();
        Methods.sortBy("arrival", array);
        PriorityQueue<Process> ready = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingBurstTime));
        ArrayList<Process> completed = new ArrayList<>();

        float totalBurstTime = 0;
        float totalWaitingTime = 0;
        float totalResponseTime = 0;
        float totalTurnAroundTime = 0;


        int time = 0;
        int finished = 0;
        int arrayIndex = 0;

        while (finished < array.size()) { //ends when all processes are marked as complete
            if (!ready.isEmpty()) { //while we have ready processes, we will use them
                Process i = ready.peek(); //top of the ready queue is the one with the least remaining time;
                if (i.getRemainingBurstTime() == i.getBurst()) { //for first time run
                    i.setStartTime(time);
                    i.setResponse(time - i.getArrivalTime());
                    i.times.add(time);
                } else {  //was not run right before now
                    if (!i.times.contains(time)) {
                        i.times.add(time);
                    } else { //was run right before now
                        i.times.remove(i.times.size() - 1);
                    }
                }
                i.times.add(time + 1);
                i.setRemainingBurstTime(i.getRemainingBurstTime() - 1);
                if (i.getRemainingBurstTime() == 0) {
                    i.setFinished(true);
                    i.setTurnaround(time - i.getArrivalTime());
                    i.setWaiting(i.getTurnaround() - i.getBurst());
                    completed.add(i);
                    ready.remove(i);
                    finished++;
                    totalBurstTime += i.getBurst();
                    totalResponseTime += i.getResponse();
                    totalWaitingTime += i.getWaiting();
                    totalTurnAroundTime += i.getTurnaround();

                }
                time++;
            }
            if (arrayIndex < array.size()) //stop looking if all processes have been added. index reaching the end when wrong time breaks -> all processes added
                while (array.get(arrayIndex).getArrivalTime() <= time) {
                    ready.add(array.get(arrayIndex));
                    arrayIndex++;
                    if (arrayIndex == array.size()) break;
                }
            if (arrayIndex < array.size() && ready.isEmpty()) {
                time = array.get(arrayIndex).getArrivalTime();
            }
        }
        float throughput = (float) array.size() / time;
        float util = totalBurstTime / time * 100;
        float averageWaitingTime = totalWaitingTime / array.size();
        float averageTurnaroundTime = totalTurnAroundTime / array.size();
        float averageResponseTime = totalResponseTime / array.size();
        String printer = "";
        String printer2 = "";
        for (Process i : completed) {
            toPrint = new StringBuilder();
            toPrint2 = new StringBuilder();
            toPrint.append(i.getName());
            toPrint2.append(i.getName());
            for (int timeIndex = 0; timeIndex < i.times.size() - 1; ) {
                toPrint.append(", ").append(i.times.get(timeIndex)).append(", ").append(i.times.get(timeIndex + 1));
                toPrint2.append(", ").append(i.times.get(timeIndex)).append(":").append(i.times.get(timeIndex + 1));
                timeIndex += 2;
            }
            printer += toPrint + "\n";
            printer2 += toPrint2 + "\n";

        }
        Methods.IO.writeToFile("SRJF", "Throughput: " + throughput + "\n" + "CPU Utilization: " + util + "%\n" + "Average Waiting Time: " + averageWaitingTime + "\n" + "Average Turnaround Time: " + averageTurnaroundTime + "\n" + "Average Response Time: " + averageResponseTime + "\n\n" + printer);
        Methods.IO.writeToFile("SRJF_2", "Throughput: " + throughput + "\n" + "CPU Utilization: " + util + "%\n" + "Average Waiting Time: " + averageWaitingTime + "\n" + "Average Turnaround Time: " + averageTurnaroundTime + "\n" + "Average Response Time: " + averageResponseTime + "\n\n" + printer2);
        Process.reset();
    }


    public static void priorityScheduling(ArrayList<Process> array) {
        StringBuilder toPrint = new StringBuilder();
        Methods.sortBy("remaining", array);
        Methods.sortBy("priority", array);
        Methods.sortBy("arrival", array);
        float totalBurstTime = 0;
        float totalResponseTime = 0;
        float totalWaitingTime = 0;
        float totalTurnAroundTime = 0;
        PriorityQueue<Process> ready = new PriorityQueue<>(Comparator.comparingInt(Process::getPriority).thenComparing(Process::getBurst));
        ArrayList<Process> completed = new ArrayList<>();
        int time = 0;
        int finished = 0;
        int arrayIndex = 0;


        while (finished < array.size()) {
            if (!ready.isEmpty()) {//executes procces with top priority
                Process currentProcess = ready.poll();
                currentProcess.setStartTime(time);
                currentProcess.setResponse(time - currentProcess.getArrivalTime());
                currentProcess.setWaiting(currentProcess.getResponse());
                time += currentProcess.getBurst();
                currentProcess.setFinishTime(time);
                currentProcess.setFinished(true);
                currentProcess.setTurnaround(time - currentProcess.getArrivalTime());
                currentProcess.setRemainingBurstTime(0);
                toPrint.append(currentProcess.getName()).append(", ").append(currentProcess.getStartTime()).append(", ").append(currentProcess.getFinishTime()).append("\n");
                finished++;
                totalBurstTime += currentProcess.getBurst();
                totalResponseTime += currentProcess.getResponse();
                totalWaitingTime += currentProcess.getWaiting();
                totalTurnAroundTime += currentProcess.getTurnaround();
                completed.add(currentProcess);
            } //end of execution

            if (arrayIndex < array.size()) //stop looking if all processes have been added. index reaching the end when wrong time breaks -> all processes added
                while (array.get(arrayIndex).getArrivalTime() <= time) {
                    ready.add(array.get(arrayIndex));
                    arrayIndex++;
                    if (arrayIndex == array.size()) break;
                }
            if (arrayIndex < array.size() && ready.isEmpty()) { //jump the time to next arrival time
                time = array.get(arrayIndex).getArrivalTime();
            }
        }
        float throughput = (float) array.size() / time;
        float util = totalBurstTime / time * 100;
        float averageWaitingTime = totalWaitingTime / array.size();
        float averageTurnaroundTime = totalTurnAroundTime / array.size();
        float averageResponseTime = totalResponseTime / array.size();
        Methods.IO.writeToFile("Priority", "Throughput: " + throughput + "\n" + "CPU Utilization: " + util + "%\n" + "Average Waiting Time: " + averageWaitingTime + "\n" + "Average Turnaround Time: " + averageTurnaroundTime + "\n" + "Average Response Time: " + averageResponseTime + "\n\n" + toPrint);
        Process.reset();
    }


    public static void preemtivePriority(ArrayList<Process> array) {
        StringBuilder toPrint = new StringBuilder();
        StringBuilder toPrint2 = new StringBuilder();
        Methods.sortBy("remaining", array);
        Methods.sortBy("priority", array);
        Methods.sortBy("arrival", array);
        float totalBurstTime = 0;
        float totalResponseTime = 0;
        float totalWaitingTime = 0;
        float totalTurnAroundTime = 0;
        PriorityQueue<Process> ready = new PriorityQueue<>(Comparator.comparingInt(Process::getPriority).thenComparing(Process::getBurst));
        ArrayList<Process> completed = new ArrayList<>();
        Integer time = 0;
        int finished = 0;
        int arrayIndex = 0;

        while (finished < array.size()) {
            if (!ready.isEmpty()) {
                Process current = ready.peek();

                if (current.getResponse() == -1) {
                    current.setResponse(time - current.getArrivalTime());
                    current.setStartTime(time);
                }
                current.setRemainingBurstTime(current.getRemainingBurstTime() - 1);

                if (current.getRemainingBurstTime() < 1) { //if Process is finished, do the neccesary checks
                    current.setFinished(true);
                    current.setRemainingBurstTime(0);
                    current.setTurnaround(time + 1 - current.getArrivalTime());
                    current.setWaiting(current.getTurnaround() - current.getBurst());
                    current.setFinishTime(time);
                    finished++;
                    totalBurstTime += current.getBurst();
                    totalWaitingTime += current.getWaiting();
                    totalResponseTime += current.getResponse();
                    totalTurnAroundTime += current.getTurnaround();
                    ready.remove(current);
                    completed.add(current);
                }

                if (current.times.contains(time)) current.times.remove(time);
                else current.times.add(time);

                current.times.add(time + 1);
                time++;
            }


            if (arrayIndex < array.size()) //stop looking if all processes have been added. index reaching the end when wrong time breaks -> all processes added
                while (array.get(arrayIndex).getArrivalTime() <= time) {
                    ready.add(array.get(arrayIndex));
                    arrayIndex++;
                    if (arrayIndex == array.size()) break;
                }
            if (arrayIndex < array.size() && ready.isEmpty()) {
                time = array.get(arrayIndex).getArrivalTime();
            }
        }

        float throughput = (float) array.size() / time;
        float util = totalBurstTime / time * 100;
        float averageWaitingTime = totalWaitingTime / array.size();
        float averageTurnaroundTime = totalTurnAroundTime / array.size();
        float averageResponseTime = totalResponseTime / array.size();
        String printer = "";
        String printer2 = "";
        for (Process i : completed) {
            toPrint = new StringBuilder();
            toPrint2 = new StringBuilder();
            toPrint.append(i.getName());
            toPrint2.append(i.getName());
            for (int timeIndex = 0; timeIndex < i.times.size() - 1; ) {
                toPrint.append(", ").append(i.times.get(timeIndex)).append(", ").append(i.times.get(timeIndex + 1));
                toPrint2.append(", ").append(i.times.get(timeIndex)).append(":").append(i.times.get(timeIndex + 1));
                timeIndex += 2;
            }
            printer += toPrint + "\n";
            printer2 += toPrint2 + "\n";

        }
        Methods.IO.writeToFile("PreemtivePriority", "Throughput: " + throughput + "\n" + "CPU Utilization: " + util + "%\n" + "Average Waiting Time: " + averageWaitingTime + "\n" + "Average Turnaround Time: " + averageTurnaroundTime + "\n" + "Average Response Time: " + averageResponseTime + "\n\n" + printer);
        Methods.IO.writeToFile("PreemtivePriority_2", "Throughput: " + throughput + "\n" + "CPU Utilization: " + util + "%\n" + "Average Waiting Time: " + averageWaitingTime + "\n" + "Average Turnaround Time: " + averageTurnaroundTime + "\n" + "Average Response Time: " + averageResponseTime + "\n\n" + printer2);
        Process.reset();

    }


    public static void roundRobin(ArrayList<Process> array, int quantum) {
        String toPrint = "";
        String toPrint2 = "";
        Methods.sortBy("arrival", array);
        ArrayList<Process> complete = new ArrayList<>();
        ArrayDeque<Process> ready = new ArrayDeque<>();
        Process justRan = null;
        float totalTurnaroundTime = 0;
        float totalWaitingTime = 0;
        float totalResponseTime = 0;
        float totalBurstTime = 0;

        Integer time = 0;
        int finished = 0;
        int arrayIndex = 0;

        while (finished < array.size()) {
            if (!ready.isEmpty()) {
                Process current = ready.removeFirst();
                if (current.getResponse() == -1) {
                    current.setResponse(time - current.getArrivalTime());
                    current.setStartTime(time);
                }
                current.setRemainingBurstTime(current.getRemainingBurstTime() - quantum);

                if (current.getRemainingBurstTime() < 1) {
                    if (current.times.contains(time))  //for adding time of turn start
                        current.times.remove(current.times.size() - 1);
                    else current.times.add(time);//checks if process has ended
                    time += quantum + current.getRemainingBurstTime();
                    current.setFinished(true);
                    current.setFinishTime(time);
                    current.setTurnaround(time - current.getArrivalTime());
                    current.setWaiting(current.getTurnaround() - current.getBurst());
                    finished++;
                    complete.add(current);
                    current.setRemainingBurstTime(0);

                    totalBurstTime+=current.getBurst();
                    totalWaitingTime += current.getWaiting();
                    totalResponseTime += current.getResponse();
                    totalTurnaroundTime += current.getTurnaround();

                } else {
                    time += quantum;
                    ready.addLast(current);
                    justRan = current;
                }

                if (!current.isFinished()) if (current.times.contains(time - quantum))  //for adding time of turn start
                    current.times.remove(current.times.size() - 1);
                else current.times.add(time - quantum);
                current.times.add(time); //adds time of turn end
            }
            if (arrayIndex < array.size()) //stop looking if all processes have been added. index reaching the end when wrong time breaks -> all processes added
                while (array.get(arrayIndex).getArrivalTime() <= time) {
                    if (!ready.isEmpty() && justRan != null && justRan.times.get(justRan.times.size()-1) == time.intValue()) {
                        ready.remove(justRan);
                        ready.addLast(array.get(arrayIndex));
                        ready.addLast(justRan);
                    } else ready.addLast(array.get(arrayIndex));
                    arrayIndex++;
                    if (arrayIndex == array.size()) break;
                }
            if (arrayIndex < array.size() && ready.isEmpty()) {
                time = array.get(arrayIndex).getArrivalTime();
            }
        }

            float throughput = (float) array.size() / time;
            float util = totalBurstTime / time * 100;
            float averageWaitingTime = totalWaitingTime / array.size();
            float averageTurnaroundTime = totalTurnaroundTime / array.size();
            float averageResponseTime = totalResponseTime / array.size();

        for (Process i : complete) {
            toPrint += (i.getName());
            toPrint2 += (i.getName());
            for (int j = 0; j < i.times.size(); j += 2) {
                toPrint += (", " + i.times.get(j) + ", " + i.times.get(j + 1));
                toPrint2 += (", " + i.times.get(j) + ":" + i.times.get(j + 1));
            }
            toPrint += "\n";
            toPrint2 += "\n";
        }
        Methods.IO.writeToFile("RoundRobin", "Throughput: " + throughput + "\n" + "CPU Utilization: " + util + "%\n" + "Average Waiting Time: " + averageWaitingTime + "\n" + "Average Turnaround Time: " + averageTurnaroundTime + "\n" + "Average Response Time: " + averageResponseTime + "\n\n" + toPrint);
        Methods.IO.writeToFile("RoundRobin_2", "Throughput: " + throughput + "\n" + "CPU Utilization: " + util + "%\n" + "Average Waiting Time: " + averageWaitingTime + "\n" + "Average Turnaround Time: " + averageTurnaroundTime + "\n" + "Average Response Time: " + averageResponseTime + "\n\n" + toPrint2);
    }

}









