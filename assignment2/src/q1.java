import java.io.*;
import java.util.*;

public class q1 {
}

class course {
    private String code;
    private String name;
    private String instructor;
    private int priority;
    private int numberOfSlots;

    private Queue<slot> slots = new LinkedList();
}


class slot{
    private int duration;
    private String dayPrefs; //preferences
    private String timePrefs; //preferences
    public slot(int duration, String dayPrefs, String timePrefs){
        this.duration=duration;
        this.dayPrefs=dayPrefs;
        this.timePrefs=timePrefs;
    }

    public int getDuration() {
        return duration;
    }
}
