package cucumberoption;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features ="src/test/java/Features", glue = {"Stepdefination","mainclass"}, tags = "@Reg",
plugin = "json:target/jsonReports/report.json")
public class TestRunner extends AbstractTestNGCucumberTests {

	
}