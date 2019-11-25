package at.stnwtr.indymatic.entry;

import at.stnwtr.indy4j.entry.RequestEntry;
import at.stnwtr.indy4j.event.FutureEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * The super class for teacher id or room id based entries.
 *
 * @author stnwtr
 * @since 24.11.2019
 */
public abstract class Entry {

  /**
   * Compare entries by priority to sort.
   */
  public static final Comparator<Entry> PRIORITY_SORT = Comparator.comparingInt(Entry::getPriority);

  /**
   * The {@link FutureEvent} this entry is for.
   */
  protected final FutureEvent event;

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
  public Entry(FutureEvent event, String... entryParts) {
    this.event = event;
    this.entryParts = entryParts;

    this.day = entryParts[0];
    this.hour = Integer.parseInt(entryParts[1]);
    this.priority = Integer.parseInt(entryParts[2]);

    this.subject = entryParts[5];
    this.activity = entryParts[6];
  }

  /**
   * Get the entry for the specific config line.
   *
   * @return The new {@link RequestEntry}.
   */
  public abstract RequestEntry getEntry();

  /**
   * Check if the entry is valid.
   *
   * @return True if valid else false.
   */
  public abstract boolean isValid();

  /**
   * Get all entry parts.
   *
   * @return The entry parts.
   */
  public String[] getEntryParts() {
    return entryParts;
  }

  /**
   * Get the entry day.
   *
   * @return The day.
   */
  public String getDay() {
    return day;
  }

  /**
   * Get the entry hour.
   *
   * @return The hour.
   */
  public int getHour() {
    return hour;
  }

  /**
   * Get the entry priority.
   *
   * @return The priority.
   */
  public int getPriority() {
    return priority;
  }

  /**
   * Get the entry subject.
   *
   * @return The subject.
   */
  public String getSubject() {
    return subject;
  }

  /**
   * Get the entry activity.
   *
   * @return The activity.
   */
  public String getActivity() {
    return activity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Entry entry = (Entry) o;
    return hour == entry.hour &&
        priority == entry.priority &&
        Arrays.equals(entryParts, entry.entryParts) &&
        Objects.equals(day, entry.day) &&
        Objects.equals(subject, entry.subject) &&
        Objects.equals(activity, entry.activity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    int result = Objects.hash(day, hour, priority, subject, activity);
    result = 31 * result + Arrays.hashCode(entryParts);
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "Entry{" +
        "entryParts=" + Arrays.toString(entryParts) +
        ", day='" + day + '\'' +
        ", hour=" + hour +
        ", priority=" + priority +
        ", subject='" + subject + '\'' +
        ", activity='" + activity + '\'' +
        '}';
  }
}
