package mainclass;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;



public class baseclass{

	public static WebDriver driver;
	public static Properties prop;

	static {

		try {
			FileInputStream file = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/java/Resources/evn.properties");
			prop = new Properties();
			prop.load(file);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

@Before

	public void setup() {

		String browsername = prop.getProperty("browser");

//method 1 :--->  By using 

		switch (browsername) {

		case "chrome":
			driver = new ChromeDriver();
			break;

		case "chromeincognito":

			ChromeOptions option = new ChromeOptions();
			option.addArguments("--Incognito");
			driver = new ChromeDriver(option);

			break;

		case "firefox":

			driver = new FirefoxDriver();
			break;

		}

//method 2  :---> If-else

		if (browsername.equals("chrome")) {

			driver = new ChromeDriver();
		}

		else if (browsername.equals("firefox")) {

			driver = new FirefoxDriver();

		}

		else if (browsername.equals("chromeincognito")) {
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--Incognito");
			driver = new ChromeDriver(option);

		}

		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));

	}

	public void select_method_dropdown(WebElement ele, String type, String value) {

		Select slc = new Select(ele);

		switch (type) {

		case "visibletext":
			slc.selectByVisibleText(value);
			break;

		case "index":

			slc.selectByIndex(Integer.parseInt(value));
			break;
		case "value":

			slc.selectByValue(value);
			break;

		}

	}

	public void actionclass(WebElement ele, String methodtype) {

		Actions acton= new Actions(driver);

		switch (methodtype) {
		case "movehover":
			acton.moveToElement(ele).build().perform();
			break;

		case "click":

			acton.click(ele).build().perform();
			break;
		}

	}

	public void alert() {

	}

	public void threadsleep() throws InterruptedException {

		Thread.sleep(5000);

	}

	public void explicitwait(int timeout, String condition, WebElement ele) {

		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		switch (condition) {
		case "alert":
			Wait.until(ExpectedConditions.alertIsPresent());
			break;

		case "clickable":
			Wait.until(ExpectedConditions.elementToBeClickable(ele));
			break;

		case "visible":
			Wait.until(ExpectedConditions.visibilityOf(ele));
			break;

		default:
			break;
		}

	}

	public void bootstrapdropdown(List<WebElement> list, String value) {

		for (WebElement ele : list) {

			String eletext = ele.getText();

			if (eletext.equals(value)) {

				ele.click();

				break;
			}

		}

	}

	public void handleframe(String name, String type) {

		switch (type) {
		case "name":
			driver.switchTo().frame(name);
			break;
		case "index":
			driver.switchTo().frame(Integer.parseInt(type));

		default:
			break;
		}

	}

	public void switchwindow(String title) {

		Set<String> window = driver.getWindowHandles();

		for (String st : window) {

			driver.switchTo().window(st);

			String currenttitle = driver.getTitle();
			if (currenttitle.equals(title)) {

			}

			break;
		}

	}

	public void handlealert(String type) {

		Alert a = driver.switchTo().alert();

		switch (type) {
		case "select":
			a.accept();
			break;

		case "dismiss":
			a.dismiss();
			break;

		default:
			break;
		}

	}

	public void clickOnelement(WebElement ele) {

		explicitwait(10, "clickable", ele);

		try {
			ele.click();
		} catch (Exception e) {

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("argument[0].click()", ele);

		}
	}

	public void validatetext(WebElement ele, String expected) {

		String actualva = ele.getText();

		assertEquals(actualva, expected);

	}

	@After

	public void teardown(Scenario s) throws IOException {

		if (s.isFailed()) {

			TakesScreenshot sc = (TakesScreenshot) driver;
			File src = sc.getScreenshotAs(OutputType.FILE);
			FileHandler.copy(src, new File("Screenshot/" + s.getName() + ".png"));
		}

		driver.quit();
	}

}

