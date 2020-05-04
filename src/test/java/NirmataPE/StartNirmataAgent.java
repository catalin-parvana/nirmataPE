package NirmataPE;

import com.jcraft.jsch.*;
import org.testng.annotations.Test;
import utils.NirmataApplicationProperties;
import utils.NirmataSetup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.testng.Assert.assertTrue;


public class StartNirmataAgent extends NirmataSetup {


    private final String keyPair = System.getProperty("user.dir")+"/resources/data/nirmata-west-1-062014.pem";


    @Test(description = "Start Nirmata Agent")
    public void startNirmataAgent()  {
        String dcInstanceIP = (new NirmataApplicationProperties()).properties.getProperty("dcInstanceIP");
        String ec2InstanceIP = (new NirmataApplicationProperties()).properties.getProperty("ec2InstanceIP");
        String hostAgentKey = (new NirmataApplicationProperties()).properties.getProperty("hostAgentKey");
        System.out.println(" ================ Install Nirmata PE ======================");
        System.out.println("++ Last Update: 3rd May ++" );
        System.out.println("ec2InstanceIP= "+ec2InstanceIP);


        ConsoleOutputCapturer capturer= new ConsoleOutputCapturer();
        try{

            JSch jsch=new JSch();
            UserInfo ui=new MyUserInfo();
            jsch.addIdentity(keyPair);
            String host="ubuntu@"+dcInstanceIP;
            //      String urladdress="urladdress="+ec2InstanceIP;

            String user=host.substring(0, host.indexOf('@'));
            host=host.substring(host.indexOf('@')+1);

            Session session=jsch.getSession(user, host, 22);

            session.setUserInfo(ui);
            System.out.println("isCoonnect? " + session.isConnected());
            session.connect();
            System.out.println("Connected");
            System.out.println("isCoonnect? " + session.isConnected());
            System.out.println("IP address: " +dcInstanceIP );
            System.out.println("hostAgentKey: " +hostAgentKey);
            String installAgentCommand="sudo curl -k -sSL https://" +ec2InstanceIP +"/nirmata-host-agent/setup-nirmata-agent.sh | sudo sh -s -- --cloud other --hostgroup "
                    + hostAgentKey + " --insecure";
            System.out.println("InstallAgentCommand: " +installAgentCommand);

            Channel channel=session.openChannel("shell");

            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);

            System.out.println(dcInstanceIP);
            capturer.start();
            channel.connect();
            System.out.println(dcInstanceIP);

            String script = "sudo swapoff -a\n" +
                    "sudo apt-get update && sudo apt-get install -y docker.io \n" +
                   " sudo ln -sf /run/systemd/resolve/resolv.conf /etc/resolv.conf \n"+
                            installAgentCommand + "\n" +
                    "sudo curl -k -sSL https://" +ec2InstanceIP +"/nirmata-host-agent/setup-nirmata-agent.sh | sudo sh -s -- --cloud other --hostgroup "
                        + hostAgentKey + " --insecure" + "\n"
                    +"history\n";


            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(script);
            channelExec.connect();

            InputStream in = channelExec.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);

            }
            System.out.println("=============== End of Nirmata agent================");
            if (channel != null) {
                System.out.println("isConnected=" + channel.isConnected());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }finally {
            String result;
            result=capturer.stop();
            assertTrue(result.contains("Nirmata agent started successfully"),"Start Nirmata agent failed");
         //   assertTrue(result.contains("Nirmata agent started successfully"),"Start Nirmata agent failed");
            System.out.println("=============== End of Nirmata Agent Test Test ================");
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
