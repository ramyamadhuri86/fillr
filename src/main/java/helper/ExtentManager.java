package helper;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ExtentManager {
	static ExtentReports report;

	public static String imgloc;
	public static String zipfile;


	static CommonUtil util = new CommonUtil();
	static String reportigParentFolder = util.getAbsFileLocation("/test-output/result");
	public static String reportFolder = null;
	public static String downloadloc = null;
	//public static String globalpropfileloc = util.getAbsFileLocation("/src/main/java/Lib/global.properties");
//	public static PropertyHub globalhub = new PropertyHub(globalpropfileloc);

	public static ExtentReports extentReportGenerator(String ApplicationName, String ReportParentDirectory) {
		if (ReportParentDirectory.isEmpty()) {
			ReportParentDirectory = reportigParentFolder;
		}
		reportFolder = setReportFolder(ApplicationName, ReportParentDirectory);
		imgloc = reportFolder + "/Images";

		util.deleteDir(new File(reportFolder));
		util.createDirectory(reportFolder);
		util.createDirectory(imgloc);

		zipfile = setZipFile(reportFolder, ReportParentDirectory);

		//globalhub.setProperty("ZipFile", zipfile);
		String reportfile = reportFolder + "/report.html";
		System.out.println("HTML Report - " + reportfile);
		ExtentSparkReporter extent = new ExtentSparkReporter(new File(reportfile));
		extent.config().setDocumentTitle("Automation Reports");
		extent.config().setReportName(ApplicationName + " - Test Execution Result");
		report = new ExtentReports();
		report.setSystemInfo("User", System.getProperty("user.name"));
		downloadloc = setDownloadFolder(System.getProperty("user.name"));
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			report.setSystemInfo("Machine", inetAddress.getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		report.attachReporter(extent);

		return report;

	}

	private static String setReportFolder(String ApplicationName, String ReportParentDirectory) {
		String timestamp = util.getCurrentTimeWithRamdomAlphabet();

		//String reportFolder = ReportParentDirectory + "/" + ApplicationName + "/" + ApplicationName + "_" + timestamp;
		String reportFolder = ReportParentDirectory + "/" + ApplicationName ;
		return reportFolder;
	}
	
	private static String setDownloadFolder(String CurrentUser) {
		String downloadFolder = "C:/Users/" + CurrentUser + "//Downloads";
	//	System.out.println("downloadFolder EXM:" + downloadFolder);
		return downloadFolder;
	}

	private static String setZipFile(String reportFolder, String ReportParentDirectory) {
		String timestamp = util.getCurrentTimeWithRamdomAlphabet();
		String zipfile = ReportParentDirectory + "/report_" + timestamp + ".zip";
		return zipfile;
	}

	public static void deleteReportParentFolder(String ReportParentDirectory) {
		if (ReportParentDirectory.isEmpty()) {
			ReportParentDirectory = reportigParentFolder;
		}
		util.deleteDir(new File(ReportParentDirectory));
	}

}
