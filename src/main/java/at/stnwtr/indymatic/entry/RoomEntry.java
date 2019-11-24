package at.stnwtr.indymatic.entry;

import at.stnwtr.indy4j.entry.RequestEntry;
import at.stnwtr.indy4j.event.FutureEvent;
import java.util.Arrays;
import java.util.Objects;

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
   * Get the room id.
   *
   * @return The room id.
   */
  public String getRoomId() {
    return roomId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RequestEntry getEntry(FutureEvent event) {
    String id = event.getEntryCombinationForHour(hour).stream()
        .filter(entryCombination -> entryCombination.getRoom().equals(roomId))
        .findFirst()
        .map(entryCombination -> entryCombination.getTeacher().getId())
        .orElseThrow(InvalidEntryException::new);

    // extra method if entry is correct? room available, student limit not reached, etc.

    return RequestEntry.normalSchoolDay(hour, id, subject, activity);
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
    if (!super.equals(o)) {
      return false;
    }
    RoomEntry roomEntry = (RoomEntry) o;
    return Objects.equals(roomId, roomEntry.roomId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), roomId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "RoomEntry{" +
        "roomId='" + roomId + '\'' +
        ", entryParts=" + Arrays.toString(entryParts) +
        ", day='" + day + '\'' +
        ", hour=" + hour +
        ", priority=" + priority +
        ", subject='" + subject + '\'' +
        ", activity='" + activity + '\'' +
        '}';
  }
}
