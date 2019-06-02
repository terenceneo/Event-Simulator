import cs2030.simulator.Server;
import cs2030.simulator.Random;
import cs2030.simulator.Event;
import cs2030.simulator.Simulator;
import java.util.Scanner;

class Main {
    public static void main(String[] args) { 
        Scanner sc = new Scanner(System.in);
        
        Random.setseed(sc.nextInt());
        Random.setserverNo(sc.nextInt());
        Random.setNself(sc.nextInt());
        Server.setqueuelength(sc.nextInt());
        Random.setcusNo(sc.nextInt());
        Random.setlambda(sc.nextDouble());
        Random.setmu(sc.nextDouble());

        Random.setrestingRate(sc.nextDouble());
        Server.setpr(sc.nextDouble());

        Random.setpg(sc.nextDouble());

        Random.createRandomGenerator();
        Random.generate();

        // System.out.println(Random.seed + " " + Random.serverNo + " " +
        // Server.queuelength + " " + Random.cusNo + " " + Random.lambda + " " + 
        // Random.mu);
        
        Simulator.run();

        while (Event.hasEvents()) {
            Event.printEvent();
        }
        System.out.println("[" + String.format("%.3f", Server.getAveWaitTime()) + " " + 
            Server.getcustomersServed() + " " + Server.getcustomersLeft() + "]");
    }
}
