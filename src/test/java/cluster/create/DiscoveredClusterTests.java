package cluster.create;

import org.testng.annotations.Test;
import pages.cluster.ClustersPage;
import pages.cluster.InsideClusterPage;
import utils.NirmataSetup;

public class DiscoveredClusterTests extends NirmataSetup {
    private ClustersPage clustersPage;
    private InsideClusterPage insideClusterPage;

    @Test(description = "Test Install Managed Cluster")
    public void testInstallAndManageExistingKubernetesCluster(){
        clustersPage=overviewPage.clickClusters();
        clustersPage
                .clickAddClusterButton()
                .clickManageExistingKubernetesClusterOption()
                .setInputNameForDiscoveredCluster("discovered-regression-cluster")
                .selectDiscoveredCloudProviderFromDropdown("Other")
                .selectDiscoveredClusterPolicyFromDropdown("aws-1.15")
                .setInputEndpointForDiscoveredCluster("")
                .checkDiscoveredAcceptSelfSignCertificatesCheckbox()
                .clickImportKubernetesClusterButton();

//                .waitForRunningPreInstallChecksLabel()
//                .waitForPreparingInstallLabel()
//                .waitForDeployingClusterLabel()
//                .waitForWaitingForControllerConnectLabel();
//
//        insideClusterPage=clustersPage.clickViewDetailsOfMyClusterButton();
//        insideClusterPage
//                .waitForClusterReadyState()
//                .verifyPanelTitle("managed-regression-cluster");
//        back();
//        refresh();
//        clustersPage
//                .isCreatedClusterDisplayed("managed-regression-cluster");
    }

    @Test(description = "Test Delete Managed Cluster")
    public void testDeleteCluster(){
        clustersPage=overviewPage.clickClusters();
        insideClusterPage=clustersPage.clickOnClusterWithName("discovered-regression-cluster");
        insideClusterPage
                .clickActionButton()
                .clickDeleteClusterButton()
                .setNameInputField("discovered-regression-cluster");
        clustersPage=insideClusterPage.clickDeleteButton();
        clustersPage
                .waitForReadyClusterStatus("discovered-regression-cluster")
                .waitForShuttingDownClusterStatus("discovered-regression-cluster")
                .waitForDeletedClusterStatus("discovered-regression-cluster")
                .isDeletedClusterDisplayed("discovered-regression-cluster");
    }
}
