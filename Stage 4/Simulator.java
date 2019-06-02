package cs2030.simulator;

public class Simulator {
    /**
     * Logic that allocates Customers to Servers as they
     * enter the simulator.
     */
    public static void run() {
        while (!Customer.isEmpty()) {
            Customer next = Customer.nextCustomer();
            Event event = new Event(next.gettime(), next.getindex(),
                next.toString(), 1, Customer.geteventCounter());

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
            if (next.getserverindex() != -1) {
                int serverindex = next.getserverindex();
                Server.getServer(serverindex - 1).triesToServe(next);
                done = true;
            }
            /*
             * scans the servers from 1 to k, and approaches
             * the first idle server to be served
             */
            if (!done) {
                for (Server s: Server.getServers()) {
                    if (s.isIdle(next)) {
                        s.triesToServe(next);
                        done = true;
                        break;
                    }
                }
            }
            /*
             * a greedy customer is introduced that always chooses
             * the queue with the fewest customers to join. In the
             * case of a tie, the customer breaks the tie by
             * choosing the first one while scanning from servers 1
             * to k.
             */
            if (next.gettype().equals("(greedy)") && !done) {
                int shortestQueueServer = 0;
                int shortestQueueLength = Server.getqueuelength();
                for (int i = 0; i < Server.getServerNo(); i++) {
                    Server s = Server.getServer(i);
                    if (s.getcurrQueueSize() < shortestQueueLength) {
                        shortestQueueLength = s.getcurrQueueSize();
                        shortestQueueServer = i;
                    }
                }
                Server s = Server.getServer(shortestQueueServer);
                if (s.hasQueueSpace(next)) {
                    s.triesToServe(next);
                    done = true;
                }
            }
            /*
             * if there is no idle server, the customer scans
             * the servers from 1 to k, and waits at the first
             * busy server without any waiting customer
             */
            if (!done) {
                if (Random.gettotalServers() < 1) { // no servers present
                    if (next.getstate() == "leaves") {
                        Customer.remove(next);
                    } else {
                        next.leaves();
                        Server.addCustomersLeft(next);
                    }
                }
                for (Server s: Server.getServers()) {
                    if (s.hasQueueSpace(next) || s.getlastServer()) {
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
    }
}