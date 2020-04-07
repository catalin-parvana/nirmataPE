package pages.policies;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import pages.catalog.catalog.InsideApplicationPage;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;


import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class WorkloadPoliciesPage extends LibraryUtils {

    private WebDriver driver;
    private SelenideElement clusterPoliciesPage=$x("//*[@id='clusterPolicy_menu']");
    private SelenideElement configMapsPage=$x("//*[@id='configMapPolicy_menu']");
    private SelenideElement secretsPage=$x("//*[@id='secretPolicy_menu']");
    private SelenideElement patchPoliciesPage=$x("//*[@id='resourcePatchPolicy_menu']");
    private SelenideElement environmentTypesPage=$x("//*[@id='environmentTypes_menu']");
    private SelenideElement addPolicyButton=$x("//button[contains(.,'Add  Policy')]");
    private SelenideElement actionButton=$x("//a[contains(@class,'btn-lg dropdown-toggle')]");
    private SelenideElement nameInputField=$x("//input[@id='name']");
    private SelenideElement categoryDropdown=$x("//select[@id='category']");
    private SelenideElement enabledCheckbox=$x("(//*[contains(@class,'icheckbox_square-blue')])[1]");
    private SelenideElement deployToAllClustersCheckbox=$x("(//*[contains(@class,'icheckbox_square-blue')])[2]");
    private SelenideElement validationFailureActionDropdown=$x("//select[@id='validationFailureAction']");
    private SelenideElement deployToClustersDropdown=$x("//select[@id='deployToClusters']");
    private SelenideElement uploadYamlFileOption=$x("//*[@class='card-title'][text()='Upload YAML File']");
    private SelenideElement clonePolicyOption=$x("//*[@class='card-title'][text()='Clone Policy']");
    private SelenideElement resourcePolicyYamlDropdown=$x("//select[@id='resource-policy-yamls']");
    private SelenideElement nextButton=$x("(//button[contains(.,'Next')])[1]");
    private SelenideElement deleteButton=$x("//button[contains(.,'Delete')]");
    private SelenideElement completeSetupButton=$x("//button[text()='Complete Setup']");
    private SelenideElement importPolicyYamlsButton=$x("//a[@id='importResourcePolicies']");
    private SelenideElement importPoliciesButton=$x("//button[contains(.,'Import Policies')]");
    private SelenideElement yamlInputField=$x("(//input[@class='dz-hidden-input'])[1]");
    private SelenideElement yaml=$x("//div[@class='dz-details']");

    public WorkloadPoliciesPage(WebDriver driver){
        this.driver=driver;
        addPolicyButton.shouldBe(visible);
        actionButton.shouldBe(visible);
    }

    public ConfigMapsPage clickConfigMaps(){
        click("Config Maps Page",configMapsPage);
        return new ConfigMapsPage(driver);
    }

    public SecretsPage clickSecrets(){
        click("Secrets Page",secretsPage);
        return new SecretsPage(driver);
    }

    public PatchPoliciesPage clickPatchPolicies(){
        click("Patch Policies Page",patchPoliciesPage);
        return new PatchPoliciesPage(driver);
    }

    public EnvironmentTypesPage clickEnvironmentTypes(){
        click("Environment Types Page",environmentTypesPage);
        return new EnvironmentTypesPage(driver);
    }

    public ClusterPoliciesPage clickClusterPolicies(){
        click("Cluster Policies Page",clusterPoliciesPage);
        return new ClusterPoliciesPage(driver);
    }

    public WorkloadPoliciesPage clickAddPolicyButton(){
        click("Add Policies Button",addPolicyButton);
        return this;
    }

    public WorkloadPoliciesPage clickActionButton(){
        click("Action Button",actionButton);
        return this;
    }

    public WorkloadPoliciesPage setNameInputField(String name){
        type("Name Input Field",nameInputField,name);
        return this;
    }

    public WorkloadPoliciesPage selectFromCategoryDropdown(String categoryValue){
        selectOptionByText("Category Dropdown",categoryDropdown,categoryValue);
        return this;
    }

    public WorkloadPoliciesPage clickEnabledCheckbox(){
        click("Enabled Checkbox",enabledCheckbox);
        return this;
    }

    public WorkloadPoliciesPage clickDeployToAllClustersCheckbox(){
        click("Deploy To All Clusters Checkbox",deployToAllClustersCheckbox);
        return this;
    }

    public WorkloadPoliciesPage selectFromValidationFailureActionDropdown(String validationFailureActionValue){
        selectOptionByValue("Validation Failure Action Dropdown",validationFailureActionDropdown,validationFailureActionValue);
        return this;
    }

    public WorkloadPoliciesPage selectFromDeployToClusterDropdown(String clusterNameText){
        selectOptionByText("Deploy To Cluster Dropdown",deployToClustersDropdown,clusterNameText);
        return this;
    }

    public WorkloadPoliciesPage selectFromResourcePolicyYamlDropdown(String resourcePolicyYamlText){
        selectOptionByText("Resource Policy Yaml Dropdown",resourcePolicyYamlDropdown,resourcePolicyYamlText);
        return this;
    }

    public WorkloadPoliciesPage clickUploadYamlFileOption(){
        click("Upload Yaml File Option",uploadYamlFileOption);
        return this;
    }

    public WorkloadPoliciesPage clickClonePolicyOption(){
        click("Clone Policy Option",clonePolicyOption);
        return this;
    }

    public WorkloadPoliciesPage clickNextButton(){
        click("Next Button",nextButton);
        return this;
    }

    public WorkloadPoliciesPage clickDeleteButton(){
        click("Delete Button",deleteButton);
        deleteButton.should(disappear);
        return this;
    }

    public WorkloadPoliciesPage clickImportPolicyYamlsButton(){
        click("Import Policy Yamls Button",importPolicyYamlsButton);
        return this;
    }

    public WorkloadPoliciesPage clickImportPoliciesButton(){
        click("Import Policies  Button",importPoliciesButton);
        importPoliciesButton.should(disappear);
        return this;
    }

    public WorkloadPoliciesPage clickCompleteSetupButton(){
        click("Complete Setup Button",completeSetupButton);
        completeSetupButton.should(disappear);
        return this;
    }

    public WorkloadPoliciesPage setYamlFile(String yamlName){
        String filePath=absolutePathOfFile+"/resources/yaml/"+ yamlName+".yaml";
        upload("Yaml File "+yamlName+".yaml",yamlInputField,filePath);
        yaml.shouldBe(visible);
        return this;
    }

    public WorkloadPoliciesPage isCreatedWorkloadPolicyDisplayed(String workloadPolicyName){
        assertTrue(isWorkloadPolicyDisplayed(workloadPolicyName),"Workload Policy Was Not Created");
        return this;
    }

    public WorkloadPoliciesPage isDeletedSecretPolicyDisplayed(String workloadPolicyName){
        assertFalse(isWorkloadPolicyDisplayed(workloadPolicyName),"Workload Policy Was Not Deleted");
        return this;
    }

    private boolean isWorkloadPolicyDisplayed(String workloadPolicyName) {
        addPolicyButton.shouldBe(visible);
        boolean found = false;
        SelenideElement workloadPolicy = $x("//*[text()='"+workloadPolicyName+"']/../../..");
        try{
            if (workloadPolicy.exists()){
                found=true;
            }
        }catch(Exception e){
            found=false;
        }
        return found;
    }

    public WorkloadPoliciesPage clickDeleteWorkloadPolicyButton(String workloadPolicyName){
        SelenideElement deleteWorkloadPolicyButton=$x("//*[text()='"+workloadPolicyName+"']/../../..//*[@class='fa fa-times']");
        click("Delete Workload Policy Button",deleteWorkloadPolicyButton);
        return this;
    }

    public WorkloadPoliciesPage clickEditWorkloadPolicyButton(String workloadPolicyName){
        SelenideElement editWorkloadPolicyButton=$x("//*[text()='"+workloadPolicyName+"']/../../..//*[@class='fa fa-edit']");
        click("Edit Workload Policy Button",editWorkloadPolicyButton);
        return this;
    }

    public InsideWorkloadPolicyPage clickOnWorkloadPolicyWithName(String workloadPolicyName){
        SelenideElement workloadPolicy=$x("//*[text()='"+workloadPolicyName+"']/..");
        click("Workload Policy "+workloadPolicyName,workloadPolicy);
        return new InsideWorkloadPolicyPage(driver);
    }





}
