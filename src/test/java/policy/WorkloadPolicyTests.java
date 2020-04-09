package policy;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.policies.InsideWorkloadPolicyPage;
import pages.policies.WorkloadPoliciesPage;
import utils.NirmataSetup;

public class WorkloadPolicyTests extends NirmataSetup {

    private WorkloadPoliciesPage workloadPoliciesPage;
    private InsideWorkloadPolicyPage insideWorkloadPolicyPage;

    @Test(description = "Test Import Workload Policy Yaml")
    public void testImportWorkloadPolicyYaml(){
        login();
        workloadPoliciesPage=overviewPage.clickPolicies();
        workloadPoliciesPage
                .clickActionButton()
                .clickImportPolicyYamlsButton()
                .setYamlFile("require-pod-requests-limits")
                .clickImportPoliciesButton()
                .isCreatedWorkloadPolicyDisplayed("require-pod-requests-limits");
    }

    @Test(description = "Test Delete Imported Workload Policy")
    public void testDeleteImportedWorkloadPolicy(){
        login();
        workloadPoliciesPage=overviewPage.clickPolicies();
        workloadPoliciesPage
                .clickDeleteWorkloadPolicyButton("require-pod-requests-limits")
                .clickDeleteButton()
                .isDeletedSecretPolicyDisplayed("require-pod-requests-limits");
    }

    @Test(description = "Test Add Workload Policy")
    @Parameters({ "workloadPolicyName"})
    public void testAddWorkloadPolicy(String workloadPolicyName){
        login();
        workloadPoliciesPage=overviewPage.clickPolicies();
        workloadPoliciesPage
                .clickAddPolicyButton()
                .setNameInputField(workloadPolicyName)
                .selectFromCategoryDropdown("Workload Management")
                .clickEnabledCheckbox()
                .selectFromValidationFailureActionDropdown("audit")
                .clickNextButton()
                .clickUploadYamlFileOption()
                .setYamlFile("require-pod-requests-limits")
                .clickNextButton()
                .clickDeployToAllClustersCheckbox()
                .clickCompleteSetupButton()
                .isCreatedWorkloadPolicyDisplayed(workloadPolicyName);
    }

    @Test(description = "Test Clone Workload Policy")
    @Parameters({ "workloadPolicyName"})
    public void testCloneWorkloadPolicy(String workloadPolicyName){
        login();
        workloadPoliciesPage=overviewPage.clickPolicies();
        workloadPoliciesPage
                .clickAddPolicyButton()
                .setNameInputField(workloadPolicyName)
                .selectFromCategoryDropdown("Workload Management")
                .clickEnabledCheckbox()
                .selectFromValidationFailureActionDropdown("audit")
                .clickNextButton()
                .clickClonePolicyOption()
                .selectFromResourcePolicyYamlDropdown("add-networkpolicy")
                .clickNextButton()
                .clickDeployToAllClustersCheckbox()
                .clickCompleteSetupButton()
                .isCreatedWorkloadPolicyDisplayed(workloadPolicyName);
    }

    @Test(description = "Test Delete Workload Policy")
    @Parameters({ "workloadPolicyName"})
    public void testDeleteWorkloadPolicy(String workloadPolicyName){
        login();
        insideWorkloadPolicyPage=overviewPage.clickPolicies().clickOnWorkloadPolicyWithName(workloadPolicyName);
        insideWorkloadPolicyPage
                .verifyModelContentPanelTitle(workloadPolicyName)
                .clickActionButton()
                .clickDeletePolicyButton();
        workloadPoliciesPage=insideWorkloadPolicyPage.clickDeleteButton();
        workloadPoliciesPage
                .isDeletedSecretPolicyDisplayed(workloadPolicyName);
    }

}
