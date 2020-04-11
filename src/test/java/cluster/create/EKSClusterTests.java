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
        login();
        clustersPage=overviewPage.clickClusters();
        clustersPage
                .clickAddClusterButton()
                .clickCreateEKSClusterOption()
                .setInputNameForEKSCluster(eksClusterName)
                .selectEKSCloudProviderFromDropdown(cloudProviderName)
                .selectEKSRegionFromDropdown("us-east-2")
                .selectEKSKubernetesVersionFromDropdown("1.13")
                .scrollDownToClusterRole()
                .selectEKSVpcIdFromDropdown("vpc-dbff00b2")
                .selectEKSNetworksFromDropdown("subnet-e6445d9e")
                .selectEKSNetworksFromDropdown("subnet-c46ca1ad")
                .selectEKSSecurityGroupFromDropdown("sg-02a84990a940e2295")
                .checkEKSPrivateEndpointAccessCheckbox()
                .selectEKSClusterRoleArnFromDropdown("arn:aws:iam::094919933512:role/eks-role")
                .clickEKSNextButton()
                .scrollUpToEnableAutoScaling()
                .selectEKSNodeSecurityGroupFromDropdown("sg-0d43fe261ece89071")
                .selectEKSInstanceTypeFromDropdown("t2.large")
//                .setEKSImageId("ami-038a987c6425a84ad")
                .scrollDownToDiskSize()
                .setEKSInputAutoScalingGroupDesiredCapacity("2")
                .setEKSInputAutoScalingGroupMinSize("1")
                .setEKSInputAutoScalingGroupMaxSize("3")
                .selectEKSSshKeyFromDropdown("nirmata-west-1-071814")
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
        login();
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
