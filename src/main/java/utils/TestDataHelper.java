package utils;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestDataHelper {

    private final static Faker FAKER = Faker.instance();

    public static String getFutureDate(int plusDays, DateTimeFormatter dateTimeFormatter){
        return LocalDate.now().plusDays(FAKER.number().randomNumber(2, true))
                        .format(dateTimeFormatter);
    }

    public static Faker getFaker() {
        return FAKER;
    }

    public static int getRandomNumber(int numberOfDigits){
        return Math.toIntExact(FAKER.number().randomNumber(numberOfDigits, true));
    }
}
