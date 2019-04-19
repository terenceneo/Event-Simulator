import java.util.*;

class Customer{
    public int index;
    public static int count = 0;
    public static PriorityQueue<Customer> customers = new PriorityQueue<Customer>(100, new CusCompare());
    public double time;
    public String state;
    
	Customer(double time){
        this.index = ++count;
        this.time = time;
        this.state = "arrives";
    }
	public static void add(Customer c){
		customers.add(c);
	}
    public void isServed(){
        this.state = "served";
    }
	public void isServed(double time){
		this.time = time;
        this.state = "served";
		customers.remove(this);
		customers.add(this);
    }
    public void waits(){
        this.state = "waits";
    }
    public void leaves(){
        this.state = "leaves";
    }
    public void done(double time){
		this.time = time;
        this.state = "done";
		customers.remove(this);
		customers.add(this);
		//System.out.println("cus " + this.index + " removed and added");
    }
    public String toString(){
        return (String.format("%.3f", time) + " " + index + " " + state);
    }
}
