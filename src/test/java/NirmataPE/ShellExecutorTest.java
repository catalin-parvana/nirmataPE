package NirmataPE;


import org.testng.annotations.Test;
import utils.ShellExecutor;

public class ShellExecutorTest {
    private static String userDir = System.getProperty("user.dir");

    @Test
    public void testSimple() throws ShellExecutor.CommandTimeoutException {
        System.out.println(
                ShellExecutor.execute(
                        "pwd",
                        userDir + "/resources/scripts",
                        null,
                        ((message, process) -> System.out.println(message))
                )
        );
    }

    @Test
    public void test() throws ShellExecutor.CommandTimeoutException {
        int result = ShellExecutor.execute(
                userDir + "/resources/scripts/test.sh",
                null,
                null,
                (message, process) -> System.out.println(String.format("Communication[1]: %s", message)),
                (message, process) -> System.out.println(String.format("Communication[2]: %s", message)),
                (message, process) -> System.out.println(String.format("Communication[3]: %s", message))
        );
        System.out.println(result);
    }

    @Test
    public void testMavenBuild() throws ShellExecutor.CommandTimeoutException {
        System.out.println(ShellExecutor.execute(
                userDir + "/resources/scripts/testMvnInstall.sh",
                null,
                null,
                (message, process) -> System.out.println(message))
        );
    }

    @Test
    public void simpleTest() {
        try {
            int exitValue = ShellExecutor.execute(
                    "./test.sh",
                    System.getProperty("user.dir") + "/resources/scripts",
                    null,
                    (message, process) -> System.out.println(message)
            );
            System.out.println("exitValue: " + exitValue);
        } catch (ShellExecutor.CommandTimeoutException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void complexCommandTest() {
        try {
            int exitValue = ShellExecutor.execute(
                    "ps -ef | grep java | grep -v grep",
                    System.getProperty("user.dir"),
                    null,
                    (message, process) -> System.out.println(message)
            );
            System.out.println("exitValue: " + exitValue);
        } catch (ShellExecutor.CommandTimeoutException e) {
            System.out.println(e.getMessage());
        }
    }
}
