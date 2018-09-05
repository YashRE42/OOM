import java.io.*;
import java.util.*;

public class q1 {
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
