package cs2030.simulator;

import java.util.PriorityQueue;

public class Event {
    public static PriorityQueue<Event> events = new PriorityQueue<>(10000, new EventComparator());
    public int time;
    public String 

    public static void addEvent(Person event) {
        events.add(event);
    }

    public static boolean hasEvents() {
        return (events.size() > 0);
    }

    public static void printEvent() {
        System.out.println(events.poll());
    }
}