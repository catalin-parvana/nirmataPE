package pages.environment;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Selenide.$x;

public class InsideDeploymentPage extends LibraryUtils {

    private SelenideElement deploymentLabel=$x("//div[@class='pull-left model-index-name'][contains(.,'Deployment')]");
    private SelenideElement actionButton=$x("//a[@class='btn btn-default dropdown-toggle']");
    private SelenideElement restartButton=$x("//a[@id='addRolloutRestart']");
    private SelenideElement exposeAsServiceButton=$x("//a[@id='exposeAsService']");
    private SelenideElement deleteDeploymentButton=$x("//a[@id='deleteComponent']");
    private SelenideElement deleteButton=$x("//button[text()='Delete']");
    private SelenideElement deploymentNameInputField=$x("//input[@id='name']");

    private WebDriver driver;


    public InsideDeploymentPage(WebDriver driver){
        this.driver=driver;
        deploymentLabel.shouldBe(visible);
        actionButton.shouldBe(visible);
    }

    public InsideRunningApplicationPage clickDeleteButton(){
        click("Delete Button",deleteButton);
        deleteButton.should(disappear);
        return new InsideRunningApplicationPage(driver);
    }

    public InsideDeploymentPage clickActionButton(){
        click("Action Button",actionButton);
        return this;
    }

    public InsideDeploymentPage clickRestartButton(){
        click("Restart Button",restartButton);
        return this;
    }

    public InsideDeploymentPage clickExposeAsServiceButton(){
        click("Expose As Service Button",exposeAsServiceButton);
        return this;
    }

    public InsideDeploymentPage clickDeleteDeploymentButton(){
        click("Delete Deployment Button",deleteDeploymentButton);
        return this;
    }

    public InsideDeploymentPage setDeploymentNameToDelete(String deploymentName){
        type("Name Input Field",deploymentNameInputField,deploymentName);
        return this;
    }

}
