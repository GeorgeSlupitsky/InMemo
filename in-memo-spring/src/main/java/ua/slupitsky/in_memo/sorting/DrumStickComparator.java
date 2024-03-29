package ua.slupitsky.in_memo.sorting;

import ua.slupitsky.in_memo.entities.DrumStick;

import java.util.Comparator;

public class DrumStickComparator implements Comparator<DrumStick> {

    @Override
    public int compare(DrumStick o1, DrumStick o2) {
        if (o1.getBand().compareTo(o2.getBand()) == 0){
            if (o1.getDate().compareTo(o2.getDate()) == 0){
                return o1.getDrummerName().compareTo(o2.getDrummerName());
            }
            return o1.getDate().compareTo(o2.getDate());
        }
        return o1.getBand().toLowerCase().compareTo(o2.getBand().toLowerCase());
    }

}
