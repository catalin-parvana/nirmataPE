package imageRegistries;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.imageRegistries.ImageRegistriesPage;
import pages.imageRegistries.InsideImageRegistryPage;
import utils.NirmataSetup;


public class ImageRegistriesTests extends NirmataSetup {
    private ImageRegistriesPage imageRegistriesPage;
    private InsideImageRegistryPage insideImageRegistryPage;

    @Test(description = "Test Add Image Registry")
    @Parameters({ "imageRegistryName"})
    public void testAddImageRegistry(String imageRegistryName){
        login();
        imageRegistriesPage=overviewPage.clickImageRegistries();
        imageRegistriesPage
                .clickAddImageRegistryButton()
                .selectFromRegistryProviderDropdown("docker")
                .setNameInputField(imageRegistryName)
                .setLocationInputField("hub.docker.com/repository/docker/")
                .setUsernameInputField("")
                .setPasswordInputField("")
//                .checkPreferredRegistryCheckbox()
//                .selectFromPrivateCloudDropdown("private-cloud-lab")
                .clickAddButton()
                .isCreatedRegistryDisplayed(imageRegistryName);
    }

    @Test(description = "Test Delete Image Registry")
    @Parameters({ "imageRegistryName"})
    public void testDeleteImageRegistry(String imageRegistryName){
        login();
        imageRegistriesPage=overviewPage.clickImageRegistries();
        insideImageRegistryPage=imageRegistriesPage.clickOnImageRegistry(imageRegistryName);
        insideImageRegistryPage
                .clickActionButton()
                .clickDeleteRegistryButton();
        imageRegistriesPage=insideImageRegistryPage.clickDeleteButton();
        imageRegistriesPage
                .isDeletedRegistryDisplayed(imageRegistryName);

    }
}
