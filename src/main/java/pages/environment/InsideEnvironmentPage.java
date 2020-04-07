package pages.environment;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import pages.catalog.catalog.InsideApplicationPage;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;


public class InsideEnvironmentPage extends LibraryUtils {

    private SelenideElement runAnApplicationButton= $x("//*[@id='launchApplication']");
    private SelenideElement modelContentPanelTitle= $x("//h1[@id='model-content-panel-title']");
    private SelenideElement actionButton= $x("//*[contains(@class,'btn-lg dropdown-toggle')]");
    private SelenideElement nameInputField=$x("//input[@id='run']");
    private SelenideElement runApplicationButton=$x("//button[text()='Run Application']");
    private SelenideElement upstreamTypeDropdown=$x("//select[@id='upstreamType']");
    private SelenideElement catalogApplicationDropdown=$x("//select[@id='application']");
    private SelenideElement deleteEnvironmentButton=$x("//a[@id='deleteEnvironment']");
    private SelenideElement deleteAllApplicationsCheckbox=$x("//input[@id='deleteAllApplications']");
    private SelenideElement deleteFromClusterCheckbox=$x("//input[@id='deleteOnCluster']");
    private SelenideElement deleteButton=$x("//button[text()='Delete']");
    private SelenideElement pendingDeleteState=$x("//span[@id='state-popover'][contains(.,'Pending Delete')]");
    private SelenideElement applicationRunningState=$x("//span[@id='state-popover'][contains(.,'Running')]");
    private SelenideElement applicationExecutingState=$x("//span[@id='state-popover'][contains(.,'Executing')]");
    private SelenideElement clusterConnectedState=$x("//span[@id='state-popover'][contains(.,'Cluster Connected')]");
    private SelenideElement toggleTableView=$x("//img[@id='toggleTableView']");
    private SelenideElement toggleCardView=$x("//img[@id='toggleCardView']");
    private SelenideElement nextButtonEnabled=$x("//button[contains(text(),'Next')][not(@disabled)]");
    private SelenideElement nextButton = $x("//button[contains(text(),'Next')]");
    private SelenideElement modalDialog=$x("//div[@class='modal-dialog']");
    private SelenideElement applicationButton=$x("//li[@id='applications']");
    private SelenideElement deployment, application, runningApplicationLink;
    private boolean found=false;


    private WebDriver driver;


    public InsideEnvironmentPage(WebDriver driver){
        this.driver=driver;
        actionButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
        applicationButton.shouldBe(visible)
                .shouldHave(text("Applications"));
    }

    public InsideEnvironmentPage verifyPanelTitle(String environmentName){
        assertEquals(modelContentPanelTitle.text(),environmentName, "Incorrect environment name");
        return this;
    }

    public InsideEnvironmentPage clickRunAnApplicationButton(){
        click("Run Application Button",runAnApplicationButton);
        return this;
    }

    public InsideEnvironmentPage setNameInputField(String runName){
        type("Name Input Field",nameInputField,runName);
        return this;
    }

    public InsideEnvironmentPage selectUpstreamTypeFromDropdown(String upstreamType){
        selectOptionByText("Upstream Type Dropdown ",upstreamTypeDropdown,upstreamType);
        return this;
    }

    public InsideEnvironmentPage selectCatalogApplicationFromDropdown(String applicationName) {
        selectOptionByText("Catalog Application Dropdown ", catalogApplicationDropdown, applicationName);
        return this;
    }

    public InsideDeployedApplicationPage clickRunApplicationButton(){
        click("Run Application Button",runApplicationButton);
        runAnApplicationButton.should(disappear);
        return new InsideDeployedApplicationPage(driver);
    }

    public InsideApplicationPage clickOnApplicationWithName(String applicationName){
        if(toggleTableView.exists()){
            application=$x("//div[contains(@class,'card-title')]//*[text()='"+applicationName+"']");
        }else if(toggleCardView.exists()){
            application=$("//div[@class='runningAppName']//*[text()='"+applicationName+"']");
        }

        if (application.exists()){
            click("Application "+applicationName,application);
        }else
            if(nextButtonEnabled.exists())
                do{
                    nextButton.click();
                    modelContentPanelTitle.shouldBe(visible).scrollIntoView(true);
                    if(application.exists()){
                        click("Application "+applicationName,application);
                        break;
                    }
                }
            while(nextButtonEnabled.exists());
        return new InsideApplicationPage(driver);
    }

    public InsideEnvironmentPage clickActionButton(){
        click("Action Button",actionButton);
        return this;
    }

    public InsideEnvironmentPage clickDeleteEnvironmentButton(){
        click("Delete Environment Button",deleteEnvironmentButton);
        return this;
    }

    public InsideEnvironmentPage checkDeleteApplicationsCheckboxIfNecessary(){
        modalDialog.shouldBe(visible);
        if(deleteAllApplicationsCheckbox.exists()){
            click("Delete Application Checkbox",deleteAllApplicationsCheckbox);
        }
        return this;
    }

    public InsideEnvironmentPage checkDeleteFromClusterCheckboxIfNecessary(){
        modalDialog.shouldBe(visible);
        if(deleteFromClusterCheckbox.exists()){
            click("Delete From Cluster Checkbox",deleteFromClusterCheckbox);
        }
        return this;
    }


    public InsideEnvironmentPage setEnvironmentNameIfNecessary(String environmentName){
        modalDialog.shouldBe(visible);
        if(nameInputField.isDisplayed()){
            type("Name Input Field",nameInputField,environmentName);
        }
        return this;
    }

    public EnvironmentsPage clickDeleteButton(){
       click("Delete Button",deleteButton);
       deleteButton.should(disappear);
        return new EnvironmentsPage(driver);
    }

    public InsideEnvironmentPage isDeletedDeploymentDisplayed(String appDeployName){
        assertFalse(isDeploymentDisplayed(appDeployName),"Deployment was not deleted");
        return this;
    }


    public boolean isDeploymentDisplayed(String deploymentName){
        actionButton.shouldBe(visible);

        if(toggleTableView.exists()){
            deployment=$x("//div[@class='card-title']//*[text()='"+deploymentName+"']");
        }else if(toggleCardView.exists()){
            deployment=$x("//div[@class='runningAppName']//*[text()='"+deploymentName+"']");
        }

            if (deployment!=null && deployment.exists()) {
                found = true;
            } else
                if(nextButton.exists()){
                    if (nextButtonEnabled.exists()) {
                        do {
                            nextButton.click();
                            if (deployment.exists()){
                                found = true;
                                break;
                            }
                        }
                        while (nextButtonEnabled.exists());
                    }
                }
        return found;
    }


    public InsideDeployedApplicationPage clickRunningApplicationLink(String deploymentName){
        actionButton.shouldBe(visible);

        if(toggleTableView.exists()){
            runningApplicationLink=$x("//div[@class='card-title']//*[text()='"+deploymentName+"']");
        }else if(toggleCardView.exists()){
            runningApplicationLink=$x("//div[@class='application-name']//*[text()='"+deploymentName+"']");
        }
        click("Running Application Link: "+deploymentName,runningApplicationLink);
        return new InsideDeployedApplicationPage(driver);
    }


    public InsideEnvironmentPage waitForClusterConnectedStatus(){
        waitFor("Cluster Connected Status",clusterConnectedState,120);
        return this;
    }
}
