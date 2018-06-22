package com.prestashop.tests;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PositiveScenarios {
	WebDriver driver;
	Faker faker;
	Random random;
	String email;
	String firstName;
	String lastName;
	String password;
	Select day;
	Select month;
	Select years;
	Select state;

	@BeforeMethod
	public void setUp() {
		faker = new Faker();
		random = new Random();
		email = faker.internet().emailAddress();
		firstName = faker.name().firstName();
		lastName = faker.name().lastName();
		password = faker.internet().password(5, 6);
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterMethod
	public void teardown() {
		driver.close();
	}

	// test scenario: login test
	@Test
	public void loginTest() throws InterruptedException {
		driver.get("http://automationpractice.com");
		driver.findElement(By.className("login")).click();
		driver.findElement(By.id("email_create")).sendKeys(email);
		driver.findElement(By.id("SubmitCreate")).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.id("id_gender" + (random.nextInt(2) + 1))).click();
		driver.findElement(By.id("customer_firstname")).sendKeys(firstName);
		driver.findElement(By.id("customer_lastname")).sendKeys(lastName);
		driver.findElement(By.id("passwd")).sendKeys(password);
		
		Thread.sleep(2000);
		
		day = new Select(driver.findElement(By.id("days")));
		day.selectByIndex((random.nextInt(27) + 1));
		month = new Select(driver.findElement(By.id("months")));
		month.selectByIndex((random.nextInt(12) + 1));
		years = new Select(driver.findElement(By.id("years")));
		years.selectByIndex((random.nextInt(116) + 2));

		Thread.sleep(2000);

		driver.findElement(By.id("uniform-newsletter")).click();
		driver.findElement(By.id("optin")).click();
		driver.findElement(By.id("company")).sendKeys(faker.company().name());
		driver.findElement(By.id("address1")).sendKeys(faker.address().streetAddress());
		driver.findElement(By.id("address2")).sendKeys(faker.address().secondaryAddress());
		driver.findElement(By.id("city")).sendKeys(faker.address().city());

		Thread.sleep(2000);

		state = new Select(driver.findElement(By.id("id_state")));
		state.selectByIndex((random.nextInt(50) + 1));

		Thread.sleep(2000);

		driver.findElement(By.id("postcode")).sendKeys(faker.address().zipCode().substring(0, 5));
		driver.findElement(By.id("other")).sendKeys("This was hard!");
		driver.findElement(By.id("phone")).sendKeys(faker.phoneNumber().cellPhone());
		driver.findElement(By.id("phone_mobile")).sendKeys(faker.phoneNumber().cellPhone());
		driver.findElement(By.id("alias")).clear();
		driver.findElement(By.id("alias")).sendKeys(faker.internet().emailAddress());
		driver.findElement(By.id("submitAccount")).click();
		driver.findElement(By.partialLinkText("Sign out")).click();
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("passwd")).sendKeys(password);
		driver.findElement(By.id("SubmitLogin")).click();

		Thread.sleep(2000);

		Assert.assertEquals(driver.findElement(By.cssSelector("a[title='View my customer account']>span")).getText(),
				firstName + " " + lastName);

	}
}
