package hfe.testing;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.tomcat.util.scan.StandardJarScanFilter;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import javax.servlet.ServletException;
import java.io.File;

public class EmbeddedTomcatListener implements IInvokedMethodListener {

    public static final String MODULE_PATH = "build/war_exploded/";
    public static final String WEB_INF_PATH = MODULE_PATH + "WEB-INF/";
    public static final String APP_NAME = "hfe";

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        Tomcat tomcat = new Tomcat();
        try {
            StandardContext ctx = (StandardContext)tomcat.addWebapp(APP_NAME, new File(MODULE_PATH).getAbsolutePath());
            StandardJarScanner scanner = (StandardJarScanner)ctx.getJarScanner();
            scanner.setScanClassPath(false);
            StandardJarScanFilter jarScanFilter = (StandardJarScanFilter) ctx.getJarScanner().getJarScanFilter();
            //jarScanFilter.setTldSkip("*");

            File additionWebInfClasses = new File("target/classes");
            WebResourceRoot resources = new StandardRoot(ctx);
            resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
            //ctx.setResources(resources);


            tomcat.start();
        } catch (LifecycleException | ServletException e) {
            System.exit(1);
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

    }
}
