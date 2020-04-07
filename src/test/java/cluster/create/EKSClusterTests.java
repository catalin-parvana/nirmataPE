package cluster.create;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.cluster.ClustersPage;
import pages.cluster.InsideClusterPage;
import utils.NirmataSetup;

import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.refresh;

public class EKSClusterTests extends NirmataSetup {

    private ClustersPage clustersPage;
    private InsideClusterPage insideClusterPage;


    @Test(description = "Test Create EKS Cluster")
    @Parameters({"eksClusterName","cloudProviderName"})
    public void testCreateEKSCluster(String eksClusterName, String cloudProviderName){
        clustersPage=overviewPage.clickClusters();
        clustersPage
                .clickAddClusterButton()
                .clickCreateEKSClusterOption()
                .setInputNameForEKSCluster(eksClusterName)
                .selectEKSCloudProviderFromDropdown(cloudProviderName)   //"58f3d637-7e79-45c6-ba64-be3ef54cb2b2"
                .selectEKSRegionFromDropdown("us-west-2")
                .selectEKSKubernetesVersionFromDropdown("1.14")
                .scrollDownToClusterRole()
                .selectEKSVpcIdFromDropdown("vpc-06199a7c8ebb38d03")
                .selectEKSNetworksFromDropdown("subnet-0b9f9778f62a39070")
                .selectEKSNetworksFromDropdown("subnet-06a580f4a0cc28efa")
                .selectEKSSecurityGroupFromDropdown("sg-03f0d9dcee425e903")
                .checkEKSPrivateEndpointAccessCheckbox()
                .selectEKSClusterRoleArnFromDropdown("arn:aws:iam::094919933512:role/eks-role")
                .clickEKSNextButton()
                .scrollUpToEnableAutoScaling()
//                .checkEKSEnableAutoScalingCheckbox()
                .selectEKSNodeSecurityGroupFromDropdown("sg-03f0d9dcee425e903")
                .selectEKSInstanceTypeFromDropdown("t2.large")
                .selectEKSImageIdFromDropdown("ami-038a987c6425a84ad")
                .scrollDownToDiskSize()
                .setEKSInputAutoScalingGroupDesiredCapacity("2")
                .setEKSInputAutoScalingGroupMinSize("1")
                .setEKSInputAutoScalingGroupMaxSize("3")
                .selectEKSSshKeyFromDropdown("nirmata-west-1-062014")
                .setEKSDiskSize("30")
                .clickCreateClusterButton()
                .waitForDeployingControlPlaneLabel()
                .waitForDeployingWorkerNodeLabel()
                .waitForLoadingAWSAuthConfigMapLabel()
                .waitForDeployingNirmataControllerLabel()
                .waitForDeployingMetricsServerLabel();
        insideClusterPage=clustersPage.clickViewDetailsOfMyClusterButton();
        insideClusterPage
                .waitForClusterReadyState()
                .verifyPanelTitle(eksClusterName);
        back();
        refresh();
        clustersPage
                .isCreatedClusterDisplayed(eksClusterName);
    }

    @Test(description = "Test Delete EKS Cluster")
    @Parameters({"eksClusterName"})
    public void testDeleteEKSCluster(String eksClusterName){
        clustersPage=overviewPage.clickClusters();
        insideClusterPage=clustersPage.clickOnClusterWithName(eksClusterName);
        insideClusterPage
                .clickActionButton()
                .clickDeleteClusterButton()
                .setNameInputField(eksClusterName);
        clustersPage=insideClusterPage.clickDeleteButton();
        refresh();
        clustersPage
//                .waitForReadyClusterStatus("eks-regression-cluster")
//                .waitForShuttingDownClusterStatus("gke-regression-cluster")
//                .waitForDeletedClusterStatus(eksClusterName)
                .isDeletedClusterDisplayed(eksClusterName);

    }
}
