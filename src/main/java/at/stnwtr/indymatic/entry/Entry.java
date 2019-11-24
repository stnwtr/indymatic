package at.stnwtr.indymatic.entry;

import at.stnwtr.indy4j.entry.RequestEntry;
import at.stnwtr.indy4j.event.FutureEvent;

/**
 * The super class for teacher id or room id based entries.
 *
 * @author stnwtr
 * @since 24.11.2019
 */
public abstract class Entry {

  /**
   * All parts of this entry.
   */
  protected final String[] entryParts;

  /**
   * The day this entry is for.
   */
  protected final String day;

  /**
   * The hour this entry is for.
   */
  protected final int hour;

  /**
   * The priority this entry has.
   */
  protected final int priority;

  /**
   * The subject this entry has.
   */
  protected final String subject;

  /**
   * The activity this entry has.
   */
  protected final String activity;

  /**
   * Constructor which expects the entry parts.
   *
   * @param entryParts The entry parts.
   */
  public Entry(String... entryParts) {
    this.entryParts = entryParts;

    this.day = entryParts[0];
    this.hour = Integer.parseInt(entryParts[1]);
    this.priority = Integer.parseInt(entryParts[2]);

    this.subject = entryParts[5];
    this.activity = entryParts[6];
  }

  /**
   *
   *
   * @param event
   * @return
   */
  public abstract RequestEntry getEntry(FutureEvent event);
}
