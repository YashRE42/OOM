import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class q1 {
    public static void main(String[] args) {
        Integer n;
        Scanner s = new Scanner(System.in);
        n = s.nextInt();
        students students = new students();

        for( Integer i=0; i<n; i++){
            students.add(new student(s.next(),s.next(),s.nextInt()));
        }

        for(Integer i=0; i<n; i++){
            student student = students.findByRoll(s.next());
            Integer k=s.nextInt();
            students friends = new students();
            for(Integer j=0; j<k; j++) {
                friends.add(students.findByRoll(s.next()));
            }
            student.setFriends(friends);
        }
        students.giveSweets();
        students.sortAll();
        System.out.print(students);
    }
}

class students implements Iterable<student> {
    private Vector<student> students;

    public students() {
        this.students = new Vector<>();
    }

    void add(student student){
        students.add(student);
    }

    public  student findByRoll(String Roll){
        for(student student:students){
            if(student.getRoll().compareTo(Roll)==0){
                return student;
            }
        }
        return null;
    }

    @Override
    public Iterator<student> iterator() {
        return students.iterator();
    }

    void giveSweets(){
        for(student student:students){
            student.giveSweets();
        }
    }

    void sort(){
        students.sort(new sortByRoll());
    }

    void sortAll(){
        this.sort();
        for(student student:students){
            student.sortFriends();
        }
    }

    @Override
    public String toString() {
        String result="";
        for(student student:students){
            result+=student;
        }
        return result;
    }
}

class student{
    private String roll;
    private String name;
    private Integer numberOfSweetsToGive;
    private Integer numberOfSweetsReceived;
    private students friends;
    private students friendsThatGaveSweets;

    public student(String roll, String name, Integer numberOfSweetsToGive) {
        this.friendsThatGaveSweets=new students();
        this.roll = roll;
        this.name = name;
        this.numberOfSweetsToGive = numberOfSweetsToGive;
        this.numberOfSweetsReceived = 0;
    }

    void givePersonASweet(student friendThatGaveSweet){
        //accepts sweet from friendThatGaveSweet
        this.numberOfSweetsReceived++;
        this.friendsThatGaveSweets.add(friendThatGaveSweet);
    }

    void giveSweets(){
        for(student friend:friends){
            if(this.numberOfSweetsToGive>0) {
                friend.givePersonASweet(this);
                this.numberOfSweetsToGive--;
            }
        }
    }

    void sortFriends(){
        this.friendsThatGaveSweets.sort();
    }

    public void setFriends(students friends) {
        this.friends = friends;
    }

    public String getRoll() {
        return roll;
    }

    @Override
    public String toString() {
        String result= "";
        result+=roll+ " " + numberOfSweetsReceived+"\n";
        for(student friend:friendsThatGaveSweets){
            result+=friend.getRoll()+"\n";
        }
        return result;
    }
}

class sortByRoll implements Comparator<student>
{
    // Used for sorting in ascending order of roll number
    public int compare(student a, student b)
    {
        return (a.getRoll()).compareTo((b.getRoll()));
    }
}