package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.SettingsPage;
import utilities.Driver;
import utilities.SeleniumUtils;

import java.time.Duration;


public class UserAccess_StepDef {
    WebDriver driver = Driver.getDriver();
    LoginPage loginPage = new LoginPage();
    SettingsPage settingsPage = new SettingsPage();


    @Given("user is navigated to Crater login page")
    public void user_is_navigated_to_crater_login_page() {
        //maximize window
        driver.manage().window().maximize();
        //navigate to the crater app login page
        driver.get("http://crater.primetech-apps.com/login");
        //implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }
    @When("user enters valid username and valid password")
    public void user_enters_valid_username_and_valid_password() throws InterruptedException {
        SeleniumUtils.sendkeysWithActionsClass(loginPage.emailInput, "entityadmin@primetechschool.com");
        Thread.sleep(2000);
        SeleniumUtils.sendkeysWithActionsClass(loginPage.passwordInput, "primetech@school");
        Thread.sleep(2000);
    }

    @And("user clicks on login button")
    public void user_clicks_on_login_button() throws InterruptedException {
        loginPage.loginButton.click();
        Thread.sleep(2000);
    }
    @Then("user should be logged in successfully")
    public void user_should_be_logged_in_successfully() {
        //verify using url that is different from login url
        String loginUrl = "http://crater.primetech-apps.com/login";
        String afterLoginUrl = driver.getCurrentUrl();
        System.out.println("Current URL after login is : " + afterLoginUrl);
        Assert.assertNotEquals(loginUrl, afterLoginUrl);
        //Verify using setting label, because we land on the dashboard on setting page
        Assert.assertTrue(settingsPage.settingsLabel.isDisplayed());
        Driver.closeDriver();

    }

    // Second scenario

    @When("user enters invalid username and valid password")
    public void user_enters_invalid_username_and_valid_password() throws InterruptedException {
        SeleniumUtils.sendkeysWithActionsClass(loginPage.emailInput, "xxx@primetechschool.com");
        Thread.sleep(2000);
        SeleniumUtils.sendkeysWithActionsClass(loginPage.passwordInput, "primetech@school");
        Thread.sleep(2000);
    }
    @Then("user should see an error message {string} displayed")
    public void user_should_see_an_error_message_displayed(String expectedErrorMessage) {
        //verify that the error message displayed is equal to the expected error message
        String actualErrorMessage = loginPage.loginErrorMessageLabel.getText();
        Assert.assertTrue(expectedErrorMessage.equals(actualErrorMessage));
    }
    @And("user should not be logged in")
    public void user_should_not_be_logged_in() {
        String loginUrl = "http://crater.primetech-apps.com/login";
        //verify that login url didn't change, meaning we are still in the same page and we couldn't login
        Assert.assertTrue(driver.getCurrentUrl().equals(loginUrl));
        Driver.closeDriver();
    }




}
