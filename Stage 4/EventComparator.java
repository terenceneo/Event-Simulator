package cs2030.simulator;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {
    @Override
    public int compare(Event c1, Event c2) {
        if (c1.gettime() < c2.gettime()) {
            return -1;
        } else if (c1.gettime() > c2.gettime()) {
            return 1;
        } else if (c1.gettype() < c2.gettype()) {
            return -1;
        } else if (c1.gettype() > c2.gettype()) {
            return 1;
        } else if (c1.getindex() < c2.getindex()) { 
            return -1;
        } else if (c1.getindex() > c2.getindex()) {
            return 1;
        } else if (c1.getcounter() < c2.getcounter()) { 
            return -1;
        } else if (c1.getcounter() > c2.getcounter()) {
            return 1;   
        } else {
            return 0;
        }
    }
}
