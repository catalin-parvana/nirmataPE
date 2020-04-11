package pages.hostGroups;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.title;
import static org.testng.Assert.*;


public class AmazonWebServicesPage extends LibraryUtils {

    private final SelenideElement addHostGroupButton= $x("//*[@id='addHostGroup']");
    private final SelenideElement modelContentPanelTitle= $x("//h1[@id='model-content-panel-title']");
    private final SelenideElement nameInputField= $x("//input[@id='name']");
    private final SelenideElement regionDropdown=$x("//*[@id='region']");
    private final SelenideElement hostInstancesDropdown=$x("//*[@id='awsConfigType']");
    private final SelenideElement inputAMIField=$x("//*[@id='image']");
    private final SelenideElement instanceTypeDropDown=$x("//*[@id='instanceType']");
    private final SelenideElement sshKeyPairDropdown=$x("//*[@id='keypair']");
    private final SelenideElement securityGroupsDropDown=$x("//*[@id='securityGroups']");
    private final SelenideElement networkDropDown=$x("//*[@id='network']");
    private final SelenideElement iamRoleDropDown=$x("//*[@id='instanceRole']");
    private final SelenideElement iamRoleLabel=$x("//span[contains(text(),'IAM Role')]");
    private final SelenideElement nextButton= $x("//button[text()='Next']");
    private final SelenideElement finishButton= $x("//button[text()='Finish']");
    private final SelenideElement desireHostCountsInput= $x("//input[@id='desiredCount']");
    private final SelenideElement minimumHostCountsInput= $x("//input[@id='minHosts']");
    private final SelenideElement maximumHostCountsInput= $x("//input[@id='maxHosts']");
    private final SelenideElement userDataTemplateDropDown=$x("//*[@id='installSelect']");
    private final SelenideElement actionButton=$x("//a[@class='btn btn-default dropdown-toggle']");
    private final SelenideElement cloudProviderDropdown=$x("//span[@id='select2-parent-container']");
    private final SelenideElement toggleCardView=$x("//img[@id='toggleCardView'][contains(@style,'block')]");
    private final SelenideElement toggleTableView=$x("//img[@id='toggleTableView'][contains(@style,'block')]");
    private final WebDriver driver;
    private SelenideElement hostGroup;


    public AmazonWebServicesPage(WebDriver driver){
        this.driver=driver;
        addHostGroupButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
        assertEquals(modelContentPanelTitle.getText(), "AWS Host Groups", "Incorrect Panel Title");
        assertEquals(title(), "Nirmata | Host Groups | AWS Host Groups", "Incorrect Page Title");
    }

    public AmazonWebServicesPage clickAddHostGroupButton(){
        click("Add Host Group Button",addHostGroupButton);
        return this;
    }

    public AmazonWebServicesPage setNameInputField(String hostGroupName){
        type("Name Input Field",nameInputField,hostGroupName);
        return this;
    }

    public AmazonWebServicesPage selectCloudProviderFromDropdown(String cloudProvider){
        select2OptionByText("Cloud Provider Dropdown",cloudProviderDropdown,cloudProvider);
        return this;

    }

    public AmazonWebServicesPage selectRegionFromDropdown(String regionValue){
        selectOptionByValue("Region Dropdown",regionDropdown,regionValue);
        return this;
    }

    public AmazonWebServicesPage selectHostInstanceFromDropdown(String hostInstanceValue){
        selectOptionByValue("Host Instance Dropdown",hostInstancesDropdown,hostInstanceValue);
        return this;
    }

    public AmazonWebServicesPage setAmazonMachineImageID(String ami){
        type("Amazon Machine Image ID Input Field",inputAMIField,ami);
        return this;
    }

    public AmazonWebServicesPage selectInstanceTypeFromDropdown(String instanceType){
        selectOptionByText("Instance Type Dropdown",instanceTypeDropDown,instanceType);
        return this;
    }

    public AmazonWebServicesPage selectSshKeyPairFromDropdown(String sshKeyValue){
        selectOptionByValue("SSH Key Pair Dropdown",sshKeyPairDropdown,sshKeyValue);
        return this;
    }

    public AmazonWebServicesPage scrollDown(){
        sshKeyPairDropdown.shouldBe(visible);
        sshKeyPairDropdown.scrollIntoView(true);
        return this;
    }

    public AmazonWebServicesPage selectSecurityGroupFromDropdown(String securityGroupValue){
        selectOptionByValue("Security Group Dropdown",securityGroupsDropDown,securityGroupValue);
        return this;
    }

    public AmazonWebServicesPage selectNetworkFromDropdown(String networkValue){
        selectOptionByValue("Network Dropdown",networkDropDown,networkValue);
        return this;
    }

    public AmazonWebServicesPage selectIamRoleFromDropdown(String iamRoleValue){
        selectOptionByValue("IAM Role Dropdown",iamRoleDropDown,iamRoleValue);
        return this;
    }

    public AmazonWebServicesPage clickNextButton(){
        click("Next Button",nextButton);
        return this;
    }

    public AmazonWebServicesPage setDesiredHostCounts(String desireHostNumber){
       type("Desire Host Group Counts Input Field",desireHostCountsInput, desireHostNumber);
       return this;
    }

    public AmazonWebServicesPage setMinimumHostCounts(String minimumHostNumber){
        type("Minimum Host Counts Input Field",minimumHostCountsInput,minimumHostNumber);
        return this;
    }

    public AmazonWebServicesPage setMaximumHostCounts(String maximumHostNumber){
        type("Maximum Host Counts Input Field",maximumHostCountsInput,maximumHostNumber);
        return this;
    }

    public AmazonWebServicesPage selectUserDataTemplateFromDropdown(String iamRoleValue){
        selectOptionByValue("User Data Template Dropdown",userDataTemplateDropDown,iamRoleValue);
        return this;
    }

    public AmazonWebServicesPage clickFinishButton(){
        click("Finish Button",finishButton);
        finishButton.should(disappear);
        return this;
    }

    public AmazonWebServicesPage isCreatedHostGroupDisplayed(String hostGroupName){
       addHostGroupButton.shouldBe(visible);
       if(toggleTableView.exists()){
           hostGroup=$x("//div[@class='card-title'][contains(.,'"+hostGroupName+"')]");
       }else if(toggleCardView.exists()){
           hostGroup=$x("//*[contains(@class,'card-content card-select')]//*[text()='"+hostGroupName+"']");
       }

        waitFor("Created Host Group: "+hostGroupName,hostGroup);
        assertTrue(hostGroup.exists(),"New Host Group Was Not Created");
        return this;
    }

    public AmazonWebServicesPage isDeletedHostGroupDisplayed(String hostGroupName){
        hostGroup=$x("//div[@class='card-title'][contains(.,'"+hostGroupName+"')]");
        assertFalse(hostGroup.exists(),"Host Group Was Not Deleted");
        return this;
    }

    public InsideHostGroupPage clickOnHostGroupWithName(String hostGroupName){
        hostGroup=$x("//*[contains(@class,'card-content card-select')][contains(.,'"+hostGroupName+"')]");
        click("Host Group "+hostGroupName,hostGroup);
        return new InsideHostGroupPage(driver);
    }


}
