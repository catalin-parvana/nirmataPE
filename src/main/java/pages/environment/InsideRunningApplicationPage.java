package pages.environment;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.title;
import static org.testng.Assert.*;

public class InsideRunningApplicationPage extends LibraryUtils {


    private final SelenideElement actionButton= $x("//a[@class='btn btn-default dropdown-toggle']");
    private final SelenideElement modelContentPanelTitle= $x("//h1[@id='model-content-panel-title']");
    private final SelenideElement deleteApplicationButton= $x("//a[@id='deleteApplication']");
    private final SelenideElement deleteOnClusterCheckbox=$x("//input[@id='deleteOnCluster']");
    private final SelenideElement deleteButton=$x("//button[contains(.,'Delete')]");
    private final SelenideElement nameInputField=$x("//input[@id='run']");
    private final SelenideElement runningDeploymentState=$x("//span[@id='state-popover'][contains(.,'Running')]");
    private final SelenideElement degradedDeploymentState=$x("//span[@id='state-popover'][contains(.,'Degraded')]");
    private final SelenideElement executingDeploymentState=$x("//span[@id='state-popover'][contains(.,'Executing')]");
    private final SelenideElement failedDeploymentState=$x("//span[@id='state-popover'][contains(.,'Failed')]");
    private final SelenideElement importToApplicationButton=$x("//a[@id='importApplication']");
    private final SelenideElement yamlInputField=$x("(//input[@class='dz-hidden-input'])[1]");
    private final SelenideElement yaml=$x("//div[@class='dz-details']");
    private final SelenideElement importButton=$x("//button[text()='Import']");
    private final SelenideElement deleteDeploymentButton=$x("//a[@id='deleteComponent']");
    private final SelenideElement deploymentNameInputField=$x("//input[@id='name']");
    private final SelenideElement alarmsButton=$x("//li[@id='alarms']");
    private final SelenideElement cloneApplicationButton=$x("//a[@id='cloneApplication']");
    private final SelenideElement targetEnvironmentDropdown=$x("//select[@id='environment']");
    private final SelenideElement cloneButton=$x("//button[contains(.,'Clone Application')]");
    private final SelenideElement runningApplicationLabel=$x("//div[@class='pull-left model-index-name'][contains(.,'Running Application')]");

    private SelenideElement deployment;
    private final WebDriver driver;

    public InsideRunningApplicationPage(WebDriver driver){
        this.driver=driver;
        runningApplicationLabel.shouldBe(visible);
        actionButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);

    }

    public InsideRunningApplicationPage verifyPanelTitle(String appDeployName){
        waitFor("Content Panel Title",modelContentPanelTitle);
        assertEquals(modelContentPanelTitle.text(),appDeployName,"Incorrect deployed application title");
        return this;
    }

    public InsideRunningApplicationPage verifyPageTitle(String environmentName, String appDeployName){
        assertEquals(title(), "Nirmata | Environments | "+environmentName+" | "+appDeployName+" | Workloads", "Incorrect Page Title");
        return this;
    }

    public InsideRunningApplicationPage clickActionButton(){
        click("Action Button",actionButton);
        return this;
    }

    public InsideRunningApplicationPage clickDeleteApplicationButton(){
        click("Delete Application Button",deleteApplicationButton);
        return this;
    }

    public InsideRunningApplicationPage checkDeleteOnClusterIfNecessary(){
        deleteButton.shouldBe(visible);
        if(deleteOnClusterCheckbox.exists()){
            click("Delete On Cluster Checkbox",deleteOnClusterCheckbox);
        }
        return this;
    }

    public InsideRunningApplicationPage setApplicationNameToDeleteIfNecessary(String applicationName){
        deleteButton.shouldBe(visible);
        if(nameInputField.exists()){
            type("Application Name",nameInputField,applicationName);
        }
        return this;
    }

    public InsideEnvironmentPage clickDeleteButton(){
        click("Delete Button",deleteButton);
        deleteButton.should(disappear);
        waitFor("Executing Deployment State",executingDeploymentState);
        return new InsideEnvironmentPage(driver);
    }

    public InsideRunningApplicationPage waitForDegradedApplicationState(){
        waitFor("Degraded Application State",degradedDeploymentState,120);
        return this;
    }

    public InsideRunningApplicationPage waitForRunningApplicationState(){
        waitFor("Running Application State",runningDeploymentState,150);
        return this;
    }

    public InsideRunningApplicationPage waitForExecutingApplicationState(){
        waitFor("Running Application State",executingDeploymentState,120);
        return this;
    }

    public InsideRunningApplicationPage clickImportToApplicationButton(){
        click("Import To Application Button",importToApplicationButton);
        return this;
    }

    public InsideRunningApplicationPage setYamlFile(String yamlName){
        absolutePathOfFile+="/resources/yaml/"+ yamlName+".yaml";
        upload("Yaml File "+yamlName+".yaml",yamlInputField,absolutePathOfFile);
        return this;
    }

    public InsideRunningApplicationPage clickImportButton(){
        click("Import Button",importButton);
        importButton.should(disappear);
        return this;
    }

    public InsideRunningApplicationPage seeDeploymentDetails(String deploymentName){
        deployment=$x("//*[text()='"+deploymentName+"']/../../..");
        click("Deployment Details",deployment);
        return this;
    }

    public InsideRunningApplicationPage waitForUnknownDeploymentState(String deploymentName){
        deployment=$x("//*[text()='"+deploymentName+"']/../..//*[contains(text(),'Unknown')]");
        waitFor("Executing Deployment State", deployment,120);
        return this;
    }

    public InsideRunningApplicationPage waitForRunningDeploymentState(String deploymentName){
        deployment=$x("//*[text()='"+deploymentName+"']/../..//*[contains(text(),'Running')]");
        waitFor("Executing Deployment State", deployment,120);
        return this;
    }

    public InsideDeploymentPage clickOnDeployment(String deploymentName){
        deployment=$x("//tr//a[contains(text(),'"+deploymentName+"')]");
        click("Deployment "+deploymentName,deployment);
        return new InsideDeploymentPage(driver);
    }

    public InsideRunningApplicationPage clickDeleteDeploymentButton(){
        click("Delete Deployment Button",deleteDeploymentButton);
        return this;
    }

    public InsideRunningApplicationPage clickCloneApplicationButton(){
        click("Clone Application Button",cloneApplicationButton);
        return this;
    }

    public InsideRunningApplicationPage setNewNameForClonedApplication(String clonedApplicationName){
        type("Clone Application Name Input Field",nameInputField,clonedApplicationName);
        return this;
    }

    public InsideRunningApplicationPage selectEnvironmentFromDropdown(String environmentName){
        selectOptionByText("Environment Dropdown",targetEnvironmentDropdown,environmentName);
        return this;
    }

    public InsideRunningApplicationPage clickCloneButton(){
        click("Clone Button", cloneButton);
        return this;
    }

    public InsideRunningApplicationPage isDeletedDeploymentDisplayed(String deploymentName){
        deployment = $x("//*[text()='"+deploymentName+"']/../../..");
        deployment.shouldNotBe(visible);
        assertFalse(deployment.exists(),"Deployment Was Not Deleted");
        return this;
    }

    public InsideRunningApplicationPage isCreatedDeploymentDisplayed(String deploymentName){
        deployment = $x("//*[text()='"+deploymentName+"']/../../..");
        deployment.shouldBe(visible);
        assertTrue(deployment.exists(),"Deployment Was Not Created");
        return this;
    }

}