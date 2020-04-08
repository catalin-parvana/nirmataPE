package NirmataPE;

import com.jcraft.jsch.*;
import org.testng.annotations.Test;
import utils.NirmataSetup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class NirmataPE extends NirmataSetup {
    private String key = System.getProperty("user.dir")+"/resources/data/nirmata-west-1-062014.pem";
    private String instanceIP = appProperties.properties.getProperty("instanceIP");
    private String nadmVersion = appProperties.properties.getProperty("nadmVersion");
    private String seturladdress = "urladdress="+instanceIP;
    private String echourl = "echo \"$urladdress\"";
    private String nadmfile=nadmVersion+".tar.gz";
    private String changedir= "cd " + nadmVersion;
    private String downloadNadm = "curl -LO " + appProperties.properties.getProperty("nadmURL");
    private String getConfig= "wget " + appProperties.properties.getProperty("nadmconfigURL");


    @Test(description = "Install NirmataPE")
    public void installNirmataPE()  {

        try{
            JSch jsch=new JSch();

            jsch.addIdentity(key);
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
                    downloadNadm +"\n" +
                    "ls\n" +
                    "tar -xf " + nadmfile + "\n" +
                    changedir + "\n" + "pwd\n"+
                   "openssl req -subj '/O=Nirmata/CN=nirmata.local/C=US' -new -newkey rsa:2048 -days 3650 -sha256 -nodes -x509 -keyout server.key -out server.crt\n" +
                    getConfig + "\n"+
                    seturladdress +"\n" + echourl + "\n" +
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
