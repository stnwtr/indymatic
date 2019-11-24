package at.stnwtr.indymatic.config;

/**
 * Exception which is thrown if a {@link Config} is invalid.
 *
 * @author stnwtr
 * @since 24.11.2019
 */
public class InvalidConfigException extends RuntimeException {

  /**
   * {@inheritDoc}
   */
  public InvalidConfigException() {
    super();
  }

  /**
   * {@inheritDoc}
   */
  public InvalidConfigException(String message) {
    super(message);
  }

  /**
   * {@inheritDoc}
   */
  public InvalidConfigException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * {@inheritDoc}
   */
  public InvalidConfigException(Throwable cause) {
    super(cause);
  }
}
