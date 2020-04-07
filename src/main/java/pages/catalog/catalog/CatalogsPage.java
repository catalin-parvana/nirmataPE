package pages.catalog.catalog;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import pages.catalog.helmRepo.HelmChartsPage;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.*;

public class CatalogsPage extends LibraryUtils {

    private SelenideElement addCatalogButton = $x("//button[contains(.,'Add Catalog')]");
    private SelenideElement addApplicationButton = $x("//button[contains(.,'Add Application')]");
    private SelenideElement modalDialogTitle = $(".bootstrap-dialog-title");
    private SelenideElement nameInputField = $("input#name");
    private SelenideElement finishButton = $x("//button[contains(text(),'Finish')]");
    private SelenideElement modelContentPanelTitle = $("h1#model-content-panel-title");
    private SelenideElement helmChartsPageLink = $("a[href='#chartRepositories']");
    private SelenideElement nextButton = $x("//button[contains(text(),'Next')]");
    private SelenideElement nextButtonEnabled=$x("//button[contains(text(),'Next')][not(@disabled)]");

    private SelenideElement catalog;
    private WebDriver driver;
    private boolean found=false;



    public CatalogsPage(WebDriver driver){
        this.driver=driver;
        addCatalogButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
        assertEquals(modelContentPanelTitle.getText(), "Catalogs", "Incorrect Panel Title");
//        assertEquals(title(), "Nirmata | Catalog | Catalogs", "Incorrect Page Title");
    }

    public HelmChartsPage clickHelmCharts(){
        helmChartsPageLink.click();
        return new HelmChartsPage(driver);
    }

    public CatalogsPage clickAddCatalogButton(){
        click("Add Catalog Button", addCatalogButton);
        return this;
    }

    public CatalogsPage setNewCatalogName(String catalogName){
        type("Catalog Name Field "+catalogName, nameInputField, catalogName);
        return this;
    }

    public InsideCatalogPage clickFinishButton(){
        click("Finish Button",finishButton);
        finishButton.should(disappear);
        return new InsideCatalogPage(driver);
   }

   public InsideCatalogPage clickOnCatalogWithName(String catalogName){
       addCatalogButton.shouldBe(visible);
       catalog = $x("//div[contains(@class,'card-content card-select')]//*[text()='"+catalogName+"']");
        if (catalog.exists()){
            click("Catalog "+catalogName,catalog);
        }else if(nextButton.exists()) {
            nextButton.scrollIntoView(true);
            if (nextButtonEnabled.exists()) {
                do {
                    click("Next Button", nextButton);
                    addCatalogButton.shouldBe(visible).scrollIntoView(true);
                    if (catalog.exists()) {
                        click("Catalog "+catalogName, catalog);
                        break;
                    }
                }
                while (nextButtonEnabled.exists());
            }
        }
        return new InsideCatalogPage(driver);
    }

   public CatalogsPage isRecentVisitedApplicationDisplayed(String appName){
       SelenideElement application = $x("//div[contains(@class,'mini-card-content')]//*[text()='" + appName + "']");
       application.shouldBe(visible);
       assertTrue(application.exists(),"Application Is Not Displayed In Recent Visited");
       return this;
   }

   public CatalogsPage isDeletedCatalogDisplayed(String catalogName){
       assertFalse(isCatalogDisplayed(catalogName),"Catalog Was Not Deleted");
       return this;
   }

    public CatalogsPage isCreatedCatalogDisplayed(String catalogName){
        assertTrue(isCatalogDisplayed(catalogName),"Catalog Was Not Created");
        return this;
    }

    private boolean isCatalogDisplayed(String catalogName){
        addCatalogButton.shouldBe(visible);
        catalog = $x("//span[@for='name'][text()='"+catalogName+"']");
        if(catalog.exists()){
            found=true;
        }else
            if(nextButton.exists()){
                if(nextButtonEnabled.exists()){
                    do {
                        nextButton.scrollIntoView(true);
                        click("Next Button",nextButton);
                        modelContentPanelTitle.scrollIntoView(true);
                        if (catalog.exists()) {
                            found = true;
                            break;
                        }
                    } while (nextButtonEnabled.exists());
                }
            }
            return found;
    }


}
