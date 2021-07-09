package it.frassi.resource;



public class Message {

    //The message
    private final String message;

    //The code of error
    private final String errorCode;

    //Addition details (if any)
    private final String errorDetails;

    //The message is about an error or not
    private final boolean isError;

    //CONSTRUCTOR: creates an error message
    public Message(final String message, final String errorCode, final String errorDetails){
        this.message = message;
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
        this.isError = true;
    }

    //CONSTRUCTOR: creates a generic message
    public Message(final String message){
        this.message = message;
        this.errorCode = null;
        this.errorDetails = null;
        this.isError = false;
    }

    //Returns the message
    public final String getMessage(){ return message; }

    //Returns the code of the error
    public final String getErrorCode(){ return errorCode; }

    //Returns additional details about the error
    public final String getErrorDetails(){ return errorDetails; }

    //Indicates if the message is about an error or not
    public final boolean isError(){ return isError; }

}