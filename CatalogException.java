

public class CatalogException extends Exception{

    public CatalogException(){
        super();
    }

    public CatalogException(String message){
        super(message);
    }

    public CatalogException(String message, Throwable cause){
        super(message, cause);
    }

    public CatalogException(Throwable cause){
        super(cause);
    }
}