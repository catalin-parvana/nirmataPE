package pages.policies;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertEquals;

public class InsideClusterPolicyPage extends LibraryUtils {

    private final WebDriver driver;
    private final SelenideElement actionButton=$x("//a[contains(@class,' dropdown-toggle')]");
    private final SelenideElement deletePolicyButton=$x("//a[@id='deletePolicy']");
    private final SelenideElement deleteButton=$x("//button[contains(.,'Delete')]");
    private final SelenideElement modelContentPanelTitle=$x("//h1[@id='model-content-panel-title']");
    private final SelenideElement editControllerSettingsCheckbox=$x("//div[@id='controller-section']//a[contains(@class,'edit-button')]");
    private final SelenideElement acceptSelfsignedCertificateCheckbox=$x("//span[contains(text(),'Accept Self-signed Certificate')]/..//*[@class='icheckbox_square-blue']");
    private final SelenideElement saveButton=$x("//button[contains(.,'Save')]");
    public InsideClusterPolicyPage(WebDriver driver){
        this.driver=driver;
        actionButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
    }

    public InsideClusterPolicyPage clickActionButton(){
        click("Action Button", actionButton);
        return this;
    }

    public InsideClusterPolicyPage clickDeletePolicyButton(){
        click("Delete Policy Button", deletePolicyButton);
        return this;
    }

    public ClusterPoliciesPage clickDeleteButton(){
        click("Delete Button", deleteButton);
        deleteButton.should(disappear);
        return new ClusterPoliciesPage(driver);
    }

    public InsideClusterPolicyPage verifyPanelTitle(String policyName){
        waitFor("Content Panel Title",modelContentPanelTitle);
        assertEquals(modelContentPanelTitle.text(), policyName, "Incorrect Panel Title");
        return this;
    }

    public InsideClusterPolicyPage clickEditControllerSettings(){
        scrollIntoView("Edit Controller Settings",editControllerSettingsCheckbox);
        click("Edit Controller Settings", editControllerSettingsCheckbox);
        return this;
    }

    public InsideClusterPolicyPage clickAcceptSelfsignedCertificate(){
        click("Accept Self-signed Certificate", acceptSelfsignedCertificateCheckbox);
        return this;
    }

    public InsideClusterPolicyPage clickSaveButton(){
        click("Save Button", saveButton);
        saveButton.should(disappear);
        return new InsideClusterPolicyPage(driver);
    }

}
