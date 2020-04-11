package pages.adminDashboard;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class CreateAdminPage extends LibraryUtils {

    private final SelenideElement logoImage=$x("//div[@class='logo']//a//img");
    private final SelenideElement subtitleText=$x("//div[@id='setup-subtitle']");
    private final SelenideElement nameInputField= $x("//input[@id='name']");
    private final SelenideElement companyInputField= $x("//input[@id='company']");
    private final SelenideElement emailInputField= $x("//input[@id='email']");
    private final SelenideElement passwordInputField= $x("//input[@id='password']");
    private final SelenideElement createSystemAdministratorAndTenantButton= $x("//button[@id='setupAdmin']");
    private final SelenideElement completedLabel= $x("//*[contains(.,'Completed')]");

    private final WebDriver driver;

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
