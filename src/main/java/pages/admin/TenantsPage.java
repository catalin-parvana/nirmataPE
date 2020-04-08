package pages.admin;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Condition.visible;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;


public class TenantsPage extends LibraryUtils {

    private SelenideElement dropdownToggleButton=$x("//a[@class='dropdown-toggle']");
    private SelenideElement switchAccountsButton=$x("//a[contains(.,'Switch Accounts')]");
    private SelenideElement logOutButton=$x("//a[contains(.,'Log Out')]");
    private SelenideElement tenantsPage=$x("//li[@id='menu-tenant']");
    private SelenideElement administratorsLink=$x("//li[@id='menu-admin']");
    private SelenideElement emailServicePage=$x("//li[@id='menu-smtp']");
    private SelenideElement licencePage=$x("//li[@id='menu-license']");
    private SelenideElement modelContentPanelTitle=$x("//div[@id='model-content-panel-actions']");
    private SelenideElement addTenantButton=$x("//div[@id='addTenant']");
    private SelenideElement nameInputField=$x("//input[@id='name']");
    private SelenideElement tenantAdminNameInputField=$x("//input[@id='adminName']");
    private SelenideElement tenantAdminEmailInputField=$x("//input[@id='ownerEmail']");
    private SelenideElement addButton=$x("//button[text()='Add']");
    private SelenideElement tenant;
    private WebDriver driver;
    private boolean found;


    public TenantsPage(WebDriver driver){
        this.driver=driver;
        tenantsPage.shouldBe(visible);
        administratorsLink.shouldBe(visible);
        emailServicePage.shouldBe(visible);
        licencePage.shouldBe(visible);
    }

    public TenantsPage clickDropdownToggle(){
        click("Dropdown Toggle Button",dropdownToggleButton);
        return this;
    }

    public LoginPage clickSwitchAccountButton(){
        click("Switch Account Button",switchAccountsButton);
        return new LoginPage(driver);
    }

    public LoginPage clickLogOutButton(){
        click("Log Out Button",logOutButton);
        return new LoginPage(driver);
    }

    public TenantsPage clickAddTenantButton(){
        click("Add Tenant Button",addTenantButton);
        return this;
    }

    public TenantsPage setNameInputField(String tenantName){
        type("Name Input Field",nameInputField,tenantName);
        return this;
    }

    public TenantsPage setTenantAdminNameInputField(String tenantAdminName){
        type("Tenant Admin Name Input Field",tenantAdminNameInputField,tenantAdminName);
        return this;
    }

    public TenantsPage setTenantAdminEmailInputField(String tenantAdminEmail){
        type("Tenant Admin Email Input Field",tenantAdminEmailInputField,tenantAdminEmail);
        return this;
    }

    public TenantsPage clickAddButton(){
        click("Add Button",addButton);
        addButton.should(disappear);
        return this;
    }

    public InsideTenantPage clickOnTenant(String tenantName){
        tenant=$x("//*[text()='nirmata']/../..");
        click("Tenant "+tenantName,tenant);
        return new InsideTenantPage(driver);
    }

    public TenantsPage isDeletedTenantDisplayed(String tenantName){
        assertFalse(isTenantDisplayed(tenantName),"Tenant Was Not Deleted");
        return this;
    }

    public TenantsPage isCreatedTenantDisplayed(String tenantName){
        assertTrue(isTenantDisplayed(tenantName),"Tenant Was Not Created");
        return this;
    }

    private boolean isTenantDisplayed(String tenantName){
        addTenantButton.shouldBe(visible);
        tenant=$x("//*[text()='nirmata']/../..");
        if(tenant.exists()){
            found=true;
        }else
        {
           found=false;
        }
        return found;
    }

}
