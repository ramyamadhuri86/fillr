package helper;

//import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
//import java.io.OutputStream;
import java.util.Properties;

public class PropertyHub {
	private static Properties prop = new Properties();
	private String filepath;
	
	public PropertyHub(String fileloc) {
		this.filepath = fileloc;
	}

	public String getProperty( String property  ) {
		String value = null;
		try {
			//Properties prop = new Properties();

			InputStream input = new FileInputStream(filepath);
			prop.load(input);
			value= prop.getProperty(property);
            input.close();
			
			//return prop.getProperty("browser");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
		}	

		return value;
	}


	public  void setProperty(String property ,String  propertyvalue) { try { //
		//Properties prop = new	  Properties();

		OutputStream output = new FileOutputStream(filepath);

		prop.setProperty(property,propertyvalue); 
		prop.store(output, null);
		output.close();
		

	
		

	} catch (Exception e) { System.out.println(e.getMessage());
	System.out.println(e.getCause()); e.printStackTrace(); }


	}

}