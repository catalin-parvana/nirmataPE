package NirmataPE;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.admin.CreateAdminPage;
import pages.admin.InsideTenantPage;
import pages.admin.TenantsPage;
import utils.NirmataApplicationProperties;
import utils.NirmataSetup;



public class AdminPanelTests extends NirmataSetup {

    protected NirmataApplicationProperties appProperties = new NirmataApplicationProperties();
    private String email = appProperties.properties.getProperty("email");
    private String password = appProperties.properties.getProperty("password");
    private CreateAdminPage createAdminPage;
    private TenantsPage tenantsPage;
    private LoginPage loginPage;
    private InsideTenantPage insideTenatPage;
    private WebDriver driver;



    @Test(description = "Create NirmataPE Admin")
    public void createNirmataPEAdmin()  {
        createAdminPage = new CreateAdminPage(driver);
        createAdminPage
               .setNameInputField("nirmata-automation")
               .setCompanyInputField("nirmata")
               .setEmailInputField(email)
               .setPasswordInputField(password)
               .clickSetupAdminButton();
    }

    @Test(description = "Test Login NirmataPE Admin Account")
    public void loginNirmataPEAdminAccount()  {
        loginPage = new LoginPage(driver);
        loginPage
                .setEmailInputField(email)
                .clickSignInAsNirmataAdministratorButton()
                .setPasswordInputField(password);
        tenantsPage=loginPage.clickLoginAsAdminButton();
    }

    @Test(description = "Create New Tenant")
    public void createNewTenant()  {
        loginNirmataPEAdminAccount();
        tenantsPage
                .clickAddTenantButton()
                .setNameInputField("regression-tenant")
                .setTenantAdminNameInputField("regression-tenant")
                .setTenantAdminEmailInputField("regression-tenant@gmail.com")
                .clickAddButton()
                .isCreatedTenantDisplayed("regression-tenant");
    }

    @Test(description = "Create New User")
    public void createNewUser()  {
        loginNirmataPEAdminAccount();
        insideTenatPage=tenantsPage.clickOnTenant("nirmata");
        insideTenatPage
                .clickAddUserButton()
                .setNameInputField("regression-user")
                .setEmailInputField("regression-user@gmail.com")
                .selectRoleFromDropdown("admin")
                .clickAddButton()
                .isCreatedUserDisplayed("regression-user@gmail.com")
                .isUserAdmin("regression-user@gmail.com");
    }

    @Test(description = "Edit User")
    public void editUser()  {
        loginNirmataPEAdminAccount();
        insideTenatPage=tenantsPage.clickOnTenant("nirmata");
        insideTenatPage
                .clickEditUserButton("regression-user@gmail.com")
                .setNameInputField("regression-user")
                .selectRoleFromDropdown("readonly")
                .clickSaveButton()
                .isUserReadOnly("regression-user@gmail.com")

                .clickEditUserButton("regression-user@gmail.com")
                .setNameInputField("regression-user")
                .selectRoleFromDropdown("devops")
                .clickSaveButton()
                .isUserDevops("regression-user@gmail.com")

                .clickEditUserButton("regression-user@gmail.com")
                .setNameInputField("regression-user")
                .selectRoleFromDropdown("platform")
                .clickSaveButton()
                .isUserPlatform("regression-user@gmail.com")

                .clickEditUserButton("regression-user@gmail.com")
                .setNameInputField("regression-user")
                .selectRoleFromDropdown("admin")
                .clickSaveButton()
                .isUserAdmin("regression-user@gmail.com");

    }

    @Test(description = "Delete User")
    public void deleteUser()  {
        loginNirmataPEAdminAccount();
        insideTenatPage=tenantsPage.clickOnTenant("nirmata");
        insideTenatPage
                .clickDeleteUserButton("regression-user@gmail.com")
                .clickDeleteButton()
                .isDeletedUserDisplayed("regression-user@gmail.com");
    }

}
