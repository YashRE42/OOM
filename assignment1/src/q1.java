
import java.util.*;

class Q1 {
    public static void main(String args[] ) throws Exception {
        int i = 0;
        int n = 0;
        Scanner Scan = new Scanner(System.in);
        n=Scan.nextInt();
        student[] students = new student[n];
        for(i=0;i<n;i++) {
            students[i]=new student(Scan.next(),Scan.next());
            students[i].printStudent();
        }
        Scan.close();
    }
}

class student {
    private String rollNo;
    private String name;
    void printStudent() {
        System.out.println(rollNo + " " + name);
    }
    student(String rollNoIn, String nameIn){
        setData(rollNoIn,nameIn);
    }
    private void setData(String rollSet, String nameSet){
        rollNo=rollSet;
        name=nameSet;
    }
    String getRollNo(){
        return rollNo;
    }
    int getRollNoInInt(){
        return Integer.parseInt(getRollNo().substring(7));
    }
}