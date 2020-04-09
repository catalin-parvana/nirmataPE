package catalog;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.catalog.catalog.InsideApplicationPage;
import pages.catalog.helmRepo.HelmChartsPage;
import pages.catalog.helmRepo.InsideHelmChartPage;
import utils.NirmataSetup;

public class HelmChartsTests extends NirmataSetup {

    private HelmChartsPage helmChartsPage;
    private InsideHelmChartPage insideHelmChartPage;
    private InsideApplicationPage insideApplicationPage;

    @Test(description = "Test Helm Chart Page Panel Title")
    public void testHelmChartsPagePanelTitle(){
        login();
        helmChartsPage=overviewPage.clickCatalog().clickHelmCharts();
        helmChartsPage
                .verifyPanelTitle();
    }

    @Test(description = "Test Add Helm Repository")
    @Parameters({ "helmChartName"})
    public void testAddHelmRepository(String helmChartName){
        login();
        helmChartsPage=overviewPage.clickCatalog().clickHelmCharts();
        helmChartsPage
                .clickAddHelmRepository()
                .setNameInputField(helmChartName)
                .setLocationInputField("https://charts.jfrog.io/")
                .clickAddButton();
        helmChartsPage
                .isCreatedHelmChartDisplayed(helmChartName);
    }

    @Test(description = "Test Inside Helm Chart Page Panel Title")
    @Parameters({ "helmChartName"})
    public void testInsideHelmChartsPagePanelTitle(String helmChartName){
        login();
        helmChartsPage=overviewPage.clickCatalog().clickHelmCharts();
        insideHelmChartPage=helmChartsPage.clickOnHelmChartRepo(helmChartName);
        insideHelmChartPage
                .verifyPanelTitle(helmChartName);
    }

    @Test(description = "Test Edit Helm Chart")
    @Parameters({ "helmChartName","newHelmChartName"})
    public void testEditHelmChart(String helmChartName, String newHelmChartName){
        login();
        helmChartsPage=overviewPage.clickCatalog().clickHelmCharts();
        insideHelmChartPage=helmChartsPage.clickOnHelmChartRepo(helmChartName);
        insideHelmChartPage
                .clickActionButton()
                .clickEditRepositoryButton()
                .setRepositoryName(newHelmChartName)
                .setRepositoryLocation("https://charts.bitnami.com/stable")
                .clickSaveButton();
       insideHelmChartPage
               .verifyPanelTitle(newHelmChartName);

    }

    @Test(description = "Test Delete Helm Chart Repository")
    @Parameters({ "helmChartName"})
    public void testDeleteHelmRepository(String helmChartName){
        login();
        helmChartsPage=overviewPage.clickCatalog().clickHelmCharts();
        insideHelmChartPage=helmChartsPage.clickOnHelmChartRepo(helmChartName);
        insideHelmChartPage
                .clickActionButton()
                .clickDeleteRepositoryButton();
        helmChartsPage=insideHelmChartPage.clickDeleteButton();
        helmChartsPage
                .isDeletedHelmChartDisplayed(helmChartName);
    }

    @Test(description = "Test Import Application From Helm Chart")
    @Parameters({ "helmChartName","catalogName"})
    public void testImportApplication(String helmChartName, String catalogName){
        login();
        helmChartsPage=overviewPage.clickCatalog().clickHelmCharts();
        insideHelmChartPage=helmChartsPage.clickOnHelmChartRepo(helmChartName);
        insideHelmChartPage
                .clickOnApplicationToImport("drupal")
                .selectCatalogToImportApplicationFromDropdown(catalogName);
        insideApplicationPage=insideHelmChartPage.clickImportChart();
        insideApplicationPage
                .verifyPanelTitle("drupal");
    }
}
