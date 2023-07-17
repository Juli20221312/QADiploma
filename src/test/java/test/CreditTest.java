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

public class CreditTest {

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
    void buyCreditApprovedCard() { // Позитивный 1. Через карту APPROVED
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkApprovedForm();
        assertEquals("APPROVED", SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditApprovedCardLongName() { // Позитивный 2. Через карту APPROVED, длинные имя и фамилия
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getValidOwnerLong(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkApprovedForm();
        assertEquals("APPROVED", SqlHelper.getCreditStatus());

    }

    @SneakyThrows
    @Test
    void buyPCreditApprovedCardShortName() { // Позитивный 3. Через карту APPROVED, короткие имя и фамилия
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getValidOwnerShort(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkApprovedForm();
        assertEquals("APPROVED", SqlHelper.getCreditStatus());

    }

    @SneakyThrows
    @Test
    void buyCreditApprovedCardDoubleName() { // Позитивный 4. Через карту APPROVED, имя и фамилия через дефис
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getValidOwnerDouble(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkApprovedForm();
        assertEquals("APPROVED", SqlHelper.getCreditStatus());

    }

    @SneakyThrows
    @Test
    void buyCreditDeclinedCard() { // Негативный 1. Через карту DECLINED
        CardHelper card = new CardHelper(getValidDeclinedCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkDeclinedForm();
        assertEquals("DECLINED", SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditEmptyCard() { // Негативный 2. Поле "Номер карты" пустое
        CardHelper card = new CardHelper(getEmptyNumberCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkCardNumberError();
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditEmptyMonth() { // Негативный 3. Поле "Месяц" пустое
        CardHelper card = new CardHelper(getValidActiveCard(), getEmptyMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkMonthError();
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditEmptyYear() { // Негативный 4. Поле "Год" пустое
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getEmptyYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkYearError();
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditEmptyOwner() { // Негативный 5. Поле "Владелец" пустое
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getEmptyOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkOwnerError();
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditEmptyCVC() { // Негативный 6. Поле "CVC/CVV" пустое
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getEmptyCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkCVCError();
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditInvalidNumberCard() { // Негативный 7. Поле "Номер карты" заполнено случайными цифрами
        CardHelper card = new CardHelper(getInvalidNumberCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkDeclinedForm();
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditInvalidShortNumberCard() { // Негативный 8. Поле "Номер карты" заполнено 15 цифрами
        CardHelper card = new CardHelper(getInvalidShortNumberCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkCardNumberError();
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditInvalidLongCard() { // Негативный 9. Поле "Номер карты" заполнено 17 цифрами
        CardHelper card = new CardHelper(getInvalidLongNumberCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkDeclinedForm();
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditZeroNumberCard() { // Негативный 10. Поле "Номер карты" заполнено нолями
        CardHelper card = new CardHelper(getZeroNumberCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkCardNumberError();
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditInvalidPreviousMonth() { // Негативный 11. Поле "Месяц" заполнено датой предыдущего месяца
        CardHelper card = new CardHelper(getValidActiveCard(), getPreviousMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkMonthError();;
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditInvalidNoExistMonth() { // Негативный 12. Поле "Месяц" заполнено "13"
        CardHelper card = new CardHelper(getValidActiveCard(), getNoExistMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkMonthError();;
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditZeroMonth() { // Негативный 13. Поле "Месяц" заполнено "00"
        CardHelper card = new CardHelper(getValidActiveCard(), getZeroMonth(), getNextYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkMonthError();
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditInvalidPreviousYear() { // Негативный 14. Поле "Год" заполнено датой предыдущего года
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getPreviousYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkYearError();
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditInvalidFutureYear() { // Негативный 15. Поле "Год" заполнено датой будущего через 6 лет года
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getFutureYear(), getValidOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkYearError();
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditInvalidNumberOwner() { // Негативный 16. Поле "Владелец" заполнено цифрами
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getInvalidNumberOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkOwnerError();
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditRussianOwner() { // Негативный 17. Поле "Владелец" заполнено кириллицей
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getInvalidRuOwner(), getValidCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkOwnerError();
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditInvalidLettersCVC() { // Негативный 18. Поле "CVC/CVV" заполнено латиницей
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getInvalidLettersCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkCVCError();
        assertNull(SqlHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void buyCreditZeroCVC() { // Негативный 19. Поле "CVC/CVV" заполнено тремя нолями
        CardHelper card = new CardHelper(getValidActiveCard(), getCurrentMonth(), getNextYear(), getValidOwner(), getZeroCVC());
        val mainPage = new MainPage();
        mainPage.checkButtonToCredit().
                fillingForm(card).
                checkCVCError();
        assertNull(SqlHelper.getCreditStatus());
    }


}
