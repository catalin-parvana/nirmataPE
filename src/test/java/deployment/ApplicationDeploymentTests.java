package deployment;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.environment.EnvironmentsPage;
import pages.environment.InsideDeploymentPage;
import pages.environment.InsideRunningApplicationPage;
import pages.environment.InsideEnvironmentPage;
import utils.NirmataSetup;


public class ApplicationDeploymentTests  extends NirmataSetup {
    private EnvironmentsPage environmentsPage;
    private InsideEnvironmentPage insideEnvironmentPage;
    private InsideRunningApplicationPage insideRunningApplicationPage;
    private InsideDeploymentPage insideDeploymentPage;

    @Test(description = "Test Deployment Page Title")
    @Parameters({ "environmentName","runningApplicationName"})
    public void testDeploymentPageTitle(String environmentName, String runningApplicationName) {
        login();
        environmentsPage=overviewPage.clickEnvironments();
        insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environmentName);
        insideRunningApplicationPage =insideEnvironmentPage.clickRunningApplicationWithName(runningApplicationName);
        insideRunningApplicationPage.verifyPageTitle(environmentName,runningApplicationName);
    }

    @Test(description = "Test Deployment Page Panel Title")
    @Parameters({ "environmentName","runningApplicationName"})
    public void testDeploymentPagePanelTitle(String environmentName, String runningApplicationName) {
        login();
        environmentsPage=overviewPage.clickEnvironments();
        insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environmentName);
        insideRunningApplicationPage =insideEnvironmentPage.clickRunningApplicationWithName(runningApplicationName);
        insideRunningApplicationPage
                .verifyPanelTitle(runningApplicationName);
    }

    @Test(description = "Test Run An Application")
    @Parameters({ "environmentName","runningApplicationName","appName"})
    public void testRunAnApplication(String environmentName,String runningApplicationName,String appName){
        login();
        environmentsPage=overviewPage.clickEnvironments();
        insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environmentName);
        insideEnvironmentPage
                .clickActionButton()
                .clickRunAnApplicationButton()
                .setNameInputField(runningApplicationName)
                .selectUpstreamTypeFromDropdown("Catalog")
                .selectCatalogApplicationFromDropdown(appName);
        insideRunningApplicationPage =insideEnvironmentPage.clickRunApplicationButton();
        insideRunningApplicationPage
                .waitForExecutingApplicationState()
//                .waitForDegradedApplicationState()
                .waitForRunningApplicationState()
                .verifyPanelTitle(runningApplicationName);
    }

    @Test(description = "Test Delete Deployed Application")
    @Parameters({ "environmentName","runningApplicationName"})
    public void testDeleteDeployedApplication(String environmentName,String runningApplicationName) {
        login();
        environmentsPage=overviewPage.clickEnvironments();
        insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environmentName);
        insideRunningApplicationPage =insideEnvironmentPage.clickRunningApplicationWithName(runningApplicationName);
        insideRunningApplicationPage
                .clickActionButton()
                .clickDeleteApplicationButton()
                .checkDeleteOnClusterIfNecessary()
                .setApplicationNameToDeleteIfNecessary(runningApplicationName);
        insideEnvironmentPage= insideRunningApplicationPage.clickDeleteButton();
        insideEnvironmentPage
                .verifyPanelTitle(environmentName)
                .isDeletedDeploymentDisplayed(runningApplicationName);
    }


    @Test(description = "Test Import Yaml To Deployed Application")
    @Parameters({ "environmentName","runningApplicationName"})
    public void testImportYamlToDeployedApplication(String environmentName, String runningApplicationName){
        login();
        environmentsPage=overviewPage.clickEnvironments();
        insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environmentName);
        insideRunningApplicationPage =insideEnvironmentPage.clickRunningApplicationWithName(runningApplicationName);
        insideRunningApplicationPage
                .clickActionButton()
                .clickImportToApplicationButton()
                .setYamlFile("app")
                .clickImportButton()
                .isCreatedDeploymentDisplayed("hello-deployment")
                .seeDeploymentDetails("hello-deployment")
                .waitForUnknownDeploymentState("hello-deployment")
                .waitForRunningDeploymentState("hello-deployment")
                .isCreatedDeploymentDisplayed("hello-deployment");
    }

    @Test(description = "Test Delete Deployment Applied")
    @Parameters({ "environmentName","runningApplicationName"})
    public void testDeleteDeployment(String environmentName, String runningApplicationName){
        login();
        environmentsPage=overviewPage.clickEnvironments();
        insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environmentName);
        insideRunningApplicationPage =insideEnvironmentPage.clickRunningApplicationWithName(runningApplicationName);
        insideDeploymentPage=insideRunningApplicationPage.clickOnDeployment("hello-deployment");
        insideDeploymentPage.clickActionButton()
                .clickDeleteDeploymentButton()
                .setDeploymentNameToDelete("hello-deployment")
                .clickDeleteButton()
                .isDeletedDeploymentDisplayed("hello-deployment");
    }

    @Test(description = "Test Clone Deployed Application")
    @Parameters({ "environmentName","runningApplicationName","environmentName2"})
    public void testCloneDeployedApplication(String environmentName, String runningApplicationName, String environmentName2) {
        login();
        environmentsPage=overviewPage.clickEnvironments();
        insideEnvironmentPage=environmentsPage.clickOnEnvironmentWithName(environmentName);
        insideRunningApplicationPage =insideEnvironmentPage.clickRunningApplicationWithName(runningApplicationName);
        insideRunningApplicationPage.clickActionButton()
                .clickCloneApplicationButton()
                .setNewNameForClonedApplication("regression-deployed-app-clone")
                .selectEnvironmentFromDropdown(environmentName2)
                .clickCloneButton();
    }

}
