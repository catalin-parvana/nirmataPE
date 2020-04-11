package pages.cloudProviders;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.*;

public class CloudProvidersPage extends LibraryUtils {

    private final SelenideElement addCloudProviderButton=$x("//button[contains(.,'Add Cloud Provider')]");
    private final SelenideElement nameInputField=$x("//input[@id='name']");
    private final SelenideElement typeDropdown=$x("//select[@id='type']");
    private final SelenideElement nextButton=$x("//button[text()='Next']");
    private final SelenideElement regionDropdown=$x("//select[@id='region']");
    private final SelenideElement accountInputField=$x("//input[@id='accountId']");
    private final SelenideElement externalIdInputField=$x("//input[@id='awsExternalId']");
    private final SelenideElement roleArnInputField=$x("//input[@id='awsRoleArn']");  // arn:aws:iam::094919933512:role/NirmataV2
    private final SelenideElement subscriptionIdInputField=$x("//input[@id='subscriptionId']");  // arn:aws:iam::094919933512:role/NirmataV2
    private final SelenideElement tenantIdInputField=$x("//input[@id='tenant']");  // arn:aws:iam::094919933512:role/NirmataV2
    private final SelenideElement clientIdInputField=$x("//input[@id='username']");  // arn:aws:iam::094919933512:role/NirmataV2
    private final SelenideElement clientSecretInputField=$x("//input[@id='password']");  // arn:aws:iam::094919933512:role/NirmataV2
    private final SelenideElement deleteButton=$x("//button[text()='Delete']");
    private final SelenideElement finishButton=$x("//button[text()='Finish']");
    private final SelenideElement successProviderStatus=$x("//span[@id='providerStatus'][contains(.,'Success')]");
    private final SelenideElement serviceAccountKeyInputField=$x("//input[@class='dz-hidden-input']");
    private final SelenideElement apiKeyDropBoxMessage=$x("//form[@id='apiKey']");
    private final SelenideElement toggleTableView=$x("//img[@id='toggleTableView']");
    private final SelenideElement toggleCardView=$x("//img[@id='toggleCardView']");
    private final SelenideElement modelContentPanelTitle=$x("//h1[@id='model-content-panel-title']");
    private final SelenideElement nextButtonEnabled=$x("//button[contains(text(),'Next')][not(@disabled)]");


    private SelenideElement cloudProvider;
    private final WebDriver driver;

    public CloudProvidersPage(WebDriver driver){
        this.driver=driver;
        addCloudProviderButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
        assertEquals(modelContentPanelTitle.getText(), "CloudProvider", "Incorrect Panel Title");
//        assertEquals(title(), "Nirmata | Cloud Providers", "Incorrect Page Title");
    }

    public CloudProvidersPage clickAddCloudProviderButton(){
        click("Add Cloud Provider Button",addCloudProviderButton);
        return this;
    }

    public CloudProvidersPage setNameInputField(String cloudProviderName){
        type("Name Input Field",nameInputField,cloudProviderName);
        return this;
    }

    public CloudProvidersPage selectTypeFromDropdown(String cloudProviderType){
        selectOptionByValue("Cloud Provider Type Dropdown",typeDropdown,cloudProviderType);
        return this;
    }

    public CloudProvidersPage clickNextButton(){
        click("Next Button",nextButton);
        return this;
    }

    public CloudProvidersPage clickFinishButton(){
        click("Next Button",finishButton);
        finishButton.should(disappear);
        return this;
    }

    public CloudProvidersPage selectRegionFromDropdown(String cloudProviderRegion){
        selectOptionByValue("Cloud Provider Region Dropdown",regionDropdown,cloudProviderRegion);
        return this;
    }

    public CloudProvidersPage setRoleArnInputField(String cloudProviderRoleArn){
        type("Cloud Provider Role ARN",roleArnInputField,cloudProviderRoleArn);
        return this;
    }

    public CloudProvidersPage setSubscriptionIdField(String subscriptionId){
        type("Subscription Id",subscriptionIdInputField,subscriptionId);
        return this;
    }

    public CloudProvidersPage setTenantIdInputField(String tenantId){
        type("Tenant Id",tenantIdInputField,tenantId);
        return this;
    }

    public CloudProvidersPage setClientIdInputField(String clientId){
        type("Client Id",clientIdInputField,clientId);
        return this;
    }

    public CloudProvidersPage setClientSecretInputField(String clientSecret){
        type("Client Secret",clientSecretInputField,clientSecret);
        return this;
    }

    public CloudProvidersPage waitForSuccessProviderStatus(){
        waitFor("SuccessProviderStatus",successProviderStatus);
        return this;
    }

    public InsideCloudProviderPage clickOnCloudProvider(String cloudProviderName){
        if(toggleTableView.exists()){
            cloudProvider=$x("//*[contains(@class,'card-content card-select')]//*[text()='"+cloudProviderName+"']");
        }else
            if(toggleCardView.exists()){
            cloudProvider=$x("//div[@class='provider-name']//*[text()='"+cloudProviderName+"']");
            }
        addCloudProviderButton.shouldBe(visible);
        cloudProvider.shouldBe(visible).click();
        return new InsideCloudProviderPage(driver);
    }

    public CloudProvidersPage hoverCloudProviderAndClickDeleteButton(String cloudProviderName){
        cloudProvider=$x("//*[@class='card-content card-select'][contains(.,'"+cloudProviderName+"')]");
        SelenideElement cloudProviderRemoveButton = $x("//*[text()='" + cloudProviderName + "']/../..//button[@action='deleteCloudProvider']");
        if(toggleCardView.exists()){
             toggleCardView.click();
             toggleTableView.shouldBe(visible);
        }
        cloudProvider.shouldBe(visible).hover();
        cloudProviderRemoveButton.shouldBe(visible).click();
        return this;
    }

    public CloudProvidersPage clickDeleteButton(){
        click("Delete Button",deleteButton);
        deleteButton.should(disappear);
        return this;
    }

    public CloudProvidersPage isCreatedCloudProviderDisplayed(String cloudProviderName){
        assertTrue(isCloudProviderDisplayed(cloudProviderName),"Cloud Provider Was Not Created");
        return this;
    }

    public CloudProvidersPage isDeletedCloudProviderDisplayed(String cloudProviderName){
        assertFalse(isCloudProviderDisplayed(cloudProviderName),"Cloud Provider Was Not Deleted");
        return this;
    }

    public CloudProvidersPage uploadGcpServiceAccountKey(String serviceAccountKey){
        String filePath=absolutePathOfFile+"/resources/data/"+ serviceAccountKey+".json";
        upload("GKE Service Account Key",serviceAccountKeyInputField, filePath);
        return this;
    }

    public CloudProvidersPage verifyApiKeyDropBoxMessage(){
        apiKeyDropBoxMessage.shouldBe(visible);
        assertTrue(apiKeyDropBoxMessage.getText().contains("Key file successfully loaded. Click Next to validate it."),
                "Incorrect API Key DropBox Message");
        return this;
    }

    private boolean isCloudProviderDisplayed(String cloudProviderName) {
        addCloudProviderButton.shouldBe(visible);
        boolean found = false;

        if(toggleTableView.exists()){
            cloudProvider=$x("//*[contains(@class,'card-content card-select')]//*[text()='"+cloudProviderName+"']");
        }else if(toggleCardView.exists()){
            cloudProvider=$x("//div[@class='provider-name']//*[text()='"+cloudProviderName+"']");
        }

        if (cloudProvider != null && cloudProvider.exists()) {
            found = true;
        } else if (nextButton.exists()) {
            nextButton.scrollIntoView(true);
            if (nextButtonEnabled.exists()) {
                do {
                    click("Next Button", nextButton);
                    modelContentPanelTitle.scrollIntoView(true);
                    if (cloudProvider.exists()) {
                        found = true;
                        break;
                    }
                } while (nextButtonEnabled.exists());
            }
        }
        return found;
    }

}
