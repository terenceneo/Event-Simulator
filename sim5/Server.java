import java.util.ArrayList;
import java.lang.Math;

class Server{
    public double serviceTime;
    public String state;
    public static ArrayList<Customer> customersServed = new ArrayList<>();
    public static ArrayList<Customer> customersLeft = new ArrayList<>();
    public static ArrayList<Customer> queue = new ArrayList<>();
    public double time;
	public double nextTime;
	public Customer isServing;
	public static ArrayList<Double> waitTimes = new ArrayList<>();
    
	Server(){
        this.state = "idle";
        this.serviceTime = 1.0;
        this.time = 0;
    }
	public void triesToServe(Customer c){
		if (c.state.equals("leaves")){
			Customer.customers.remove(c);
		}else if (c.state.equals("done")){
			Customer.customers.remove(c);
			//System.out.println("server removed");
		}else if (c.state.equals("served")){
			if (queue.contains(c)){
				queue.remove(c);
			}
			c.done(nextTime);
		}else if (this.isIdle(c)){
			this.serves(c);
		}else if (!this.hasQueue()){
			this.waits(c);
		}else if (this.queue.get(0) != c){
			this.leaves(c);
		}else if (this.queue.get(0) == c){
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
    public void serves(Customer c){
		this.isServing = c;
        this.state = "serving";
        this.customersServed.add(c);
        if (queue.contains(c)){
			waitTimes.add(this.nextTime - c.time);
			c.isServed(this.nextTime);
		}else{
			c.isServed();
		}
		this.time = Math.max(c.time, this.nextTime);
        this.nextTime = this.time + this.serviceTime;
		//System.out.println(c);
    }
    public void waits(Customer c){
		this.time = Math.max(c.time, this.time);
        c.waits();
        queue.add(c);
    }
    public void leaves(Customer c){
        this.time = Math.max(c.time, this.time);
		c.leaves();
        customersLeft.add(c);
		//System.out.println(c);
    }
    public boolean isIdle(Customer c){
        if (this.state.equals("serving") && this.nextTime <= c.time){
			this.state = "idle";
		}
        return(this.state.equals("idle"));
    }
    public boolean hasQueue(){
        return(this.queue.size() > 0);
    }
	public static double getAveWaitTime(){
		double num = 0;
		for(double time: waitTimes){
			num += time;
		}
		return num / customersServed.size();
	}
}