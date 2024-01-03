import java.util.ArrayList;

public class Process {
    public static ArrayList<Process> allProcesses = new ArrayList<>();
    public int[] attrs;
    private String name;
    private int arrivalTime;
    private int Priority;
    private int burst;
    private int response;
    private int waiting;
    private int turnaround;
    private int startTime;
    private int finishTime;
    private boolean finished = false;
    private int remainingBurstTime;
    public ArrayList<Integer> startTimes = new ArrayList<>();
    public ArrayList<Integer> endTimes = new ArrayList<>();


    public Process(String name, int arrival_time, int priority, int burst) {
        this.name = name;
        this.arrivalTime = arrival_time;
        Priority = priority;
        this.burst = this.remainingBurstTime = burst;
        this.attrs = new int[]{this.arrivalTime, this.Priority, this.remainingBurstTime};
        allProcesses.add(this);
    }

    public static void reset() {
        for (Process i : allProcesses) {
            i.turnaround = i.waiting = i.response = -1;
            i.finished = false;
            i.remainingBurstTime = i.burst;
            i.startTimes = new ArrayList<>();
            i.endTimes = new ArrayList<>();
        }
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getRemainingBurstTime() {
        return remainingBurstTime;
    }

    public void setRemainingBurstTime(int remainingBurstTime) {
        this.remainingBurstTime = remainingBurstTime;
    }

    @Override
    public String toString() {
        return "Process{" +
                "name='" + name + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", Priority=" + Priority +
                ", burst=" + burst +
                ", finished=" + finished +
                ", remainingBurstTime=" + remainingBurstTime +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getPriority() {
        return Priority;
    }

    public int getBurst() {
        return burst;
    }

    public int getWaiting() {
        return waiting;
    }

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public int getTurnaround() {
        return turnaround;
    }

    public void setTurnaround(int turnaround) {
        this.turnaround = turnaround;
    }

}
