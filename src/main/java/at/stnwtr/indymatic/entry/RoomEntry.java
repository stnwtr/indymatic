package at.stnwtr.indymatic.entry;

import at.stnwtr.indy4j.entry.EntryCombination;
import at.stnwtr.indy4j.entry.RequestEntry;
import at.stnwtr.indy4j.event.FutureEvent;

public class RoomEntry extends Entry {

  /**
   * The ID of the room.
   */
  private final String roomId;

  /**
   * {@inheritDoc}
   */
  public RoomEntry(String... entryParts) {
    super(entryParts);

    this.roomId = entryParts[4];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RequestEntry getEntry(FutureEvent event) {
    EntryCombination combination = event.getEntryCombinationForHour(hour).stream()
        .filter(entryCombination -> entryCombination.getRoom().equals(roomId))
        .findFirst()
        .orElseThrow(InvalidEntryException::new);

    // extra method if entry is correct? room available, student limit not reached, etc.

    return RequestEntry.normalSchoolDay(
        hour,
        combination.getTeacher().getId(),
        subject,
        activity
    );
  }
}
