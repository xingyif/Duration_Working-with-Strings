package cs3500.hw01.publication;


/**
 * Represents bibliographic information for web pages.
 */
public class Webpage implements Publication {
  private final String title, url, retrieved;

  /**
   * construct a webpage
   *
   * @param title     the title of the webpage
   * @param url       the url of the webpage
   * @param retrieved the retrieved of the webpage
   */
  public Webpage(String title, String url, String retrieved) {
    this.title = title;
    this.url = url;
    this.retrieved = retrieved;
  }

  @Override
  public String citeApa() {
    return String.format("%s. Retrieved %s, from %s.", title, retrieved, url);
  }

  @Override
  public String citeMla() {
    return String.format("\"%s.\" Web. %s <%s>.", title, retrieved, url);
  }

}