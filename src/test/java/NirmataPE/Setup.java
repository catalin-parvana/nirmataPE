package NirmataPE;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.adminDashboard.CreateAdminPage;
import pages.catalog.catalog.CatalogsPage;
import pages.catalog.catalog.InsideApplicationPage;
import pages.catalog.catalog.InsideCatalogPage;
import pages.environment.InsideDeploymentPage;
import pages.environment.InsideEditContainerPage;
import pages.environment.InsideEditInitContainerPage;
import pages.environment.InsideRunningApplicationPage;
import pages.imageRegistries.ImageRegistriesPage;
import pages.policies.ClusterPoliciesPage;
import pages.policies.InsideClusterPolicyPage;
import utils.NirmataApplicationProperties;
import utils.NirmataSetup;

import static com.codeborne.selenide.Selenide.back;

public class Setup extends NirmataSetup {

    private ImageRegistriesPage imageRegistriesPage;
    private CatalogsPage catalogPage;
    private InsideCatalogPage insideCatalogPage;
    private InsideApplicationPage insideApplicationPage;
    private InsideDeploymentPage insideDeploymentPage;
    private InsideEditInitContainerPage insideEditInitContainerPage;
    private InsideEditContainerPage insideEditContainerPage;
    private String ec2InstanceID,ec2InstanceIP;
    private ClusterPoliciesPage clusterPoliciesPage;
    private InsideClusterPolicyPage insideClusterPolicyPage;

    @Test(description = "Initilization")
    public void initilization() {
        login();
        overviewPage.clickCatalog();
    }

    @Test(description = "Add Private Docker Registry ")
    public void addPrivateDockerRegistry() {
       //ec2InstanceID = (new NirmataApplicationProperties()).properties.getProperty("ec2InstanceID");
        login();
        imageRegistriesPage=overviewPage.clickImageRegistries()
                .clickAddImageRegistryButton()
                .setNameInputField("Private Registry")
                .setLocationInputField("registry-open.nirmata.io")
                .checkPreferredRegistryCheckbox()
                .clickAddButton();
    }

    @Test(description = "Kyverno Add On")
    public void kyvernoAddOn() {
        ec2InstanceID = (new NirmataApplicationProperties()).properties.getProperty("ec2InstanceID");
        login();
        catalogPage=overviewPage.clickCatalog();
        insideCatalogPage=catalogPage.clickOnCatalogWithName("default-addon-catalog");
        insideApplicationPage=insideCatalogPage.clickOnApplicationWithName("kyverno");
        insideDeploymentPage=insideApplicationPage.clickDeploymentLink("kyverno");
        insideEditInitContainerPage=insideDeploymentPage.editInitContainer()
                .setImageInputField("registry-open.nirmata.io/nirmata/kyvernopre")
                .clickFinishButton();
        insideEditContainerPage=insideDeploymentPage.editContainer()
                .setImageInputField("registry-open.nirmata.io/nirmata/kyverno")
                .selectTagDropdown("v1.1.3")
                .clickFinishButton();

    }

    @Test(description = "Cluster Policy")
    @Parameters({ "clusterPolicyName"})
    public void clustePolicy(String clusterPolicyName) {
        login();
        clusterPoliciesPage=overviewPage.clickPolicies().clickClusterPolicies();
        clusterPoliciesPage
                .clickAddClusterPolicy()
                .setPolicyName(clusterPolicyName)
                .selectClusterTypeFromDropdown("Installed Clusters")  //managed
                .selectCloudProviderFromDropdown("Other")
                .clickNextButton()
                .selectKubernetesVersionFromDropdown("v1.15.3")   //   v1.16.0
                .clickNextButton()
                .selectHaproxyIngress();
        insideClusterPolicyPage=clusterPoliciesPage.clickFinishButton();
        insideClusterPolicyPage
                .verifyPanelTitle(clusterPolicyName)
                .clickEditControllerSettings()
                .clickAcceptSelfsignedCertificate()
                .clickSaveButton();
        back();
        clusterPoliciesPage
                .isCreatedClusterPolicyDisplayed(clusterPolicyName);
    }



}
