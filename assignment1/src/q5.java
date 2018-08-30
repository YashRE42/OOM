import javax.lang.model.type.NullType;
import  java.util.*;
public class q5 {
    public static void main(String arg[]) {
        int i = 0;
        int j = 0;
        int t = 0;
        Scanner Scan = new Scanner(System.in);
        t=Scan.nextInt();
        for(i=0;i<t;i++) {
            int n=Scan.nextInt();
            int k=Scan.nextInt();
            group group = new group(n);
            for(j=0;j<n;j++) {
                group.insertStudent(Scan.next(), Scan.next());
            }
            group.sortStudents();
            assignment[] assignments = new assignment[k];
            for(j=0;j<k;j++) {
                assignments[j]= new assignment(Scan.next(),Scan.nextInt());
                for(int x=0;x<assignments[j].getSubmittersLength();x++){
                    assignments[j].addSubmitter(Scan.next(),Scan.nextInt());
                }
            }
            group.generateScoreAndStrings(assignments);
            group.printStudentsWithScore();
        }
        Scan.close();
    }
}

class group {
    private student[] students;
    private Integer fillIndex;
    group(Integer numberOfStudents){
        students = new student[numberOfStudents];
        fillIndex=0;
    }

    void insertStudent(String rollNumber, String name){
        students[fillIndex]=new student(rollNumber, name);
        fillIndex++;
    }

    void printStudents(){
        int i = 0;
        for(i=0; i<students.length;i++){
            students[i].printStudent();
        }
    }
    void generateScoreAndStrings(assignment[] assignments){
        int i = 0;
        for(i=0; i<students.length;i++){
            String string="";
            Integer Score=0;
            for(int j=0; j<assignments.length;j++){
                for(int x=0;x<assignments[j].getSubmittersLength();x++) {
                    if(assignments[j].getSubmittersRoll(x).equals(students[i].getRollNo())){
                        if(!string.equals("")){
                            string+=" +";
                        }
                        string=string + " " + assignments[j].getSubmittersScore(x) + " "+ assignments[j].getID();
                        Score+=assignments[j].getSubmittersScore(x);
                    }
                }
            }
            students[i].setString(string);
            students[i].setScore(Score);
        }
    }
    void printStudentsWithScore(){
        int i = 0;
        for(i=0; i<students.length;i++){
            students[i].printStudent();
            System.out.println(students[i].getString() + " = " + students[i].getScore());
        }
    }

    void sortStudents() {
        int i=0;
        int j=0;
        boolean swapped;
        student temp;
        //bubble sort algo
        for (i = 0; i < students.length - 1; i++) {
            for (j = 0; j < students.length-i-1; j++) {
                if (students[j].getRollNo().compareTo(students[j + 1].getRollNo())> 0) {
                    temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
    }
}

class student {
    private String rollNo;
    private String name;
    private String string;
    private Integer Score;
    void printStudent() {
        System.out.print(rollNo + " " + name);
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
    String getString(){
        return string;
    }
    Integer getScore(){
        return Score;
    }
    void setString(String string){
        this.string=string;
    }
    void setScore(Integer Score){
        this.Score=Score;
    }
}

class assignment {
    private String ID;
    private completeSet[] submitters;
    int submittersIndex;
    assignment(String name, Integer number){
        ID=name;
        submitters=new completeSet[number];
    }
    void addSubmitter(String roll, Integer score){
        submitters[submittersIndex]=new completeSet(roll,score);
        submittersIndex++;
    }
    int getSubmittersLength(){
        return submitters.length;
    }
    String getSubmittersRoll(int x){
        return submitters[x].getRoll();
    }
    Integer getSubmittersScore(int x){
        return submitters[x].getScore();
    }
    String getID(){
        return this.ID;
    }
}

class completeSet {
    private String roll;
    private Integer score;
    completeSet(String roll, Integer score){
        this.score=score;
        this.roll=roll;
    }
    String getRoll(){
        return this.roll;
    }
    Integer getScore(){
        return this.score;
    }
}