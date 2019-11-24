package at.stnwtr.indymatic.entry;

/**
 * Exception which is thrown if a {@link Entry} is invalid.
 *
 * @author stnwtr
 * @since 24.11.2019
 */
public class InvalidEntryException extends RuntimeException {

  /**
   * {@inheritDoc}
   */
  public InvalidEntryException() {
    super();
  }

  /**
   * {@inheritDoc}
   */
  public InvalidEntryException(String message) {
    super(message);
  }

  /**
   * {@inheritDoc}
   */
  public InvalidEntryException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * {@inheritDoc}
   */
  public InvalidEntryException(Throwable cause) {
    super(cause);
  }
}
