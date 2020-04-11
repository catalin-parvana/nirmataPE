package NirmataPE;

import com.jcraft.jsch.*;
import org.testng.annotations.Test;
import utils.NirmataApplicationProperties;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;


public class NadmCluster {

    private NirmataApplicationProperties appProperties= new NirmataApplicationProperties();
    private String keyPair = System.getProperty("user.dir")+"/resources/data/nirmata-west-1-062014.pem";
    private String nadmVersion = appProperties.properties.getProperty("nadmVersion");
    private String nadmUrl = appProperties.properties.getProperty("nadmUrl");
    private String nadmConfig = appProperties.properties.getProperty("nadmConfigURL");


    @Test(description = "Install NirmataPE")
    public void installNirmataPE()  {
        appProperties= new NirmataApplicationProperties();
        String ec2InstanceIP = appProperties.properties.getProperty("ec2InstanceIP");
        System.out.println(ec2InstanceIP);
        JSch jsch=new JSch();
        UserInfo ui=new MyUserInfo();

        try{

            jsch.addIdentity(keyPair);
            String host="ubuntu@"+ec2InstanceIP;

            String user=host.substring(0, host.indexOf('@'));
            host=host.substring(host.indexOf('@')+1);

            Session session=jsch.getSession(user, host, 22);

            session.setUserInfo(ui);
            session.connect();
            System.out.println("Connected");
            System.out.println("IP address: " +ec2InstanceIP );

            Channel channel=session.openChannel("shell");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            channel.connect();
            String script ="sudo swapoff -a\n" +
                    "sudo apt update -y && sudo apt install -y docker.io\n" +"";
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(script+"\n");
            channelExec.connect();
            InputStream in = channelExec.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }






    public static class MyUserInfo implements UserInfo{
        public String getPassword(){ return null; }
        public boolean promptYesNo(String str){
            return true;
        }

        String passphrase;

        public String getPassphrase(){ return passphrase; }
        public boolean promptPassphrase(String message){
            return true;
        }
        public boolean promptPassword(String message){ return true; }
        public void showMessage(String message){ }

    }

}
