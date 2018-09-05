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

    public course(String code,String name,String instructor,int priority, int numberOfSlots ){
        this.code=code;
        this.name=name;
        this.instructor=instructor;
        this.priority=priority;
        this.numberOfSlots=numberOfSlots;
    }

    public void addSlot(int duration, String dayPrefs, String timePrefs) {
        slots.add(new slot(duration, dayPrefs, timePrefs));
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public slot dequeue(){
        return slots.remove();
    }

    public boolean isEmpty(){
        if(slots.peek()==null){
            return true;
        } else {
            return false;
        }
    }
}

class sortByPriority implements Comparator<course>
{
    // Used for sorting in ascending order of priority
    public int compare(course a, course b)
    {
        return a.getPriority() - b.getPriority();
    }
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
