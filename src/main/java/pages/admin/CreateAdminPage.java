package pages.admin;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class CreateAdminPage extends LibraryUtils {

    private SelenideElement logoImage=$x("//div[@class='logo']//a//img");
    private SelenideElement subtitleText=$x("//div[@id='setup-subtitle']");
    private SelenideElement nameInputField= $x("//input[@id='name']");
    private SelenideElement companyInputField= $x("//input[@id='company']");
    private SelenideElement emailInputField= $x("//input[@id='email']");
    private SelenideElement passwordInputField= $x("//input[@id='password']");
    private SelenideElement createSystemAdministratorAndTenantButton= $x("//button[@id='setupAdmin']");
    private SelenideElement completedLabel= $x("//*[contains(.,'Completed')]");

    private WebDriver driver;

    public CreateAdminPage(WebDriver driver){
        this.driver=driver;
        logoImage
                .shouldBe(visible);
        subtitleText
                .shouldBe(visible)
                .shouldHave(text("Create a System Administrator and Tenant"));
        createSystemAdministratorAndTenantButton
                .shouldBe(visible);

    }

    public CreateAdminPage setNameInputField(String name){
        type("Name Input Field",nameInputField,name);
        return this;
    }

    public CreateAdminPage setCompanyInputField(String company){
        type("Company Input Field",companyInputField,company);
        return this;
    }

    public CreateAdminPage setEmailInputField(String email){
        type("Email Input Field",emailInputField,email);
        return this;
    }

    public CreateAdminPage setPasswordInputField(String password){
        type("Password Input Field",passwordInputField,password);
        return this;
    }

    public TenantsPage clickSetupAdminButton(){
        click("Setup Admin Button",createSystemAdministratorAndTenantButton);
        createSystemAdministratorAndTenantButton.should(disappear);
        completedLabel.shouldBe(visible);
        return new TenantsPage(driver);
    }

}
