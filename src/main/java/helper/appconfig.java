package helper;

public class appconfig {
    static CommonUtil util = new CommonUtil();
    public static 	 String proploc =util.getAbsFileLocation("/config.properties");
    public static String sheetloc=util.getAbsFileLocation("/excel-sheets");
}
