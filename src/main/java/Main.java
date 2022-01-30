import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Main class for Currency Calculator.
 * <p>Starts program after obtaining configuration data.</p>
 * <p>If app exit with code 1 there was a problem with config.properties file.</p>
 *
 * @author Mateusz Prill
 * @version 1.0
 */
public class Main {
    //Configuration file is needed to run application
    private static final String FILEPATH = "src/main/resources/config.properties";

    /**
     * Main function that starts app running.
     * <p>It also gets properties from config.properties file and set them.</p>
     *
     * @param args Optional arguments, not used by app
     */
    public static void main(String[] args) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(FILEPATH)) {
            properties.load(fis);

            //Trying to find needed property, if not, exit app with code 1
            if(!properties.containsKey("app.xml.path")){
                System.out.println("Configuration doesn't contain XML file path. Add it and try again.");
                System.exit(1);
            }
            String xmlFilePath = properties.getProperty("app.xml.path");

            //Property xmlFilePath is used by Calculator object to get (currencies,rates) map from xml file
            Menu menu = new Menu(new Calculator(xmlFilePath));
            menu.showMenu();
        } catch (IOException e) {
            System.out.println("No configuration file detected.");
            System.out.println("App will close.");
        }
    }

}
