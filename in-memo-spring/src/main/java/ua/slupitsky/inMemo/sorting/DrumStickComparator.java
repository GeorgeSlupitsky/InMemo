package ua.slupitsky.inMemo.sorting;

import ua.slupitsky.inMemo.models.mongo.DrumStick;

import java.util.Comparator;

public class DrumStickComparator implements Comparator<DrumStick> {

    @Override
    public int compare(DrumStick o1, DrumStick o2) {
        if (o1.getBand().compareTo(o2.getBand()) == 0){
            if (o1.getDrummerName().compareTo(o2.getDrummerName()) == 0){
                return o1.getDate().compareTo(o2.getDate());
            }
            return o1.getDrummerName().compareTo(o2.getDrummerName());
        }
        return o1.getBand().compareTo(o2.getBand());
    }

}
