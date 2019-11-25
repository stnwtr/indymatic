package at.stnwtr.indymatic.entry;

import at.stnwtr.indy4j.entry.EntryCombination;
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
  public TeacherEntry(FutureEvent event, String... entryParts) {
    super(event, entryParts);

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
  public RequestEntry getEntry() {
    String id = event.getTeachers(hour).stream()
        .filter(teacher -> teacher.getId().equalsIgnoreCase(teacherId))
        .findFirst()
        .map(Teacher::getId)
        .orElseThrow(InvalidEntryException::new);

    return RequestEntry.normalSchoolDay(hour, id, subject, activity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid() {
    EntryCombination combination = event.getEntryCombinationForHour(hour).stream()
        .filter(entryCombination -> entryCombination.getTeacher().getId().equalsIgnoreCase(teacherId))
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
