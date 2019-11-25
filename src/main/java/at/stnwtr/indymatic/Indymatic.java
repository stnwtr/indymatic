package at.stnwtr.indymatic;

import at.stnwtr.indy4j.Indy;
import at.stnwtr.indy4j.credentials.Credentials;
import at.stnwtr.indy4j.entry.RequestEntry;
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
    if (args.length != 4) {
      System.out.println("Need 3 CommandLineArguments:");
      System.out.println("  <username>         Indy username");
      System.out.println("  <password>         Indy password");
      System.out.println("  <number>           Number of future events");
      System.out.println("  <priority_file>    The priority file");
      System.exit(0);
    }

    System.out.println("Reading credentials...");
    Credentials credentials = Credentials.from(args[0], args[1]);

    System.out.println("Reading number...");
    int limit = Integer.parseInt(args[2]);

    System.out.println("Checking path...");
    Path file = Paths.get(args[3]);

    System.out.println("Building config...");
    Config config = new Config(file);

    System.out.println("Building Indy instance...");
    Indy indy = new Indy(credentials);
    System.out.println("Log into Indy service...");
    indy.login();

    System.out.println("Loading events...");
    indy.getNextEventContexts(limit).stream()
        .map(EventContext::loadEvent)
        .filter(event -> event instanceof FutureEvent)
        .map(event -> (FutureEvent) event)
        .forEach(event -> {
          System.out.println("--------------------------------");
          System.out.println("Found event " + event.getEventContext().getDate() + "...");
          System.out.println("Checking entries...");
          for (int hour : event.getEventContext().getHours()) {
            if (!event.getEntries().get(hour).isPresent()) {
              RequestEntry requestEntry = config.getEntriesForDay(event).stream()
                  .filter(entry -> entry.getHour() == hour)
                  .filter(Entry::isValid)
                  .findFirst()
                  .map(Entry::getEntry)
                  .orElse(null);

              if (requestEntry == null) {
                System.out.println("no valid entry found for hour " + hour + "...");
                continue;
              }

              System.out.println("trying to enrol for hour " + hour + "...");
              event.enrol(requestEntry);
            } else {
              System.out.println("Already enrolled for hour " + hour + "...");
            }
          }
          System.out.println("--------------------------------");
        });

    System.out.println("Log out of indy service...");
    indy.logout();

    System.out.println("done");
  }
}
