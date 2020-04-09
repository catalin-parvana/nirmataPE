package policy;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.policies.SecretsPage;
import utils.NirmataSetup;

public class SecretPolicyTests extends NirmataSetup {

    private SecretsPage secretsPage;

    @Test(description = "Test Create Secret Policy")
    @Parameters({ "secretPolicyName"})
    public void testCreateSecretPolicy(String secretPolicyName){
        login();
        secretsPage=overviewPage.clickPolicies().clickSecrets();
        secretsPage
                .clickAddSecretPolicyButton()
                .setNameInputField(secretPolicyName)
                .selectFromSecretsManagerDropdown("kubernetes")
                .clickNextButton()
                .selectFromApplicationSelectorDropdown("all")
                .selectFromTypeDropdown("Opaque")
                .clickFinishButton()
                .isCreatedSecretPolicyDisplayed(secretPolicyName);
    }

    @Test(description = "Test Delete Secret Policy")
    @Parameters({ "secretPolicyName"})
    public void testDeleteSecretPolicy(String secretPolicyName){
        login();
        secretsPage=overviewPage.clickPolicies().clickSecrets();
        secretsPage
                .clickDeleteSecretPolicyButton(secretPolicyName)
                .clickDeleteButton()
                .isDeletedSecretPolicyDisplayed(secretPolicyName);
    }
}
