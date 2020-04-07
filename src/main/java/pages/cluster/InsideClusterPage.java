package pages.cluster;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;
import static org.testng.Assert.*;


public class InsideClusterPage extends LibraryUtils {
    private SelenideElement clusterReadyState=$x("//span[@id='state-popover'][not(contains(.,'Not Ready'))][contains(.,'Ready')]");
    private SelenideElement actionButton=$x("//a[contains(@class,'btn-lg dropdown-toggle')]");
    private SelenideElement modelContentPanelTitle=$x("//h1[@id='model-content-panel-title']");
    private SelenideElement editClusterButton=$x("//a[@id='editHostCluster']");
    private SelenideElement downloadKubeconfigFileButton=$x("//a[@id='downloadConfig']");
    private SelenideElement enableVaultSettingsButton=$x("//a[@id='editVaultSettings']");
    private SelenideElement applyYamlButton=$x("//a[@id='applyYaml']");
    private SelenideElement downloadControllerYAMLButton=$x("//a[@id='downloadControllerYaml']");
    private SelenideElement resizeClusterButton=$x("//a[@id='resizeCluster']");
    private SelenideElement launchTerminalClusterButton=$x("//a[@id='openTerminal']");
    private SelenideElement upgradeClusterButton=$x("//a[@id='upgradeCluster']");
    private SelenideElement disableClusterButton=$x("//a[@id='disableCluster']");
    private SelenideElement removeClusterButton=$x("//a[@id='removeCluster']");
    private SelenideElement deleteClusterButton=$x("//a[@id='deleteHostCluster'] | //a[contains(.,'Delete Cluster')]");
    private SelenideElement nameInputField=$x("//input[@id='name']");
    private SelenideElement deleteButton=$x("//button[text()='Delete']");
    private SelenideElement addOnsButton=$x("//li[@id='addons']");
    private SelenideElement prometheusAddOn=$x("//div[@id='addPrometheus']");
    private SelenideElement valeroAddOn=$x("//div[@id='addVelero']");
    private SelenideElement kyvernoAddOn=$x("//div[@id='addKyverno']");
    private SelenideElement addKyvernoButton=$x("//a[@id='addKyvernoButton']");
    private SelenideElement kubeappsAddOn=$x("//div[@id='addKubeapps']");
    private SelenideElement nextButton=$x("//button[contains(.,'Next')]");
    private SelenideElement deployingAddOnLabel=$x("//div[@id='install-summary'][contains(.,'this may take a few minutes')]");
    private SelenideElement addOnDeployedLabel=$x("//div[@id='install-summary'][contains(.,'deployed successfully!')]");
    private SelenideElement closeButton=$x("//div[@class='modal-dialog modal-lg']//button[contains(.,'Close')]");
    private SelenideElement readyState=$x("//span[@id='state-popover'][contains(.,'Ready')]");
    private SelenideElement addButton=$x("//button[@id='addButton']");
    private SelenideElement addPrometheusButton=$x("//a[@id='addPrometheusButton']");
    private SelenideElement addKubeappsButton=$x("//a[@id='addKubeappsButton']");

    private WebDriver driver;

    public InsideClusterPage(WebDriver driver){
        this.driver=driver;
        modelContentPanelTitle.shouldBe(visible);
        actionButton.shouldBe(visible);
    }

    public InsideClusterPage verifyPanelTitle(String aksClusterName){
        waitFor("Content Panel Title",modelContentPanelTitle);
        assertEquals(modelContentPanelTitle.text(), aksClusterName, "Incorrect Panel Title");
        return this;
    }

    public InsideClusterPage waitForClusterReadyState(){
        actionButton.shouldBe(visible);
        waitFor("Cluster Ready State",clusterReadyState,60);
        return this;
    }

    public InsideClusterPage clickActionButton(){
        click("Action Button",actionButton);
        return this;
    }

    public InsideClusterPage clickDeleteClusterButton(){
        deleteClusterButton.shouldBe(visible);
        click("Delete Cluster Button",deleteClusterButton);
        return this;
    }

    public InsideClusterPage setNameInputField(String clusterName){
        type("Name Input Field",nameInputField,clusterName);
        return this;
    }

    public ClustersPage clickDeleteButton(){
        click("Delete Button",deleteButton);
        deleteButton.shouldNotBe(visible);
        return new ClustersPage(driver);
    }

    public InsideClusterPage clickDeleteAddOnButton(){
        click("Delete Button",deleteButton);
        deleteButton.shouldNotBe(visible);
        return this;
    }

    public InsideClusterPage clickEditClusterButton(){
        click("Edit Cluster Button",editClusterButton);
        return this;
    }

    public InsideClusterPage clickOnAddOnsButton(){
        actionButton.shouldBe(visible);
        click("AddOns Button",addOnsButton);
        return this;
    }

    public InsideClusterPage clickAddPrometheus(){
        if(prometheusAddOn.exists()){
           click("Prometheus AddOn",prometheusAddOn);
        }
        else{
            click("Add Button",addButton);
            click("Add Prometheus Button",addPrometheusButton);
        }
        return this;
    }

    public InsideClusterPage clickAddKubeapps(){
        if(kubeappsAddOn.exists()){
            click("KubeApps Add On",kubeappsAddOn);
        }
        else{
            click("Add Button",addButton);
            click("Add KubeApps Button",addKubeappsButton);
        }
        return this;
    }

    public InsideClusterPage clickOnValero(){
       click("Valero AddOn",valeroAddOn);
       return this;
    }

    public InsideClusterPage clickAddKyverno(){
        if(kyvernoAddOn.exists()){
            click("Kyverno AddOn",kyvernoAddOn);
        }
        else{
            click("Add Button",addButton);
            click("Add Kyverno Button",addKyvernoButton);
        }
        return this;
    }

    public InsideClusterPage waitForDeployingLabel(){
        waitFor("Deploying Label",deployingAddOnLabel);
        return this;
    }

    public InsideClusterPage waitForDeployedLabel(){
        waitFor("Deployed Successfully Label",addOnDeployedLabel,300);
        return this;
    }

    public InsideClusterPage clickOnKubeapps(){
        click("KubeApps AddOn",kubeappsAddOn);
        return this;
    }

    public InsideClusterPage clickNextButton(){
        click("next Button",nextButton);
        return this;
    }

    public InsideClusterPage clickCloseButton(){
        click("Close Button",closeButton);
        refresh();
        return this;
    }

    public InsideClusterPage isInstalledAddOnDisplayed(String addOnName){
        SelenideElement addOn=$x("(//div[@class='rt-tbody']//*[contains(text(),'"+addOnName+"')])[1]");
        assertTrue(addOn.exists(), "AddOn was not installed");
        return this;
    }

    public InsideClusterPage isDeletedAddOnDisplayed(String addOnName){
        SelenideElement addOn=$x("(//div[@class='rt-tbody']//*[contains(text(),'"+addOnName+"')])[1]");
        assertFalse(addOn.exists(), "AddOn was not deleted");
        return this;
    }

    public InsideClusterPage clickDeleteAddOn(String addOnName){
        SelenideElement addOnRemoveButton=$x("//div[contains(text(),'"+addOnName+"')]/..//button[2]");
        click("Delete AddOn "+addOnName,addOnRemoveButton);
        return this;
    }

}
