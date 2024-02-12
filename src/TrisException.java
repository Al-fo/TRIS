public class TrisException extends Exception{
    String message;

    public TrisException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
