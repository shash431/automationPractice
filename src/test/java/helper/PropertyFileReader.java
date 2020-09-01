package helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Class will read and load config.properties/ command line arguments into a Map
 * 
 * @author sheetalsingh
 *
 */
public class PropertyFileReader {

	Properties properties = new Properties();
	Map<String, String> propMap = new HashMap<String, String>();

	/**
	 * Constructor will load Map
	 */
	public PropertyFileReader() {
		loadHashMapWithPropertyValues();
	}

	/**
	 * Read config.properties file and load Map
	 */
	public void loadHashMapWithPropertyValues() {
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream("config.properties");
			properties.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Java - 8
		// propMap.putAll(properties.entrySet().stream()
		// .collect(Collectors.toMap(e -> e.getKey().toString(), e ->
		// e.getValue().toString())));
		// Java - 7
		for (final Entry<Object, Object> entry : properties.entrySet()) {
			propMap.put((String) entry.getKey(), (String) entry.getValue());
		}

		reloadMapWithCommandLineArguments(propMap);
	}

	/**
	 * This method will read properties from command line and replace Map
	 * already existing entries Command line argument will be given preference
	 */
	public void reloadMapWithCommandLineArguments(Map<String, String> propMap) {

		String driverFromCommandLine = System.getProperty("driver");
		String serverFromCommandLine = System.getProperty("server");

		if (driverFromCommandLine != null && !driverFromCommandLine.trim().isEmpty()) {
			propMap.put("driver", System.getProperty("driver"));
		}

		if (serverFromCommandLine != null && !serverFromCommandLine.trim().isEmpty()) {
			propMap.put("server", System.getProperty("server"));
		}

	}
	
	

	/**
	 * return Map with updated key-value pair 
	 * command line arg will override config.properties arg
	 */
	public Map<String, String> getPropertyMap() {
		return propMap;
	}
	
	
	

}
