package pro.sky.telegrambot.service;

import org.springframework.beans.factory.annotation.Value;
import pro.sky.telegrambot.exeption.FormatExeption;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageProcessingImpl {
    @Value("${message.pattern}")
    private static String pattern;

    public static NotificationTask messageProcessing(String string, Long idChat) {
        NotificationTask notificationTask = new NotificationTask();
        LocalDateTime dateTime = stringToDate(string);
        String text = createText(string);
        notificationTask.setDate(dateTime);
        notificationTask.setIdChat(idChat);
        notificationTask.setText(text);
        return notificationTask;
    }

    //    проверяем на наличие даты и времени и возвращаем их
    private static String checkMessage(String string) {
        Pattern pattern = Pattern.compile(MessageProcessingImpl.pattern);
        Matcher matcher = pattern.matcher(string);
        if (matcher.lookingAt()) {
            return matcher.group();
        } else throw new FormatExeption();
    }

    //    переводим время в LocalDateTime
    private static LocalDateTime stringToDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(checkMessage(string), formatter);
            if (checkDate(dateTime)) {

                return dateTime.truncatedTo(ChronoUnit.MINUTES);
            }else throw new FormatExeption();
        } catch (DateTimeException e) {
            throw new FormatExeption();
        }
    }

    //    отделяем текст записи от даты
    private static String createText(String string) {
        return string.substring(17);
    }

    //    проверяем актуальность даты
    private static boolean checkDate(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }
}
