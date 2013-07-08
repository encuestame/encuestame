import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.encuestame.utils.categories.test.DefaultTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;

//@Category(DefaultTest.class)
public class AjaxCounterIntegrationTest extends TestCase{

    protected WebDriver driver;

    private String testUrl = "http://localhost:8080/prime-showcase/ui/pprCounter.jsf";

    @Before
    public void before() {
        driver = new FirefoxDriver();
    }

    @After
    public void after() {
        driver.quit();
    }

    @Test
    public void shouldIncreaseCounter() {

        driver.get(testUrl);

        WebElement button = driver.findElement(By.id(("j_idt14:j_idt17")));

        button.click();

        new FluentWait<WebDriver>(driver).withTimeout(10, TimeUnit.SECONDS).pollingEvery(2, TimeUnit.SECONDS).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver wd) {
                WebElement element = wd.findElement(By.id("j_idt14:txt_count"));
                return element.getText().equals("1");
            }
        });

        WebElement numberText = driver.findElement(By.id(("j_idt14:txt_count")));

        assertThat(numberText.getText(), equalTo("1"));

        button.click();

    }
}