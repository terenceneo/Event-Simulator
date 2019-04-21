package cs2030.simulator;

import java.util.ArrayList;
import java.lang.Math;
import java.util.LinkedList;

public class Server {
    private static ArrayList<Server> servers = new ArrayList<>();
    private static ArrayList<Customer> customersServed = new ArrayList<>();
    private static ArrayList<Customer> customersLeft = new ArrayList<>();
    private static ArrayList<Double> waitTimes = new ArrayList<>();
    private LinkedList<Customer> queue = new LinkedList<>();
    private static int queuelength;
    private int eventCounter = 0;
    private double time;
    private double nextTime;
    private double serviceTime;
    private String state;
    private Customer isServing;
    private int index;
    private boolean lastServer = false;
    private static double pr;
    private String type;
    
    /**
     * Constructs a Server.
     * @param index index of Server, starting at 1
     * @param type type of Server, String to be passed into printing of Customer events
     */
    public Server(int index, String type) {
        this.state = "idle";
        this.time = 0;
        this.index = index;
        this.type = type;
        // this.serviceTime = Random.genServiceTime();
    }

    /**
     * Serving logic that determines if Customer
     * waits, isServed or leaves.
     * @param c Customer to be served
     */
    public void triesToServe(Customer c) {
        if (c.getstate().equals("leaves")) {
            Customer.remove(c);
        } else if (c.getstate().equals("done")) {
            this.doneServing(c);
        } else if (c.getstate().equals("served")) {
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
    private void toServe(Customer c) {
        this.time = Math.max(c.gettime(), this.time);
        this.nextTime = Math.max(c.gettime(), this.nextTime);
        this.isServing = c;
        c.setServer(this.index, this.type);
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
    private void serving(Customer c) {
        this.state = "serving";
        if (this.queue.peek() == c) {
            this.queue.poll();
        }
        this.serviceTime = Random.genServiceTime();
        this.time = Math.max(this.time, c.gettime());
        this.nextTime = Math.max(this.nextTime, 
            this.time + this.serviceTime);
        waitTimes.add(this.time - c.getarrivalTime());
        this.updateQueueNextTime();
        // System.out.println("Server next time: " + this.nextTime);
        c.done(this.nextTime);
    }

    /**
     * Removes Customer done serving and checks if it is time for
     * Server to rest.
     * @param c Customer done serving
     */
    private void doneServing(Customer c) {
        Customer.remove(c);
        // checks if it is time for server to rest
        if (this.type == "server" && Random.genRandomRest() < pr) { //server_rest
            this.state = "rest";
            this.time = Math.max(c.gettime(), this.time);
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
    private void waits(Customer c) {
        this.time = Math.max(c.gettime(), this.time);
        c.waits();
        c.setServer(this.index, this.type);
        if (this.queue.peekLast() != c) {
            this.queue.add(c);
        }
    }

    /**
     * Update Customer's state to leave.
     * @param c Customer
     */
    private void leaves(Customer c) {
        this.time = Math.max(c.gettime(), this.time);
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
        if (!this.state.equals("idle") && this.nextTime <= c.gettime()) {
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
        //     + this.time + " Customer: " + c.gettime());
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

    public static void addCustomersLeft(Customer c) {
        customersLeft.add(c);
    }

    public void isLast() {
        this.lastServer = true;
    }

    public static void setpr(double pr) {
        Server.pr = pr;
    }

    public static void setqueuelength(int queuelength) {
        Server.queuelength = queuelength;
    }

    public static int getqueuelength() {
        return queuelength;
    }

    public boolean getlastServer() {
        return this.lastServer;
    }

    public int getcurrQueueSize() {
        return this.queue.size();
    }

    public static int getcustomersServed() {
        return customersServed.size();
    }

    public static int getcustomersLeft() {
        return customersLeft.size();
    }

    public static int getServerNo() {
        return servers.size();
    }

    public static Server getServer(int i) {
        return servers.get(i);
    }

    public static ArrayList<Server> getServers() {
        return servers;
    }
    
    public String toString() {
        return (String.format("%.3f", this.time) + " server "
            + this.index + " " + this.state);
    }
}
