package cs2030.simulator;

import java.util.ArrayList;
import java.lang.Math;
import java.util.LinkedList;

public class Server {
    public static ArrayList<Server> servers = new ArrayList<>();
    public static ArrayList<Customer> customersServed = new ArrayList<>();
    public static ArrayList<Customer> customersLeft = new ArrayList<>();
    public static ArrayList<Double> waitTimes = new ArrayList<>();
    public LinkedList<Customer> queue = new LinkedList<>();
    public static int queuelength;
    public int eventCounter = 0;
    public double time;
    public double nextTime;
    public double serviceTime;
    public String state;
    public Customer isServing;
    public int index;
    public boolean lastServer = false;
    public static double pr;
    
    /**
     * Constructs a Server.
     * @param index index of Server, starting at 1
     */
    public Server(int index) {
        this.state = "idle";
        this.time = 0;
        this.index = index;
        // this.serviceTime = Random.genServiceTime();
    }

    /**
     * Serving logic that determines if Customer
     * waits, isServed or leaves.
     * @param c Customer to be served
     */
    public void triesToServe(Customer c) {
        if (c.state.equals("leaves")) {
            Customer.customers.remove(c);
        } else if (c.state.equals("done")) {
            this.doneServing(c);
        } else if (c.state.equals("served")) {
            this.serving(c);
        } else if (this.isIdle(c)) {
            this.toServe(c);
        } else if (this.queue.contains(c)) { //queueing at server, not idle
            this.toServe(c);
        } else if (this.hasQueueSpace(c)) {
            this.waits(c);
        } else if (this.queue.peek() != c && this.lastServer) {
            this.leaves(c);
        }
        /*
        while(this.hasQueue()){
            Customer cwaiting = this.queue.get(0);
            if (cwaiting.time >= this.nextTime){
                //System.out.println(cwaiting.time + ", " + this.nextTime);
                this.toServe(cwaiting);
            }else{
                System.out.println("else");
                break;
            }
        }
        */
    }

    /**
     * Server toServe Customer in the future, updating Customer's
     * state.
     * @param c Customer to serve
     */
    public void toServe(Customer c) {
        this.time = Math.max(c.time, this.time);
        this.nextTime = Math.max(c.time, this.nextTime);
        this.isServing = c;
        c.setServerIndex(this.index);
        if (!customersServed.contains(c)) {
            customersServed.add(c);
        }
        c.isServed(this.nextTime);
        //System.out.println(c);
    }

    /**
     * Server is currently serving a Customer at the front of the
     * queue or other wise.
     * @param c Customer serving
     */
    public void serving(Customer c) {
        this.state = "serving";
        if (this.queue.peek() == c) {
            this.queue.poll();
        }
        this.serviceTime = Random.genServiceTime();
        this.time = Math.max(this.time, c.time);
        this.nextTime = Math.max(this.nextTime, 
            this.time + this.serviceTime);
        waitTimes.add(this.time - c.arrivalTime);
        this.updateQueueNextTime();
        // System.out.println("Server next time: " + this.nextTime);
        c.done(this.nextTime);
    }

    /**
     * Removes Customer done serving and checks if it is time for
     * Server to rest.
     * @param c Customer done serving
     */
    public void doneServing(Customer c) {
        Customer.customers.remove(c);
        // checks if it is time for server to rest
        if (Random.genRandomRest() < pr) { //server_rest
            this.state = "rest";
            this.time = Math.max(c.time, this.time);
            Event event = new Event(this.time, this.index,
                this.toString(), 2, this.eventCounter++);
            this.nextTime = Math.max(this.nextTime,
                this.time + Random.genRestPeriod());
            this.time = this.nextTime;
            this.state = "back";
            event = new Event(this.time, this.index,
                this.toString(), 0, this.eventCounter++);
            this.updateQueueNextTime();
        }
    }

    /**
     * Adds Customer to queue of Server.
     * @param c Customer that waits
     */
    public void waits(Customer c) {
        this.time = Math.max(c.time, this.time);
        c.waits();
        c.setServerIndex(this.index);
        if (this.queue.peekLast() != c) {
            this.queue.add(c);
        }
    }

    /**
     * Update Customer's state to leave.
     * @param c Customer
     */
    public void leaves(Customer c) {
        this.time = Math.max(c.time, this.time);
        c.leaves();
        customersLeft.add(c);
        //System.out.println(c);
    }

    private void updateQueueNextTime() {
        for (Customer cqueue: this.queue) {
            cqueue.isServed(this.nextTime);
        }
    }

    /**
     * Checks if Server isIdle at the time CUstomer c
     * arrives.
     * @param c Customer
     * @return True if Server is Idle
     */
    public boolean isIdle(Customer c) {
        if (!this.state.equals("idle") && this.nextTime <= c.time) {
            this.state = "idle";
        }
        return (this.state.equals("idle"));
    }

    /**
     * Checks if there is space in Server's queue.
     * @param c Customer to enqueue
     * @return True if Server has queue space
     */
    public boolean hasQueueSpace(Customer c) {
        // System.out.println("Server " + this.index + " time: "
        //     + this.time + " Customer: " + c.time);
        return (this.queue.size() < Server.queuelength);
    }

    /**
     * Average Waiting Time of all Customers.
     * @return average waiting time of all Customers
     */
    public static double getAveWaitTime() {
        double num = 0;
        for (double time: waitTimes) {
            num += time;
        }
        if (num == 0) {
            return 0.0;
        }
        return num / customersServed.size();
    }

    public static void add(Server s) {
        servers.add(s);
    }

    public void isLast() {
        this.lastServer = true;
    }
    
    public String toString() {
        return (String.format("%.3f", this.time) + " server "
            + this.index + " " + this.state);
    }
}
