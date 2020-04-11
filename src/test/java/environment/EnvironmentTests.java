package environment;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.environment.EnvironmentsPage;
import pages.environment.InsideEnvironmentPage;
import utils.NirmataSetup;

import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.refresh;

public class EnvironmentTests extends NirmataSetup {

    private EnvironmentsPage environmentsPage;
    private InsideEnvironmentPage insideEnvironmentPage;

    @Test(description = "Test Add Environment")
    @Parameters({"environmentName","clusterName"})
    public void testAddEnvironment(String environmentName, String clusterName){
        login();
        environmentsPage=overviewPage.clickEnvironments();
        environmentsPage.clickAddEnvironment()
                .setInputFieldName(environmentName)
                .selectEnvironmentTypeFromDropdown("medium")
                .selectClusterFromDropdown(clusterName);
        insideEnvironmentPage=environmentsPage.clickFinishButton();
        insideEnvironmentPage
                .waitForClusterConnectedStatus()
                .verifyPanelTitle(environmentName);
        back();
        environmentsPage
                .isCreatedEnvironmentDisplayed(environmentName);

    }

    @Test(description = "Test Delete Environment")
    @Parameters({ "environmentName"})
    public void testDeleteEnvironment(String environmentName){
        login();
        environmentsPage=overviewPage.clickEnvironments();
        insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environmentName);
        insideEnvironmentPage
                .clickActionButton()
                .clickDeleteEnvironmentButton()
                .checkDeleteApplicationsCheckboxIfNecessary()
                .checkDeleteFromClusterCheckboxIfNecessary()
                .setEnvironmentNameIfNecessary(environmentName);
        environmentsPage=insideEnvironmentPage.clickDeleteButton();
        refresh();
        environmentsPage
                .isDeletedEnvironmentDisplayed(environmentName);
    }


    @Test(description = "Test Add Multiple Environments")
    @Parameters({ "environmentName","clusterName","nr"})
    public void testAddMultipleCatalogs(String environmentName,String clusterName,int nr) {
        login();
        environmentsPage=overviewPage.clickEnvironments();
        for (int i = 1; i <= nr; i++) {
            String environment = environmentName + i;
                environmentsPage.clickAddEnvironment()
                        .setInputFieldName(environment)
                        .selectEnvironmentTypeFromDropdown("medium")
                        .selectClusterFromDropdown(clusterName);
                insideEnvironmentPage=environmentsPage.clickFinishButton();
                insideEnvironmentPage
                        .waitForClusterConnectedStatus()
                        .verifyPanelTitle(environment);
                back();
                refresh();
                environmentsPage
                        .isCreatedEnvironmentDisplayed(environment);
        }
    }

    @Test(description = "Test Delete Multiple Environments")
    @Parameters({ "environmentName","nr"})
    public void testDeleteMultipleCatalogs(String environmentName, int nr) {
        login();
        environmentsPage=overviewPage.clickEnvironments();
        for (int i = 1; i <= nr; i++) {
            String environment = environmentName + i;
            insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environment);
            insideEnvironmentPage
                    .clickActionButton()
                    .clickDeleteEnvironmentButton()
                    .checkDeleteApplicationsCheckboxIfNecessary()
                    .checkDeleteFromClusterCheckboxIfNecessary()
                    .setEnvironmentNameIfNecessary(environment);
            environmentsPage=insideEnvironmentPage.clickDeleteButton();
            environmentsPage
                    .isDeletedEnvironmentDisplayed(environment);
            refresh();
        }
    }

}
