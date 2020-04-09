package application;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.catalog.catalog.CatalogsPage;
import pages.catalog.catalog.InsideApplicationPage;
import pages.catalog.catalog.InsideCatalogPage;
import pages.environment.InsideRunningApplicationPage;
import pages.environment.InsideEnvironmentPage;
import utils.NirmataSetup;


public class ApplicationDeploymentTests extends NirmataSetup {

    private CatalogsPage catalogsPage;
    private InsideCatalogPage insideCatalogPage;
    private InsideApplicationPage insideApplicationPage;
    private InsideRunningApplicationPage insideRunningApplicationPage;
    private InsideEnvironmentPage insideEnvironmentPage;

    @Test(description = "Run Application On Environment")
    @Parameters({ "catalogName","appName","runningApplicationName","environmentName"})
    public void testRunApplicationOnEnvironment(String catalogName,String appName,String runningApplicationName,String environmentName){
        login();
        catalogsPage=overviewPage.clickCatalog();
        insideCatalogPage=catalogsPage.clickOnCatalogWithName(catalogName);
        insideApplicationPage=insideCatalogPage.clickOnApplicationWithName(appName);
        insideApplicationPage
                .clickRunApplication()
                .setRunName(runningApplicationName)
                .selectEnvironmentFromDropdown(environmentName);
        insideRunningApplicationPage =insideApplicationPage.clickRunApplicationOnEnvironment();
        insideRunningApplicationPage
                .waitForExecutingApplicationState()
//                .waitForDegradedApplicationState()
                .waitForRunningApplicationState()
                .verifyPanelTitle(runningApplicationName);
    }

    @Test(description = "Delete Deployed Application")
    @Parameters({ "catalogName","appName","runningApplicationName","environmentName"})
    public void testDeleteRunningApplication(String catalogName,String appName,String runningApplicationName,String environmentName){
        login();
        catalogsPage=overviewPage.clickCatalog();
        insideCatalogPage=catalogsPage.clickOnCatalogWithName(catalogName);
        insideApplicationPage=insideCatalogPage.clickOnApplicationWithName(appName);
        insideApplicationPage
                .clickToViewLaunchedApplication();
        insideRunningApplicationPage =insideApplicationPage.clickRunningApplicationLink(runningApplicationName);
        insideRunningApplicationPage
                .clickActionButton()
                .clickDeleteApplicationButton()
                .checkDeleteOnClusterIfNecessary()
                .setApplicationNameToDeleteIfNecessary(runningApplicationName);
        insideEnvironmentPage= insideRunningApplicationPage.clickDeleteButton();
        insideEnvironmentPage
                .isDeletedDeploymentDisplayed(runningApplicationName);
    }
}
