import java.util.*;

public class labtest1q2 {
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
            int s;
            s=Scan.nextInt();
            students students = new students();
            for(int j=0;j<s;j++){
                students.addStudent(Scan.next(), Scan.next(), Scan.next(), Scan.next());
            }
            int r;
            r=Scan.nextInt();
            for(int j=0;j<r;j++) {
                Effervesence.register(Scan.next(), Scan.next(), students);
            }
            int q;
            System.out.print(Effervesence);
            q=Scan.nextInt();
            for(int j=0;j<q;j++){
//                System.out.print(students.get(students.indexOfStudent(Scan.next())).toStri);
                System.out.print(students.get(students.indexOfStudent(Scan.next())).query());
            }
        }
    }
}

class Effervesence{
    private Vector<event> events;
    private Vector<day> days;

    void addEvent(String ID, String name, String description, Integer day, String timeOfStart, Integer duration){
        this.events.add(new event(ID, name, description, day, timeOfStart, duration));
    }

    void insertEvent(event event){
        this.events.add(event);
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
            if(i!=days.size()-1)
                result+="\n";
        }
        return result;
    }

    public String stringify(){
        String result="";

        for(int i=0;i<days.size();i++) {
            result += "Day " + days.get(i).getDay() +"\n" + days.get(i).stringify();
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

    void register(String rollNumber, String eventID, students students){
        events.get(indexOfEvent(eventID)).register(students.get(students.indexOfStudent(rollNumber)));
        students.get(students.indexOfStudent(rollNumber)).register(events.get(indexOfEvent(eventID)));
    }

    int indexOfEvent(String eventID){
        for(int i=0;i<events.size();i++){
            if(events.get(i).getID().compareTo(eventID)==0){
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
            if(i!=events.size()-1)
                    result+="\n";
        }
        return result;
    }

    public String stringify() {
        String result="";
        for(int i=0;i<events.size();i++){
            result+=events.get(i).stringify();
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
    private students participants;

    event(String ID, String name, String description, Integer day, String timeOfStart, Integer duration){
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.day = day;
        this.timeOfStart = timeOfStart;
        this.duration = duration;
        this.participants= new students();
    }

    void register(student student){
            participants.insertStudent(student);
    }

    @Override
    public String toString() {
        return timeOfStart+"("+duration+") "+ID+" "+name+" "+description+"\n"+participants;
    }

    public String stringify() {
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

class sortDaysByDay implements Comparator<day> {
    // Used for sorting in ascending order of day
    public int compare(day a, day b)
    {
        return a.getDay() - b.getDay();
    }
}

class sortEventsByTime implements Comparator<event> {
    // Used for sorting in ascending order of time
    public int compare(event a, event b)
    {
        if(a.getTimeOfStart().compareTo(b.getTimeOfStart())==0){
            return a.getID().compareTo(b.getID());
        }
        return a.getTimeOfStart().compareTo(b.getTimeOfStart());
    }

}

class students{
    private Vector<student> students;

    student get(int i){
        return students.get(i);
    }

    int indexOfStudent (String rollNumber){
        for(int i=0;i<students.size();i++){
            if(students.get(i).getRollNumber().compareTo(rollNumber)==0){
                return i;
            }
        }
        return -1;
    }

    students(){
        students=new Vector<>();
    }

    void insertStudent(student student){
        students.add(student);
    }

    void addStudent(String rollNumber, String name, String levelOfStudy, String course){
        students.add(new student(rollNumber, name, levelOfStudy, course));
    }

    @Override
    public String toString() {
        String result="";
        for(int i=0;i<students.size();i++){
            result+=students.get(i).getRollNumber()+" "+students.get(i).getName()+" "+students.get(i).getLevelOfStudy()+" "+students.get(i).getCourse();
            if(i!=students.size()-1){
                result+="\n";
            }
        }
        return result;
    }
}

class student{
    private String rollNumber; 
    private String name;
    private String levelOfStudy; 
    private String course;
    private Vector<event> events;

    student(String rollNumber, String name, String levelOfStudy, String course){
        this.rollNumber = rollNumber;
        this.name = name;
        this.levelOfStudy = levelOfStudy;
        this.course = course;
        this.events = new Vector<>();
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String getLevelOfStudy() {
        return levelOfStudy;
    }

    void register(event event){
        this.events.add(event);
    }

    @Override
    public String toString() {
        return this.getRollNumber()+" "+this.getName()+" "+this.getLevelOfStudy()+" "+this.getCourse();
    }

    String query(){
        Effervesence schedule = new Effervesence();
        for(int j=0;j<events.size();j++){
            schedule.insertEvent(events.get(j));
        }
        schedule.order();
        return schedule.stringify();
    }
}
