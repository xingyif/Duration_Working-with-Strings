package cs3500.hw01.duration;

/**
 * Abstract base class for implementations of {@link Duration}.
 */
abstract class AbstractDuration implements Duration {
  /**
   * Constructs a {@link Duration} in a manner selected by concrete
   * subclasses of this class.
   *
   * @param inSeconds the length in seconds
   * @return the new {@code Duration}
   */
  protected abstract Duration fromSeconds(long inSeconds);

  @Override
  public String toString() {
    return asHms();
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (! (that instanceof Duration)) {
      return false;
    }

    return ((Duration) that).inSeconds() == this.inSeconds();
  }

  @Override
  public int hashCode() {
    return Long.hashCode(inSeconds());
  }

  @Override
  public int compareTo(Duration that) {

    return Long.compare(this.inSeconds(), that.inSeconds());
  }

  @Override
  public Duration plus(Duration that) {

    return fromSeconds(this.inSeconds() + that.inSeconds());
  }

  /**
   * Converts an unpacked hours-minutes-seconds duration to its length
   * in seconds.
   *
   * @param hours the number of hours
   * @param minutes the number of minutes
   * @param seconds the number of seconds
   * @return the duration in seconds
   */
  protected static long inSeconds(int hours, int minutes, int seconds) {
    return 3600 * hours + 60 * minutes + seconds;
  }

  /**
   * Formats an unpacked hours-minutes-seconds duration in the same
   * {@code H:MM:SS} format that {@link Duration#asHms()} returns.
   * Assumes that
   *
   * @param hours the number of hours
   * @param minutes the number of minutes
   * @param seconds the number of seconds
   * @return formatted duration
   * @throws IllegalArgumentException if any argument is negative
   */
  protected static String asHms(int hours, int minutes, int seconds) {
    return String.format("%d:%02d:%02d", hours, minutes, seconds);
  }

  /**
   * Ensures that the hours, minutes, and seconds are all non-negative.
   * Is factoring this out overkill? Or should we also factor out the
   * {@code inSeconds < 0} check in the two unary constructors? Discuss.
   *
   * @param hours the number of hours
   * @param minutes the number of minutes
   * @param seconds the number of seconds
   * @throws IllegalArgumentException if any argument is negative
   */
  protected static void ensureHms(int hours, int minutes, int seconds) {
    if (hours < 0 || minutes < 0 || seconds < 0) {
      throw new IllegalArgumentException("must be non-negative");
    }
  }
  // checks if the template is valid, if it is, then places the given
  // characters in templates by the corresponding number;
  // else throw an exception
  public String format(String template) {

    String result = "";

    for (int i = 0; i <= template.length() - 1; i = i + 1) {
      // represents the string at the current index
      String currentString = String.valueOf(template.charAt(i));
      // represents the modified string
      String thisString;
      // if the current string is %
      if (currentString.equals("%")) {

        // if there is nothing after %, then throw an error
        if (template.length() == i + 1) {
          throw new IllegalArgumentException("not enough character");
        }
        else {
          i = i + 1;
          // represents the string at the next index
          String nextString = String.valueOf(template.charAt(i));

          // if % follows by t, then return the duration in seconds
          if (nextString.equals("t")) {
            thisString = String.valueOf(inSeconds());
          }
          // if % follows by h, then return the the hours component
          // of the decomposed duration
          else if (nextString.equals("h")) {
            thisString = String.valueOf(hoursOf(inSeconds()));
          }
          // if % follows by m, then return the the minutes of the duration
          else if (nextString.equals("m")) {
            thisString = String.valueOf(minutesOf(inSeconds()));
          }
          // if % follows by s, then return the the seconds of the duration
          else if (nextString.equals("s")) {
            thisString = String.valueOf(secondsOf(inSeconds()));
          }

          // if % follows by H, then return the the hours of the duration
          // make the hour become 2 digits by adding zero in front
          else if (nextString.equals("H")) {
            // if the hour is bigger than or equal to 10, then keep it
            // else add a zero in front
            if (hoursOf(inSeconds()) >= 10) {
              thisString = String.valueOf(hoursOf(inSeconds()));
            } else {
              thisString = "0" + String.valueOf(hoursOf(inSeconds()));
            }
          }
          // if % follows by M, then return the the hours of the duration
          // make the minute become 2 digits by adding zero in front
          else if (nextString.equals("M")) {
            // if the minute is bigger than or equal to 10, then keep it
            // else add a zero in front
            if (minutesOf(inSeconds()) >= 10) {
              thisString = String.valueOf(minutesOf(inSeconds()));
            } else {
              thisString = "0" + String.valueOf(minutesOf(inSeconds()));
            }
          }
          // if % follows by S, then return the the seconds of the duration
          // make the second become 2 digits by adding zero in front
          else if (nextString.equals("S")) {
            // if the second is bigger than or equal to 10, then keep it
            // else add a zero in front
            if (secondsOf(inSeconds()) >= 10) {
              thisString = String.valueOf(secondsOf(inSeconds()));
            } else {
              thisString = "0" + String.valueOf(secondsOf(inSeconds()));
            }
          }
          // if % follows by %, then escape the second %
          // and make thisString just a percent sign
          else if (nextString.equals("%")) {
            thisString = "%";
          } else {
            throw new IllegalArgumentException("code is malformed");
          }
        }
      }
      else {
        thisString = currentString;
      }
      result = result + thisString;
    }
    return result;
  }

  /**
   * Returns the number of whole hours in the given number of seconds.
   *
   * @param inSeconds the total number of seconds
   * @return the number of hours
   * @throws ArithmeticException if the correct result cannot fit in an
   *          {@code int}.
   */


  protected static int hoursOf(long inSeconds) {
    if (inSeconds / 3600 > Integer.MAX_VALUE) {
      throw new ArithmeticException("result cannot fit in type");
    }

    return (int) (inSeconds / 3600);
  }

  /**
   * Returns the number of whole minutes in the given number of seconds, less
   * the number of whole hours.
   *
   * @param inSeconds the total number of seconds
   * @return the number of remaining minutes
   */
  protected static int minutesOf(long inSeconds) {
    return (int) (inSeconds / 60 % 60);
  }

  /**
   * Returns the number of seconds remaining after all full minutes are
   * removed from the given number of seconds.
   *
   * @param inSeconds the total number of seconds
   * @return the number of remaining seconds
   */
  protected static int secondsOf(long inSeconds) {
    return (int) (inSeconds % 60);
  }
}
