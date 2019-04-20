import cs2030.simulator.Server;
import cs2030.simulator.Customer;
import cs2030.simulator.Random;
import cs2030.simulator.Event;
import java.util.Scanner;

class Main {
    public static void main(String[] args) { 
        Scanner sc = new Scanner(System.in);
        
        Random.seed = sc.nextInt();
        Random.serverNo = sc.nextInt();
        Random.Nself = sc.nextInt();
        Server.queuelength = sc.nextInt();
        Random.cusNo = sc.nextInt();
        Random.lambda = sc.nextDouble();
        Random.mu = sc.nextDouble();

        Random.restingRate = sc.nextDouble();
        Server.pr = sc.nextDouble();

        Random.createRandomGenerator();
        Random.generate();

        // System.out.println(Random.seed + " " + Random.serverNo + " " +
        // Server.queuelength + " " + Random.cusNo + " " + Random.lambda + " " + 
        // Random.mu);
        
        while (!Customer.customers.isEmpty()) {
            Customer next = Customer.customers.peek();
            Event event = new Event(next.time, next.index,
                next.toString(), 1, Customer.eventCounter++);

            // if (next.index == 10 || next.index == 9) {
            //     System.out.println(next.index);
            //     System.out.println("PQ: " + Customer.customers);
            //     for (Server s: Server.servers) {
            //         System.out.println(s.index + ": " + s.queue);
            //     }
            //     System.out.println();
            // }

            boolean done = false;
            // Already assigned a Server
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
                if (Random.totalServers < 1) { // no servers present
                    if (next.state == "leaves") {
                        Customer.customers.remove(next);
                    } else {
                        next.leaves();
                        Server.customersLeft.add(next);
                    }
                }
                for (Server s: Server.servers) {
                    if (s.hasQueueSpace(next) || s.lastServer) {
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

        while (Event.hasEvents()) {
            Event.printEvent();
        }
        System.out.println("[" + String.format("%.3f", Server.getAveWaitTime()) + " " + 
            Server.customersServed.size() + " " + Server.customersLeft.size() + "]");
    }
}
