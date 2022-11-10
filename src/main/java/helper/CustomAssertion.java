package helper;

import com.aventstack.extentreports.ExtentTest;

public class CustomAssertion {
    public ExtentTest logger;
    public void setLogger(ExtentTest logger){
        this.logger= logger;
    }

    public boolean cu_assertEquals(String ActValue,String ExpValue){
        if (ActValue.equals(ExpValue)){
            return  true;

        }else {
            return  false;
        }
    }

    public boolean cu_assertPartialCompare(String ActValue,String ExpValue){
        if (ActValue.contains(ExpValue)){
            return  true;

        }else {
            return  false;
        }
    }
}
