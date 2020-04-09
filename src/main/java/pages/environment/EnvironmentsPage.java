package pages.environment;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.*;


public class EnvironmentsPage extends LibraryUtils {

    private SelenideElement toggleCardView=$x("//*[@id='toggleCardView']");
    private SelenideElement toggleTableView=$x("//*[@id='toggleTableView']");
    private SelenideElement addEnvironmentButton= $x("//button[@class='btn btn-primary']");
    private SelenideElement inputNameField= $("input#name");
    private SelenideElement environmentTypeDropdown=$("select#resourceType");
    private SelenideElement clusterDropdown=$("select#hostCluster");
    private SelenideElement finishButton=$x("//button[text()='Finish']");
    private SelenideElement modelContentPanelTitle=$("h1#model-content-panel-title");
    private SelenideElement selectEnvironmentType=$("#resourceType");
    private SelenideElement selectCluster=$("#hostCluster");
    private SelenideElement nextButtonEnabled=$x("(//button[contains(text(),'Next')][not(@disabled)])[2]");
    private SelenideElement nextButton = $x("(//button[contains(text(),'Next')])[2]");
    private SelenideElement actionButton= $x("//a[@class='btn  dropdown-toggle']");


    private SelenideElement environment;
    private boolean found=false;

    private WebDriver driver;


    public EnvironmentsPage(WebDriver driver){
        this.driver=driver;
        addEnvironmentButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
        assertEquals(modelContentPanelTitle.getText(), "Environments", "Incorrect Panel Title");
    }

    public EnvironmentsPage clickAddEnvironment(){
        click("Add Environment Button",addEnvironmentButton);
        return this;
    }

    public EnvironmentsPage setInputFieldName(String environmentName){
        type("Input Name Field",inputNameField,environmentName);
        return this;
    }

    public EnvironmentsPage selectEnvironmentTypeFromDropdown(String environmentTypeValue){
        selectOptionByValue("Environment Type <b>"+environmentTypeValue+"</b>",environmentTypeDropdown,environmentTypeValue);
        return this;
    }

    public EnvironmentsPage selectClusterFromDropdown(String clusterName){
        selectOptionByText("Cluster <b>"+clusterName+"</b>",clusterDropdown,clusterName);
        return this;
    }

    public InsideEnvironmentPage clickFinishButton(){
        click("Finish Button",finishButton);
        finishButton.should(disappear);
        return new InsideEnvironmentPage(driver);
    }

    public InsideEnvironmentPage clickOnEnvironmentWithName(String environmentName)  {
        addEnvironmentButton.shouldBe(visible);

        if(toggleCardView.exists()){
            environment=$x("//div[@class='environment-name']//*[text()='"+environmentName+"']");
        }else if(toggleTableView.exists()){
            environment=$x("//div[contains(@class,'card-content card-select')]//*[text()='"+environmentName+"']");}

        if (environment!=null && environment.exists()){
            click("Environment "+environmentName,environment);
        }else
            if(nextButton.exists()) {
                if (nextButtonEnabled.exists()) {
                    do {
                        nextButton.scrollIntoView(true);
                        click("Next Button", nextButton);
                        modelContentPanelTitle.scrollIntoView(true);
                        if (environment.exists()) {
                            click("Environment " + environmentName, environment);
                            break;
                        }
                    }
                    while (nextButtonEnabled.exists());
                }
            }
            return new InsideEnvironmentPage(driver);
    }

    public EnvironmentsPage isDeletedEnvironmentDisplayed(String environmentName){
        assertFalse(isEnvironmentDisplayed(environmentName),"Environment Was Not Deleted");
        return this;
    }

    public EnvironmentsPage isCreatedEnvironmentDisplayed(String environmentName){
        assertTrue(isEnvironmentDisplayed(environmentName),"Environment Was Not Created");
        return this;
    }

    private boolean isEnvironmentDisplayed(String environmentName) {
        addEnvironmentButton.shouldBe(visible);

        if(toggleTableView.exists()){
            environment=$x("//div[contains(@class,'card-content card-select')]//*[text()='"+environmentName+"']");
        }else
            if(toggleCardView.exists()){
            environment=$x("//div[@class='environment-name']//*[text()='"+environmentName+"']");
            }

        if(environment!=null && environment.exists()){
            found=true;
        }else
            if(nextButton.exists()) {
                if (nextButtonEnabled.exists()) {
                    do {
                        nextButton.scrollIntoView(true);
                        click("Next Button", nextButton);
                        modelContentPanelTitle.scrollIntoView(true);
                        if (environment.exists()) {
                            found = true;
                            break;
                        }
                    } while (nextButtonEnabled.exists());
                }
            }
            return found;
    }

}
