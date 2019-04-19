import java.util.PriorityQueue;

class Customer {
    public static int count = 0;
    public int index;    
    public static PriorityQueue<Customer> customers
        = new PriorityQueue<Customer>(100, new CusCompare());
    public double time;
    public String state;
    public Server server;
    
    Customer(double time) {
        this.index = ++count;
        this.time = time;
        this.state = "arrives";
        this.server = null;
    }

    public static void add(Customer c) {
        customers.add(c);
    }

    public void isServed() {
        this.state = "served";
    }

    public void isServed(double time) {
        this.time = time;
        this.state = "served";
        customers.remove(this);
        customers.add(this);
    }

    public void waits() {
        this.state = "waits";
    }

    public void leaves() {
        this.state = "leaves";
    }

    public void done(double time) {
        this.time = time;
        this.state = "done";
        customers.remove(this);
        customers.add(this);
        //System.out.println("cus " + this.index + " removed and added");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%.3f", this.time) + " " + this.index + " " + this.state);
        if (this.state.equals("waits")) {
            sb.append(" to be served by " + this.server.index);
        } else if (this.state.equals("served")) {
            sb.append(" by " + this.server.index);
        } else if (this.state.equals("done")) {
            sb.append(" serving by " + this.server.index);
        }
        return sb.toString();
    }
}
