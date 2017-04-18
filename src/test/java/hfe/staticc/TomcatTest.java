package hfe.staticc;

import hfe.testing.EmbeddedTomcatListener;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.testng.Assert.assertTrue;

@Listeners(EmbeddedTomcatListener.class)
public class TomcatTest {

    @Test
    public void startTomcat() throws IOException {
        File callmeHtml = new File(EmbeddedTomcatListener.MODULE_PATH + "/callme.html");
        callmeHtml.deleteOnExit();
        FileUtils.write(callmeHtml, "pippa", Charset.defaultCharset());
        WebDriver driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080/" + EmbeddedTomcatListener.APP_NAME + "/callme.html");
        assertTrue(driver.getPageSource().contains("pippa"));
        driver.close();
        driver.quit();
    }
}
