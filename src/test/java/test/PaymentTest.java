package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.CardHelper;
import data.SqlHelper;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PaymentTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080/");
        SqlHelper.clearTables();
    }

    @SneakyThrows
    @Test
    void buyPaymentApprovedCard() { // Позитивный 1. Через карту APPROVED
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkApprovedForm();
        assertEquals("APPROVED", SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentApprovedCardLongName() { // Позитивный 2. Через карту APPROVED, длинные имя и фамилия
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getValidOwnerLong(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkApprovedForm();
        assertEquals("APPROVED", SqlHelper.getPaymentStatus());

    }

    @SneakyThrows
    @Test
    void buyPaymentApprovedCardShortName() { // Позитивный 3. Через карту APPROVED, короткие имя и фамилия
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getValidOwnerShort(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkApprovedForm();
        assertEquals("APPROVED", SqlHelper.getPaymentStatus());

    }

    @SneakyThrows
    @Test
    void buyPaymentApprovedCardDoubleName() { // Позитивный 4. Через карту APPROVED, имя и фамилия через дефис
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getValidOwnerDouble(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkApprovedForm();
        assertEquals("APPROVED", SqlHelper.getPaymentStatus());

    }

    @SneakyThrows
    @Test
    void buyPaymentDeclinedCard() { // Негативный 1. Через карту DECLINED
        CardHelper card = new CardHelper(getValidDeclinedCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkDeclinedForm();
        assertEquals("DECLINED", SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentEmptyCard() { // Негативный 2. Поле "Номер карты" пустое
        CardHelper card = new CardHelper(getEmptyNumberCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkCardNumberError();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentEmptyMonth() { // Негативный 3. Поле "Месяц" пустое
        CardHelper card = new CardHelper(getValidActiveCard(), getEmptyMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkMonthError();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentEmptyYear() { // Негативный 4. Поле "Год" пустое
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getEmptyYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkYearError();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentEmptyOwner() { // Негативный 5. Поле "Владелец" пустое
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getEmptyOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkOwnerError();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentEmptyCVC() { // Негативный 6. Поле "CVC/CVV" пустое
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getEmptyCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkCVCError();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentInvalidNumberCard() { // Негативный 7. Поле "Номер карты" заполнено случайными цифрами
        CardHelper card = new CardHelper(getInvalidNumberCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkDeclinedForm();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentInvalidShortNumberCard() { // Негативный 8. Поле "Номер карты" заполнено 15 цифрами
        CardHelper card = new CardHelper(getInvalidShortNumberCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkCardNumberError();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentInvalidLongCard() { // Негативный 9. Поле "Номер карты" заполнено 17 цифрами
        CardHelper card = new CardHelper(getInvalidLongNumberCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkDeclinedForm();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentZeroNumberCard() { // Негативный 10. Поле "Номер карты" заполнено нолями
        CardHelper card = new CardHelper(getZeroNumberCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkCardNumberError();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentInvalidPreviousMonth() { // Негативный 11. Поле "Месяц" заполнено датой предыдущего месяца
        CardHelper card = new CardHelper(getValidActiveCard(), getPreviousMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkMonthError();;
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentInvalidNoExistMonth() { // Негативный 12. Поле "Месяц" заполнено "13"
        CardHelper card = new CardHelper(getValidActiveCard(), getNoExistMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkMonthError();;
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentZeroMonth() { // Негативный 13. Поле "Месяц" заполнено "00"
        CardHelper card = new CardHelper(getValidActiveCard(), getZeroMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkMonthError();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentInvalidPreviousYear() { // Негативный 14. Поле "Год" заполнено датой предыдущего года
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getPreviousYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkYearError();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentInvalidFutureYear() { // Негативный 15. Поле "Год" заполнено датой будущего через 6 лет года
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getFutureYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkYearError();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentInvalidNumberOwner() { // Негативный 16. Поле "Владелец" заполнено цифрами
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getInvalidNumberOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkOwnerError();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentRussianOwner() { // Негативный 17. Поле "Владелец" заполнено кириллицей
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getInvalidRuOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkOwnerError();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentInvalidLettersCVC() { // Негативный 18. Поле "CVC/CVV" заполнено латиницей
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getInvalidLettersCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkCVCError();
        assertNull(SqlHelper.getPaymentStatus());
    }

    @SneakyThrows
    @Test
    void buyPaymentZeroCVC() { // Негативный 19. Поле "CVC/CVV" заполнено тремя нолями
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getZeroCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToBuy().
                fillingForm(card).
                checkCVCError();
        assertNull(SqlHelper.getPaymentStatus());
    }




}
