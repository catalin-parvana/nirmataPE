package catalog;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.catalog.catalog.CatalogsPage;
import pages.catalog.catalog.InsideCatalogPage;
import utils.NirmataSetup;

import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.refresh;

public class CatalogTests extends NirmataSetup {

    private CatalogsPage catalogPage;
    private InsideCatalogPage insideCatalogPage;

    @Test(description = "Inside Catalog Page Title Test")
    @Parameters({ "catalogName"})
    public void testInsideCatalogsPageTitle(String catalogName){
        catalogPage=overviewPage.clickCatalog();
        insideCatalogPage=catalogPage.clickOnCatalogWithName(catalogName);
        insideCatalogPage
                .verifyPageTitle(catalogName);
    }

    @Test(description = "Create New Catalog Test")
    @Parameters({ "catalogName"})
    public void testCreateNewCatalog(String catalogName){
        catalogPage=overviewPage.clickCatalog()
                .clickAddCatalogButton()
                .setNewCatalogName(catalogName);

        insideCatalogPage =catalogPage.clickFinishButton();
        insideCatalogPage
                .verifyPanelTitle(catalogName);
        back();
        refresh();
        catalogPage
                .isCreatedCatalogDisplayed(catalogName);
    }

    @Test(description = "Delete Catalog Test")
    @Parameters({ "catalogName"})
    public void testDeleteCatalog(String catalogName){
        catalogPage=overviewPage.clickCatalog();
        insideCatalogPage=catalogPage.clickOnCatalogWithName(catalogName);
        insideCatalogPage.clickActionButton()
                .clickDeleteCatalogButton()
                .setCatalogNameIfNecessary(catalogName);
        catalogPage=insideCatalogPage.clickDeleteButton();
        catalogPage
                .isDeletedCatalogDisplayed(catalogName);
    }

    @Test(description = "Rename Catalog Test")
    @Parameters({ "catalogName","newCatalogName"})
    public void testRenameCatalog(String catalogName, String newCatalogName){
        catalogPage=overviewPage.clickCatalog();
        insideCatalogPage=catalogPage.clickOnCatalogWithName(catalogName);
        insideCatalogPage
                .clickActionButton()
                .clickEditCatalogButton()
                .setNewCatalogName(newCatalogName)
                .clickSaveButton();
        insideCatalogPage
                .verifyPanelTitle(newCatalogName);
    }

    @Test(description = "Test Add Multiple Catalogs")
    @Parameters({ "catalogName","nr"})
    public void testAddMultipleCatalogs(String catalogName,int nr) {
        catalogPage = overviewPage.clickCatalog();
        for (int i = 1; i <= nr; i++) {
            String catalog  = catalogName + i;
            catalogPage
                    .clickAddCatalogButton()
                    .setNewCatalogName(catalog);

            insideCatalogPage = catalogPage.clickFinishButton();
            insideCatalogPage
                    .verifyPanelTitle(catalog);
            back();
            refresh();
            catalogPage
                    .isCreatedCatalogDisplayed(catalog);
        }
    }

    @Test(description = "Test Delete Multiple Catalogs")
    @Parameters({ "catalogName","nr"})
    public void testDeleteMultipleCatalogs(String catalogName, int nr) {
        catalogPage = overviewPage.clickCatalog();
        for (int i = 1; i <= nr; i++) {
            String catalog = catalogName + i;
            insideCatalogPage=catalogPage.clickOnCatalogWithName(catalog);
            insideCatalogPage
                    .clickActionButton()
                    .clickDeleteCatalogButton()
                    .setCatalogNameIfNecessary(catalog);
            catalogPage=insideCatalogPage.clickDeleteButton();
            catalogPage
                    .isDeletedCatalogDisplayed(catalog);
            refresh();
        }
    }


}
