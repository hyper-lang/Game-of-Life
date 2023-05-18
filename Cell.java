public class Cell{
    private boolean state;
    private String location = "";
    public Cell(){
        state = false;
    }
    public Cell(boolean ded){
        state = ded;
    }
    public boolean getState(){
        return this.state;
    }
    public void changeState(boolean newState){
        state = newState;
    }
    public void changeLocation(String loc){
        location = loc;
    }
    public String getLocation(){
        return location;
    }
}