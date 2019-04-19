package cs2030.simulator;

public class Random {
    public static int seed;
    public static int serverNo;
    public static int cusNo;
    public static double lambda;
    public static double mu;
    public static RandomGenerator rng;

    public static void createRandomGenerator() {
        rng = new RandomGenerator(seed, lambda, mu);
    }

    /**
     * Generates Servers and Customers.
     */
    public static void generate() {
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

    public static double genServiceTime() {
        return rng.genServiceTime();
    }
}