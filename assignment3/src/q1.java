import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;

public class q1 {
    public static void main(String[] args) {
        Scanner Scan = new Scanner(System.in);
        int t = Scan.nextInt();
        for(int i=0;i<t;i++){
            int q= Scan.nextInt(), n= Scan.nextInt(), k= Scan.nextInt();
            Vector<question> questions = new Vector<>();
            for(int j=0;j<q;j++){
                questions.add(new question(Scan.next(), Scan.nextInt()));
            }
            Vector<student> students = new Vector<>();
            for(int j=0;j<n;j++){
                students.add(new student(Scan.next(), Scan.next()));
            }
            for(int j=0;j<n;j++){
                //input order of entrance into exam room
            }
        }
    }
}

class question {
    private String question;
    private Integer ID;

    public question(String question, Integer ID) {
        this.question = question;
        this.ID = ID;
    }
}

class student {
    private String rollNumber;
    private String name;

    public student(String rollNumber, String name) {
        this.rollNumber = rollNumber;
        this.name = name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public int getRollNumberAsInteger(){
        return Integer.parseInt(rollNumber,3, rollNumber.length(),10);
    }
}

class SortByRoll implements Comparator<student>{
    public int compare(student a, student b){
     return  a.getRollNumberAsInteger()-b.getRollNumberAsInteger();
    }
}