package data;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    // Поле "Номер Карты"
    public static String getValidActiveCard() {
        return "4444 4444 4444 4441";
    }

    public static String getValidDeclinedCard() {
        return ("4444 4444 4444 4442");
    }

    public static String getZeroNumberCard() {
        return ("0000 0000 0000 0000");
    }

    public static String getEmptyNumberCard() {
        return ("");
    }

    public static String getInvalidNumberCard() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.numerify("#### #### #### ####");
    }

    public static String getInvalidShortNumberCard() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.numerify("#### #### #### ###");
    }

    public static String getInvalidLongNumberCard() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.numerify("#### #### #### #### #");
    }

    // Поле "Месяц"
    public static String getCurrentMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getPreviousMonth() {
        int currentMonth = LocalDate.now().getMonthValue();
        if (currentMonth == 1) {
            return getLastMonth();
        } else {
            return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
        }
    }

    public static String getLastMonth() {
        int plusMonth = LocalDate.now().getMonthValue();
        return LocalDate.now().plusMonths(12 - plusMonth).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getZeroMonth() {
        return ("00");
    }

    public static String getNoExistMonth() {
        return ("13");
    }

    public static String getEmptyMonth() {
        return ("");
    }

    // Поле "Год"
    public static String getCurrentYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getPreviousYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getFutureYear() {
        return LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
    }


    public static String getNextYear() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }


    public static String getEmptyYear() { // пустой Год
        return ("");
    }

    // Поле "Владелец"
    public static String getValidOwner() {
        Faker faker = new Faker((new Locale("en")));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getValidOwnerLong() {
        return generateLetterString(20) + " " + generateLetterString(60);
    }
    private static String generateLetterString(int length) {
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static String getValidOwnerShort() {
        Faker faker = new Faker((new Locale("en")));

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        if (firstName.length() > 1) {
            firstName = firstName.substring(0, 1);
        }
        if (lastName.length() > 2) {
            lastName = lastName.substring(0, 2);
        }
        return firstName + " " + lastName;
    }

    public static String getValidOwnerDouble() {
        Faker faker = new Faker((new Locale("en")));

        String firstName = faker.name().firstName() + "-" + faker.name().firstName();
        String lastName = faker.name().lastName() + "-" + faker.name().lastName();

        return firstName + " " + lastName;
    }

    public static String getInvalidNumberOwner() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.numerify("###### ##########");
    }

    public static String getInvalidRuOwner() {
        Faker faker = new Faker((new Locale("ru")));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getEmptyOwner() {
        return ("");
    }

    // Поле "CVC/CVV"
    public static String getValidCVC() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.numerify("###");
    }

    public static String getZeroCVC() {
        return ("000");
    }

    public static String getInvalidLettersCVC() {
        int CVC = 3;
        boolean useLetters = true;
        boolean useNumbers = false;

        return RandomStringUtils.random(CVC, useLetters, useNumbers);
    }

    public static String getEmptyCVC() {
        return ("");
    }

}
