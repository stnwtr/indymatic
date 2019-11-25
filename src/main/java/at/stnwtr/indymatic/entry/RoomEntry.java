package at.stnwtr.indymatic.entry;

import at.stnwtr.indy4j.entry.EntryCombination;
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
  public RoomEntry(FutureEvent event, String... entryParts) {
    super(event, entryParts);

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
  public RequestEntry getEntry() {
    String id = event.getEntryCombinationForHour(hour).stream()
        .filter(entryCombination -> entryCombination.getRoom().equalsIgnoreCase(roomId))
        .findFirst()
        .map(entryCombination -> entryCombination.getTeacher().getId())
        .orElseThrow(InvalidEntryException::new);

    return RequestEntry.normalSchoolDay(hour, id, subject, activity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid() {
    EntryCombination combination = event.getEntryCombinationForHour(hour).stream()
        .filter(entryCombination -> entryCombination.getRoom().equalsIgnoreCase(roomId))
        .findFirst()
        .orElse(null);

    return combination != null && combination.getStudentAmount() < combination.getStudentLimit();
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
