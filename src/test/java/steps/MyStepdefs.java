package steps;

import com.codeborne.selenide.Configuration;
import io.cucumber.java.ru.Допустим;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.testng.Assert;
import pages.*;
import utils.Log;
import utils.Person;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

public class MyStepdefs {

    ProductsPage productsPage;
    BaseMenu baseMenu = new BaseMenu();
    CartPage cartPage = new CartPage();
    YourInformationPage yourInformationPage = new YourInformationPage();
    OverViewPage overViewPage = new OverViewPage();
    Person myPerson = new Person.Builder()
            .withFirstName("Jane")
            .withLastName("Doe")
            .withPostalCode("32")
            .build();
    double sumPricesExpected;

    @Допустим("^открыта страница \"([^\"]*)\"$")
    public void openPage(String pageURL) {
        Configuration.startMaximized = true;
        Configuration.timeout = 60000;
        open(pageURL);
    }

    @И("^введен логин \"([^\"]*)\" и пароль \"([^\"]*)\"$")
    public void enterCredential(String logIn, String password) {
        Log.info("Метод для проверки logIn");
        LogInPage logInPage = new LogInPage();
        productsPage = logInPage.logIn(logIn, password);
        productsPage.productsTitle.shouldBe(visible);
    }

    @Тогда("^на странице присутствует надпись \"([^\"]*)\"$")
    public void titleIsPresentTest(String title) {
        Assert.assertEquals(productsPage.productsTitle.getText(), title);
        baseMenu.logOut();
    }

    @И("добавлен первый товар в корзину")
    public void addFirstGood() {
        productsPage.addToCart(productsPage.sauceLabsBackpackAddTooCartButton);
    }

    @И("добавлен второй товар в корзину")
    public void addSecondGood() {
        productsPage.addToCart(productsPage.sauceLabsBikeLightAddTooCartButton);
    }

    @И("перешли в корзину")
    public void moveToCart() {
        cartPage = baseMenu.moveToCart();
    }

    @И("нажата кнопка checkOut")
    public void checkOut() {
        yourInformationPage = cartPage.checkOut();
    }

    @И("заполнена информация о пользователе")
    public void yourInformationFill() {
        yourInformationPage.yourInformationFill(myPerson);
    }

    @Когда("нашли сумму стоимости двух товаров")
    public void sumPricesExpected() {
        sumPricesExpected = overViewPage.sumPrices();
        overViewPage.totalPriceLabel.shouldBe(visible);
    }

    @Тогда("суммарная стоимость двух товаов ровна отображенной на странице")
    public void checkSumPrices() {
        double sumPricesActual = overViewPage.getTotalPriceFromPage();
        Assert.assertEquals(sumPricesActual, sumPricesExpected);
        baseMenu.logOut();
    }

}
