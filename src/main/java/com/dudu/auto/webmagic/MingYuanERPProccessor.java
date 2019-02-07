package com.dudu.auto.webmagic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.processor.PageProcessor;

public class MingYuanERPProccessor implements PageProcessor {
  /*  private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(3000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");*/
    private Site site=Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(3000)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; LCTE; rv:11.0) like Gecko");
    @Override
    public void process(Page page) {
    	System.setProperty("webdriver.chrome.driver","D:\\工作文件夹\\dlm\\dlm\\java\\webdriver\\chromedriver_win32\\chromedriver.exe");
    	System.setProperty("webdriver.ie.driver","D:\\工作文件夹\\dlm\\dlm\\java\\webdriver\\IEDriverServer_Win32_3.13.0\\IEDriverServer.exe");
       //WebDriver driver = new ChromeDriver();
        //driver.get("http://www.meipai.com/");
    	
    	WebDriver driver=new InternetExplorerDriver();
    	 try {
             Thread.sleep(2000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
    	 //driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    	 
    	driver.get("http://crm.yango.com.cn:8060/Default2.aspx");
    	//防止页面未能及时加载出来而设置一段时间延迟
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //driver.findElement(By.id("dialogLWrap")).findElement(By.id("dialogLPlatform")).findElement(By.tagName("a")).click();
        driver.findElement(By.id("txtUserCode")).sendKeys("hupingping");
        driver.findElement(By.id("txtPsw")).sendKeys("qaz123");
        //driver.findElement(By.xpath("//p[@class='oauth_formbtn']/a[@node-type='submit']")).click();
        driver.findElement(By.id("btnLogin")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // driver.findElement(By.xpath("//p[@class='oauth_formbtn']/a[@node-type='submit']")).click();
 
    }
 
    @Override
    public Site getSite() {
        return site;
    }
 
    public static void main(String[] args) {
        List<SpiderListener> spiderListeners = new ArrayList<>();
        SpiderListener spiderListener = new SpiderListener() {
            @Override
            public void onSuccess(Request request) {
                System.out.println("sucsess");
            }
 
            @Override
            public void onError(Request request) {
 
            }
        };
        spiderListeners.add(spiderListener);
        Spider.create(new MingYuanERPProccessor())
                .setSpiderListeners(spiderListeners)
                .addUrl("http://crm.yango.com.cn:8060/Default2.aspx")
                .thread(5)
                .start();
    }
    
}
