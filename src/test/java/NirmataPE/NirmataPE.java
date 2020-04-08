package NirmataPE;

import com.jcraft.jsch.*;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.testng.annotations.Test;
import utils.NirmataApplicationProperties;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class NirmataPE  {

    protected NirmataApplicationProperties appProperties = new NirmataApplicationProperties();
    private String keyPair = System.getProperty("user.dir")+"/resources/data/nirmata-west-1-062014.pem";
    private String instanceIP = appProperties.properties.getProperty("instanceIP");
    private String nadmVersion = appProperties.properties.getProperty("nadmVersion");
    private String nadmConfig = appProperties.properties.getProperty("nadmConfigURL");
    private PropertiesConfiguration config;


    @Test(description = "Install NirmataPE")
    public void installNirmataPE()  {

        try{
            JSch jsch=new JSch();

            jsch.addIdentity(keyPair);
            String host="ubuntu@"+instanceIP;

            String user=host.substring(0, host.indexOf('@'));
            host=host.substring(host.indexOf('@')+1);

            Session session=jsch.getSession(user, host, 22);

            UserInfo ui=new MyUserInfo();
            session.setUserInfo(ui);
            session.connect();
            System.out.println("Connected");
            System.out.println("IP address: " +instanceIP );

            Channel channel=session.openChannel("shell");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);

            channel.connect();


            String script ="sudo swapoff -a\n" +
                    "sudo apt update -y && sudo apt install -y docker.io\n" +
                    "curl -LO https://nadm-builds.s3-us-west-1.amazonaws.com/Go+components/go-nadm-151/"+nadmVersion+".tar.gz" +"\n" +
                    "ls\n" +
                    "tar -xf " + nadmVersion+".tar.gz" + "\n" +
                    "cd " + nadmVersion + "\n" + "pwd\n"+
                    "openssl req -subj '/O=Nirmata/CN=nirmata.local/C=US' -new -newkey rsa:2048 -days 3650 -sha256 -nodes -x509 -keyout server.key -out server.crt\n" +
                    "wget " + nadmConfig + "\n"+
                    "urladdress="+instanceIP +"\n" + "echo $urladdress" + "\n" +
                    "sed \"s/%urladdress/${urladdress}/\" config > nadmconfig\n" +
                    "echo \"y\" | sudo ./nadm install --quiet --nadmconfig config\n" +
                    "mkdir -p $HOME/.kube\n" +
                    "sudo cp -i /opt/nirmata/.nirmata-nadm/.kube/config $HOME/.kube/config\n" +
                    "sudo chown $(id -u):$(id -g) $HOME/.kube/config\n";


            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(script+"\n");
            channelExec.connect();

            InputStream in = channelExec.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            try{
                channel.disconnect();
                session.disconnect();
            }catch(Exception ex){
                ex.printStackTrace();
            }

        }
        catch(Exception e){
            System.out.println(e);
        }

        try {
            config = new PropertiesConfiguration("resources/data/Application.properties");
            config.setProperty("url","https://"+instanceIP+":443");
            config.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        System.out.println("url= "+"https://"+instanceIP+":443");
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
