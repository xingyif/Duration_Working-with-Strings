package cs3500.hw01.duration;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for {@link Duration}s.
 */
public abstract class AbstractDurationTest {
  @Test
  public void formatExample1() {
    assertEquals("4 hours, 0 minutes, and 9 seconds",
            hms(4, 0, 9)
                    .format("%h hours, %m minutes, and %s seconds"));
  }

  @Test
  public void formatExample2() {
    assertEquals("4:05:17",
            hms(4, 5, 17).format("%h:%M:%S"));
  }

  // tests whether %%, %%%h, %%%m, etc. respond correctly
  @Test
  public void formatExample4() {
    assertEquals("%4 hours, 0 minutes, and 9 seconds",
            hms(4, 0, 9)
                    .format("%%%h hours, %m minutes, and %s seconds"));
    assertEquals("6 hours, %5 minutes, and 7 seconds",
            hms(6, 5, 7)
                    .format("%h hours, %%%m minutes, and %s seconds"));
    assertEquals("%h hours, 5 minutes, and 7 seconds",
            hms(6, 5, 7)
                    .format("%%h hours, %m minutes, and %s seconds"));
    assertEquals("6 hours, %%m minutes, and 7 seconds",
            hms(6, 5, 7)
                    .format("%h hours, %%%%m minutes, and %s seconds"));
    assertEquals("%5:05:26",
            hms(5, 5, 26).format("%%%h:%M:%s"));
    assertEquals("05:5:26",
            hms(5, 5, 26).format("%H:%m:%S"));
    assertEquals("05 5 26",
            hms(5, 5, 26).format("%H %m %S"));
    assertEquals("7:15:07",
            hms(7, 15, 7).format("%h:%M:%S"));
  }

  // tests whether %h, %m, %s, %H, %M, %S respond correctly
  @Test
  public void formatExample5() {
    assertEquals("6 hours, 5 minutes, and 7 seconds",
            hms(6, 5, 7)
                    .format("%h hours, %m minutes, and %s seconds"));
    assertEquals("04 hours, 08 minutes, and 9 seconds",
            hms(4, 8, 9)
                    .format("%H hours, %M minutes, and %s seconds"));
    assertEquals("06 hours, 05 minutes, and 07 seconds",
            hms(6, 5, 7)
                    .format("%H hours, %M minutes, and %S seconds"));
    assertEquals("16 hours, 35 minutes, and 47 seconds",
            hms(16, 35, 47)
                    .format("%H hours, %M minutes, and %S seconds"));
    assertEquals("16 hours, 35 minutes, and 47 seconds",
            hms(16, 35, 47)
                    .format("%h hours, %m minutes, and %s seconds"));


  }

  // tests whether a string that contains only one % works correctly
  @Test
  public void formatExample6() {
    assertEquals("6 hours",
            hms(6, 5, 7)
                    .format("%h hours"));
    assertEquals("5 minutes",
            hms(6, 5, 7)
                    .format("%m minutes"));
    assertEquals("7 seconds",
            hms(6, 5, 7)
                    .format("%s seconds"));
    assertEquals("right now is: 6",
            hms(6, 5, 7)
                    .format("right now is: %h"));
    assertEquals("right now is: 5",
            hms(6, 5, 7)
                    .format("right now is: %m"));
    assertEquals("right now is: 7",
            hms(6, 5, 7)
                    .format("right now is: %s"));
  }

  // tests whether a string that contains no %, and %t works correctly
  @Test
  public void formatExample7() {
    assertEquals("no duration",
            hms(6, 5, 7)
                    .format("no duration"));
    assertEquals("",
            hms(7, 2, 7)
                    .format(""));
    assertEquals(" ",
            hms(7, 2, 7)
                    .format(" "));
    assertEquals("r",
            hms(7, 2, 7)
                    .format("r"));
    assertEquals("25327",
            hms(7, 2, 7)
                    .format("%t"));
  }

  // tests the exceptions
  // not enough character exception
  @Test(expected = IllegalArgumentException.class)
  public void NotEnoughCharException() {
    hms(0, 5, 7).format("% ");
  }

  // not enough character exception
  @Test(expected = IllegalArgumentException.class)
  public void NotEnoughCharException2() {
    hms(0, 5, 7).format("%");
  }

  // not enough character exception
  @Test(expected = IllegalArgumentException.class)
  public void NotEnoughCharException3() {
    hms(0, 5, 7).format(" %");
  }

  // code is malformed exception
  @Test(expected = IllegalArgumentException.class)
  public void MalformedException() {

    hms(0, 5, 7).format("%f hours, %r minutes, %q seconds");
  }

  // code is malformed exception
  @Test(expected = IllegalArgumentException.class)
  public void MalformedException2() {
    hms(6, 7, 8).format("%g hours, %i minutes: %q");
  }


  long sec1 = 10800;
  long sec2 = 10802;
  long sec3 = 3600;
  long sec5 = 10860;
  long sec6 = 36;

  // checks if the method hoursOf() respond correctly
  @Test
  public void testHoursOf() {
    assertEquals(3, AbstractDuration.hoursOf(sec1));
    assertEquals(3, AbstractDuration.hoursOf(sec2));
    assertEquals(1, AbstractDuration.hoursOf(sec3));
    assertEquals(0, AbstractDuration.hoursOf(0));
  }

  // check the max-value integer exception
  @Test(expected = ArithmeticException.class)
  public void MaxValueException() {
    AbstractDuration.hoursOf(Long.MAX_VALUE);
  }

  // checks if the method minutesOf() respond correctly
  @Test
  public void testMinutesOf() {
    assertEquals(0, AbstractDuration.minutesOf(sec1));
    assertEquals(1, AbstractDuration.minutesOf(sec5));
    assertEquals(0, AbstractDuration.minutesOf(0));
  }

  // checks if the method secondsOf() respond correctly
  @Test
  public void testSecondsOf() {
    assertEquals(0, AbstractDuration.secondsOf(sec1));
    assertEquals(2, AbstractDuration.secondsOf(sec2));
    assertEquals(36, AbstractDuration.secondsOf(sec6));
    assertEquals(0, AbstractDuration.secondsOf(0));
  }

  /**
   * Constructs an instance of the class under test
   * representing the duration given in hours,
   * minutes, and seconds.
   *
   * @param hours   the hours in the duration
   * @param minutes the minutes in the duration
   * @param seconds the seconds in the duration
   * @return an instance of the class under test
   */
  protected abstract Duration hms(int hours, int minutes, int seconds);

  /**
   * Constructs an instance of the class under test
   * representing the duration given in seconds.
   *
   * @param inSeconds the total seconds in the duration
   * @return an instance of the class under test
   */
  protected abstract Duration sec(long inSeconds);

  public final static class HmsDurationTest extends AbstractDurationTest {
    @Override
    protected Duration hms(int hours, int minutes, int seconds) {
      return new HmsDuration(hours, minutes, seconds);
    }

    @Override
    protected Duration sec(long inSeconds) {
      return new HmsDuration(inSeconds);
    }
  }

  public final static class CompactDurationTest extends AbstractDurationTest {
    @Override
    protected Duration hms(int hours, int minutes, int seconds) {
      return new CompactDuration(hours, minutes, seconds);
    }

    @Override
    protected Duration sec(long inSeconds) {
      return new CompactDuration(inSeconds);
    }
  }

  static long s1 = 328375982;
  static long s2 = 299699;

  Duration d1_23_45 = hms(1, 23, 45);
  Duration d2_03_00 = hms(2, 3, 0);
  Duration d457_03_00 = hms(457, 3, 0);

  @Test
  public void zeroIsZero() {
    assertEquals(0, hms(0, 0, 0).inSeconds());
  }

  @Test
  public void asHmsWorks() {
    assertEquals("1:23:45", d1_23_45.asHms());
    assertEquals("2:03:00", d2_03_00.asHms());
    assertEquals("457:03:00", d457_03_00.asHms());
  }

  @Test
  public void equalsWorks() {
    assertEquals(hms(1, 23, 45), d1_23_45);
    assertEquals(d1_23_45, d1_23_45);
    assertEquals(sec(s1), sec(s1));
    assertEquals(sec(s2), sec(s2));

    assertNotEquals(sec(s1), sec(s2));
    assertNotEquals(sec(s2), sec(s1));
    assertNotEquals(d1_23_45, d2_03_00);
    assertNotEquals(d2_03_00, d1_23_45);
    assertNotEquals(hms(1, 23, 44), hms(1, 23, 45));
  }

  @Test
  public void hashCodeIsHashOfSeconds() {
    assertEquals(Long.hashCode(s1), sec(s1).hashCode());
    assertEquals(Long.hashCode(s2), sec(s2).hashCode());
    assertEquals(Long.hashCode(d1_23_45.inSeconds()), d1_23_45.hashCode());
    assertEquals(Long.hashCode(d457_03_00.inSeconds()),
            d457_03_00.hashCode());
  }

  @Test
  public void hmsConstructorCarriesMinutesAndSeconds() {
    assertEquals("2:33:45", hms(1, 93, 45).asHms());
    assertEquals("2:34:08", hms(2, 33, 68).asHms());
    assertEquals("4:34:34", hms(2, 33, 7294).asHms());
  }

  @Test(expected = IllegalArgumentException.class)
  public void hmsConstructorDisallowsNegative() {
    hms(2, 4, -9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void secondsConstructorDisallowsNegative() {
    sec(-1);
  }

  @Test
  public void inSecondsIsInverseOfSecondsConstructor() {
    assertEquals(s1, sec(s1).inSeconds());
    assertEquals(s2, sec(s2).inSeconds());

    assertEquals(sec(s1),
            sec(sec(s1).inSeconds()));
    assertEquals(sec(s2),
            sec(sec(s2).inSeconds()));

    assertEquals(d1_23_45,
            sec(d1_23_45.inSeconds()));
    assertEquals(d2_03_00,
            sec(d2_03_00.inSeconds()));
    assertEquals(d457_03_00,
            sec(d457_03_00.inSeconds()));
  }

  @Test
  public void _1_23_03_plus_2_14_45_is_3_37_48() {
    assertEquals(hms(3, 37, 48),
            hms(1, 23, 3).plus(hms(2, 14, 45)));
  }

  @Test
  public void plusCarries() {
    assertEquals(hms(3, 38, 18),
            hms(1, 23, 33).plus(hms(2, 14, 45)));
    assertEquals(hms(4, 19, 48),
            hms(1, 23, 3).plus(hms(2, 56, 45)));
    assertEquals(hms(4, 20, 18),
            hms(1, 23, 33).plus(hms(2, 56, 45)));
  }

  @Test
  public void addWorks() {
    assertEquals(s1 + s2, sec(s1).plus(sec(s2)).inSeconds());
    assertEquals(2 * s1 + s2, sec(2 * s1).plus(sec(s2)).inSeconds());
  }
}