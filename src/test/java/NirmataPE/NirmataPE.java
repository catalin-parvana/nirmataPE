package NirmataPE;

import com.jcraft.jsch.*;
import org.testng.annotations.Test;
import utils.NirmataApplicationProperties;
import utils.NirmataSetup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class NirmataPE  extends NirmataSetup {


    private final String keyPair = System.getProperty("user.dir")+"/resources/data/nirmata-west-1-062014.pem";
    private final String nadmVersion = appProperties.properties.getProperty("nadmVersion");
    private final String nadmURL = appProperties.properties.getProperty("nadmURL");

    //private PropertiesConfiguration config;



    @Test(description = "Install NirmataPE")
    public void installNirmataPE()  {
        String ec2InstanceIP = (new NirmataApplicationProperties()).properties.getProperty("ec2InstanceIP");
        System.out.println(" ================ Install Nirmata PE ======================");
        System.out.println("ec2InstanceIP= "+ec2InstanceIP);
        System.out.println("nadmVersion: " + "tar -xf " + nadmVersion +".tar.gz" );
        System.out.println("nadmURL= "+nadmURL);
        System.out.println("nadmVersion= "+nadmVersion);
        try{

            JSch jsch=new JSch();
            UserInfo ui=new MyUserInfo();
            jsch.addIdentity(keyPair);
            String host="ubuntu@"+ec2InstanceIP;
      //      String urladdress="urladdress="+ec2InstanceIP;

            String user=host.substring(0, host.indexOf('@'));
            host=host.substring(host.indexOf('@')+1);

            Session session=jsch.getSession(user, host, 22);

            session.setUserInfo(ui);
            System.out.println("isCoonnect? " + session.isConnected());
            session.connect();
            System.out.println("Connected");
            System.out.println("isCoonnect? " + session.isConnected());
            System.out.println("IP address: " +ec2InstanceIP );

            Channel channel=session.openChannel("shell");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);

            System.out.println(ec2InstanceIP);
            channel.connect();
            System.out.println(ec2InstanceIP);

            String script ="sudo swapoff -a\n" +
                    "sudo apt update -y && sudo apt install -y docker.io\n" +
                    "curl -LO " + nadmURL +"\n" +
                    "ls\n" +
                    "tar -xf " + nadmVersion +".tar.gz" + "\n" +
                    "cd " + nadmVersion + "\n" + "pwd\n"+
                    "varconfig={\\\"ClusterInstall\\\":true,\\\"ImageRepository\\\":\\\"index.docker.io\\\",\\\"Username\\\":\\\"nirmatainstaller\\\",\\\"Password\\\":\\\"xeNcMck0vbLvC5KLTmy-7AsEYBztkfN0zw==\\\",\\\"URL\\\":\\\"https://%urladdress:443\\\",\\\"Replicas\\\":\\\"1\\\",\\\"Namespace\\\":\\\"nirmatape\\\",\\\"Kubeconfig\\\":\\\"/opt/nirmata/.nirmata-nadm/.kube/config\\\",\\\"StorageClass\\\":\\\"nirmata-hostpath\\\",\\\"Certificate\\\":\\\"server.crt\\\",\\\"Key\\\":\\\"server.key\\\",\\\"MountPath\\\":{\\\"zk\\\":\\\"/mnt/nirmata/zk\\\",\\\"mongodb\\\":\\\"/mnt/nirmata/mongodb\\\",\\\"kafka\\\":\\\"/mnt/nirmata/kafka\\\"}}\n" +
                    "echo \"$varconfig\" >> config \n" +
                    "urladdress="+ec2InstanceIP +"\n" + "echo $urladdress" + "\n" +
                    "sed \"s/%urladdress/${urladdress}/\" config > nadmconfig\n" +
                    "openssl req -subj '/O=Nirmata/CN=nirmata.local/C=US' -new -newkey rsa:2048 -days 3650 -sha256 -nodes -x509 -keyout server.key -out server.crt\n" +
                    "echo \"y\" | sudo ./nadm install --quiet --nadmconfig nadmconfig\n" +
                    "mkdir -p $HOME/.kube\n" +
                    "sudo cp -i /opt/nirmata/.nirmata-nadm/.kube/config $HOME/.kube/config\n" +
                    "sudo chown $(id -u):$(id -g) $HOME/.kube/config\n"+
                    "sudo kubectl get all -A\n";


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
        catch(Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("=============== End of test ================");
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
