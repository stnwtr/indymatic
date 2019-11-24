package at.stnwtr.indymatic.entry;

import at.stnwtr.indy4j.entry.RequestEntry;
import at.stnwtr.indy4j.event.FutureEvent;
import at.stnwtr.indy4j.teacher.Teacher;
import java.util.Arrays;
import java.util.Objects;

public class TeacherEntry extends Entry {

  /**
   * The ID of the teacher.
   */
  private final String teacherId;

  /**
   * {@inheritDoc}
   */
  public TeacherEntry(String... entryParts) {
    super(entryParts);

    teacherId = entryParts[4];
  }

  /**
   * Get the teacher id.
   *
   * @return The teacher id.
   */
  public String getTeacherId() {
    return teacherId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RequestEntry getEntry(FutureEvent event) {
    String id = event.getTeachers(hour).stream()
        .filter(teacher -> teacher.getId().equals(teacherId))
        .findFirst()
        .map(Teacher::getId)
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
    TeacherEntry that = (TeacherEntry) o;
    return Objects.equals(teacherId, that.teacherId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), teacherId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "TeacherEntry{" +
        "teacherId='" + teacherId + '\'' +
        ", entryParts=" + Arrays.toString(entryParts) +
        ", day='" + day + '\'' +
        ", hour=" + hour +
        ", priority=" + priority +
        ", subject='" + subject + '\'' +
        ", activity='" + activity + '\'' +
        '}';
  }
}
