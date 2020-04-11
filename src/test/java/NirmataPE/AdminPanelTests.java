package NirmataPE;

import org.testng.annotations.Test;
import pages.adminDashboard.CreateAdminPage;
import pages.adminDashboard.InsideTenantPage;
import utils.NirmataSetup;



public class AdminPanelTests extends NirmataSetup {

    private CreateAdminPage createAdminPage;
    private InsideTenantPage insideTenatPage;



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

    @Test(description = "Create New Tenant")
    public void createNewTenant()  {
        loginNirmataPEAdminDashboard();
        tenantsPage
                .clickAddTenantButton()
                .setNameInputField("regression-tenant")
//                .setTenantAdminNameInputField("regression-tenant")
                .setTenantAdminEmailInputField("regression-tenant@gmail.com")
                .clickAddButton()
                .isCreatedTenantDisplayed("regression-tenant");
    }

    @Test(description = "Create New User")
    public void createNewUser()  {
        loginNirmataPEAdminDashboard();
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
        loginNirmataPEAdminDashboard();
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
        loginNirmataPEAdminDashboard();
        insideTenatPage=tenantsPage.clickOnTenant("nirmata");
        insideTenatPage
                .clickDeleteUserButton("regression-user@gmail.com")
                .clickDeleteButton()
                .isDeletedUserDisplayed("regression-user@gmail.com");
    }

}
