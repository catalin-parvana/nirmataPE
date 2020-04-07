package pages.policies;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import pages.cluster.InsideClusterPage;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertEquals;

public class InsideClusterPolicyPage extends LibraryUtils {

    private WebDriver driver;
    private SelenideElement actionButton=$x("//a[contains(@class,' dropdown-toggle')]");
    private SelenideElement deletePolicyButton=$x("//a[@id='deletePolicy']");
    private SelenideElement deleteButton=$x("//button[contains(.,'Delete')]");
    private SelenideElement modelContentPanelTitle=$x("//h1[@id='model-content-panel-title']");


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

}
