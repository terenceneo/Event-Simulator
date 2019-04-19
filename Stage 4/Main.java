import cs2030.simulator.Server;
import cs2030.simulator.Customer;
import cs2030.simulator.Random;
import java.util.Scanner;

class Main {
    public static void main(String[] args) { 
        Scanner sc = new Scanner(System.in);
        
        Random.seed = sc.nextInt();
        Random.serverNo = sc.nextInt();
        Random.cusNo = sc.nextInt();
        Random.lambda = sc.nextDouble();
        Random.mu = sc.nextDouble();

        Random.createRandomGenerator();
        Random.generate();
        
        while (!Customer.customers.isEmpty()) {
            Customer next = Customer.customers.peek();
            System.out.println(next);
            boolean done = false;
            if (next.serverindex != -1) {
                int serverindex = next.serverindex;
                Server.servers.get(serverindex - 1).triesToServe(next);
                done = true;
            }
            /*
             * scans the servers from 1 to k, and approaches
             * the first idle server to be served
             */
            if (!done) {
                for (Server s: Server.servers) {
                    if (s.isIdle(next)) {
                        s.triesToServe(next);
                        done = true;
                        break;
                    }
                }
            }
            /*
             * if there is no idle server, the customer scans
             * the servers from 1 to k, and waits at the first
             * busy server without any waiting customer
             */
            if (!done) {
                for (Server s: Server.servers) {
                    if (!s.hasQueue() || s.lastServer) {
                        s.triesToServe(next);
                        break;
                    }
                }
            }
            //Iterator  c = Customer.customers.iterator();
            /*
            for (Customer c: Customer.customers) {
                System.out.println(c);
            }
            System.out.println();
            */
        }
        System.out.println("[" + String.format("%.3f", Server.getAveWaitTime()) + " " + 
            Server.customersServed.size() + " " + Server.customersLeft.size() + "]");
    }
}
