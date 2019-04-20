package cs2030.simulator;

public class Random {
    public static int seed;
    public static int serverNo;
    public static int cusNo;
    public static int Nself;
    public static double lambda;
    public static double mu;
    public static double restingRate;
    public static RandomGenerator rng;
    public static int totalServers;
    public static double pg;

    public static void createRandomGenerator() {
        rng = new RandomGenerator(seed, lambda, mu, restingRate);
    }

    /**
     * Generates Servers and Customers.
     */
    public static void generate() {
        // constructing human servers
        totalServers = serverNo + Nself;
        for (int i = 0; i < serverNo; i++) {
            Server s = new Server(i + 1, "server");
            if (i + 1 == totalServers) {
                s.isLast();
            }
            Server.add(s);
        }
        // constructing self-checkouts
        for (int i = 0; i < Nself; i++) {
            Server s = new Server(i + 1 + serverNo, "self-check");
            if (i + 1 + serverNo == totalServers) {
                s.isLast();
            }
            Server.add(s);
        }

        // constructing customers
        double time = 0;
        for (int i = 0; i < cusNo; i++) {
            String type = (genCustomerType() < pg) ? "(greedy)" : "";
            Customer c = new Customer(time, type);
            Customer.add(c);
            time += rng.genInterArrivalTime();
        }
    }

    public static double genServiceTime() {
        return rng.genServiceTime();
    }

    public static double genRandomRest() {
        return rng.genRandomRest();
    }

    public static double genRestPeriod() {
        return rng.genRestPeriod();
    }

    public static double genCustomerType() {
        return rng.genCustomerType();
    }
}