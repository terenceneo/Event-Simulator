// package cs2030.simulator;

import java.util.ArrayList;
import java.lang.Math;

public class Server {
    public static ArrayList<Server> servers = new ArrayList<>();
    public static ArrayList<Customer> customersServed = new ArrayList<>();
    public static ArrayList<Customer> customersLeft = new ArrayList<>();
    public static ArrayList<Double> waitTimes = new ArrayList<>();
    public ArrayList<Customer> queue = new ArrayList<>();
    public double time;
    public double nextTime;
    public double serviceTime;
    public String state;
    public Customer isServing;
    public int index;
    public boolean lastServer = false;

    
    /**
     * Constructs a Server.
     * @param index index of Server, starting at 1
     */
    public Server(int index) {
        this.state = "idle";
        this.serviceTime = 1.0;
        this.time = 0;
        this.index = index;
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
            Customer.customers.remove(c);
            //System.out.println("server removed");
        } else if (c.state.equals("served")) {
            if (this.queue.contains(c)) {
                this.queue.remove(c);
            }
            //System.out.println("Server next time: " + this.nextTime);
            c.done(this.nextTime);
        } else if (this.isIdle(c)) {
            this.serves(c);
        } else if (!this.hasQueue()) {
            this.waits(c);
        } else if (this.queue.get(0) != c && this.lastServer) {
            this.leaves(c);
        } else if (this.queue.get(0) == c) {
            this.serves(c);
        }
        /*
        while(this.hasQueue()){
            Customer cwaiting = this.queue.get(0);
            if (cwaiting.time >= this.nextTime){
                //System.out.println(cwaiting.time + ", " + this.nextTime);
                this.serves(cwaiting);
            }else{
                System.out.println("else");
                break;
            }
        }
        */
    }

    /**
     * Server serves Customer, updating Customer's
     * state.
     * @param c Customer to serve
     */
    public void serves(Customer c) {
        this.isServing = c;
        this.state = "serving";
        c.server = this;
        customersServed.add(c);
        if (this.queue.contains(c)) {
            waitTimes.add(this.nextTime - c.time);
            c.isServed(this.nextTime);
        } else {
            c.isServed();
        }
        this.time = Math.max(c.time, this.nextTime);
        this.nextTime = this.time + this.serviceTime;
        //System.out.println(c);
    }

    /**
     * Adds Customer to queue of Server.
     * @param c Customer that waits
     */
    public void waits(Customer c) {
        this.time = Math.max(c.time, this.time);
        c.waits();
        c.server = this;
        this.queue.add(c);
    }

    /**
     * Update CUstomer's state to leave.
     * @param c Customer
     */
    public void leaves(Customer c) {
        this.time = Math.max(c.time, this.time);
        c.leaves();
        customersLeft.add(c);
        //System.out.println(c);
    }

    /**
     * Checks if Server isIdle at the time CUstomer c
     * arrives.
     * @param c Customer
     * @return True if Server is Idle
     */
    public boolean isIdle(Customer c) {
        if (this.state.equals("serving") && this.nextTime <= c.time) {
            this.state = "idle";
        }
        return (this.state.equals("idle"));
    }

    public boolean hasQueue() {
        return (this.queue.size() > 0);
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
        return num / customersServed.size();
    }

    public static void add(Server s) {
        servers.add(s);
    }

    public void isLast() {
        this.lastServer = true;
    }
}
