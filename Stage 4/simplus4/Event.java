package cs2030.simulator;

import java.util.PriorityQueue;

public class Event {
    private static PriorityQueue<Event> events
        = new PriorityQueue<>(10000, new EventComparator());
    private double time;
    private int index;
    private String text;
    private int type;
    private int counter;

    /**
     * Constructs an Event.
     * @param time time of event
     * @param index index of Customer or Server
     * @param text Event contents to be printed
     * @param type Sequence of events: 0 for Server rest, 1 for Customer, 2 for Server back
     * @param counter Event's squence of input as a tie breakers for same time same object events
     */
    public Event(double time, int index, String text, int type, int counter) {
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

    public double gettime() {
        return this.time;
    }

    public int getindex() {
        return this.index;
    }

    public int gettype() {
        return this.type;
    }
        
    public int getcounter() {
        return this.counter;
    }

    /**
     * Prints top event and removes event from the list of events.
     */
    public static void printEvent() {
        Event event = events.poll();
        // System.out.println("  time: " + String.format("%.3f", event.time));
        // System.out.println("  type: " + event.type);
        // System.out.println("  index: " + event.index);
        System.out.println(event.text);
    }
}