package policy;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.policies.PatchPoliciesPage;
import utils.NirmataSetup;

public class PatchPoliciesTests extends NirmataSetup {

    private PatchPoliciesPage patchPoliciesPage;

    @Test(description = "Test Create Patch Policy")
    @Parameters({ "patchPolicyName"})
    public void testCreatePatchPolicy(String patchPolicyName){
        login();
        patchPoliciesPage=overviewPage.clickPolicies().clickPatchPolicies();
        patchPoliciesPage
                .clickAddPatchPolicyButton()
                .setNameInputField(patchPolicyName)
                .selectFromResourceTypeDropdown("ResourceQuota")
                .clickAddButton()
                .isCreatedPatchPolicyDisplayed(patchPolicyName);
    }

    @Test(description = "Test Delete Patch Policy")
    @Parameters({ "patchPolicyName"})
    public void testDeletePatchPolicy(String patchPolicyName){
        login();
        patchPoliciesPage=overviewPage.clickPolicies().clickPatchPolicies();
        patchPoliciesPage
                .clickDeletePatchPolicyButton(patchPolicyName)
                .clickDeleteButton()
                .isDeletedPatchPolicyDisplayed(patchPolicyName);
    }
}
