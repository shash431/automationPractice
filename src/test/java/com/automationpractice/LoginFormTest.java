package com.automationpractice;

import com.listeners.TestListener;
import io.github.bonigarcia.wdm.WebDriverManager;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import io.qameta.allure.Attachment;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.pageObject.Account;
import com.pageObject.CreateAccountForm;
import com.pageObject.SignInForm;
import utils.EmailsGenerator;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.net.MalformedURLException;

@Story("Verify Login form Tests")
@Listeners(TestListener.class)
public class LoginFormTest {

	private WebDriver driver;
	private CreateAccountForm createAccountForm;
	private SignInForm signin;
	private Account account;
	private BasePageClass basePage;

	@BeforeClass
	public void setup() throws MalformedURLException {
		basePage = BasePageClass.getInstance();
		driver = basePage.getDriver();
		driver.manage().window().maximize();
    createAccountForm = new CreateAccountForm(driver);
		signin = new SignInForm(driver);
		account = new Account(driver);

	}

	@AfterClass
	public void closeAll() {
		account.getAccountLogout().click();
		driver.quit();
	}

	@Test(priority = 1)
	public void loginPage() {
		Assert.assertTrue(signin.getSignInForm().isDisplayed());
		Assert.assertTrue(signin.getSignInEmailField().isDisplayed());
		Assert.assertTrue(signin.getSignInPasswordField().isDisplayed());
		Assert.assertTrue(signin.getSignInBtn().isEnabled());
	}

	@Test(priority = 2)
	public void invalidCredentials() {
		signin.setEmailField("neki@email.com");
		signin.setPasswordField("sifra");
		signin.getSignInBtn().click();

		Assert.assertTrue(signin.getAuthenticationFailedError().isDisplayed());

		signin.setEmailField("email@email.email");
		signin.setPasswordField("sifra");
		signin.getSignInBtn().click();

		Assert.assertTrue(signin.getAuthenticationFailedError().isDisplayed());

		signin.setEmailField("mapkoct@gmail.com");
		signin.setPasswordField("asddsa");
		signin.getSignInBtn().click();

		Assert.assertTrue(signin.getAuthenticationFailedError().isDisplayed());

	}

	@Test(priority = 3)
	public void loginWithoutCredentials() {
		signin.setEmailField("");
		signin.setPasswordField("asddsa");
		signin.getSignInBtn().click();

		Assert.assertTrue(signin.getEmailRequiredError().isDisplayed());

		signin.setEmailField("mapkoct@gmail.com");
		signin.setPasswordField("");
		signin.getSignInBtn().click();

		Assert.assertTrue(signin.getPasswordRequiredError().isDisplayed());

		signin.setEmailField("");
		signin.setPasswordField("");
		signin.getSignInBtn().click();

		Assert.assertTrue(signin.getEmailRequiredError().isDisplayed());
	}

	@Test(priority = 4)
	public void emailFormat() {
		signin.setEmailField("email");
		signin.getSignInPasswordField().click();

		Assert.assertTrue(signin.getEmailHighlightedRed().isDisplayed());

		signin.setEmailField("email@email");
		signin.getSignInPasswordField().click();

		Assert.assertTrue(signin.getEmailHighlightedRed().isDisplayed());

		signin.setEmailField("email@email.email");
		signin.getSignInPasswordField().click();

		Assert.assertTrue(signin.getEmailHighlightedGreen().isDisplayed());
	}

	@Test(priority = 5)
	public void successfulLogin() {
		signin.setEmailField(EmailsGenerator.getCurrentEmail());
		signin.setPasswordField("Welcome@1");
		signin.getSignInBtn().click();

		Assert.assertTrue(createAccountForm.successfullyCreatedAccount().isDisplayed());
	}

}
