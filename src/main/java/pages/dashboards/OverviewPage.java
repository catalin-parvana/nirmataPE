package pages.dashboards;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.catalog.catalog.CatalogsPage;
import pages.catalog.helmRepo.HelmChartsPage;
import pages.cloudProviders.CloudProvidersPage;
import pages.cluster.ClustersPage;
import pages.cluster.PEClustersPage;
import pages.environment.EnvironmentsPage;
import pages.hostGroups.AmazonWebServicesPage;
import pages.hostGroups.DirectConnectHostGroupsPage;
import pages.imageRegistries.ImageRegistriesPage;
import pages.policies.WorkloadPoliciesPage;
import pages.settings.ProfilePage;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.title;
import static org.testng.Assert.assertEquals;

public class OverviewPage extends LibraryUtils {


    private final WebDriver driver;
    public static SelenideElement welcomeMessage = $x("//div[@id='user-welcome'][contains(text(),'Welcome to Nirmata')]");
    public static SelenideElement nirmataLogo = $x("//a[@class='navbar-brand']//img");


    //pages
    private final SelenideElement cloudProvidersPage = $x("//*[@id='providers_menu']");
    private final SelenideElement imageRegistriesPage = $x("//*[@id='registries_menu']");
    private final SelenideElement dashboardsPage = $x("//*[@id='overview_menu']");
    private final SelenideElement inventoryPage = $x("//*[@id='dashboard_inventory_menu']");
    private final SelenideElement settingsPage = $x("//*[@id='settings_menu']");
    private final SelenideElement catalogsPage = $x("//*[@id='applications_menu']");
    private final SelenideElement helmChartsPage = $x("//*[@id='chartRepositories_menu']");
    private final SelenideElement environmentsPage = $x("//*[@id='env_menu']");
    private final SelenideElement peClustersPage = $x("//*[@id='hostClusters_menu']");
    private final SelenideElement hostGroupsPage = $x("//*[@id='hostgroups_menu']");
    private final SelenideElement awsPage = $x("//*[@id='aws_menu']");
    private final SelenideElement workloadPoliciesPage = $x("//*[@id='policies_menu']");
    private final SelenideElement tenantName = $x("//span[@id='tenant-name']");
    private final SelenideElement logOutButton = $x("//a[contains(.,'Log Out')]");
    private final SelenideElement ClustersPage = $x("//*[@id='hostClusters_menu']");

    public OverviewPage(WebDriver driver){
        this.driver=driver;
        welcomeMessage.shouldBe(visible);
        nirmataLogo.shouldBe(visible);
        assertEquals(title(), "Nirmata | Dashboards | Overview", "Incorrect Page Title");
    }

    public OverviewPage clickDashboards(){
        click("Dashboards Page",dashboardsPage);
        return new OverviewPage(driver);
    }

    public OverviewPage clickOverview(){
        click("Dashboards Page",dashboardsPage);
        return new OverviewPage(driver);
    }

    public CatalogsPage clickCatalog(){
        click("Catalogs Page",catalogsPage);
        return new CatalogsPage(driver);
    }

    public HelmChartsPage clickHelmCharts(){
        click("Helm Charts Page",helmChartsPage);
        return new HelmChartsPage(driver);
    }

    public EnvironmentsPage clickEnvironments(){
        click("Environments Page",environmentsPage);
        return new EnvironmentsPage(driver);
    }

  /*  public ClustersPage clickClusters(){
        click("Clusters Page",clustersPage);
        return new ClustersPage(driver);
    }*/

    public PEClustersPage clickPEClusters(){
        click("Clusters Page",peClustersPage);
        return new PEClustersPage(driver);
    }

    public ClustersPage clickClusters(){  //disable cluster
        click("Clusters Page",peClustersPage);
        return new ClustersPage(driver);
    }

    public DirectConnectHostGroupsPage clickHostGroups(){
        click("Host Groups Page",hostGroupsPage);
        return new DirectConnectHostGroupsPage(driver);
    }

    public AmazonWebServicesPage clickAmazonWebServices(){
        click("Amazon Web Services Page", awsPage);
        return new AmazonWebServicesPage(driver);
    }

    public ProfilePage clickSettings(){
        click("Profile Page",settingsPage);
        return new ProfilePage(driver);
    }

    public InventoryPage clickInventory(){
        click("Inventory Page",inventoryPage);
        return new InventoryPage(driver);
    }

    public ImageRegistriesPage clickImageRegistries(){
        click("Image Registries Page",imageRegistriesPage);
        return new ImageRegistriesPage(driver);
    }

    public CloudProvidersPage clickCloudProviders(){
        click("Cloud Providers Page",cloudProvidersPage);
        return new CloudProvidersPage(driver);
    }

    public OverviewPage clickTenantName(){
        click("Tenant Name Button",tenantName);
        return this;
    }

    public LoginPage clickLogOutButton(){
        click("Log Out Button",logOutButton);
        return new LoginPage(driver);
    }

    public WorkloadPoliciesPage clickPolicies(){
        click("Workload Policies Page",workloadPoliciesPage);
        return new WorkloadPoliciesPage(driver);
    }



}
