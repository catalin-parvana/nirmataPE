package policy;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.cluster.ClustersPage;
import pages.policies.ClusterPoliciesPage;
import pages.policies.InsideClusterPolicyPage;
import utils.NirmataSetup;

import static com.codeborne.selenide.Selenide.back;

public class ClusterPolicyTests extends NirmataSetup {

    private ClusterPoliciesPage clusterPoliciesPage;
    private InsideClusterPolicyPage insideClusterPolicyPage;

    @Test(description = "Test Create Cluster Policy")
    @Parameters({ "clusterPolicyName"})
    public void testCreateClusterPolicy(String clusterPolicyName){
        clusterPoliciesPage=overviewPage.clickPolicies().clickClusterPolicies();
        clusterPoliciesPage
                .clickAddClusterPolicy()
                .setPolicyName(clusterPolicyName)
                .selectClusterTypeFromDropdown("Installed Clusters")  //managed
                .selectCloudProviderFromDropdown("AWS")
                .clickNextButton()
                .selectKubernetesVersionFromDropdown("v1.15.3")   //   v1.16.0
                .clickNextButton();
        insideClusterPolicyPage=clusterPoliciesPage.clickFinishButton();
        insideClusterPolicyPage
                .verifyPanelTitle(clusterPolicyName);
        back();
        clusterPoliciesPage
                .isCreatedClusterPolicyDisplayed(clusterPolicyName);
    }

    @Test(description = "Test Delete Cluster Policy")
    @Parameters({ "clusterPolicyName"})
    public void testDeleteClusterPolicy(String clusterPolicyName){
        clusterPoliciesPage=overviewPage.clickPolicies().clickClusterPolicies();
        insideClusterPolicyPage=clusterPoliciesPage.clickClusterPolicyWithName(clusterPolicyName);
        insideClusterPolicyPage
                .clickActionButton()
                .clickDeletePolicyButton();
        clusterPoliciesPage=insideClusterPolicyPage.clickDeleteButton();
        clusterPoliciesPage
                .isDeletedClusterPolicyDisplayed(clusterPolicyName);
    }


}
