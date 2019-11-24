package at.stnwtr.indymatic.config;

import at.stnwtr.indymatic.entry.Entry;
import at.stnwtr.indymatic.entry.RoomEntry;
import at.stnwtr.indymatic.entry.TeacherEntry;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The class which reads the configuration.
 *
 * @author stnwtr
 * @since 24.11.2019
 */
public class Config {

  /**
   * The number of splitting chars.
   */
  public static final int OCCURRENCES = 6;

  /**
   * The splitting character.
   */
  public static final String TERMINATOR = ":";

  /**
   * This char can be any day or hour.
   */
  public static final String ANY = "*";

  /**
   * Indicates a room.
   */
  public static final String ROOM = "R";

  /**
   * Indicates a teacher.
   */
  public static final String TEACHER = "T";

  /**
   * The configuration file.
   */
  private final Path file;

  /**
   * The valid config lines.
   */
  private final Stream<String> lines;

  /**
   * Constructor which only expects the config file.
   *
   * @param file The config file.
   */
  public Config(Path file) {
    this.file = file;

    try {
      lines = Files.readAllLines(file, StandardCharsets.UTF_8).stream()
          .filter(s -> !s.startsWith("#"))
          .filter(s -> s.chars().filter(value -> value == TERMINATOR.charAt(0)).count() == OCCURRENCES);
    } catch (IOException e) {
      throw new InvalidConfigException("Could not load the configuration!", e);
    }
  }

  /**
   * Get the configuration file.
   *
   * @return The config file.
   */
  public Path getFile() {
    return file;
  }

  /**
   * Get all valid lines.
   *
   * @return The valid config lines.
   */
  public List<String> getLines() {
    return lines.collect(Collectors.toCollection(LinkedList::new));
  }

  /**
   * Get a list of entries for a specific day.
   *
   * @param day The day.
   * @return A list of entries.
   */
  public List<Entry> getEntriesForDay(final String day) {
    return lines.map(s -> s.split(TERMINATOR))
        .filter(strings -> strings[0].equals(day) || strings[0].equals(ANY))
        .map(strings -> {
          if (strings[3].equals(TEACHER)) {
            return new TeacherEntry(strings);
          } else if (strings[3].equals(ROOM)) {
            return new RoomEntry(strings);
          } else {
            return null;
          }
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toCollection(LinkedList::new));
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
    Config config = (Config) o;
    return Objects.equals(file, config.file) &&
        Objects.equals(lines, config.lines);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(file, lines);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "Config{" +
        "file=" + file +
        ", lines=" + lines +
        '}';
  }
}
