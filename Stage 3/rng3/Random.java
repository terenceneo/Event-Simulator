package cs2030.simulator;

public class Random {

    /**
     * Create Servers and Customers.
     * @param seed base seed for the RandomGenerator object
     * @param serverNo number of servers
     * @param cusNo number of customers (or the number of arrival events) to simulate
     * @param lambda arrival rate
     * @param mu service rate
     */
    public void generate(int seed, int serverNo, int cusNo, double lambda, double mu) {
        RandomGenerator rng = new RandomGenerator(seed, lambda, mu);
        
        for (int i = 0; i < serverNo; i++) {
            Server s = new Server(i + 1);
            if (i == serverNo - 1) {
                s.isLast();
            }
            Server.add(s);
        }

        double time = 0;
        for (int i = 0; i < cusNo; i++) {
            Customer c = new Customer(time);
            Customer.add(c);
            time += rng.genInterArrivalTime();
        }
    }
}