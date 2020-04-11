package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import pages.adminDashboard.TenantsPage;
import pages.dashboards.OverviewPage;
import utils.LibraryUtils;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage extends LibraryUtils {

    private final SelenideElement titleSignUp = $x("//div[contains(text(),'Sign in to Nirmata')]");
    private final SelenideElement nirmataLogo = $x("//div[@class='logo']");
    private final SelenideElement nirmataWhiteLogo = $x("//a[@class='navbar-brand']//img");
    private final SelenideElement skipSetupButton = $x("//a[@id='skipSetup']");
    private final SelenideElement emailInputField = $x("//input[@id='email']");
    private final SelenideElement passwordInputField = $x("//input[@id='password']");
    private final SelenideElement loginButton = $x("//button[@id='btnLogin']");
    private final SelenideElement signUpLink=$x("//a[@id='showSignup']");
    private final SelenideElement signInAsNirmataAdministratorButton=$x("//*[text()='Sign in as Nirmata Administrator']");
    private final SelenideElement loginErrorText=$x("//p[@class='help-block']");
    private final SelenideElement loginFormError=$x("//p[@id='form-errors']");
    private final SelenideElement contactSupportLink=$x("//a[text()='contact support']");
    private final SelenideElement dontHaveAccountLink=$x("//a[@id='showSignup']");
    private final SelenideElement forgotPasswordLink=$x("//a[contains(text(),'forgot your password?')]");

    private final WebDriver driver;


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
        clickSkipIntroIfExists();
        return new OverviewPage(driver);
    }

    public TenantsPage clickLoginAsAdminButton() {
        click("Login Button",loginButton);
        return new TenantsPage(driver);
    }

    public SignUpPage clickSignUpLink(){
        click("Sign Up Link",signUpLink);
        return new SignUpPage(driver);
    }

    public LoginPage clickSignInAsNirmataAdministratorButton(){
        click("Sign In As Nirmata Administrator Button",signInAsNirmataAdministratorButton);
        return this;
    }

    public LoginPage waitForPleaseEnterValueForThisFieldError(){
        loginErrorText.shouldBe(visible)
                .should(have(text("Please enter a value for this field.")));
        return this;
    }

    public LoginPage waitForInvalidEmailAddressError(){
        loginErrorText.shouldBe(visible)
                .should(have(text("Invalid email address")));
        return this;
    }

    public LoginPage waitForUnregisteredEmailAddressText(){
        loginFormError.shouldBe(visible)
                .should(have(text("We could not find an account with that email.")))
                .should(have(text("You can try another email or")))
                .should(have(text("contact support")));
        return this;
    }

    public LoginPage waitForContactSupportLink(){
        contactSupportLink.shouldBe(visible)
                .should(have(text("contact support")));
        return this;
    }

    public LoginPage waitForDontHaveAccountLink(){
        dontHaveAccountLink.shouldBe(visible)
                .should(have(text("Don't have an account? Sign up for Nirmata here")));
        return this;
    }

    public LoginPage waitForInvalidPasswordError(){
        loginFormError.shouldBe(visible)
                      .should(have(text("Invalid password. Please try again.")));
        return this;
    }

    public LoginPage waitForForgotPasswordLink(){
        forgotPasswordLink.shouldBe(visible);
        return this;
    }


    public LoginPage choseAccountIfExists(String accountName, String userEmail){
       SelenideElement email=$x("//*[contains(.,'"+userEmail+"')]");
       email.shouldBe(visible);
        SelenideElement account=$x("//*[text()='"+accountName+"']/../../../..");
            if(account.exists()){
                account.click();
            }
        return this;
    }

    public LoginPage pressEnter(){
       passwordInputField.pressEnter();
        return this;
    }

    private void clickSkipIntroIfExists(){
        nirmataWhiteLogo.shouldBe(visible);
        if(skipSetupButton.exists()){
            skipSetupButton.click();
        }
    }
}
