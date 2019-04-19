import java.util.*;

class Main{
    public static void main(String[] args) { 
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            double time = sc.nextDouble();
            Customer c = new Customer(time);
			Customer.add(c);
        }
        Server s = new Server();
		while (!Customer.customers.isEmpty()){
			Customer next = Customer.customers.peek();
			System.out.println(next);
            s.triesToServe(next);
			//Iterator  c = Customer.customers.iterator();
			/*
			for (Customer c: Customer.customers){
				System.out.println(c);
			}
			System.out.println();
			*/
        }
        System.out.println("[" + String.format("%.3f", Server.getAveWaitTime()) + " " +
		Server.customersServed.size() + " " + Server.customersLeft.size() + "]");
    }
}
