package utils;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.codeborne.selenide.*;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.dashboards.OverviewPage;

import java.lang.reflect.Method;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;


public class NirmataSetup {

	protected LoginPage loginPage;
	protected OverviewPage overviewPage;
	private WebDriver driver;
	protected  NirmataApplicationProperties appProperties = new NirmataApplicationProperties();


	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentTest methodInfo;

	private String url = appProperties.properties.getProperty("url");
	private String email = appProperties.properties.getProperty("email");
	private String password = appProperties.properties.getProperty("password");
	private String reportDirectory = appProperties.properties.getProperty("ReportDirectory");

	private String absolutePath = System.getProperty("user.dir");
	private String osName = System.getProperty("os.name");
	private String osVersion = System.getProperty("os.version");
	private String osArch = System.getProperty("os.arch");
	private String javaRuntimeVersion = System.getProperty("java.runtime.version");
	private String host = System.getProperty("user.name");
	private String browserName;
	private String browserVersion;
	private Capabilities cap;


	@BeforeSuite
	public void setUpExtentReport(ITestContext context) {

		Configuration.browser=NirmataCustomWebDriverProvider.class.getName();
		addListener(new Highlighter());

		htmlReporter = new ExtentHtmlReporter(absolutePath + reportDirectory);
		htmlReporter.config().setDocumentTitle("NirmataReport");
		htmlReporter.config().setReportName("Nirmata Automation Report");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.setAnalysisStrategy(AnalysisStrategy.TEST);
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Tester Name", "Catalin Parvana");
		extent.setSystemInfo("Hostname", host);
		extent.setSystemInfo("OS Version", osName + " " + osVersion);
		extent.setSystemInfo("OS Architecture", osArch);
		extent.setSystemInfo("Java Version", javaRuntimeVersion);
	}


	@BeforeMethod
	public void logIn(Method method, ITestResult result) {
		test = extent.createTest(result.getMethod().getDescription()).assignCategory(result.getMethod().getGroups());
		open(url);
		cap = ((EventFiringWebDriver) WebDriverRunner.getWebDriver()).getCapabilities();
		browserName = cap.getBrowserName().toLowerCase();
		browserVersion = cap.getVersion();

//		loginPage = new LoginPage(driver);
//		overviewPage=loginPage
//				 .setEmailInputField(email)
//				.setPasswordInputField(password)
//				.clickLoginButton();
	}


	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.fail(MarkupHelper.createLabel("Test " + result.getName() + " failed", ExtentColor.RED));
			test.fail(result.getThrowable());

			String screenshotName=(result.getMethod().getDescription() + " " + result.getStartMillis());
			String	screenshotPath=screenshot(screenshotName);
			test.fail("Screenshot can be seen below: ").addScreenCaptureFromPath(screenshotPath);
			test.fail("Screenshot path"+screenshotPath);

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass(MarkupHelper.createLabel("Test " + result.getName() + " executed successfully", ExtentColor.GREEN));
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.skip(MarkupHelper.createLabel("Test " + result.getName() + " blocked", ExtentColor.YELLOW));
			test.skip(result.getThrowable());
		}
		WebDriverRunner.getWebDriver().close();
	}

	@AfterSuite
	public void tearDown() {
		extent.setSystemInfo("Browser", browserName+" "+browserVersion);
		extent.flush();
	}



}