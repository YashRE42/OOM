import java.util.*;

public class labtest1q1 {
    public static void main(String[] args) {
        int t;
        Scanner Scan = new Scanner(System.in);
        t=Scan.nextInt();
        for(int i=0;i<t;i++){
            int n;
            n=Scan.nextInt();
            Effervesence Effervesence  = new Effervesence();
            for(int j=0;j<n;j++){
                Effervesence.addEvent(Scan.next(),Scan.next(),Scan.next(),Scan.nextInt(),Scan.next(),Scan.nextInt());
            }
            Effervesence.order();
            System.out.print(Effervesence);
        }
    }
}

class Effervesence{
    private Vector<event> events;
    private Vector<day> days;

    void addEvent(String ID, String name, String description, Integer day, String timeOfStart, Integer duration){
        this.events.add(new event(ID, name, description, day, timeOfStart, duration));
    }

    void order(){
        for(int i=0;i<events.size();i++){
            Integer indexOfDay = indexOfDay(events.get(i).getDay());
            if(indexOfDay!=-1){
                days.get(indexOfDay).addEvent(events.get(i));
            }   else    {
                days.add(new day(events.get(i).getDay()));
                indexOfDay = indexOfDay(events.get(i).getDay());
                days.get(indexOfDay).addEvent(events.get(i));
            }
        }
        days.sort(new sortDaysByDay());
        for(int i=0;i<days.size();i++){
            days.get(i).sort();
        }
    }

    @Override
    public String toString(){
        String result="";

        for(int i=0;i<days.size();i++) {
            result += "Day " + days.get(i).getDay() +"\n" + days.get(i);
        }
        return result;
    }

    Integer indexOfDay(Integer day){
        for(int i=0;i<days.size();i++){
            if(days.get(i).getDay()==day){
                return i;
            }
        }
        return -1;
    }

    Effervesence(){
        events=new Vector<>();
        days=new Vector<>();
    }
}

class day{
    private Integer day;
    private Vector<event> events;

    day(Integer day){
        this.day=day;
        events=new Vector<>();
    }

    void addEvent(event event){
        this.events.add(event);
    }

    @Override
    public String toString() {
        String result="";
        for(int i=0;i<events.size();i++){
            result+=events.get(i);
        }
        return result;
    }

    public Integer getDay() {
        return day;
    }

    void sort(){
        events.sort(new sortEventsByTime());
    }
}

class event {
   private String ID;
   private String name;
   private String description;
   private Integer day;
   private String timeOfStart;
   private Integer duration;
   
   event(String ID, String name, String description, Integer day, String timeOfStart, Integer duration){
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.day = day;
        this.timeOfStart = timeOfStart;
        this.duration = duration;
   }

    @Override
    public String toString() {
        return timeOfStart+"("+duration+") "+ID+" "+name+" "+description+"\n";
    }

    public Integer getDay() {
        return day;
    }

    public String getID() {
        return ID;
    }

    public String getTimeOfStart() {
        return timeOfStart;
    }
}

class sortDaysByDay implements Comparator<day>
{
    // Used for sorting in ascending order of day
    public int compare(day a, day b)
    {
        return a.getDay() - b.getDay();
    }
}
class sortEventsByTime implements Comparator<event>
{
    // Used for sorting in ascending order of time
    public int compare(event a, event b)
    {
        if(a.getTimeOfStart().compareTo(b.getTimeOfStart())==0){
            return a.getID().compareTo(b.getID());
        }
        return a.getTimeOfStart().compareTo(b.getTimeOfStart());
    }
}