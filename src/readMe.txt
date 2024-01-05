Operating Systems Final Project -- scheduling algorithms
    Student: Sayyed Muhammad Jaffry : 220700016
    muhammadj110@gmail.com
    NOTE: No AI tool was used in the making of this project, apart from some autocomplete provided by the IDE.
--------------------------------------------------------------------
RUN:
1- Edit "list.txt" to your liking. The format is :
[Process name] [arrival time] [priority] [burst time]

2- change the time Quantum in "main.java" in the implementation of Round Robin. Default value is 2.

3- Run "main.java"
--------------------------------------------------------------------
Full Documentation - you can use "cmd/ctrl+f" to search for something if you're not sure where to look.
Classes:

    Main.java {
        This is the controller class, it is the class that contains the "main" method. The
        functions related to each algorithm are called in order in this class.
    }

    Process.java {
        This class holds the attributes and methods for a process that has been passed to the
        operating system. It contains the following attributes and methods:

        Attributes (Process)

            static ArrayList<Process> allProcesses
               - Description: A static ArrayList that stores each process that is added.

            int[] attrs
               - Description: An array holding the attributes based on which different algorithms
                 would want to sort the processes. The attributes are (arrivalTime, priority, remainingBurstTime).
                 Check sortBy() method for usage details.

            ArrayList<Integer> times:
                -Holds the list of all times a process started or ended. An index which is even indicates a start time and
                an index which is odd indicates a finish time.



        Methods (Process) [not to be mistaken with Methods.java class]

            Process():
                constructor call method:
                    1- Returns a Process, and sets it's remaining burst time equal to the initial burst
                       time provided.
                    2- Initializes (this.attrs) array for the process.
                    3- Adds process to Process.allProcesses.

            void reset():
                resets the temporary values so that the values are not reused after each algorithm changes them.

            setters & getters: routine
    }

    Methods.java{
        This class contains helper methods that provide useful functions throughout the program.

        Methods:

            void sortBy(String type, ArrayList Array): sorts the ArrayList {array} based on the sorting element {type}
                details: throughout the program, we need to sort the array based on different elements such as
                arrivalTime, priority and burstTime. This method helps us achieve this by comparing said element (which
                is stored in Process.attrs) and sorting it. BubbleSort is used due to its simplicity.
                Sorting guide: {
                                    type = "arrival" -> arrivalTime-based
                                    type = "priority" -> priority-based
                                    type = "remaining" -> remaining-burst-time-based
                }

                void readFromFile(filename): reads information from list.txt and processes it. then creates
                    Processes.allProcesses.

                void writeToFile(filename, string): creates a file with the name provided in {filename},
                    writes string to the filename provided.

    }

    Alg.java

        This class contains all the scheduling algorithms that were implemented. All of the algorithms have these
        attributes in common:
            String/StringBuiler toPrint : 2nd part of the final string that will be printed to the file, contains
            the information about the processes' start and end times. toPrint is concated with cpu info in the end.

            time, totalBurstTime, totalWaitingTime(averageWaitingTimeTempSum), totalResponseTime(averageResponseTimeTempSum)
            , totalTurnaroundTime(averageTurnaroundTimeTempSum). time is incremented differently in each algorithm,
            the other attributes are updated only after a process is marked as Finished. They are used to calculate the
            times wanted (averageWaiting, etc)

            float throughput
            float util -> CPU utilization
            float averageWaitingTime
            float averageTurnaroundTime
            float averageResponseTime

            writeTofile(): used for writing to file
            reset(): To reset the temporary values


            ALGORITHMS
            Note: In order to sort based on multiple factors, the sorting would take place for each factor from least important to most important.
                example: array.sort(arrivalTime, priority); :: is the same as :: array.sort(priority); array.sort(arrivalTime);

            1- FCFS:
                - EXECUTION: sorts the processes based on arrival time as first rule, then priority in case of equal arrival time,
                                then executes them one-by-one. all times (other than response, waiting) are updated when a process is
                                marked as finished.
                - TIME : time is set as the next process' arrival time after a process is marked as Finished,. Time jump
                            may be required if the next process has not arrived yet.


            2- SJF: I accept that there are many incorrect design flaws in this algorithm (it still works correctly) - this
                    is due to lack of experience at this point, and lack of time after gaining experience. Hopefully this can
                    be updated in the near future.

                -EXECUTION:
                    - sorts processes based on remainingBurstTime, arrivalTime, Priority.
                    - checks if the first process (least burst time) has arrived. If it has arrived, it will be executed, marked as finished, and
                        relevent times will be updated. If the process has not arrived, it will keep moving through the list until it sees an arrived
                        process to execute. it starts over from the first process after executing each process.
                    -TIME: Time is updated by currentProcess.burstTime after execution. Time jumps to the next smallest unfinished arrivaltime
                        if no process has arrived at the end of the search. minArrivalTime checks the minimum arrival time of the unfinished processs.


            3- SRJF: This algorithm utilizes a priority queue(using priority as measure to prioritize) to keep Ready processes handy,
            and a list to keep completed processs. It sorts the input array based on (arrivalTime, priority) then adds all arrived processes to {Ready} queue.

                -EXECUTION: Executes the first process in the ready queue. processs are added from the sorted input if they have an arrival time not greater
                			than the current time. The process runs for 1 time unit. It then removes the proces from ready and adds it to completed before
                			rechecking if any processs need to be added the ready queue, and then switching to the next shortest job in the ready queue.
                			The index of the sorted input is not reset throughout the algorithm,and the search for arrived processes stops when a process that
                			has not arrived is seen. The index is then this Process.


               	-TIME: Time is incremented by 1, and it jumps only if the ready queue is empty after adding all arrived
               				processes. It jumps to the next process in the sorted input.



			4- priorityScheduling (nonPreemtive): This algorithm also uses a priority queue that inserts based on (Priority, remainingBurstTime). It is similar to SRJF
					in most manners except time increment.

					-EXECUTION: Similar to SRJF. Executes first process in ready Queue. But it runs for entirety of burst time,
					marks as finished, then adds all arrived processes.

					-TIME: Silimar to SJF. Increments time with burst, jumps if ready list is empty and no process has arrived.



			5- preemtivePriority: This algorithm also uses a priority queue that inserts based on (Priority, remainingBurstTime). It is similar to SRJF
					in most manners except time increment. (Input is sorted based on arrival, priority, burst)

					-EXECUTION: Similar to SRJF. Executes first process in ready Queue. and runs for 1 time unit, checks if proces is finished, if
					so, removes it from ready, adds it to complete, and then adds all arrived processes from sorted input.

					-TIME: Silimar to SJF. Increments time by 1, jumps if ready list is empty and no process has arrived.


			6- RoundRobin: This algorithm takes array and quantum as inputs. It runs for a time quantum before executing the next process in the ready dequeue.

					-EXECUTION: It removes the first process from the dequeue, runs it for q units, then updates time and checks if any process has arrived. If
								any process has arrived, it is added to the end of the list, otherwise, it adds the current process to the end if it has not finished,
								 else adds is it to {completed} list. If the ready DQ is empty and no process has arrived, time jump occurs.



					-TIME: Increment by q units. Related time values updated when process is marked finished.

					*justRan: keeps track of the current process in case multiple processes are added to the end of the list. this is important because
						the current task is added outside its scope.


NOTE:
	All algorithms end when all processes are marked as finished. {int finished} keeps track of the number of finished processes.
	CPU attributes calculations:

			float throughput = array.size() / time;
			float util = totalBurstTime / time * 100;
			float averageWaitingTime = totalWaitingTime / array.size();
			float averageTurnaroundTime = totalTurnaroundTime / array.size();
			float averageResponseTime = totalResponseTime / array.size();

		where total_x_time is incremented after a task if finished


	-START AND END TIMES: These times are handled using the {times} list in Process. It has been explained in the Process section. After executing
							a process for 1 time unit, the algorithm adds the start time, only if the preivous finish time is different from the current start time.
							else, the previous finish time is removed from the {times} list, as the process has been executed back-to-back. The finish time is added
							regardless.

