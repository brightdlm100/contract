package com.dudu.auto.webmagic;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	System.setProperty("webdriver.chrome.driver","D:\\工作文件夹\\dlm\\dlm\\java\\webdriver\\chromedriver_win32\\chromedriver.exe");
    	System.setProperty("webdriver.ie.driver","D:\\工作文件夹\\dlm\\dlm\\java\\webdriver\\IEDriverServer_Win32_3.13.0\\IEDriverServer.exe");
       //WebDriver driver = new ChromeDriver();
       // driver.get("http://www.meipai.com/");
    	
    	WebDriver driver=new InternetExplorerDriver();
    	 try {
             Thread.sleep(2000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
    	// driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    	driver.get("http://www.meipai.com/");
    	//防止页面未能及时加载出来而设置一段时间延迟
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //找到登录按钮，点击
        driver.findElement(By.id("headerLogin")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.id("dialogLWrap")).findElement(By.id("dialogLPlatform")).findElement(By.tagName("a")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.id("userId")).sendKeys("happydyh100@sina.com");
        driver.findElement(By.id("passwd")).sendKeys("brightdlm100");
        driver.findElement(By.xpath("//p[@class='oauth_formbtn']/a[@node-type='submit']")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//p[@class='oauth_formbtn']/a[@node-type='submit']")).click();
    }
}
