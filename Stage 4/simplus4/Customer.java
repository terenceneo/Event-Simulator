package cs2030.simulator;

import java.util.PriorityQueue;

public class Customer {
    private static int count = 0;
    private static int eventCounter = 0;
    private int index;    
    private static PriorityQueue<Customer> customers
        = new PriorityQueue<Customer>(10000, new CusComparator());
    private double time;
    private double arrivalTime;
    private String state;
    private int serverindex;
    private String servertype;
    private String type;
    
    /**
     * Creates a Customer.
     * @param time arrival time of Customer
     */
    public Customer(double time, String type) {
        this.index = ++count;
        this.time = time;
        this.arrivalTime = time;
        this.state = "arrives";
        this.serverindex = -1;
        this.type = type;
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

    public static void remove(Customer c) {
        customers.remove(c);
    }

    public static boolean isEmpty() {
        return customers.isEmpty();
    }

    public static Customer nextCustomer() {
        return customers.peek();
    }

    public void setServer(int serverindex, String servertype) {
        this.serverindex = serverindex;
        this.servertype = servertype;
    }

    public static int geteventCounter() {
        return eventCounter++;
    }

    public double gettime() {
        return this.time;
    }

    public int getindex() {
        return this.index;
    }

    public String getstate() {
        return this.state;
    }

    public double getarrivalTime() {
        return this.arrivalTime;
    }
    
    public String gettype() {
        return this.type;
    }
    
    public int getserverindex() {
        return this.serverindex;
    }

    /**
     * Prints Customer status.
     * @return time, index, state and server
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%.3f", this.time) + " " + this.index
            + this.type + " " + this.state);
        if (this.state.equals("waits")) {
            sb.append(" to be served by " + this.servertype + " " + this.serverindex);
        } else if (this.state.equals("served")) {
            sb.append(" by " + this.servertype + " " + this.serverindex);
        } else if (this.state.equals("done")) {
            sb.append(" serving by " + this.servertype + " " + this.serverindex);
        }
        return sb.toString();
    }
}
