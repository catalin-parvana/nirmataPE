package NirmataPE;

import com.aventstack.extentreports.Status;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.NirmataApplicationProperties;
import utils.NirmataSetup;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class EC2Instance extends NirmataSetup {

    private final String awsAccount = appProperties.properties.getProperty("awsAccount");
    private final String awsUsername = appProperties.properties.getProperty("awsUsername");
    private final String awsPassword = appProperties.properties.getProperty("awsPassword");
    private final String nadmVersion = appProperties.properties.getProperty("nadmVersion");
    private String ec2InstanceID,ec2InstanceIP;
    private PropertiesConfiguration config;


    @Test(description = "Create EC2 Instance From Template")
    @Parameters({"templateName"})
    public void createEC2InstanceFromTemplate(String templateName) {
        open("https://us-west-1.console.aws.amazon.com/ec2/v2/home?region=us-west-1#LaunchInstanceFromTemplate:");
        $x("//*[@id='iam_user_radio_button']").shouldBe(visible).click();
        $x("//input[@id='resolving_input']").setValue(awsUsername);
        $x("//*[@id='next_button_text']").shouldBe(visible).click();
        $x("//input[@id='account']").setValue(awsAccount);
        $x("//input[@id='username']").setValue(awsUsername);
        $x("//input[@id='password']").setValue(awsPassword);
        $x("//a[@id='signin_button']").shouldBe(visible).click();
        switchTo().frame("instance-lx-react-frame");
        $x("//span[@id='awsui-select-0-textbox']").shouldBe(visible).click();
        $x("//span[contains(text(),'" + templateName + "')]").shouldBe(visible).click();
        $x("//button[contains(.,'Launch instance from template')]").shouldBe(visible).scrollIntoView(true).click();
        $x("//*[@data-id='summaryContainer'][contains(.,'Successfully initiated launch of instance')]").shouldBe(visible);
        ec2InstanceID=$x("//*[@data-id='summaryContainer']/a").shouldBe(visible).getText();
        $x("//*[@data-id='summaryContainer']/a").shouldBe(visible).click();
        System.out.println("ec2InstanceID= "+ec2InstanceID);
        try {
            config = new PropertiesConfiguration("resources/data/Application.properties");
            config.setProperty("ec2InstanceID",ec2InstanceID);
            config.save();
            Thread.sleep(120000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        		test.log(Status.PASS, "ec2InstanceID= "+"<b>"+ec2InstanceID+"/b");

    }

    @Test(description = "Get EC2 Instance Ip")
    public void getEC2InstanceIP() {
        ec2InstanceID = (new NirmataApplicationProperties()).properties.getProperty("ec2InstanceID");
        open("https://us-west-1.console.aws.amazon.com/ec2/v2/home?region=us-west-1#Instances:search=" + ec2InstanceID + ";sort=desc:launchTime");
        $x("//*[@id='iam_user_radio_button']").shouldBe(visible).click();
        $x("//input[@id='resolving_input']").setValue(awsUsername);
        $x("//*[@id='next_button_text']").shouldBe(visible).click();
        $x("//input[@id='account']").setValue(awsAccount);
        $x("//input[@id='username']").setValue(awsUsername);
        $x("//input[@id='password']").setValue(awsPassword);
        $x("//a[@id='signin_button']").shouldBe(visible).click();
        $x("//td/div[text()='"+ec2InstanceID+"']/../..//*[text()='running']").waitUntil(visible, 120000).click();
        $x("//span[@id='detailsPublicIp']").shouldBe(visible);
        ec2InstanceIP = $x("//span[@id='detailsPublicIp']").shouldBe(visible).getText();
        try {
            config = new PropertiesConfiguration("resources/data/Application.properties");
            config.setProperty("ec2InstanceIP",ec2InstanceIP);
            config.setProperty("url","https://"+ec2InstanceIP+":443");
            config.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        System.out.println("ec2InstanceIP= "+ec2InstanceIP);
        System.out.println("url= "+"https://"+ec2InstanceIP+":443");
//        test.log(Status.PASS, "ec2InstanceID= "+"<b>"+ec2InstanceID+"</b>");
//        test.log(Status.PASS, "ec2InstanceIP= "+"<b>"+ec2InstanceIP+"</b>");
        test.log(Status.PASS, "ec2InstanceID= "+"<a href=https://us-west-1.console.aws.amazon.com/ec2/v2/home?region=us-west-1#Instances:search=" + ec2InstanceID + ";sort=desc:launchTime>"+ec2InstanceID+"</a>");
        test.log(Status.PASS, "ec2InstanceIP= "+"<a href=https://us-west-1.console.aws.amazon.com/ec2/v2/home?region=us-west-1#Instances:search=" + ec2InstanceID + ";sort=desc:launchTime>"+ec2InstanceIP+"</a>");
        test.log(Status.PASS, "nadmVersion= "+"<b>"+nadmVersion+"</b>");
        test.log(Status.PASS, "NirmataPE url= "+ "<a href=https://"+ec2InstanceIP+":443>"+"https://"+ec2InstanceIP+":443</a>");
    }


    @Test(description = "Delete EC2 Instance")
    public void deleteEC2Instance(){
        ec2InstanceID = (new NirmataApplicationProperties()).properties.getProperty("ec2InstanceID");
        System.out.println("ec2InstanceID= "+ec2InstanceID);
        open("https://us-west-1.console.aws.amazon.com/ec2/v2/home?region=us-west-1#Instances:search="+ec2InstanceID+";sort=securityGroupNames");
        $x("//*[@id='iam_user_radio_button']").shouldBe(visible).click();
        $x("//input[@id='resolving_input']").setValue(awsUsername);
        $x("//*[@id='next_button_text']").shouldBe(visible).click();
        $x("//input[@id='account']").setValue(awsAccount);
        $x("//input[@id='username']").setValue(awsUsername);
        $x("//input[@id='password']").setValue(awsPassword);
        $x("//a[@id='signin_button']").shouldBe(visible).click();
        $x("//td/div[text()='"+ec2InstanceID+"']/../..//*[text()='running']").shouldBe(visible).click();
        $x("//button[contains(.,'Actions')]").shouldBe(visible).click();
        $x("//div[@id='gwt-debug-menu-instance-state']").shouldBe(visible).click();
        $x("//div[@id='gwt-debug-action-terminate-instances']").shouldBe(visible).click();
        $x("//li[contains(.,'"+ec2InstanceID+"')]").shouldBe(visible);
        $x("//span[contains(text(),'Yes, Terminate')]").shouldBe(visible).click();
        $x("//span[contains(text(),'Yes, Terminate')]").should(disappear);
        $x("//td/div[text()='"+ec2InstanceID+"']/../..//*[text()='shutting-down']").waitUntil(disappear,120000);
        $x("//td/div[text()='"+ec2InstanceID+"']/../..//*[text()='terminated']").waitUntil(appears,120000);
        try {
            config = new PropertiesConfiguration("resources/data/Application.properties");
            config.setProperty("url","https://nirmata.io");
            config.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        test.log(Status.PASS, "ec2InstanceID= "+"<b>"+ec2InstanceID+"/b");
    }

}
