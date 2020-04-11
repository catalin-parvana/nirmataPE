package pages;

import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Selenide.$x;

public class SignUpPage extends LibraryUtils {

    private final SelenideElement nameInputField=$x("//input[@id='name']");
    private final SelenideElement emailInputField=$x("//input[@id='email']");
    private final SelenideElement companyNameInputField=$x("//input[@id='company']");
    private final SelenideElement phoneNumberInputField=$x("//input[@id='phone']");
    private final SelenideElement signUpGithubButton=$x("//button[@id='btnSignupGithub']");
    private final SelenideElement signUpGoogleButton=$x("//button[@id='btnSignupGoogle']");
    private final SelenideElement loginTitle=$x("//div[@class='login-title']");
    private final SelenideElement signUpNowButton=$x("//button[@id='btnSignupEmail']");
    private final SelenideElement acceptAndProceedButton=$x("//button[contains(.,'Accept and Proceed')]");
//    private SelenideElement signUpCompletedMessage=$x("//div[@class='login-title']");
    private final SelenideElement alertInfoBox=$x("//div[@class='alert alert-info']");
    private final SelenideElement notReceivedAnEmailMessage=$x("//span[contains(.,'Not received an email?')]");
    private final SelenideElement contactSupportLink=$x("//a[contains(text(),'Contact support')]");
    private final WebDriver driver;

    public SignUpPage(WebDriver driver){
        this.driver=driver;
        signUpGithubButton.shouldBe(visible);
        signUpGoogleButton.shouldBe(visible);
        loginTitle.shouldBe(visible)
                .should(have(text("Sign up for a Free Trial")));
    }

    public SignUpPage setNameInputField(String name){
        type("Name Input Field",nameInputField,name);
        return this;
    }

    public SignUpPage setEmailInputField(String email){
        type("Email Input Field",emailInputField,email);
        return this;
    }

    public SignUpPage setCompanyNameInputField(String name){
        type("Company Name Input Field",companyNameInputField,name);
        return this;
    }

    public SignUpPage setPhoneNumberInputField(String name){
        type("Phone Number Input Field",phoneNumberInputField,name);
        return this;
    }

    public SignUpPage clickSignUpNowButton(){
        click("Sign Up Now Button",signUpNowButton);
        return this;
    }

    public SignUpPage clickSignUpGithubButton(){
        click("Sign Up Github Button",signUpGithubButton);
        return this;
    }

    public SignUpPage clickSignUpGoogleButton(){
        click("Sign Up Google Button",signUpGoogleButton);
        return this;
    }

    public SignUpPage clickAcceptAndProceedButton(){
        click("Accept and Proceed Button",acceptAndProceedButton);
        acceptAndProceedButton.should(disappear);
        return this;
    }

    public SignUpPage waitForSignUpCompletedMessage(){
        loginTitle.should(have(text("Sign up completed")));
        return this;
    }

    public SignUpPage waitForYourAccountHasBeenCreatedMessage(){
        alertInfoBox.should(have(text("Your account has been created! Please check your email, and follow the instructions to start using Nirmata.")));
        return this;
    }

    public SignUpPage waitForNotReceivedAnEmailMessage(){
        notReceivedAnEmailMessage.shouldBe(visible);
        return this;
    }

    public SignUpPage waitForContactSupportLink(){
        contactSupportLink.shouldBe(visible);
        return this;
    }

}
