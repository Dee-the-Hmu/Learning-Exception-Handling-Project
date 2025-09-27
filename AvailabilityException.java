
public class AvailabilityException extends CatalogException { //creating my own Custom Exception Hierarchy 
    
    // now all the (CatalogException e) can catch AvailabilityException 
    //not only that, we can still catch AvailabilityException if we want to handle it differently

    public AvailabilityException(){
        super();
    }

    public AvailabilityException(String message){
        super(message);
    }

    public AvailabilityException(String message, Throwable cause){
        super(message, cause);
    }

    public AvailabilityException(Throwable cause){
        super(cause);
    }

}