import java.util.Scanner;

public class q1 {
    public static void main(String[] args) {
        Scanner Scan = new Scanner(System.in);
        int t = Scan.nextInt();
        for(int i=0;i<t;i++){
            int q= Scan.nextInt(), n= Scan.nextInt(), k= Scan.nextInt();
            for(int j=0;j<q;j++){
                //input questions
            }
            for(int j=0;j<n;j++){
                //input students
            }
            for(int j=0;j<n;j++){
                //input order of entrance into exam room
            }
        }
    }
}

class question {
    String question;
    Integer ID;

    public question(String question, Integer ID) {
        this.question = question;
        this.ID = ID;
    }
}

class student {
    String rollNumber;
    String name;

    public student(String rollNumber, String name) {
        this.rollNumber = rollNumber;
        this.name = name;
    }
}