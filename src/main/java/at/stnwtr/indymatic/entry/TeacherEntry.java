package at.stnwtr.indymatic.entry;

import at.stnwtr.indy4j.entry.EntryCombination;
import at.stnwtr.indy4j.entry.RequestEntry;
import at.stnwtr.indy4j.event.FutureEvent;
import at.stnwtr.indy4j.teacher.Teacher;

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
}
