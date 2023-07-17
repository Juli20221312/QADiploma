package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private SelenideElement buttonToBuy = $(byText("Купить"));
    private SelenideElement buttonToCredit = $(byText("Купить в кредит"));

    public PaymentPage checkButtonToBuy() {
        buttonToBuy.click();
        return new PaymentPage();
    }
    public CreditPage checkButtonToCredit() {
        buttonToCredit.click();
        return new CreditPage();
    }
}
