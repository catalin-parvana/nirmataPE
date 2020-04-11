package login;

import org.testng.annotations.Test;
import pages.LoginPage;
import pages.SignUpPage;
import utils.NirmataSetup;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginTests extends NirmataSetup {

	private SignUpPage signUpPage;

	@Test(description = "Login Nirmata Test")
	public void signInTest() {
//		test.log(Status.PASS, MarkupHelper.createLabel("Sign Up test started", ExtentColor.GREEN));
		loginPage = new LoginPage(driver);
		loginPage.setEmailInputField(email)
				.setPasswordInputField(password)
				.clickLoginButton();
//		test.log(Status.PASS, "Test of presence of the Welcome Message");
//		overviewPage.welcomeMessage.shouldHave(text("Welcome to Nirmata!"));
//		test.log(Status.PASS, "Test of the User Logged In performed successfully.");
    }


	@Test(description = "Infinite Sign Up Nirmata ")
	public void signUpTest() {
		String url=appProperties.properties.getProperty("url");
		while(true){
			String dateName=getNewDate();
			loginPage = new LoginPage(driver);
			signUpPage=loginPage.clickSignUpLink();
			signUpPage
					.setNameInputField("and14ever"+dateName)
					.setEmailInputField("and14eve"+ dateName+"@mail.ru")
					.clickSignUpNowButton()
					.clickAcceptAndProceedButton()
					.waitForSignUpCompletedMessage();
			getWebDriver().navigate().to(url);
		}
	}

	@Test(description= "Test Login With Empty Email")
	public void testLoginWithEmptyEmailField(){
		loginPage = new LoginPage(driver);
		loginPage.setEmailInputField("")
				.waitForPleaseEnterValueForThisFieldError()
				.waitForDontHaveAccountLink();
	}

	@Test(description= "Test Login With Invalid Email")
	public void testLoginWithInvalidEmailField(){
		loginPage = new LoginPage(driver);
		loginPage.setEmailInputField("invalid_email")
				.waitForInvalidEmailAddressError();
	}

	@Test(description= "Test Login With Unregistered Email")
	public void testLoginWithUnregisteredEmailField(){
		loginPage = new LoginPage(driver);
		loginPage.setEmailInputField("unregistered_email@gmail.com")
				.waitForUnregisteredEmailAddressText()
				.waitForContactSupportLink();
	}

	@Test(description= "Test Login With Invalid Password")
	public void testLoginWithInvalidPassword(){
		loginPage = new LoginPage(driver);
		loginPage.setEmailInputField(email)
				.setPasswordInputField("invalid")
				.pressEnter()
				.waitForInvalidPasswordError()
				.waitForForgotPasswordLink();
	}

	private String getNewDate(){
		return new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	}
}

