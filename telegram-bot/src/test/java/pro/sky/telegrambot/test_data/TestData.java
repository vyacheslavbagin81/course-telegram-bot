package pro.sky.telegrambot.test_data;

import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;

public class TestData {
    public static final String EMPTY_MESSAGE ="";
    public static final String CORRECT_FORMAT_MESSAGE ="12.03.2024 20:15 CORRECT_FORMAT_MESSAGE";
    public static final String INCORRECT_FORMAT_MESSAGE ="INCORRECT_FORMAT_MESSAGE";
    public static final NotificationTask TASK = new NotificationTask(0, 1L,
            "CORRECT_FORMAT_MESSAGE",
            LocalDateTime.of(2024,03,12,20,15));
}
