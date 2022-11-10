package myer;

import helper.CommonUtil;
import helper.ExcelDataConfig;
import helper.appconfig;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.util.List;

public class DataFactory {
    static CommonUtil util = new CommonUtil();

    static String proploc = appconfig.proploc;
    static String sheetloc=appconfig.sheetloc;
    //private String propfile = projPath+"/src/main/java/arcbs/DonorToolCalculator/config.properties";


    @DataProvider(name = "myer")
    public static Object[][] myer(){

        // Excel File to use
        String dataFile = sheetloc+"/myer.xlsx";
        ExcelDataConfig config = new ExcelDataConfig(new File(dataFile));

        // Execution scenario parameter in the properties file.
        List<String> list =util.getExecutionScenario(proploc, "datascenario");
        // Sheet in the Excel workbook
        Object[][] data = config.passData("Shop", list);

        config.closeFile();
        return data;
    }
}
