package mavenproject.elpaisProject;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElPaisHomePage {

    private WebDriver driver;
    private String url = "https://elpais.com/";

    // Locator for Language
 
    private By Language = By.xpath("//meta[@name='lang']");


   // Constructor
    public ElPaisHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(url);
    }

// Return the boolean value if spanish is detected
    public boolean isSpanish() {
    	 
        String htmlLang = driver.findElement(By.tagName("html")).getAttribute("lang");
    	if(htmlLang.compareToIgnoreCase("es-ES")==0)
    		return true;
    	else
    		return false;
    	
    }


}
