package cs2030.simulator;

import java.util.PriorityQueue;

public class Event {
    public static PriorityQueue<Event> events
        = new PriorityQueue<>(10000, new EventComparator());
    public double time;
    public int index;
    public String text;
    public int type;
    public int counter;

    public Event (double time, int index, String text, int type, int counter) {
        this.time = time;
        this.index = index;
        this.text = text;
        this.type = type;
        this.counter = counter;
        addEvent(this);
    }

    public static void addEvent(Event event) {
        events.add(event);
    }

    public static boolean hasEvents() {
        return (events.size() > 0);
    }

    public static void printEvent() {
        Event event = events.poll();
        // System.out.println("  time: " + String.format("%.3f", event.time));
        // System.out.println("  type: " + event.type);
        // System.out.println("  index: " + event.index);
        System.out.println(event.text);
    }
}