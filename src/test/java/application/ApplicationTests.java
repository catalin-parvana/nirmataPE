package application;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.catalog.catalog.CatalogsPage;
import pages.catalog.catalog.InsideApplicationPage;
import pages.catalog.catalog.InsideCatalogPage;
import utils.NirmataSetup;

import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.refresh;

public class ApplicationTests extends NirmataSetup {
    private CatalogsPage catalogsPage;
    private InsideCatalogPage insideCatalogPage;
    private InsideApplicationPage insideApplicationPage;

    @Test(description = "Test Inside Application Page Title")
    @Parameters({ "catalogName","appName"})
    public void testInsideApplicationPageTitle(String catalogName, String appName){
        login();
        catalogsPage =overviewPage.clickCatalog();
        insideCatalogPage= catalogsPage.clickOnCatalogWithName(catalogName);
        insideApplicationPage=insideCatalogPage.clickOnApplicationWithName(appName);
        insideApplicationPage
                .verifyPageTitle(appName);
    }

    @Test(description = "Test Create Application")
    @Parameters({ "catalogName","appName"})
    public void testCreateApplication(String catalogName, String appName){
        login();
        catalogsPage =overviewPage.clickCatalog();
        insideCatalogPage= catalogsPage.clickOnCatalogWithName(catalogName);
        insideCatalogPage
                .clickAddApplicationButton()
                .setApplicationName(appName)
                .setYamlFile("guestbook");
        insideApplicationPage=insideCatalogPage.clickCreateApplicationButton();
        insideApplicationPage
                .verifyPanelTitle(appName);
        back();
        refresh();
       insideCatalogPage
               .isCreatedApplicationDisplayed(appName);

    }

    @Test(description = "Test Delete Application")
    @Parameters({ "catalogName","appName"})
    public void testDeleteApplication(String catalogName, String appName){
        login();
        catalogsPage =overviewPage.clickCatalog();
        insideCatalogPage= catalogsPage.clickOnCatalogWithName(catalogName);
        insideApplicationPage=insideCatalogPage.clickOnApplicationWithName(appName);
        insideApplicationPage
                .clickActionButton()
                .clickDeleteApplicationButton()
                .setApplicationNameIfNecessary(appName);
        insideCatalogPage=insideApplicationPage.clickDeleteButton();
        insideCatalogPage
                .verifyPanelTitle(catalogName)
                .isDeletedApplicationDisplayed(appName);
    }

    @Test(description = "Test Rename Application")
    @Parameters({ "catalogName","appName","newAppName"})
    public void testRenameApplication( String catalogName,String appName, String newAppName){
        login();
        catalogsPage =overviewPage.clickCatalog();
        insideCatalogPage= catalogsPage.clickOnCatalogWithName(catalogName);
        insideApplicationPage=insideCatalogPage.clickOnApplicationWithName(appName);
        insideApplicationPage
                .clickActionButton()
                .clickEditApplicationButton()
                .setNewApplicationName(newAppName)
                .clickSaveButton();
        insideApplicationPage
                .verifyPanelTitle(appName);
    }

    @Test(description = "Test Import YAML In Application")
    @Parameters({"appName","catalogName"})
    public void testImportYamlInApplication(String appName, String catalogName){
        login();
        catalogsPage =overviewPage.clickCatalog();
        insideCatalogPage= catalogsPage.clickOnCatalogWithName(catalogName);
        insideApplicationPage=insideCatalogPage.clickOnApplicationWithName(appName);
        insideApplicationPage
                .clickActionButton()
                .clickImportYamlButton()
                .setYamlFile("kyverno")
                .verifyYamlFileText("kyverno")
                .clickImportButton()
                .isImportedApplicationDisplayed("kyverno");
    }

    @Test(description = "Test Delete Imported YAML Application")
    @Parameters({ "catalogName","appName"})
    public void testDeleteImportedYamlApplication(String catalogName, String appName){
        login();
        catalogsPage =overviewPage.clickCatalog();
        insideCatalogPage= catalogsPage.clickOnCatalogWithName(catalogName);
        insideApplicationPage=insideCatalogPage.clickOnApplicationWithName(appName);
        insideApplicationPage
                .clickDeleteDeploymentButton("kyverno")
                .clickModalDialogDeleteButton()
                .isRecentDeletedDeploymentDisplayed("kyverno");
    }

    @Test(description = "Test Recent Visited Application")
    @Parameters({ "catalogName","appName"})
    public void testRecentVisitedApplication(String catalogName, String appName){
        login();
        catalogsPage =overviewPage.clickCatalog();
        insideCatalogPage= catalogsPage.clickOnCatalogWithName(catalogName);
        insideCatalogPage
                .clickOnApplicationWithName(appName);
        back();
        back();
        catalogsPage
                .isRecentVisitedApplicationDisplayed(appName);
    }

    @Test(description = "Test Clone Application")
    @Parameters({ "catalogName","appName","clonedAppName","catalogName2"})
    public void testCloneApplication(String catalogName, String appName, String clonedAppName, String catalogName2){
        login();
        catalogsPage =overviewPage.clickCatalog();
        insideCatalogPage= catalogsPage.clickOnCatalogWithName(catalogName);
        insideApplicationPage=insideCatalogPage.clickOnApplicationWithName(appName);
        insideApplicationPage
                .clickActionButton()
                .clickCloneApplicationButton()
                .renameCloneApplication(clonedAppName)
                .setTargetCatalogDropdown(catalogName2);
        insideCatalogPage=insideApplicationPage.clickCloneApplicationModalWindowButton();
        insideCatalogPage
                .isClonedApplicationDisplayed(clonedAppName);
    }

    @Test(description = "Test Move Application")
    @Parameters({ "catalogName","appName","catalogName2"})
    public void testMoveApplication(String catalogName, String appName, String catalogName2){
        login();
        catalogsPage =overviewPage.clickCatalog();
        insideCatalogPage=catalogsPage.clickOnCatalogWithName(catalogName);
        insideApplicationPage=insideCatalogPage.clickOnApplicationWithName(appName);
        insideApplicationPage.clickActionButton()
                .clickMoveApplicationButton()
                .setTargetCatalogDropdown(catalogName2);
        insideCatalogPage=insideApplicationPage.clickMoveApplicationModalWindowButton();
        insideCatalogPage
                .isMovedApplicationDisplayed(appName);
    }

    @Test(description = "Test Create Multiple Applications")
    @Parameters({ "catalogName","appName","nr"})
    public void testCreateMultipleApplications(String catalogName,String appName,int nr) {
        login();
        catalogsPage = overviewPage.clickCatalog();
        insideCatalogPage = catalogsPage.clickOnCatalogWithName(catalogName);

        for (int i = 1; i <= nr; i++) {
            refresh();
            String app = appName + i;
            insideCatalogPage
                    .clickAddApplicationButton()
                    .setApplicationName(app)
                    .setYamlFile("guestbook");
            insideApplicationPage = insideCatalogPage.clickCreateApplicationButton();

           insideApplicationPage
                   .verifyPanelTitle(app);
            back();
//            refresh();
            insideCatalogPage
                    .isCreatedApplicationDisplayed(app);
        }
    }

        @Test(description = "Test Remove Multiple Applications")
        @Parameters({ "catalogName","appName","nr"})
        public void testRemoveMultipleApplications(String catalogName,String appName,int nr){
            login();
            catalogsPage=overviewPage.clickCatalog();
            insideCatalogPage = catalogsPage.clickOnCatalogWithName(catalogName);
            for (int i=1;i<=nr;i++) {
                refresh();
                String app=appName+i;
                insideApplicationPage=insideCatalogPage.clickOnApplicationWithName(app);
                insideApplicationPage
                        .clickActionButton()
                        .clickDeleteApplicationButton()
                        .setApplicationNameIfNecessary(app);
                insideCatalogPage=insideApplicationPage.clickDeleteButton();
                insideCatalogPage
                        .verifyPanelTitle(catalogName);
                insideCatalogPage
                        .isDeletedApplicationDisplayed(app);
            }
    }

}
