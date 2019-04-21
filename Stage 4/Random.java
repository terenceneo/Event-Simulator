package cs2030.simulator;

public class Random {
    private static int seed;
    private static int serverNo;
    private static int cusNo;
    private static int Nself;
    private static double lambda;
    private static double mu;
    private static double restingRate;
    private static double pg;

    private static RandomGenerator rng;
    private static int totalServers;

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

    public static int gettotalServers() {
        return totalServers;
    }

    public static void setseed(int i) {
        seed = i;
    }

    public static void setserverNo(int i) {
        serverNo = i;
    }

    public static void setcusNo(int i) {
        cusNo = i;
    }

    public static void setNself(int i) {
        Nself = i;
    }

    public static void setlambda(double i) {
        lambda = i;
    }

    public static void setmu(double i) {
        mu = i;
    }

    public static void setrestingRate(double i) {
        restingRate = i;
    }

    public static void setpg(double i) {
        pg = i;
    }
}