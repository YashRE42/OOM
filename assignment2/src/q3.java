import java.util.*;

public class q2 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int testCases=s.nextInt();
        for(int i=0;i<testCases;i++){
            int numberOfBatches=s.nextInt();
            Vector<String> batches = new Vector();
            for(int j=0;j<numberOfBatches;j++){
                batches.add(s.next());
            }

            int numberOfFaculty=s.nextInt();
            Vector<String> faculty = new Vector();
            for(int j=0;j<numberOfFaculty;j++){
                faculty.add(s.next());
            }

            int numberOfCourses=s.nextInt();
            Vector<course> courses = new Vector();

            for(int j=0; j<numberOfCourses; j++){
                int numberOfSlots;
                String a;
                courses.add(new course(a=s.next(), s.next(), s.next(), s.nextInt(), s.next(), numberOfSlots= s.nextInt()));
                for(int k=0;k<numberOfSlots;k++){
//                    if(a.compareTo("not44")==0){
//                        System.out.print("not44 slot sent");
//                    }
                    courses.get(j).addSlot(s.nextInt(), s.next(), s.next());
                }
            }
            courses.sort(new sortByPriority());
//            boolean slotLeft=true;
//            while(slotLeft){
//                for(int j=0; j<numberOfCourses; j++){
//                    course x = courses.get(j);
//                    if(!x.isEmpty()) {
//                        System.out.println(x.getName() + " " + x.dequeue().getDuration());
//                    }
//                }
//            }
            timeTables timeTables = new timeTables(batches,faculty);
            timeTables.createTimetables(courses);
            System.out.print(timeTables);
//            System.out.println("done");
        }
    }
}

class course {
    private  String batch;
    private String code;
    private String name;
    private String instructor;
    private int priority;
    private int numberOfSlots;

    private Queue<slot> slots = new LinkedList();

    public course(String code,String name,String instructor,int priority,String batch, int numberOfSlots ){
        this.code=code;
        this.name=name;
        this.instructor=instructor;
        this.priority=priority;
        this.numberOfSlots=numberOfSlots;
        this.batch=batch;
    }

    public void addSlot(int duration, String dayPrefs, String timePrefs) {
        slots.add(new slot(duration, dayPrefs, timePrefs));
    }

    public String getBatch() {
        return batch;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getInstructor() {
        return instructor;
    }

    public slot dequeue(){
        return slots.poll();
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

class sortByCode implements Comparator<lesson>
{
    // Used for sorting in ascending order of priority
    public int compare(lesson a, lesson b)
    {
        return a.getCourse().getCode().compareTo(b.getCourse().getCode());
    }
}

class slot{
    private int duration;
    private String dayPrefs; //preferences
    private String timePrefs; //preferences
    private Vector<Integer> abstractedTimePrefs; // first element (a(0)) is number of time preferences, next a(0) elements are time preferences
    private Vector<Integer> abstractedDayPrefs; // first element (a(0)) is number of day preferences, next a(0) elements are day preferences

    public slot(int duration, String dayPrefs, String timePrefs){
        this.duration=duration;
        this.dayPrefs=dayPrefs;
        this.timePrefs=timePrefs;
        abstractedTimePrefs=abstractTimePrefs(timePrefs);
        abstractedDayPrefs=abstractDayPrefs(dayPrefs);
    }

    public int getDuration() {
        return duration;
    }

    public String getDayPrefs() {
        return dayPrefs;
    }

    public String getTimePrefs() {
        return timePrefs;
    }

    public Vector<Integer> abstractTimePrefs(String timePrefs) {
        Vector<Integer> abstractedTimes = new Vector();
        abstractedTimes.add(0);
        if(!timePrefs.equals("NIL")){
            while(!timePrefs.equals("")) {
                abstractedTimes.set(0,abstractedTimes.get(0)+1);
                abstractedTimes.add(classifyTimeSlot(timePrefs.substring(0,4)));
                timePrefs = timePrefs.substring(4);
            }
        }
        return abstractedTimes;
    }

    public Vector<Integer> getAbstractedTimePrefs() {
        return abstractedTimePrefs;
    }

    public Vector<Integer> abstractDayPrefs(String dayPrefs) {
        Vector<Integer> abstractedDays = new Vector();
        abstractedDays.add(0);
        if(!dayPrefs.equals("NIL")){
            while(!dayPrefs.equals("")) {
                abstractedDays.set(0,abstractedDays.get(0)+1);
                abstractedDays.add(classifyDaySlot(dayPrefs.substring(0,1)));
                dayPrefs = dayPrefs.substring(1);
            }
        }
        return abstractedDays;
    }

    public Vector<Integer> getAbstractedDayPrefs() {
        return abstractedDayPrefs;
    }

    public int classifyTimeSlot(String Time){
        // 0= 9-10, 1= 10-11, 2= 11:15-12:15, 3= 12:15-1:15, 4= 3-4, 5= 4-5, and 6= 5-6.
        switch (Time){
            case "0900":return 0;
            case "1000":return 1;
            case "1115":return 2;
            case "1215":return 3;
            case "0300":return 4;
            case "0400":return 5;
            case "0500":return 6;
        }
        return 8888;
    }

    public int classifyDaySlot(String Day){
        // 0= M, 1= T, 2= W, 3= Th, 4= F;
        return Integer.parseInt(Day)-1;
    }
}

class timeTables{
    private Vector<timeTable> timeTables = new Vector();
    private Vector<timeTable> facultyTimeTables = new Vector();

    timeTables(Vector<String> batches,Vector<String> faculty){
        for(int i=0;i<batches.size();i++){
            timeTables.add(new timeTable(batches.get(i)));
        }
        for(int i=0;i<faculty.size();i++){
            facultyTimeTables.add(new timeTable(faculty.get(i)));
        }
    }

    public String toString() {
        String result="";
//        for(int i=0;i<timeTables.size();i++){
//            result+= timeTables.get(i).getID()+"\n";
//            result+= timeTables.get(i).toString();
//        }
        for(int i=0;i<facultyTimeTables.size();i++){
            result+= facultyTimeTables.get(i).getID()+"\n";
            result+= facultyTimeTables.get(i).toString();
        }
        return result;

    }
    class loadCompare implements Comparator<day>{

        @Override
        public int compare(day day1, day day2) {
            return day1.getLoad().compareTo(day2.getLoad());
        }
    }

    int indexOfBatch(String searchParam){
        for(int i=0;i<timeTables.size();i++){
            if(searchParam.compareTo(timeTables.get(i).getID())==0){
                return i;
            }
        }
        return -1;
    }

    int indexOfFaculty(String searchParam){
        for(int i=0;i<facultyTimeTables.size();i++){
            if(searchParam.compareTo(facultyTimeTables.get(i).getID())==0){
                return i;
            }
        }
        return -1;
    }
    //*//
    public void createTimetables(Vector<course> courses) {
        boolean allSlotsAssigned = false;
        while(!allSlotsAssigned) {
            allSlotsAssigned=true;
            for (int i = 0; i < courses.size(); i++) {
                slot current = courses.get(i).dequeue();
//                    System.out.print(courses.get(i).getCode()+"\n");
                if (current != null) {
//                    System.out.println("Sending slot: " + courses.get(i).getName() + " for " + current.getDuration());
                    AssignSlotChoiseDayChoiceTime(current, courses.get(i));
                    allSlotsAssigned=false;
                }
            }
        }
    }
    //*//
    public void AssignSlotChoiseDayChoiceTime(slot current, course course) {
//        if(course.getCode().compareTo("not44")==0){
//            System.out.print("____\nget not44\n____\n");
//        }
        Vector<Integer> dayPrefs = current.getAbstractedDayPrefs();
        Vector<Integer> timePrefs = current.getAbstractedTimePrefs();
        Integer duration = current.getDuration();
        boolean assigned = false;
        ArrayList<day> sortedDays = new ArrayList<day>();
        ArrayList<day> selectedDays = new ArrayList<day>();
        if(timePrefs.get(0)!=0)
            for (int j = 1; j < timePrefs.size() && !assigned; j++) {
                if (dayPrefs.get(0) != 0)
                    for (int i = 1; i < dayPrefs.size() && !assigned; i++) {
                        if (isSlotAvailable(dayPrefs.get(i), timePrefs.get(j), duration, course)) {
                            timeTables.get(indexOfBatch(course.getBatch())).takeSlot(dayPrefs.get(i), timePrefs.get(j), course, duration);
                            facultyTimeTables.get(indexOfFaculty(course.getInstructor())).takeSlot(dayPrefs.get(i), timePrefs.get(j), course, duration);
                            assigned = true;
                        //    System.out.println("timetable:"+indexOfBatch(course.getBatch())+" day :" + dayPrefs.get(i) + " time:" + timePrefs.get(j) + " slot available for " + course.getName() +" for "+duration);
                        }
                    }
                if (!assigned) {
//                    System.out.println("trying for: " + course.getName());
                    //assign by load
//                    System.out.println("printing load");
                    for (int i = 0; i < 5; i++) {
                        sortedDays.add(timeTables.get(indexOfBatch(course.getBatch())).getDays().get(i));
                        //timeTables.get(indexOfBatch(course.getBatch())).
//                        System.out.println(i + " " + sortedDays.get(i).getLoad());
                    }
                    int i = 0;
                    sortedDays.sort(new loadCompare());
                    for (int l = 0; l < 5; l++) {
                        int lowestLoad = sortedDays.get(l).getLoad();
                        for (i = 0; i < 5; i++) {
                            if (sortedDays.get(i).getLoad() == lowestLoad) {
                                selectedDays.add(sortedDays.get(i));
                            }
                        }
                        ;
//                        System.out.println("lowest load is: " + lowestLoad);
//                    selectedDays.forEach(day -> System.out.println(day.getID()));
                        for (i = 0; i < selectedDays.size() && !assigned; i++) {
                            if (isSlotAvailable(selectedDays.get(i).getID(), timePrefs.get(j), duration, course)) {
                                timeTables.get(indexOfBatch(course.getBatch())).takeSlot(selectedDays.get(i).getID(), timePrefs.get(j), course, duration);
                                facultyTimeTables.get(indexOfFaculty(course.getInstructor())).takeSlot(selectedDays.get(i).getID(), timePrefs.get(j), course, duration);
                                assigned = true;
                            //    System.out.println("timetable:"+indexOfBatch(course.getBatch())+" day :" + selectedDays.get(i).getID() + " time:" + timePrefs.get(j) + " slot available for " + course.getName() + " for " + duration + " assigned by load with time pref");
                            }
//                    System.out.println("trying day: "+selectedDays.get(i).getID()+" time: "+ timePrefs.get(j)+ " for: "+ course.getName());
                        }
                    }
//                    sortedDays=null;
//                    selectedDays=null;
                }

                if(!assigned){
                    //sort by day
                    for(int i=0;i<5 && !assigned;i++){
                        if (isSlotAvailable(i, timePrefs.get(j), duration, course)) {
                            timeTables.get(indexOfBatch(course.getBatch())).takeSlot(i, timePrefs.get(j), course, duration);
                            facultyTimeTables.get(indexOfFaculty(course.getInstructor())).takeSlot(i, timePrefs.get(j), course, duration);
                            assigned = true;
                        //    System.out.println("timetable:"+indexOfBatch(course.getBatch())+" day :" +i + " time:" + timePrefs.get(j) + " slot available for " + course.getName() +" for "+duration);
                        }
//                    System.out.println("trying day: "+i+" time: "+ timePrefs.get(j)+ " for: "+ course.getName());
                    }
                }
            }
        if(!assigned && dayPrefs.get(0)!=0)//at this point, no slots with given time pref are available
            for (int i = 1; i < dayPrefs.size() && !assigned; i++) {
                for (int j = 0; j < 7 && !assigned; j++) {
                    if (isSlotAvailable(dayPrefs.get(i), j, duration, course)) {
                        timeTables.get(indexOfBatch(course.getBatch())).takeSlot(dayPrefs.get(i), j, course, duration);
                        facultyTimeTables.get(indexOfFaculty(course.getInstructor())).takeSlot(dayPrefs.get(i), j, course, duration);
                        assigned = true;
                    //    System.out.println("timetable:"+indexOfBatch(course.getBatch())+" day :" + dayPrefs.get(i) + " time:" + j + " slot available for " + course.getName() +" for "+duration);
                    }
                }
            }
        if(!assigned){
            sortedDays = new ArrayList<day>();
            selectedDays = new ArrayList<day>();
            //all times on low load day
            for(int i=0; i < 5; i++){
//                System.out.print(course.getBatch());
                if(indexOfBatch(course.getBatch())!=-1 && timeTables.get(indexOfBatch(course.getBatch())).getDays().get(i)!=null)
                    sortedDays.add(timeTables.get(indexOfBatch(course.getBatch())).getDays().get(i));
            }
            int i=0;
            sortedDays.sort(new loadCompare());
            for (int l = 0; l < 5; l++) {
                int lowestLoad = sortedDays.get(l).getLoad();
                for (i = 0; i < 5; i++) {
                    if (sortedDays.get(i).getLoad() == lowestLoad) {
                        selectedDays.add(sortedDays.get(i));
                    }
                }
//                    selectedDays.forEach(day -> System.out.println(day.getID()));
                for (i = 0; i < selectedDays.size() && !assigned; i++) {
                    for (int j = 0; j < 7 && !assigned; j++) {
                        if (isSlotAvailable(selectedDays.get(i).getID(), j, duration, course)) {
                            timeTables.get(indexOfBatch(course.getBatch())).takeSlot(selectedDays.get(i).getID(), j, course, duration);
                            facultyTimeTables.get(indexOfFaculty(course.getInstructor())).takeSlot(selectedDays.get(i).getID(), j, course, duration);
                            assigned = true;
//                            System.out.println("timetable:" + indexOfBatch(course.getBatch()) + " day :" + selectedDays.get(i).getID() + " time:" + j + " slot available for " + course.getName() + " for " + duration + " assigned by load with no pref");
                        }
//                        System.out.println("trying day: " + selectedDays.get(i).getID() + " time: " + j + " for: " + course.getName());
                    }
                }
            }
        }
        if(!assigned){
            for(int i=0; i<5 && !assigned; i++) {
                for (int j = 0; j < 7 && !assigned; j++) {
                    if (isSlotAvailable(i, j, duration, course)) {
                        timeTables.get(indexOfBatch(course.getBatch())).takeSlot(i, j, course, duration);
                        facultyTimeTables.get(indexOfFaculty(course.getInstructor())).takeSlot(i, j, course, duration);
                        assigned = true;
                        System.out.println("timetable:"+indexOfBatch(course.getBatch())+" day :" + i + " time:" + j + " slot available for " + course.getName() +" for "+duration);
                    }
//                    System.out.println("trying day: "+selectedDays.get(i).getID()+" time: "+ timePrefs.get(j)+ " for: "+ course.getName());
                }
            }
        }
        if(!assigned){
            //saturday
            timeTables.get(indexOfBatch(course.getBatch())).pushSlot(course);
            facultyTimeTables.get(indexOfFaculty(course.getInstructor())).pushSlot(course);
            assigned=true;
//            System.out.println("saturday assigned for " + course.getName());
        }
//        System.out.println(timeTables);
//        System.out.println("Is this an infinite loop?");
    }
    //*//
    public boolean isSlotAvailable(Integer day,Integer time,Integer duration, course course){
        boolean result=true;
        boolean hasBreak=false;
        if(
                time <= 1 && (time + duration - 1) > 1
                        ||
                        time <= 3 && (time + duration - 1) > 3
                                ||
                                time <= 6 && (time + duration - 1) > 6

        )
            hasBreak=true;
        for(int i=0;i<duration && result;i++) {
//            System.out.println(time +" "+ duration);
            boolean isClassTaken = timeTables.get(indexOfBatch(course.getBatch())).getDays().get(day).getLesson(time+i).isAssigned();
            boolean isFacultyTaken = facultyTimeTables.get(indexOfFaculty(course.getInstructor())).getDays().get(day).getLesson(time+i).isAssigned();
//            boolean isFacultyTaken = false;
            result = result && !isFacultyTaken && !isClassTaken && !hasBreak;
        }
        return result;
    }
}

class timeTable {
    private Vector<day> days = new Vector();
    private Vector<lesson> saturday = new Vector();
    private int saturdayCount;
    private String ID;
    timeTable(String ID) {
        for (int i = 0; i < 5; i++) {
            days.add(new day(i));
        }
        //saturday
        saturdayCount=0;
        this.ID=ID;
//        for(int i=0;i<5;i++)
//        System.out.print(getDays().get(i)+" ");
    }

    public Vector<day> getDays() {
        return days;
    }

    public String getID() {
        return ID;
    }

    public int getSaturdayCount() {
        return saturdayCount;
    }

    public void raiseSaturdayCount(){
        saturdayCount++;
    }

//    public void assignSlot(slot current){
//        int count=0;
//        boolean moreThanOnePossibility=false;
//        Vector<Integer> timePrefs = current.getAbstractedTimePrefs();
//        if(timePrefs.get(0)!=0)
//            for(int x=1;x<timePrefs.size();x++) {
//                for (int j = 0; j < 5; j++) {
//                    if(!days.get(j).getLesson(timePrefs.get(x)).isAssigned()) {
//                        count++;
//                    }
//                }
//                if(count>0){
//                    moreThanOnePossibility=true;
//                    break;
//                }
//            }
//        if(moreThanOnePossibility){
//            System.out.println("first assigned");
//        } else {
//            System.out.println("first not assigned");
//        }
//    }

    public void takeSlot(Integer day,Integer time, course course,Integer duration){
        for(int i=0;i<duration;i++){
            days.get(day).getLesson(time+i).setCourse(course,duration);
            days.get(day).raiseLoad(1);
        }
    }
    public void pushSlot(course course){
        raiseSaturdayCount();
        saturday.add(new lesson(course));
    }

    public String toString()
    {
        String result="";
        for(int i=0;i<6;i++) {
            switch (i) {
                case 0:
                    result+="Monday\n";
                    break;
                case 1:
                    result+="Tuesday\n";
                    break;
                case 2:
                    result+="Wednesday\n";
                    break;
                case 3:
                    result+="Thursday\n";
                    break;
                case 4:
                    result+="Friday\n";
                    break;
                case 5:
                    result+="Saturday\n";
                    break;
            }
            int duration;
            if(i!=5) {//not saturday
                for (int j = 0; j < 7; j += duration) {
                    duration = 1;
                    if (days.get(i).getLesson(j).isAssigned()) {
                        if(days.get(i).getLesson(j).getDuration()!=1)
                            if (j < 6 && days.get(i).getLesson(j).getCourse() == days.get(i).getLesson(j + 1).getCourse()) {//&& ((j==1&&j+1==2)||(j==3&&3+1==4))?false:true
                                if (j < 5 && days.get(i).getLesson(j + 2).getCourse() == days.get(i).getLesson(j + 1).getCourse()) {// && ((j<=1&&j+2>=2)||(j==3&&3+1==4))?false:true
                                    duration = 3;
                                } else {
                                    duration = 2;
                                }
                            } else {
                                duration = 1;
                            }
                        if (j == 3 && duration > 1 || j == 1 && duration > 1) {
                            duration = 1;
                        }//prevents overlap from 12:15 to 1:15 into 3:00 onwards or from 10:00 to 11:00 into 11:15 onwards
                        if ( duration > 2 && j<4 ){
                            duration = 2;
                        }//prevents overlap from 9:00 to 11:00 into 12:15 onwards and similarly for 11:15 to 1:15 into 3:00 onwards

                        if(duration>2 && days.get(i).getLesson(j).getDuration()==2){
                            duration=2;
                        }
                        course course = days.get(i).getLesson(j).getCourse();
                        String courseDetails = course.getCode() + " " + course.getName() + " " + course.getInstructor();
                        result += timeDeAbstraction(j, true) + "-" + timeDeAbstraction(j + duration - 1, false) + " " + courseDetails + "\n";
                    }
                }
            } else {
                saturday.sort(new sortByCode());
                for(int k=0; k< getSaturdayCount();k++) {
//                    if(days.get(5).getLesson(k).isAssigned()) {
                    course course = saturday.get(k).getCourse();
                    result += course.getCode() + " " + course.getName() + " " + course.getInstructor() + "\n";
//                    result+=course;
//                    }
                }
            }
        }
        return result;
    }

    String timeDeAbstraction(int index, boolean isStartTimeIndex){
        if(isStartTimeIndex){
            switch (index){
                case 0: return "9:00";
                case 1: return "10:00";
                case 2: return "11:15";
                case 3: return "12:15";
                case 4: return "3:00";
                case 5: return "4:00";
                case 6: return "5:00";
            }
        }   else {
            switch (index){
                case 0: return "10:00";
                case 1: return "11:00";
                case 2: return "12:15";
                case 3: return "1:15";
                case 4: return "4:00";
                case 5: return "5:00";
                case 6: return "6:00";
            }
        }
        return "ERR";
    }
}

class day {
    private Vector<lesson> lessons = new Vector();
    private int load;
    private int ID;
    day(int day){
        ID=day;
        load = 0;
        for(int i=0;i<7;i++){
            lessons.add(new lesson(i));
        }
    }

    public void raiseLoad(int load) {
        this.load += load;
    }

    lesson getLesson (int i){
        return lessons.get(i);
    }

    public Integer getLoad() {
        return load;
    }

    public int getID() {
        return ID;
    }

}

class lesson {
    private course course;
    private int timeSlot;
    int duration;

    lesson(int i){
        course=null;
        timeSlot=i;
    }

    lesson(course course){
        this.course=course;
    }

    public void setCourse(course course, Integer duration) {
        this.course = course;
        this.duration = duration;
    }

    public course getCourse() {
        return course;
    }

    public int getDuration() {
        return duration;
    }

    boolean isAssigned(){
        if(course==null)
            return false;
        else
            return true;
    }
}