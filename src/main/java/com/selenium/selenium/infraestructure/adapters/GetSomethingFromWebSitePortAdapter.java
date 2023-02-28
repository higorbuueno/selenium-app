/**
 * 
 */
package com.selenium.selenium.infraestructure.adapters;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.selenium.selenium.domain.collector.GetSomethingFromWebSitePort;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Higor Pereira
 *
 */

@Slf4j
@Component
public class GetSomethingFromWebSitePortAdapter implements GetSomethingFromWebSitePort {

    private static final String URL = "https://higorbuueno.github.io";

    @Override
    public String getSomething() {
	System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe");

	if (System.getProperty("os.name").equalsIgnoreCase("Linux")) {
	    System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver-linux-0-32-0");

	} else {
	    System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver-win64-0-32-0.exe");
	}

	FirefoxProfile firefoxProfile = new FirefoxProfile();
	firefoxProfile.setAcceptUntrustedCertificates(true);

	FirefoxOptions firefoxOptions = new FirefoxOptions();
	firefoxOptions.setProfile(firefoxProfile);
	firefoxOptions.setHeadless(true);

	WebDriver webDriver = new FirefoxDriver(firefoxOptions);

	webDriver.get(URL);
	System.out.println(webDriver.getCurrentUrl());

	WebElement myElement = webDriver.findElement(By.xpath("//*[@id=\"status\"]/a"));
	System.out.println(myElement.getText());
	myElement.click();

	//	Recognizing the new screen
	WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/div[2]/a[1]")));
	System.out.println(webDriver.getCurrentUrl());

	myElement = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/div[2]/a[1]"));
	System.out.println(myElement.getText());
	myElement.click();

	//	Recognizing the new screen
	wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("api-url-exemple")));
	System.out.println(webDriver.getCurrentUrl());

	myElement = webDriver.findElement(By.id("api-url-exemple"));

	// Getting placeholder api example
	String myApiUri = myElement.getAttribute("placeholder");
	System.out.println(myApiUri);

	// Setting api to the same as placeholder
	myElement.sendKeys(myApiUri);

	//	Finding GET button
	myElement = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/div[1]/div[2]/button"));
	myElement.click();

	String myFinalResult = "";
		//	Waiting API call to proceed
		try {
			wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-success")));
			myElement = webDriver.findElement(By.xpath("//*[@id=\"json-pretty\"]/pre"));
			myFinalResult = myElement.getText();
			System.out.println(myElement.getText());
		} catch (Exception e) {
			System.out.println("Falha ao consultar API");
//			throw new Exception("Falha ao consultar API");
		}

//	Returning a inputstream with string value.
	return myFinalResult;
    }


}
