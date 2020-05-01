package pages.environment;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import pages.imageRegistries.InsideImageRegistryPage;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.*;

public class InsideEditInitContainerPage extends LibraryUtils {

   // private final SelenideElement addImageRegistryButton=$x("//button[contains(.,'Add Image Registry')]");
    private final SelenideElement imagePullPolicyDropdown=$x("//select[@id='imagePullPolicyDropdown']");
    private final SelenideElement nameInputField=$x("//input[@id='name']");
    private final SelenideElement imageInputField=$x("//input[@id='imageRepository']");
    private final SelenideElement cancelButton=$x("//button[text()='Cancel']");
    private final SelenideElement finishButton=$x("//button[text()='Finish']");
    private final SelenideElement modelContentPanelTitle=$x("//h1[@id='model-content-panel-title']");
    private final WebDriver driver;

    public InsideEditInitContainerPage(WebDriver driver){
        this.driver=driver;
     //   addImageRegistryButton.shouldBe(visible);
     //   modelContentPanelTitle.shouldBe(visible);
     //   assertEquals(modelContentPanelTitle.getText(), "Edit Init Container", "Incorrect Panel Title");
    }



    public InsideEditInitContainerPage setNameInputField(String name){
        type("Container Name Input Field",nameInputField,name);
        return this;
    }

    public InsideEditInitContainerPage setImageInputField(String image){
        type("Image Input Field",imageInputField,image);
        return this;
    }

    public InsideEditInitContainerPage selectImagePullPolicyDropdown(String imagePullPolicy){
        selectOptionByValue("Image Pull Policy Dropdown",imagePullPolicyDropdown,imagePullPolicy);
        return this;
    }

    public InsideEditInitContainerPage clickCancelButton(){
        click("Cancel Button",cancelButton);
        return this;
    }

    public InsideEditInitContainerPage clickFinishButton(){
        click("Finish Button",finishButton);
        finishButton.should(disappear);
        return this;
    }


}
