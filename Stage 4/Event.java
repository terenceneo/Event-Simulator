package cs2030.simulator;

import java.util.PriorityQueue;

public class Event {
    public static PriorityQueue<Event> events
        = new PriorityQueue<>(10000, new EventComparator());
    public double time;
    public int index;
    public String text;
    public int type;

    public Event (double time, int index, String text, int type) {
        this.time = time;
        this.index = index;
        this.text = text;
        this.type = type;
        addEvent(this);
    }

    public static void addEvent(Event event) {
        events.add(event);
    }

    public static boolean hasEvents() {
        return (events.size() > 0);
    }

    public static void printEvent() {
        System.out.println(events.poll().text);
    }
}