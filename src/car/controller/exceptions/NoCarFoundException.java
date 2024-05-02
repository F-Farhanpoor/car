package car.controller.exceptions;

public class NoCarFoundException extends Exception{
    public NoCarFoundException(){
        super("No Car Found!!!");
    }
}
