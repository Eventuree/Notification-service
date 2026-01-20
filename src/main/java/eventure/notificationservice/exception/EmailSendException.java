package eventure.notificationservice.exception;

public class EmailSendException extends RuntimeException {

  public EmailSendException(String message) {
    super(message);
  }
  public EmailSendException(String message, Throwable e) {
    super(message, e);
  }
}
