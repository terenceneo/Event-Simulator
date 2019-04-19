package cs2030.simulator;

import java.util.Comparator;

public class CusCompare implements Comparator<Customer> {
    @Override
    public int compare(Customer c1, Customer c2) {
        if (c1.time < c2.time) {
            return -1;
        } else if (c1.time > c2.time) {
            return 1;
        } else if (c1.index < c2.index) { 
            return -1;
        } else if (c1.index > c2.index) {
            return 1;
        } else {
            return 0;
        }
    }
}
