package cs2030.simulator;

import java.util.PriorityQueue;

public class Customer {
    public static int count = 0;
    public static int eventCounter = 0;
    public int index;    
    public static PriorityQueue<Customer> customers
        = new PriorityQueue<Customer>(10000, new CusComparator());
    public double time;
    public double arrivalTime;
    public String state;
    public int serverindex;
    
    /**
     * Creates a Customer.
     * @param time arrival time of Customer
     */
    public Customer(double time) {
        this.index = ++count;
        this.time = time;
        this.arrivalTime = time;
        this.state = "arrives";
        this.serverindex = -1;
    }

    public void isServed() {
        this.state = "served";
    }

    /**
     * Updates Customer state to "served".
     * @param time time that customer is served.
     */
    public void isServed(double time) {
        this.time = time;
        this.state = "served";
        customers.remove(this);
        customers.add(this);
    }

    public void waits() {
        this.state = "waits";
    }

    // public void waiting(double nextTime) {
    //     this.time = nextTime;
    //     customers.remove(this);
    //     customers.add(this);
    // }

    public void leaves() {
        this.state = "leaves";
    }

    /**
     * Updates Customer state to "done".
     * @param time time that customer is done
     */
    public void done(double time) {
        this.time = time;
        this.state = "done";
        customers.remove(this);
        customers.add(this);
        //System.out.println("cus " + this.index + " removed and added");
    }

    public static void add(Customer c) {
        customers.add(c);
    }

    public void setServerIndex(int serverindex) {
        this.serverindex = serverindex;
    }

    /**
     * Prints Customer status.
     * @return time, index, state and server
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%.3f", this.time) + " " + this.index + " " + this.state);
        if (this.state.equals("waits")) {
            sb.append(" to be served by " + this.serverindex);
        } else if (this.state.equals("served")) {
            sb.append(" by " + this.serverindex);
        } else if (this.state.equals("done")) {
            sb.append(" serving by " + this.serverindex);
        }
        return sb.toString();
    }
}
