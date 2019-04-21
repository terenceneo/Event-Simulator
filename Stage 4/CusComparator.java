package cs2030.simulator;

import java.util.Comparator;

public class CusComparator implements Comparator<Customer> {
    @Override
    public int compare(Customer c1, Customer c2) {
        if (c1.gettime() < c2.gettime()) {
            return -1;
        } else if (c1.gettime() > c2.gettime()) {
            return 1;
        } else if (c1.getindex() < c2.getindex()) { 
            return -1;
        } else if (c1.getindex() > c2.getindex()) {
            return 1;
        } else {
            return 0;
        }
    }
}
