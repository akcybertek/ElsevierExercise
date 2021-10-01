package Elsevier;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class AutomationEx {

    WebDriver driver;

        @BeforeMethod
        public void setUp() {

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.get("http://automationpractice.com/index.php");
            driver.manage().window().maximize();
        }
        @AfterMethod
        public void tearDown() {
            driver.quit();
        }

        @Test
        public void validateAddCart() throws Exception {


            driver.findElement(By.id("search_query_top")).sendKeys("summer dresses");
            driver.findElement(By.name("submit_search")).click();
            driver.findElement(By.className("icon-th-list")).click();


            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebElement continueShop = driver.findElement(By.xpath("//span[@title='Continue shopping']"));
            WebElement checkOut = driver.findElement(By.xpath("//a[@title='Proceed to checkout']"));


            List<WebElement> products = driver.findElements(By.xpath("//ul[@class='product_list row list']/li"));
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            List<WebElement> addCart = driver.findElements(By.xpath("//a[@class='button ajax_add_to_cart_button btn btn-default']/span"));

            int itemNum = 0;
            for (int i = 0; i <= products.size(); i++) {
                itemNum++;
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                addCart.get(i).click();

                if (itemNum == products.size()) {
                    checkOut.click();
                    break;
                }

                continueShop.click();
            }

            WebElement itemNums =driver.findElement(By.id("summary_products_quantity"));
            String itemsInCart = itemNums.getText();
            String expectedValue= "4 Products";
            Assert.assertEquals(itemsInCart , expectedValue);

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebElement checkOutCart = driver.findElement(By.xpath("//*[@id='center_column']/p[2]/a[1]/span"));
            checkOutCart.click();

            WebElement signIn = driver.findElement(By.xpath("//div[@id='center_column']/h1"));
            Assert.assertTrue(signIn.getText().equalsIgnoreCase("AUTHENTICATION"));

        }

    }
