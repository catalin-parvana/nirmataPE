package policy;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.policies.EnvironmentTypesPage;
import utils.NirmataSetup;

public class EnvironmentTypesTests extends NirmataSetup {

    private EnvironmentTypesPage environmentTypesPage;

    @Test(description = "Test Create Environment Type")
    @Parameters({ "environmentTypeName"})
    public void testCreateEnvironmentType(String environmentTypeName){
        login();
        environmentTypesPage=overviewPage.clickPolicies().clickEnvironmentTypes();
        environmentTypesPage
                .clickAddEnvironmentTypeButton()
                .setNameInputField(environmentTypeName)
                .setResourceLimitsKeyInputField("memory")
                .setResourceLimitsValueInputField("6Gi")
                .clickAddButton()
                .isCreatedEnvironmentTypeDisplayed(environmentTypeName);
    }

    @Test(description = "Test Delete Environment Type")
    @Parameters({ "environmentTypeName"})
    public void testDeleteEnvironmentType(String environmentTypeName){
        login();
        environmentTypesPage=overviewPage.clickPolicies().clickEnvironmentTypes();
        environmentTypesPage
                .clickDeleteEnvironmentType(environmentTypeName)
                .clickDeleteButton()
                .isDeletedEnvironmentTypeDisplayed(environmentTypeName);
    }

}
