package application;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.catalog.catalog.CatalogsPage;
import pages.catalog.catalog.InsideApplicationPage;
import pages.catalog.catalog.InsideCatalogPage;
import pages.environment.InsideDeployedApplicationPage;
import pages.environment.InsideEnvironmentPage;
import utils.NirmataSetup;


public class ApplicationDeploymentTests extends NirmataSetup {

    private CatalogsPage catalogsPage;
    private InsideCatalogPage insideCatalogPage;
    private InsideApplicationPage insideApplicationPage;
    private InsideDeployedApplicationPage insideDeployedApplicationPage;
    private InsideEnvironmentPage insideEnvironmentPage;

    @Test(description = "Run Application On Environment")
    @Parameters({ "catalogName","appName","appDeployName","environmentName"})
    public void testRunApplicationOnEnvironment(String catalogName,String appName,String appDeployName,String environmentName){
        catalogsPage=overviewPage.clickCatalog();
        insideCatalogPage=catalogsPage.clickOnCatalogWithName(catalogName);
        insideApplicationPage=insideCatalogPage.clickOnApplicationWithName(appName);
        insideApplicationPage
                .clickRunApplication()
                .setRunName(appDeployName)
                .selectEnvironmentFromDropdown(environmentName);
        insideDeployedApplicationPage=insideApplicationPage.clickRunApplicationOnEnvironment();
        insideDeployedApplicationPage
                .waitForExecutingApplicationState()
//                .waitForDegradedApplicationState()
                .waitForRunningApplicationState()
                .verifyPanelTitle(appDeployName);
    }

    @Test(description = "Delete Deployed Application")
    @Parameters({ "catalogName","appName","appDeployName","environmentName"})
    public void testDeleteDeployedApplication(String catalogName,String appName,String appDeployName,String environmentName){
        catalogsPage=overviewPage.clickCatalog();
        insideCatalogPage=catalogsPage.clickOnCatalogWithName(catalogName);
        insideApplicationPage=insideCatalogPage.clickOnApplicationWithName(appName);
        insideApplicationPage
                .clickToViewLaunchedApplication();
        insideDeployedApplicationPage=insideApplicationPage.clickRunningApplicationLink(appDeployName);
        insideDeployedApplicationPage
                .clickActionButton()
                .clickDeleteApplicationButton()
                .checkDeleteOnClusterIfNecessary()
                .setApplicationNameToDeleteIfNecessary(appDeployName);
        insideEnvironmentPage=insideDeployedApplicationPage.clickDeleteButton();
        insideEnvironmentPage
                .isDeletedDeploymentDisplayed(appDeployName);
    }
}
