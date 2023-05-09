package g58146.qwirkle.model;
/**
 * Exception class used to signal errors specific to the Qwirkle game.
 * @author Nour
 */
public class QwirkleException extends RuntimeException{
    public QwirkleException(String message){
        super(message);
    }
}