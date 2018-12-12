package testcaseDefinationLayer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class AutomatingBarcode {

	public static WebDriver driverthread;
	
	public static void main(String[] args) throws IOException, NotFoundException {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\girish\\Desktop\\Resources\\chromedriver.exe");
		driverthread =new ChromeDriver();
		driverthread.get("https://barcode.tec-it.com//en//Code128?data=murali");
		
		String barcodeURL=driverthread.findElement(By.tagName("img")).getAttribute("src");
		System.out.println(barcodeURL);
		
		URL url=new URL(barcodeURL);
		BufferedImage bufferedimage=ImageIO.read(url);
		
		LuminanceSource luminanceSource=new BufferedImageLuminanceSource(bufferedimage);
		
		BinaryBitmap binarybitmap=new BinaryBitmap(new HybridBinarizer(luminanceSource));
		
		Result result=new MultiFormatReader().decode(binarybitmap);
		
		System.out.println(result.getText());
		
		driverthread.close();
		
		
	}

}
