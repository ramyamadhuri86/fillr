/* Selenium  : Browser Factory Scripts */
/* Developer :  Karthik Rajagopalan  */
package helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BrowserFactory {
	static int implicitWait = 100;
	static int LoadTimeout = 300;

	// Initialize browser

	public static WebDriver startBrowser(String browser) {
		WebDriver driver = null;
		try {
			if (browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();

			} else if (browser.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				// EdgeOptions options = new EdgeOptions();
				// ((Object) options).AddAdditionalCapability("InPrivate", true);
				// driver = new EdgeDriver(options);

			} else if (browser.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(LoadTimeout, TimeUnit.SECONDS);
			// driver.get(URL);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return driver;

	}

	@SuppressWarnings("deprecation")
	public static WebDriver startBrowserWithOptions(String browser, String downloadPath) {
		WebDriver driver = null;
		downloadPath = downloadPath.replace("/", "\\");
		try {
			if (browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();

//				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				// chromePrefs.put("profile.default_content_settings.popups", 0);
				// chromePrefs.put("download.default_directory", downloadPath);
				// ChromeOptions options = new ChromeOptions();
				// options.setExperimentalOption("prefs", chromePrefs);
				// DesiredCapabilities cap = new DesiredCapabilities();
				// cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				// cap.setCapability(ChromeOptions.CAPABILITY, options);
				// driver = new ChromeDriver(cap);
				driver = new ChromeDriver();
				return driver;
			} else if (browser.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();

				EdgeOptions options = new EdgeOptions();
				EdgeDriverService edgeDriverService = EdgeDriverService.createDefaultService();

				EdgeDriver edgeDriver = new EdgeDriver(edgeDriverService, options);

				Map<String, Object> commandParams = new HashMap<String, Object>();
				commandParams.put("cmd", "Page.setDownloadBehavior");
				Map<String, String> params = new HashMap<String, String>();
				params.put("behavior", "allow");
				params.put("downloadPath", downloadPath);
				commandParams.put("params", params);
				ObjectMapper objectMapper = new ObjectMapper();
				HttpClient httpClient = HttpClientBuilder.create().build();
				String command = objectMapper.writeValueAsString(commandParams);
				String u = edgeDriverService.getUrl().toString() + "/session/" + edgeDriver.getSessionId()
						+ "/chromium/send_command";
				HttpPost request = new HttpPost(u);
				request.addHeader("content-type", "application/json");
				request.setEntity(new StringEntity(command));
				httpClient.execute(request);
				edgeDriver.manage().window().maximize();
				edgeDriver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
				edgeDriver.manage().timeouts().pageLoadTimeout(LoadTimeout, TimeUnit.SECONDS);
				return edgeDriver;
			} else if (browser.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(LoadTimeout, TimeUnit.SECONDS);
			// driver.get(URL);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return driver;

	}

	public static WebDriver startRemoteChrome() throws MalformedURLException {
		WebDriver driver = null;
		/*
		 * //DesiredCapabilities capabilities = DesiredCapabilities.chrome(); // below
		 * capabilities for the node (child) capabilities.setPlatform(Platform.WINDOWS);
		 * // below capabilities for the node (child)
		 * capabilities.setBrowserName("chrome"); // ChromeOptions options=new
		 * ChromeOptions(); // options.addArguments("disable-infobars"); //
		 * capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		 * 
		 * WebDriver driver = new RemoteWebDriver(new
		 * URL("http://10.10.10.156:4444/wd/hub"), capabilities);
		 * 
		 * driver.manage().window().maximize();
		 * driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		 */
		return driver;
	}

	public static WebDriver startChrome() throws MalformedURLException {
		WebDriver driver = null;

		return driver;
	}

	public static WebDriver startInternetExplorer() throws MalformedURLException {
		WebDriver driver = null;

		return driver;
	}

	public static void closeBrowser(WebDriver driver) {
		driver.quit();
	}

}

/*
 * System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
 * FirefoxOptions options = new FirefoxOptions();
 * options.setBinary("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
 * driver = new FirefoxDriver(options);
 * driver.get("http://ppd-pre.lb.redcrossblood.org.au/ppd/"); Download AutoIT to
 * handle wind pop in Selenium web driver Runtime.getRuntime().exec(
 * "C:\\Users\\karajagopalan\\Desktop\\AutoIT\\HandleAuthentication.exe");
 * return driver;
 */
