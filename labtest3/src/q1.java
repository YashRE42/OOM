import java.util.*;
import static java.lang.Math.abs;

//for test case 1 only

public class q1 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Integer numberOfTestCases = s.nextInt(); //T
        for(Integer i = 0; i < numberOfTestCases; i++){
            festival festival = new festival();
            Integer numberOfPeople = s.nextInt(); //K
            Integer numberOfAttractions = s.nextInt(); //l
            for(Integer j = 0; j < numberOfPeople; j++){
                //scan k people
                //i,j, person type, additional r if type is best-of-near or near-of-best,walking style
                Integer x1 = s.nextInt(), y1 = s.nextInt();

                String personType = s.next();//currently only hungry
                personType = "hungry";

//                if(personType.compareTo("best-of-near")==0 || personType.compareTo("near-of-best")==0){
//                    //not yet.
//                    Integer r = s.nextInt();
//                }

                String walkingStyle = s.next();//currently only walk
                walkingStyle = "walk";
                if(personType.compareTo("hungry")==0)
                festival.addPerson( new hungry( new coordinate(x1,y1)));
            }
            for(Integer j = 0; j < numberOfAttractions; j++){
                //scan l attractions
                //i,j, attraction value
                festival.addAttraction(new attraction(new coordinate(s.nextInt(),s.nextInt()),s.nextInt()));
            }
            festival.assign();
            String position = festival.toString();
            System.out.print(position);
            festival.step();
            while(!(position.compareTo(festival.toString())==0)){
                System.out.print(festival);
                position = festival.toString();
                festival.step();
            }
        }
    }
}

class coordinate{
    private Integer i; // row number
    private Integer j; // column number

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Integer getJ() {
        return j;
    }

    public void setJ(Integer j) {
        this.j = j;
    }

    public coordinate(Integer i, Integer j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString() {
        return "(" + i +
                "," + j +
                ')';
    }
}

interface choose{
    void setTarget(Vector<attraction> attractions);
}

abstract class person implements choose {
    private coordinate currentLocation;
    private coordinate target;

    public person(coordinate currentLocation) {
        this.currentLocation = currentLocation;
    }

    public coordinate getTarget() {
        return target;
    }

    public void setTarget(coordinate target) {
        this.target = target;
    }

    public coordinate getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(coordinate currentLocation) {
        this.currentLocation = currentLocation;
    }

    boolean isAtTargetColumn(){
        return this.target.getJ()==this.currentLocation.getJ();
    }

    boolean shouldGoUpColumn(){
        return this.target.getJ()>this.currentLocation.getJ();
    }
     boolean isAtTargetRow(){
        return this.target.getI()==this.currentLocation.getI();
    }

    boolean shouldGoUpRow(){
        return this.target.getI()>this.currentLocation.getI();
    }

    boolean shouldRunOnColumn(){
        if(abs(this.target.getJ()-this.currentLocation.getJ())>=2){
            return true;
        }
        return false;
    }
    boolean shouldRunOnRow(){
        if(abs(this.target.getI()-this.currentLocation.getI())>=2){
            return true;
        }
        return false;
    }

    void move(){
        walk();
    }

    void walk(){
        if(!isAtTargetColumn()){
            if(shouldGoUpColumn()){
                this.currentLocation.setJ(this.currentLocation.getJ()+1);
            } else {
                this.currentLocation.setJ(this.currentLocation.getJ()-1);
            }
        } else if(!isAtTargetRow()) {
            if(shouldGoUpRow()){
                this.currentLocation.setI(this.currentLocation.getI()+1);
            } else {
                this.currentLocation.setI(this.currentLocation.getI()-1);
            }
        }
        //walk function fail-safe, will not move if currentLocation == target
    }
    void run(coordinate target){
//        if(shouldRunOnColumn(target.getJ())){
//            if(shouldGoUpColumn(target.getJ())){
//                this.currentLocation.setJ(this.currentLocation.getJ()+2);
//            } else {
//                this.currentLocation.setJ(this.currentLocation.getJ()-2);
//            }
//        } else if(!isAtTargetRow(target.getI())) {
//            if(shouldGoUpRow(target.getI())){
//                this.currentLocation.setI(this.currentLocation.getI()+1);
//            } else {
//                this.currentLocation.setI(this.currentLocation.getI()-1);
//            }
//        }
//        //walk function fail-safe, will not move if currentLocation == target
//    }

    @Override
    public String toString() {
        return currentLocation + "";
    }
}

class hungry extends person {

    public coordinate getTarget() {
        return this.getTarget();
    }

    public hungry(coordinate currentLocation) {
        super(currentLocation);
    }

    public void setTarget(Vector<attraction> attractions) {
        attraction choosen = null;
        Integer minimum = Integer.MAX_VALUE;
        for(attraction attraction:attractions){
            Integer distance = calculateDistance(attraction.getLocation());
            if(minimum == distance){
                if(choosen.getAttractionValue()<attraction.getAttractionValue()){
                    choosen=attraction;
                }
            }

            if( minimum > distance){
                minimum = distance;
                choosen = attraction;
            }
        }
        this.setTarget(choosen.getLocation());
    }

    Integer calculateDistance(coordinate target){
        return abs(this.getCurrentLocation().getI()-target.getI())+abs(this.getCurrentLocation().getJ()-target.getJ());
    }
}

class festival {
    private Vector<person> people;
    private Vector<attraction> attractions;

    public festival() {
        this.people = new Vector<person>();
        this.attractions = new Vector<attraction>();
    }

    void addPerson(person person){
        people.add(person);
    }

    void addAttraction(attraction attraction){
        attractions.add(attraction);
    }

    public Vector<person> getPeople() {
        return people;
    }

    public Vector<attraction> getAttractions() {
        return attractions;
    }

    void assign(){
        for(person person:this.getPeople()){
            person.setTarget(this.getAttractions());
        }
    }

    void step(){
        for(person person:this.getPeople()){
            person.move();
        }
    }

    @Override
    public String toString() {
        String result = "";
        for(person person:this.getPeople()){
            result+=person;
        }
        return result + "\n";
    }
}

class attraction {
    private coordinate location;
    private Integer attractionValue;

    public attraction(coordinate location, Integer attractionValue) {
        this.location = location;
        this.attractionValue = attractionValue;
    }

    public coordinate getLocation() {
        return location;
    }

    public void setLocation(coordinate location) {
        this.location = location;
    }

    public Integer getAttractionValue() {
        return attractionValue;
    }

    public void setAttractionValue(Integer attractionValue) {
        this.attractionValue = attractionValue;
    }
}