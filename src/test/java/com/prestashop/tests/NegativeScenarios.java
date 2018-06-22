package com.prestashop.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NegativeScenarios {
	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://automationpractice.com");
		driver.findElement(By.cssSelector("a[href='http://automationpractice.com/index.php?controller=my-account']"))
				.click();
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}

	// test scenario: wrong credentials test
	@Test
	public void wrongCredentialsTest() throws InterruptedException {

		driver.findElement(By.id("email")).sendKeys("123456@gmail.com");
		driver.findElement(By.id("passwd")).sendKeys("123456" + Keys.ENTER);
		String actual = driver.findElement(By.xpath("(//li)[15]")).getText();
		String expected = "Authentication failed.";
		Assert.assertEquals(actual, expected);

		Thread.sleep(2000);
	}

	// test scenario: invalid email test
	@Test
	public void invalidEmailTest() throws InterruptedException {

		driver.findElement(By.id("email")).sendKeys("123456-gmail.com");
		driver.findElement(By.id("passwd")).sendKeys("123456" + Keys.ENTER);

		Thread.sleep(2000);

		String actual = driver.findElement(By.xpath("(//li)[15]")).getText();
		String expected = "Invalid email address.";
		Assert.assertEquals(actual, expected);

		Thread.sleep(2000);
	}

	// test scenario: blank email test
	@Test
	public void blankEmailAddress() throws InterruptedException {

		driver.findElement(By.id("email")).clear();
		Thread.sleep(2000);
		driver.findElement(By.id("passwd")).sendKeys("123456" + Keys.ENTER);
		String actual = driver.findElement(By.xpath("(//li)[15]")).getText();
		String expected = "An email address required.";
		Assert.assertEquals(actual, expected);

		Thread.sleep(2000);
	}

	// test scenario: blank password test
	@Test
	public void blankPasswordTest() throws InterruptedException {
		driver.findElement(By.id("email")).sendKeys("123456@gmail.com");
		driver.findElement(By.id("passwd")).clear();
		driver.findElement(By.id("passwd")).sendKeys(Keys.ENTER);
		String actual = driver.findElement(By.xpath("(//li)[15]")).getText();
		String expected = "Password is required.";
		Assert.assertEquals(actual, expected);

		Thread.sleep(2000);
	}
}
