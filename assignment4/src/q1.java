import java.util.*;

public class q1 {
    public static void main(String[] args) {
        int t;
        Scanner s = new Scanner(System.in);
        t = s.nextInt();
        for( int i=0; i<t; i++){
            int q, n;
            q = s.nextInt();
            n = s.nextInt();
            questions questions = new questions();
            for( int j=0; j<q; j++){
                questions.addQuestion( new question(s.next(), s.next(), s.next(), s.nextInt()) );
            }
            students students = new students();
            for( int j=0; j<n; j++){
                int a;
                student temp = new student(s.next(), s.next(), a = s.nextInt());
                for( int k=0; k<a; k++){
                    String ID = s.next();
                    question question = questions.findByID(ID);
                    if(question.getType().compareTo("objective")==0){
                        temp.addAnswer(new objective(question, s.next().charAt(0)));
                    }
                    if(question.getType().compareTo("subjective")==0){
                        temp.addAnswer(new subjective(question, s.next()));
                    }
                    if(question.getType().compareTo("specialized")==0){
                        String text = s.next();
                        int r = s.nextInt();
                        Vector<String> strings = new Vector<String>();
                        for(int l=0;l<r;l++){
                            strings.add(s.next());
                        }
                        temp.addAnswer(new specialized(question, text, r, strings));
                    }
                }
                students.addStudent(temp);
            }

            for(int j=0; j<n; j++){
                for(int k=0; k<students.getStudents().get(j).getAnswers().size(); k++){
                        student student = students.getStudentByRoll(s.next());
                        answer answer = student.getAnswers().findByID(s.next());
                        answer.setMarksGiven(s.nextInt());
                }
            }
            for(int j=0; j<n; j++){
                students.getStudents().get(j).calculateTotalMarks();
            }
        System.out.print(students);
        }
    }
}

class students implements Iterable<student> {
    private Vector<student> students = new Vector<student>();

    public Vector<student> getStudents() {
        return students;
    }

    public void addStudent(student student) {
        this.students.add(student);
    }

    public student getStudentByRoll(String Roll){
        for(student student:students){
            if(student.getRollNumber().compareTo(Roll)==0){
                return student;
            }
        }
        return null;
    }

    @Override
    public Iterator<student> iterator(){
        return students.iterator();
    }

    @Override
    public String toString() {
        String result="";
        for(student student:students){
            result = result + student + "\n";
        }
        return result;
    }
}

class student {
    private String rollNumber;
    private String name;
    private Integer a;
    private answers answers = new answers();
    private Integer totalMarks;

    public student(String rollNumber, String name, Integer a) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.a = a;
        this.totalMarks=0;
    }

    public void addAnswer(answer answer) {
        this.answers.add(answer);
    }

    public answers getAnswers() {
        return answers;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void calculateTotalMarks(){
        for(answer answer:answers){
            this.totalMarks+=answer.getMarksGiven();
        }
    }

    public String getName() {
        return name;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    @Override
    public String toString() {
        return this.getRollNumber()+" "+this.getName()+" "+this.getTotalMarks();
    }
}

class questions {
    private Vector<question> questions = new Vector<question>();

    public Vector<question> getQuestions() {
        return questions;
    }

    public void addQuestion(question question) {
        this.questions.add(question);
    }

    public  question findByID(String ID){
        for(question question:questions){
            if(question.getID().compareTo(ID)==0){
                return question;
            }
        }
        return null;
    }
}

class question {
    private String ID;
    private String type;
    private String text;
    private Integer marks; // maximum marks for question

    public question(String ID, String type, String text, Integer marks) {
        this.ID = ID;
        this.type = type;
        this.text = text;
        this.marks = marks;
    }

    public String getID() {
        return ID;
    }

    public String getType() {
        return type;
    }

    public Integer getMarks() {
        return marks;
    }
}

class answers implements Iterable<answer>{
    private Vector<answer> answers = new Vector<answer>();

    public  answer findByID(String ID){
        for(answer answer:answers){
            if(answer.getQuestion().getID().compareTo(ID)==0){
                return answer;
            }
        }
        return null;
    }

    public void add(answer answer){
        this.answers.add(answer);
    }

    public Integer size(){
        return this.answers.size();
    }

    @Override
    public Iterator<answer> iterator() {
        return answers.iterator();
    }
}

class answer {
    private question question;
    private Integer marksGiven;
    public answer(question question) {
        this.question = question;
    }

    public void setMarksGiven(Integer marksGiven) {
        if(marksGiven>question.getMarks()){
            marksGiven=question.getMarks();
        } else if (marksGiven<0){
            marksGiven=0;
        }
        this.marksGiven = marksGiven;
    }

    public Integer getMarksGiven() {
        return marksGiven;
    }

    public question getQuestion() {
        return question;
    }

    public void setQuestion(question question) {
        this.question = question;
    }
}

class objective extends answer {
    private Character option;

    public objective(question question, Character option) {
        super(question);
        this.option = option;
    }
}

class subjective extends answer {
    private String text;

    public subjective(question question, String text) {
        super(question);
        this.text = text;
    }
}

class specialized extends subjective {
    private Integer r;
    private Vector<String> list = new Vector<String>();

    public specialized(question question, String text, Integer r, Vector<String> list) {
        super(question, text);
        this.r = r;
        this.list = list;
    }
}