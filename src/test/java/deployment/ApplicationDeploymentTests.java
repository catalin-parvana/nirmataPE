package deployment;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.environment.EnvironmentsPage;
import pages.environment.InsideDeployedApplicationPage;
import pages.environment.InsideEnvironmentPage;
import utils.NirmataSetup;


public class ApplicationDeploymentTests  extends NirmataSetup {
    private EnvironmentsPage environmentsPage;
    private InsideEnvironmentPage insideEnvironmentPage;
    private InsideDeployedApplicationPage insideDeployedApplicationPage;

    @Test(description = "Test Deployment Page Title")
    @Parameters({ "environmentName","appDeployName"})
    public void testDeploymentPageTitle(String environmentName, String appDeployName) {
        environmentsPage=overviewPage.clickEnvironments();
        insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environmentName);
        insideDeployedApplicationPage=insideEnvironmentPage.clickRunningApplicationLink(appDeployName);
        insideDeployedApplicationPage.verifyPageTitle(environmentName,appDeployName);
    }

    @Test(description = "Test Deployment Page Panel Title")
    @Parameters({ "environmentName","appDeployName"})
    public void testDeploymentPagePanelTitle(String environmentName, String appDeployName) {
        environmentsPage=overviewPage.clickEnvironments();
        insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environmentName);
        insideDeployedApplicationPage=insideEnvironmentPage.clickRunningApplicationLink(appDeployName);
        insideDeployedApplicationPage
                .verifyPanelTitle(appDeployName);
    }

    @Test(description = "Test Run An Application")
    @Parameters({ "environmentName","appDeployName","appName"})
    public void testRunAnApplication(String environmentName,String appDeployName,String appName){
        environmentsPage=overviewPage.clickEnvironments();
        insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environmentName);
        insideEnvironmentPage
                .clickActionButton()
                .clickRunAnApplicationButton()
                .setNameInputField(appDeployName)
                .selectUpstreamTypeFromDropdown("Catalog")
                .selectCatalogApplicationFromDropdown(appName);
        insideDeployedApplicationPage=insideEnvironmentPage.clickRunApplicationButton();
        insideDeployedApplicationPage
                .waitForExecutingApplicationState()
//                .waitForDegradedApplicationState()
                .waitForRunningApplicationState()
                .verifyPanelTitle(appDeployName);
    }

    @Test(description = "Test Delete Deployed Application")
    @Parameters({ "environmentName","appDeployName"})
    public void testDeleteDeployedApplication(String environmentName,String appDeployName) {
        environmentsPage=overviewPage.clickEnvironments();
        insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environmentName);
        insideDeployedApplicationPage=insideEnvironmentPage.clickRunningApplicationLink(appDeployName);
        insideDeployedApplicationPage
                .clickActionButton()
                .clickDeleteApplicationButton()
                .checkDeleteOnClusterIfNecessary()
                .setApplicationNameToDeleteIfNecessary(appDeployName);
        insideEnvironmentPage=insideDeployedApplicationPage.clickDeleteButton();
        insideEnvironmentPage
                .verifyPanelTitle(environmentName)
                .isDeletedDeploymentDisplayed(appDeployName);
    }


    @Test(description = "Test Import Yaml To Deployed Application")
    @Parameters({ "environmentName","appDeployName"})
    public void testImportYamlToDeployedApplication(String environmentName, String appDeployName){
        environmentsPage=overviewPage.clickEnvironments();
        insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environmentName);
        insideDeployedApplicationPage=insideEnvironmentPage.clickRunningApplicationLink(appDeployName);
        insideDeployedApplicationPage
                .clickActionButton()
                .clickImportToApplicationButton()
                .setYamlFile("app")
                .clickImportButton()
                .waitForExecutingDeploymentState("hello-deployment")
                .waitForRunningDeploymentState("hello-deployment")
                .isCreatedDeploymentDisplayed("hello-deployment");
    }

    @Test(description = "Test Delete Deployment Applied")
    @Parameters({ "environmentName","appDeployName"})
    public void testDeleteDeployment(String environmentName, String appDeployName){
        environmentsPage=overviewPage.clickEnvironments();
        insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environmentName);
        insideDeployedApplicationPage=insideEnvironmentPage.clickRunningApplicationLink(appDeployName);
        insideDeployedApplicationPage
                .clickOnDeployment("hello-deployment")
                .clickActionButton()
                .clickDeleteDeploymentButton()
                .setDeploymentNameToDelete("hello-deployment")
                .clickDelete()
                .isDeletedDeploymentDisplayed("hello-deployment");
    }

    @Test(description = "Test Clone Deployed Application")
    @Parameters({ "environmentName","appDeployName","environmentName2"})
    public void testCloneDeployedApplication(String environmentName, String appDeployName, String environmentName2) {
        environmentsPage=overviewPage.clickEnvironments();
        insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environmentName);
        insideDeployedApplicationPage=insideEnvironmentPage.clickRunningApplicationLink(appDeployName);
        insideDeployedApplicationPage.clickActionButton()
                .clickCloneApplicationButton()
                .setNewNameForClonedApplication("regression-deployed-app-clone")
                .selectEnvironmentFromDropdown(environmentName2)
                .clickCloneButton();
    }

}
