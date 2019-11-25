package at.stnwtr.indymatic;

import at.stnwtr.indy4j.Indy;
import at.stnwtr.indy4j.credentials.Credentials;
import at.stnwtr.indy4j.event.EventContext;
import at.stnwtr.indy4j.event.FutureEvent;
import at.stnwtr.indymatic.config.Config;
import at.stnwtr.indymatic.entry.Entry;
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

    Indy indy = new Indy(credentials);
    indy.login();

    indy.getNextEventContexts(Integer.MAX_VALUE).stream()
        .map(EventContext::loadEvent)
        .filter(event -> event instanceof FutureEvent)
        .map(event -> (FutureEvent) event)
        .forEach(event -> {
          for (int hour : event.getEventContext().getHours()) {
            if (!event.getEntries().get(hour).isPresent()) {
              config.getEntriesForDay(event).stream()
                  .filter(entry -> entry.getHour() == hour)
                  .filter(Entry::isValid)
                  .findFirst()
                  .map(Entry::getEntry)
                  .ifPresent(event::enrol);
            }
          }
        });

    indy.logout();
  }
}
