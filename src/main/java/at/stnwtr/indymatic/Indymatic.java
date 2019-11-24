package at.stnwtr.indymatic;

import at.stnwtr.indy4j.credentials.Credentials;
import at.stnwtr.indymatic.config.Config;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Main class for this project.
 *
 * @author stnwtr
 * @since 22.11.2019
 */
public class Indymatic {

  /**
   * The entry point for this program.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    if (args.length != 3) {
      System.out.println("Need 3 CommandLineArguments:");
      System.out.println("  <username>         Indy username");
      System.out.println("  <password>         Indy password");
      System.out.println("  <priority_file>    The priority file");
      System.exit(0);
    }

    Credentials credentials = Credentials.from(args[0], args[1]);
    Path file = Paths.get(args[2]);

    Config config = new Config(file);
  }
}
