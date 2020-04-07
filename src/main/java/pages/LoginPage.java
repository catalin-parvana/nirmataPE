package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import pages.dashboards.OverviewPage;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage extends LibraryUtils {

    private SelenideElement titleSignUp = $x("//div[contains(text(),'Sign in to Nirmata')]");
    private SelenideElement nirmataLogo = $x("//div[@class='logo']//a//img");

    private SelenideElement emailInputField = $x("//input[@id='email']");
    private SelenideElement passwordInputField = $x("//input[@id='password']");
    private SelenideElement loginButton = $x("//button[@id='btnLogin']");
    private SelenideElement welcomeMessage=$x("//*[@id='user-welcome']");
    private SelenideElement signUpLink=$x("//a[@id='showSignup']");


    private SelenideElement inputNameField=$x("//input[@id='name']");
    private SelenideElement inputEmailField=$x("//input[@id='email']");
    private SelenideElement signUpButton=$x("//button[@id='btnSignupEmail']");
    private SelenideElement acceptAndProceed=$x("//button[contains(.,'Accept and Proceed')]");
    private SelenideElement confirmationMessage=$x("//div[@class='login-title']");
    private WebDriver driver;


    public LoginPage(WebDriver driver) {
        this.driver=driver;
        nirmataLogo.shouldBe(visible);
        titleSignUp.shouldBe(visible);
        loginButton.shouldBe(visible);
        emailInputField.shouldBe(visible);
    }

    public LoginPage setEmailInputField(String email) {
        type("Email Input Field",emailInputField,email);
        click("Login Button",loginButton);
        return this;
    }

    public LoginPage setPasswordInputField(String password) {
        type("Password Input Field",passwordInputField,password);
        return this;
    }

    public OverviewPage clickLoginButton() {
        click("Login Button",loginButton);
        return new OverviewPage(driver);
    }

    public LoginPage clickSignUpLink(){
        click("Sign Up Link",signUpLink);
        return this;
    }

    public LoginPage setInputName(String name){
        type("Input Name Field",inputNameField,name);
        return this;
    }

    public LoginPage setInputEmail(String email){
        type("Input Email Field",inputEmailField,email);
        return this;
    }


    public LoginPage clickSignUpButton(){
        click("Accept And Proceed",signUpButton);
        return this;
    }

    public LoginPage clickAcceptAndProceed(){
        click("Accept And Proceed",acceptAndProceed);
        return this;
    }

    public LoginPage waitForConfirmationMessage(){
        confirmationMessage.shouldBe(visible);
        return this;
    }
}
