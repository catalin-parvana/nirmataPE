package policy;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.policies.ConfigMapsPage;
import utils.NirmataSetup;

public class ConfigMapPolicyTests  extends NirmataSetup {

    private ConfigMapsPage configMapsPage;

    @Test(description = "Test Create Config Map Policy")
    @Parameters({ "configMapPolicyName"})
    public void testCreateConfigMapPolicy(String configMapPolicyName){
        login();
        configMapsPage=overviewPage.clickPolicies().clickConfigMaps();
        configMapsPage
                .clickAddConfigMapPolicyButton()
                .setNameInputField(configMapPolicyName)
                .selectFromApplicationSelectorDropdown("all")
                .clickNextButton()
                .clickFinishButton()
                .isCreatedConfigMapDisplayed(configMapPolicyName);
    }

    @Test(description = "Test Delete Config Map Policy")
    @Parameters({ "configMapPolicyName"})
    public void testDeleteConfigMapPolicy(String configMapPolicyName){
        login();
        configMapsPage=overviewPage.clickPolicies().clickConfigMaps();
        configMapsPage
                .clickDeleteConfigMapButton(configMapPolicyName)
                .clickDeleteButton()
                .isDeletedConfigMapDisplayed(configMapPolicyName);
    }
}
