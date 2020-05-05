package pages.cluster;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.*;


public class PEClustersPage extends LibraryUtils {

    private final SelenideElement installNewKubernetesClusterOption= $x("//div[contains(text(),'Install and manage a new Kubernetes cluster')]");
    private final SelenideElement inputName= $x("//div[@id='k8sManagedCluster']//input[@id='name']");
    private final SelenideElement toggleCardView=$x("//img[@id='toggleCardView']");
    private final SelenideElement toggleTableView=$x("//img[@id='toggleTableView']");
    private final SelenideElement addClusterButton= $x("//button[contains(.,'Add Cluster')]");
    private final SelenideElement createClusterButton= $x("//div[contains(.,'Create a Cluster')]");
    private final SelenideElement actionButton=$x("//div[@class='btn-group']");
    private final SelenideElement modelContentPanelTitle= $x("//h1[@id='model-content-panel-title']");
    private final SelenideElement filterButton= $x("//span[@id='filter-toggle-button']");
    private final SelenideElement setupButton= $x("(//button[contains(.,'Setup')])[1]");
    private final SelenideElement nextButton = $x("//button[contains(text(),'Next')]");
    private final SelenideElement nextButtonEnabled=$x("//button[contains(text(),'Next')][not(@disabled)]");
    private final SelenideElement acceptSelfSignedCertificate=$x("//input[@id='acceptAnyCert']");
    private final SelenideElement acceptText=$x("//span[contains(text(),'Accept Self-signed Certificate')]");
    //manage existing cluster
    private final SelenideElement installationButton =$x("//*[contains(text(),'Start the Installation')]");
    private final SelenideElement viewDetailsOfMyClusterButton=$x("//div[@id='k8sManagedClusterFinish']//div[@id='check-button']");
    private final SelenideElement hostGroupDropdown= $x("//*[@id='hostGroups']");
    private final SelenideElement clusterPolicyDropdown= $x("//div[@id='k8sManagedCluster']//select[@id='policySelector']");
    private final SelenideElement createClusterAndStartInstallation= $x("//button[contains(.,'Create Cluster and Start the Installation')]");

    private SelenideElement cluster, readyClusterState,unknownCLusterState,shuttingDownCLusterState,deletedCLusterState;
    private final WebDriver driver;

    public PEClustersPage(WebDriver driver){
        this.driver=driver;
        createClusterButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
        assertEquals(modelContentPanelTitle.getText(), "Clusters", "Incorrect Panel Title");
//        assertEquals(title(), "Nirmata | Clusters", "Incorrect Page Title");
    }

    public PEClustersPage clickCreateClusterButton(){
        click("Create Cluster Button",createClusterButton);
        return this;
    }
    public PEClustersPage clickInstallNewKubernetesClusterOption(){
        click("Install New Kubernetes Cluster Option",installNewKubernetesClusterOption);
        return this;
    }

    public PEClustersPage setInputName(String clusterName){
        type("Name Input Field",inputName,clusterName);
        return this;
    }

    public PEClustersPage selectHostGroupFromDropdown(String hostGroupOption){
        selectOptionByText("Host Groups Dropdown",hostGroupDropdown,hostGroupOption);
        return this;
    }

    public PEClustersPage selectClusterPolicyFromDropdown(String clusterPolicy){
        selectOptionByText("Cluster Policy Dropdown",clusterPolicyDropdown,clusterPolicy);
        return this;
    }

    public String getPanelTitle(){
        waitFor("Content Panel Title",modelContentPanelTitle);
        return modelContentPanelTitle.text();
    }

    public String getPageTitle(){
        addClusterButton.shouldBe(visible);
        return title();
    }

    public PEClustersPage clickAddClusterButton(){
        click("Add Cluster Button",addClusterButton);
        return this;
    }


/*
    public ClustersPage clickAcceptSelfSignedCertificate() {

        findElement("//div[@class='icheckbox_square-blue']",acceptSelfSignedCertificate);
     //   scrollIntoView("Accept Self Signed Certificate",acceptSelfSignedCertificate);

        acceptText.scrollIntoView(false);
        acceptSelfSignedCertificate.click();
      //  click("Accept Self Signed Certificate",acceptSelfSignedCertificate);
        return this;
    }
    */

    public PEClustersPage clickInstallationButton() {
        click("Installation button",installationButton);
        return this;
    }




    public InsideClusterPage clickViewDetailsOfMyClusterButton(){
        waitFor("View Details Of My Cluster Button",viewDetailsOfMyClusterButton,700);
        click("View Details Of My Cluster Button",viewDetailsOfMyClusterButton);
        return new InsideClusterPage(driver);
    }

    public PEClustersPage waitForReadyClusterStatus(String clusterName){
        if(toggleCardView.exists()){
            readyClusterState=$x("//*[text()='"+clusterName+"']/../../..//*[contains(text(),'Ready')]");
        }else if(toggleTableView.exists()){
            readyClusterState=$x("//*[contains(text(),'Ready')]/../..//*[text()='"+clusterName+"']");
        }
        waitFor("Ready Cluster State",readyClusterState);
//        readyCLusterState.waitUntil(disappear,60);
        return this;
    }

    public PEClustersPage waitForUnknownClusterStatus(String clusterName){
        if(toggleCardView.exists()){
            unknownCLusterState=$x("//*[text()='"+clusterName+"']/../../..//*[contains(text(),'Unknown')]");
        }else if(toggleTableView.exists()){
            unknownCLusterState=$x("//*[contains(text(),'Unknown')]/../..//*[text()='"+clusterName+"']");
        }
        waitFor("Unknown Cluster State",unknownCLusterState,500);
        unknownCLusterState.waitUntil(disappear,1000);
        return this;
    }

    public PEClustersPage waitForShuttingDownClusterStatus(String clusterName){
        if(toggleCardView.exists()){
            shuttingDownCLusterState=$x("//*[text()='"+clusterName+"']/../../..//*[contains(text(),'Shutting Down')]");
        }else if(toggleTableView.exists()){
            shuttingDownCLusterState=$x("//*[contains(text(),'Shutting Down')]/../..//*[text()='"+clusterName+"']");
        }
        waitFor("Shutting Down Cluster State",shuttingDownCLusterState,350);
        return this;
    }

    public PEClustersPage waitForDeletedClusterStatus(String clusterName){
        if(toggleCardView.exists()){
            deletedCLusterState=$x("//*[text()='"+clusterName+"']/../../..//*[contains(text(),'Deleted')]");
        }else if(toggleTableView.exists()){
            deletedCLusterState=$x("//*[contains(text(),'Deleted')]/../..//*[text()='"+clusterName+"']");
        }
        waitFor("Deleted Cluster State",deletedCLusterState,350);
        refresh();
        deletedCLusterState.should(disappear);
        return this;
    }



    public PEClustersPage isDeletedClusterDisplayed(String clusterName){
        refresh();
        assertFalse(isClusterDisplayed(clusterName),"Cluster Was Not Deleted");
        return this;
    }

    public PEClustersPage isCreatedClusterDisplayed(String clusterName){
        assertTrue(isClusterDisplayed(clusterName),"Cluster Was Not Created");
        return this;
    }


    private boolean isClusterDisplayed(String clusterName) {
        addClusterButton.shouldBe(visible);
        boolean found = false;

        if (toggleCardView.exists()) {
            cluster = $x("//div[contains(@class,'cluster-name')]//*[text()='" + clusterName + "']");
        } else if (toggleTableView.exists()) {
            cluster = $x("//div[contains(@class,'card-content card-select')]//*[text()='" + clusterName + "']");
        }

        if (cluster != null && cluster.exists()) {
            found = true;
        } else if (nextButton.exists()) {
            nextButton.scrollIntoView(true);
            if (nextButtonEnabled.exists()) {
                do {
                    click("Next Button", nextButton);
                    modelContentPanelTitle.scrollIntoView(true);
                    if (cluster.exists()) {
                        found = true;
                        break;
                    }
                } while (nextButtonEnabled.exists());
            }
        }
        return found;
    }

    public InsideClusterPage clickOnClusterWithName(String clusterName)  {
        addClusterButton.shouldBe(visible);

        if(toggleCardView.exists()){
            cluster=$x("//div[contains(@class,'cluster-name')]//*[text()='"+clusterName+"']");
        }else if(toggleTableView.exists()){
            cluster=$x("//div[contains(@class,'card-content card-select')]//*[text()='"+clusterName+"']");
        }

        if (cluster!=null && cluster.exists()){
                click("Cluster "+clusterName,cluster);
        }else if(nextButton.exists()){
            nextButton.scrollIntoView(true);
            if(nextButtonEnabled.exists()) {
                do {
                    click("Next Button",nextButton);
                    modelContentPanelTitle.scrollIntoView(true);
                    if (cluster.exists()) {
                        click("Cluster " + clusterName, cluster);
                        break;
                    }
                }
                while (nextButtonEnabled.exists());
            }
        }
        return new InsideClusterPage(driver);
    }
}
