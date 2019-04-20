package cs2030.simulator;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {
    @Override
    public int compare(Event c1, Event c2) {
        if (c1.time < c2.time) {
            return -1;
        } else if (c1.time > c2.time) {
            return 1;
        } else if (c1.type < c2.type) {
            return -1;
        } else if (c1.type > c2.type) {
            return 1;
        } else if (c1.index < c2.index) { 
            return -1;
        } else if (c1.index > c2.index) {
            return 1;
        } else if (c1.counter < c2.counter) { 
            return -1;
        } else if (c1.counter > c2.counter) {
            return 1;   
        } else {
            return 0;
        }
    }
}
