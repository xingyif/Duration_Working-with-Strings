import org.junit.Test;

import cs3500.hw01.duration.Duration;
import cs3500.hw01.publication.*;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * This class checks if the methods work correctly in the webpage class
 */
public class WebpageTest {

  Publication htdp = new Webpage("How to Design Programs",
          "http://www.htdp.org/2003-09-26/Book/curriculum.html", "16 Jan. 2016");
  Publication airbnb = new Webpage("Airbnb",
          "https://zh.airbnb.com/", "18 Jan. 2016");
  Publication empty = new Webpage("", "", "");

  @Test
  public void testCiteApa() {
    assertEquals("How to Design Programs. Retrieved 16 Jan. 2016, from" +
            " http://www.htdp.org/2003-09-26/Book/curriculum.html.",
            htdp.citeApa());
    assertEquals("Airbnb. Retrieved 18 Jan. 2016, from" +
                    " https://zh.airbnb.com/.",
            airbnb.citeApa());
    assertEquals(". Retrieved , from" +
                    " .",
            empty.citeApa());
  }

  @Test
  public void testCiteMla() {
    assertEquals("\"How to Design Programs.\" Web. 16 Jan. 2016" +
            " <http://www.htdp.org/2003-09-26/Book/curriculum.html>.",
            htdp.citeMla());
    assertEquals("\"Airbnb.\" Web. 18 Jan. 2016" +
                    " <https://zh.airbnb.com/>.",
            airbnb.citeMla());
    assertEquals("\".\" Web.  <>.",
            empty.citeMla());
  }

}