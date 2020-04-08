package login;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.annotations.Test;
import utils.NirmataSetup;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginTests extends NirmataSetup {

	@Test(description = "Login Nirmata Test")
	public void signInTest() {
		test.log(Status.PASS, MarkupHelper.createLabel("Sign Up test started", ExtentColor.GREEN));
        String email=appProperties.properties.getProperty("email");
        String password=appProperties.properties.getProperty("password");

        overviewPage
				.clickTenantName()
				.clickLogOutButton();
		loginPage.setEmailInputField(email)
				.setPasswordInputField(password)
				.clickLoginButton();


		test.log(Status.PASS, "Test of presence of the Welcome Message");

		overviewPage.welcomeMessage.shouldHave(text("Welcome to Nirmata!"));
		test.log(Status.PASS, "Test of the User Logged In performed successfully.");

    }


	@Test(description = "Infinite Sign Up Nirmata ")
	public void signUpTest() {
		loginPage=overviewPage.clickTenantName()
				.clickLogOutButton();

		String url=appProperties.properties.getProperty("url");
		while(true){
			String dateName=getNewDate();
			loginPage.clickSignUpLink();
			loginPage.setNameInputField("and14ever"+dateName);
			loginPage.setEmailInputField("and14eve"+ dateName+"@mail.ru");
			loginPage.clickSignUpButton();
			loginPage.clickAcceptAndProceed();
			loginPage.waitForConfirmationMessage();
			getWebDriver().navigate().to(url);
		}


	}

	public String getNewDate(){
		return new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	}
}

