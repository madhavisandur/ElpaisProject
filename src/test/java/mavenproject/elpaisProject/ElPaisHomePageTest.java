package mavenproject.elpaisProject;



import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/*Visit the website El País, a Spanish news outlet.

1. Ensure that the website's text is displayed in Spanish.

2.Scrape Articles from the Opinion Section:

	Navigate to the Opinion section of the website.

	Fetch the first five articles in this section.

	Print the title and content of each article in Spanish.

	If available, download and save the cover image of each article to your local machine.

3. Translate Article Headers:

	Use a translation API of your choice, such as:

	Google Translate API

	Translate the title of each article to English.

	Print the translated headers.

4. Analyze Translated Headers:

	From the translated headers, identify any words that are repeated more than twice across all headers combined.

	Print each repeated word along with the count of its occurrences.

5. Cross-Browser Testing:

	Run the solution locally to verify functionality.

	Once validated, execute the solution on BrowserStack across 5 parallel threads, testing across a combination of desktop and mobile browsers.
*/



public class ElPaisHomePageTest {
	private static final Logger logger = LogManager.getLogger(ElPaisHomePageTest.class);

	
	utilities checkString = new utilities();
	private WebDriver driver;
	private ElPaisHomePage homePage ;
	public static void main(String[] args) {
		logger.info("Starting Elpais Test");

	}

	@BeforeClass
	public void setUp() {
		driver = DriverFactory.getDriver();
		driver.get("https://elpais.com/");
		try {
			Thread.sleep(2500);
			//System.out.println("Waiting for Page to Load");
			logger.debug("waiting for page to load");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		homePage = new ElPaisHomePage(driver);

		testLanguage();
		//homePage.open();
	}

	//Check LAnguage is set to Spanish



	public void testLanguage() {


		logger.info("--------------------------------- ");
		//System.out.print(" Language dispalyed is : ");
		Boolean lang = homePage.isSpanish();
		if(lang)
			logger.info("Elpais.com WebPage is displayed in Spanish");
		else
			logger.info("Elpais.com WebPage is not displayed in Spanish");
		logger.info("--------------------------------- ");

	}
	//Get the Header and paragraph of opinion section


	@Test
	public void getArticles()
	{
		logger.info("Starting getArticlles");


		Wait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(500))
				.ignoring(NoSuchElementException.class);

		//long time = 10;

		//WebElement agreeButton=driver.findElement(By.id("didomi-notice-agree-button"));

		List<WebElement> agree=driver.findElements(By.id("didomi-notice-agree-button"));
		List<WebElement> accept=driver.findElements(By.partialLinkText("ACCEPT"));
		if(agree.size()>0)
		{
			agree.get(0).click();
		}
		else
			if(accept.size()>0)
			{
				accept.get(0).click();
			} 
		//agreeButton = driver.findElement(By.id("didomi-notice-agree-button"));

		logger.info(" Either Accept Button is clicked or not displayed");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//WebElement agreeButton=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("didomi-notice-agree-button")));
		//WebElement acceptButton=wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("ACCEPT")));


		//=wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("ACCEPT")));

		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"var el = document.querySelector('[id^=\"container_pbs\"]');" +
						"if(el) { el.remove(); console.log('container_pbs removed'); }"
				);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 🔎 Navigate to "Opinión" section
		//WebElement opinionLink = driver.findElement(By.linkText("Opinión"));
		WebElement opinionLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Opinión")));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//opinionLink.click();

		// Wait and find article blocks (usually under <article>)
		List<WebElement> articles = driver.findElements(By.cssSelector("article"));

		System.out.println("Opinión Articles Found: " + articles.size());
		articles = driver.findElements(By.cssSelector("article"));
		int count = 1;

		for (WebElement article : articles) {
			try {
				// Extract title and link
				WebElement titleElement = article.findElement(By.tagName("a"));
				String title = titleElement.getText().trim();


				WebElement articleContent=article.findElement(By.tagName("p"));
				if(articleContent != null )
				{
					String articleText=articleContent.getText();

					logger.info("---------------------------------");
					logger.info("Article " + count + ": " + title);
					logger.info("---------------------------------");
					logger.info("ArticleContent: " +articleText);
					String translatedText=utilities.translateText(articleText,"es","en");
					checkString.DuplicateWordCount(translatedText);
				}
				if(count==5)
				{
					logger.info("---------------------------------");
					logger.info("Completed scraping articles");
					logger.info("---------------------------------");
					break;
				}
				count++;
			} catch (NoSuchElementException e) {
				// Skip if article structure is non-standard
				break;
			}
		}


	}


	@AfterMethod

	public void tearDown() {
        driver.close();
		driver.quit();
	}
}
