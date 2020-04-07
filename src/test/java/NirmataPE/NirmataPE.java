package NirmataPE;


import org.testng.annotations.Test;
import utils.NirmataSetup;

public class NirmataPE extends NirmataSetup {
    private String key = System.getProperty("user.dir")+"/resources/data/nirmata-west-1-062014.pem";
    private String instanceIP = appProperties.properties.getProperty("instanceIP");
    private String nadmVersion = appProperties.properties.getProperty("nadmVersion");


    @Test(description = "Install NirmataPE")
    public void installNirmataPE()  {


       String curl= "curl -LO https://nadm-release.s3-us-west-1.amazonaws.com/"+nadmVersion;
    }


}
