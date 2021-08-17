package hybridDemos;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;

import java.net.MalformedURLException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class Demos extends Capability{

	AndroidDriver<AndroidElement> driver;
	
	@BeforeTest
	public void setup() throws MalformedURLException {
		driver = capabilities();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
//	Positive scenario
	@Test(enabled = false)
	public void testCase01() {
		driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Pratyashee");
		driver.findElementById("com.androidsample.generalstore:id/radioFemale").click();
		
//		DROP DOWN - clicking on the country
		driver.findElement(By.id("android:id/text1")).click();
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Australia\"))").click();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
	}
	
//	Negative scenario
	@Test(enabled = false)
	public void testCase02() {
		driver.findElementById("com.androidsample.generalstore:id/radioFemale").click();
		driver.findElement(By.id("android:id/text1")).click();
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Australia\"))").click();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		
//		POP UP MSG - checking the disappearing error msg - to read the pop up msg is any pop up comes
		String msg = driver.findElement(By.xpath("//android.widget.Toast[1]")).getAttribute("name");
		System.out.println("Error: " + msg);
		Assert.assertEquals(msg, "Please enter your name");
	}
	
	@Test(enabled = false)
	public void testCase03() throws InterruptedException {
		driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Pratyashee");
		driver.findElementById("com.androidsample.generalstore:id/radioFemale").click();
		driver.findElement(By.id("android:id/text1")).click();
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Australia\"))").click();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		
//		driver.findElementsById("com.androidsample.generalstore:id/productAddCart").get(0).click();
//		driver.findElementsById("com.androidsample.generalstore:id/productAddCart").get(1).click();
		
//		After adding to cart, text becomes added to cart. Hence index is 0 only
		driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
		driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
		driver.findElementById("com.androidsample.generalstore:id/appbar_btn_cart").click();
		Thread.sleep(3000);
		
		String amt1 = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(0).getText();
		amt1 = amt1.substring(1);
		Double amtValue1 = Double.parseDouble(amt1);
		System.out.println(amtValue1);
		
		String amt2 = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(1).getText();
		amt2 = amt2.substring(1);
		Double amtValue2 = Double.parseDouble(amt2);
		System.out.println(amtValue2);
		
		String totalAmt = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
		totalAmt = totalAmt.substring(1);
		Double totalValue = Double.parseDouble(totalAmt);
		System.out.println(totalValue);
		
		//i have to sum it and check if it is equal
        Double TotalsumofValue = amtValue1+amtValue2;
        System.out.println(TotalsumofValue);
        
        Assert.assertEquals(totalValue, TotalsumofValue);
	}
	
//	getting product by name
	@Test
	public void testCase04() throws InterruptedException {
		driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Pratyashee");
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(By.id("android:id/text1")).click();
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Australia\"))").click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        Thread.sleep(8000);
        
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"com.androidsample.generalstore:id/rvProductList\")).scrollIntoView(new UiSelector().textMatches(\"Converse All Star\"))");
        
        int count = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size();
        
        for(int i=0;i<count;i++)
        {
            String name = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i).getText();
            if(name.equals("Converse All Star"))
            {
                driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
                break;
            }
        }
        
        driver.findElementById("com.androidsample.generalstore:id/appbar_btn_cart").click();
		Thread.sleep(3000);
		
//		Tap on the checkbox
		WebElement cbox = driver.findElement(By.className("android.widget.CheckBox"));
		TouchAction t = new TouchAction(driver);
		t.tap(tapOptions().withElement(element(cbox))).perform();
		
//		Long press on terms
		WebElement terms = driver.findElementById("com.androidsample.generalstore:id/termsButton");
		t.longPress(longPressOptions().withElement(element(terms)).withDuration(ofSeconds(3))).release().perform();
		
//		Close the terms pop up
		driver.findElement(By.id("android:id/button1")).click();
		
//		Click on visit website
		driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
		Thread.sleep(9000);
		
//		Navigate to the browser and work on it
//		Check if its a native or web
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
		    System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
		}
		
//		Switch to browser
		driver.context("WEBVIEW_com.androidsample.generalstore");

		driver.findElement(By.xpath("//*[@name='q']")).sendKeys("IBM");
		driver.findElement(By.xpath("//*[@name='q']")).sendKeys(Keys.ENTER);
		
//		going back to the app - the back btn depends on the app
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		driver.context("NATIVE");
    }
}
