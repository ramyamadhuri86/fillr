package helper;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CommonUtil {

	ExtentTest logger;

	public String imgloc;
	public String zipfile;

	public CommonUtil() {

	}

	public CommonUtil(ExtentTest logger) {
		this.logger = logger;
	}

	public CommonUtil(String imgloc, String zipfile) {

		this.imgloc = imgloc;
		this.zipfile = zipfile;
	}

	public String getAbsFileLocation(String location) {

		return System.getProperty("user.dir") + location;

	}

	public void setLogger(ExtentTest logger) {
		this.logger = logger;

	}

	public void CaptureElementClip_Se(WebDriver driver, WebElement elm, Status status, String stepdetails,
			String imglocation) throws IOException {
		// Shutterbug.shootElement(driver, elm).save(location);
		try {
			String imgName = new SimpleDateFormat("yyyyMMddHHmmss'.png'").format(new Date());
			String file = imglocation + "/" + imgName;
			ImageIO.write(Shutterbug.shootElement(driver, elm).getImage(), "PNG", new File(file));
			String filePath = "./Images" + "/" + imgName;
			if (logger != null) {
				logger.log(status, stepdetails, MediaEntityBuilder.createScreenCaptureFromPath(filePath).build());
			}
			Assert.assertTrue(new File(file).exists());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (logger != null) {
				logger.log(Status.FAIL, e.getStackTrace().toString());
			}
		}
	}

	public void captureScreenClip(WebDriver driver, String location) throws IOException, Exception {

		Shutterbug.shootPage(driver, Capture.FULL_SCROLL).save(location);

		Assert.assertTrue(new File(location).exists());
	}

	public void CaptureScreenClip_Se(WebDriver driver, Status status, String stepdetails, String imglocation)
			throws IOException, Exception {

		// Shutterbug.shootPage(driver, Capture.FULL_SCROLL).save(location);
		try {
			String imgName = new SimpleDateFormat("yyyyMMddHHmmss'.png'").format(new Date());
			String file = imglocation + "/" + imgName;
			ImageIO.write(Shutterbug.shootPage(driver, Capture.FULL_SCROLL).getImage(), "PNG", new File(file));
			String filePath = "./Images" + "/" + imgName;
			if (logger != null) {
				logger.log(status, stepdetails, MediaEntityBuilder.createScreenCaptureFromPath(filePath).build());

			}
			Assert.assertTrue(new File(file).exists());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (logger != null) {
				logger.log(Status.FAIL, e.getStackTrace().toString());
			}
		}

	}

	public void CaptureScreenClipNoScroll_Se(WebDriver driver, Status status, String stepdetails, String imglocation)
			throws IOException, Exception {

		// Shutterbug.shootPage(driver, Capture.FULL_SCROLL).save(location);
		try {
			String imgName = new SimpleDateFormat("yyyyMMddHHmmss'.png'").format(new Date());
			String file = imglocation + "/" + imgName;
			ImageIO.write(Shutterbug.shootPage(driver).getImage(), "PNG", new File(file));
			String filePath = "./Images" + "/" + imgName;
			if (logger != null) {
				logger.log(status, stepdetails, MediaEntityBuilder.createScreenCaptureFromPath(filePath).build());

			}
			Assert.assertTrue(new File(file).exists());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (logger != null) {
				logger.log(Status.FAIL, e.getStackTrace().toString());
			}
		}

	}

	public List<String> getExecutionScenario(String loc, String executionscenario) {
		List<String> items = null;
		PropertyHub propertyhub = new PropertyHub(loc);
		String runtimeScenario = propertyhub.getProperty(executionscenario);
		if (!runtimeScenario.isEmpty()) {
			items = Arrays.asList(runtimeScenario.split(","));

		}

		return items;

	}

	public double roundAvoid(double value, int places) {
		double scale = Math.pow(10, places);
		return Math.round(value * scale) / scale;
	}



	public String getCurrentTimeAsText() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMdHHmmss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getCurrentTimeWithRamdomAlphabet() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMdHHmmss");
		Date date = new Date();
		Random rnd = new Random();
		// char c = (char) ('a' + rnd.nextInt(26));
		char c = (char) ('a' + ThreadLocalRandom.current().nextInt(26));
		return dateFormat.format(date) + c;
	}

	public String resolveUniqueID(String dataval) {
		Faker faker = new Faker();
		if (dataval.indexOf("UNIQUEID>") > 0) {
			String id = getCurrentTimeAsText();
			Integer l1 = dataval.lastIndexOf(">");
			String[] arry = dataval.split("&");
			if (arry.length == 2) {
				if (dataval.length() > l1 + 1 && isNumeric(dataval.substring(l1 + 1, l1 + 2))) {
					Integer l2 = Integer.parseInt(dataval.substring(l1 + 1, l1 + 2));
					Integer l3 = id.length() - l2;
					// if(id.substring(l3))
					id = id.substring(l3) + arry[1];
				} else {
					id = id + arry[1];
				}
			} else {
				if (dataval.length() > l1 + 1 && isNumeric(dataval.substring(l1 + 1))) {

					Integer l2 = Integer.parseInt(dataval.substring(l1 + 1));
					Integer l3 = id.length() - l2;
					// if(id.substring(l3))
					id = id.substring(l3);
				}

			}
			return id;
		}

		if (dataval.indexOf("SMTTODAY>") > 0) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			return dateFormat.format(new Date());
		}
		if (dataval.indexOf("FIRSTNAME>") > 0) {

			return faker.name().firstName();
		}

		if (dataval.indexOf("LASTNAME>") > 0) {

			return faker.name().lastName().toUpperCase();
		}

		if (dataval.indexOf("PPDPATIENTID>") > 0) {
			DateFormat dateFormat = new SimpleDateFormat("yyMdHss");
			Date date = new Date();
			String t;
			// System.out.println(dateFormat.format(date));
//			if (dateFormat.format(date).length() > 7) {
//				t = dateFormat.format(date).substring(0, 7);
//			} else {
//				t = dateFormat.format(date);
//			}
			t = Integer.toString(generateRandomNo(7));
			Random r = new Random();
			char c = (char) (r.nextInt(26) + 'A');
			return "P0T" + t + c;
		}

		if (dataval.indexOf("BSAID>") > 0) {
			DateFormat dateFormat = new SimpleDateFormat("yyMdHss");
			Date date = new Date();

			String id = dateFormat.format(date);
			Integer l1 = dataval.lastIndexOf(">");
			String[] arry = dataval.split("&");
			if (arry.length == 2) {
				if (dataval.length() > l1 + 1 && isNumeric(dataval.substring(l1 + 1, l1 + 2))) {
					Integer l2 = Integer.parseInt(dataval.substring(l1 + 1, l1 + 2));
					Integer l3 = id.length() - l2;
					// if(id.substring(l3))
					id = id.substring(l3) + arry[1];
				} else {
					id = id + arry[1];
				}
			} else {
				if (dataval.length() > l1 + 1 && isNumeric(dataval.substring(l1 + 1))) {

					Integer l2 = Integer.parseInt(dataval.substring(l1 + 1));
					Integer l3 = id.length() - l2;
					// if(id.substring(l3))
					id = id.substring(l3);
				}

			}
			return id;
		}

		if (dataval.indexOf("SMTTODAY2>") > 0) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			return dateFormat.format(new Date()) + " 12:00 am +1100";
		}
		if (dataval.indexOf("LOOKBACKTODAY>") > 0 || dataval.indexOf("PPDTODAY>") > 0
				|| dataval.indexOf("BSADTODAY>") > 0 || dataval.indexOf("MILKBANKTODAY>") > 0) {
			// <LOOKBACKTODAY> , <LOOKBACKTODAY>+2 ,<LOOKBACKTODAY>-2
			Integer l1 = dataval.lastIndexOf(">");
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String dt = dateFormat.format(new Date());
			Calendar c = Calendar.getInstance();

			try {
				c.setTime(dateFormat.parse(dt));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (dataval.length() > l1 + 1) {
				String op = dataval.substring(l1 + 1, l1 + 2);
				Integer l2 = Integer.parseInt(dataval.substring(l1 + 2));
				if (op.equals("+")) {
					c.add(Calendar.DAY_OF_MONTH, l2);
					dataval = dateFormat.format(c.getTime());

				} else if (op.equals("-")) {
					// dt =new Date(currdt.getTime() -l2).toString();
					c.add(Calendar.DAY_OF_MONTH, -l2);
					dataval = dateFormat.format(c.getTime());
				}
			} else {
				dataval = dt;
			}

			return dataval;
		}
		if (dataval.indexOf("PPDTODAYTIME>") > 0) {
			// <LOOKBACKTODAY> , <LOOKBACKTODAY>+2 ,<LOOKBACKTODAY>-2
			Integer l1 = dataval.lastIndexOf(">");
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			String dt = dateFormat.format(new Date());

			Calendar c = Calendar.getInstance();

			try {
				c.setTime(dateFormat.parse(dt));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (dataval.length() > l1 + 1) {
				String op = dataval.substring(l1 + 1, l1 + 2);
				Integer l2 = Integer.parseInt(dataval.substring(l1 + 2));
				if (op.equals("+")) {
					c.add(Calendar.DAY_OF_MONTH, l2);
					dataval = dateFormat.format(c.getTime());

				} else if (op.equals("-")) {
					// dt =new Date(currdt.getTime() -l2).toString();
					c.add(Calendar.DAY_OF_MONTH, -l2);
					dataval = dateFormat.format(c.getTime());
				}
			} else {
				return dt;
			}

			return dataval;
		}

		return dataval;

	}

	public int generateRandomNo(int digits) {
		int max = (int) Math.pow(10, (digits)) - 1; // for digits =7, max will be 9999999
		int min = (int) Math.pow(10, digits - 1); // for digits = 7, min will be 1000000
		int range = max - min; // This is 8999999
		Random r = new Random();
		int x = r.nextInt(range);// This will generate random integers in range 0 - 8999999
		int nDigitRandomNo = x + min; // Our random rumber will be any random number x + min
		return nDigitRandomNo;
	}



	public String getCurrentTimeAsTextddmmyyyy() {
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void createDirectory(String FilePath) {
		boolean success = (new File(FilePath)).mkdirs();
		if (!success) {
			System.out.println("Unable to create required folders.");
		}
	}



	public int getRandomInteger(int minimum, int maximum) {
		return ((int) (Math.random() * (maximum - minimum))) + minimum;
	}

	public String[] streamIntegerRange(int min, int max) {

		int count = max - min + 1; // we're including upper bound
		String[] anArray = new String[count];
		for (int i = 0; i < count; i++, min++) {
			anArray[i] = String.valueOf(min); // reused and incremented min
		}
		return anArray;

	}

	public boolean isNumeric(String s) {
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");
	}


	public void deleteDir(File file) {
		File[] contents = file.listFiles();
		if (contents != null) {
			for (File f : contents) {
				if (!Files.isSymbolicLink(f.toPath())) {
					deleteDir(f);
				}
			}
		}
		file.delete();
	}



}
