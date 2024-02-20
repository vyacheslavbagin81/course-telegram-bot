package pro.sky.telegrambot.service;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exeption.FormatException;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@NoArgsConstructor
public class MessageProcessingImpl implements MessageProcessing {
    private final String PATTERN = "\\d{1,2}\\.\\d{1,2}.\\d{4}\\s\\d{1,2}.\\d{1,2}";

    private final Logger LOGGER = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Override
    public NotificationTask messageProcessing(String string, Long idChat) {
        NotificationTask notificationTask = new NotificationTask();
        LocalDateTime dateTime = stringToDate(string);
        String text = createText(string);
        notificationTask.setDate(dateTime);
        notificationTask.setIdChat(idChat);
        notificationTask.setText(text);
        return notificationTask;
    }

    //    проверяем на наличие даты и времени и возвращаем их
    private String checkMessage(String string) {
        Pattern pattern = Pattern.compile(this.PATTERN);
        Matcher matcher = pattern.matcher(string);
        if (matcher.lookingAt()) {
            return matcher.group();
        } else {
            LOGGER.error("FormatException полученый текст не соответствует требуемому формату");
            throw new FormatException();
        }
    }

    //    переводим время в LocalDateTime
    private LocalDateTime stringToDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(checkMessage(string), formatter);
            if (checkDate(dateTime)) {

                return dateTime.truncatedTo(ChronoUnit.MINUTES);
            } else {
                LOGGER.error("FormatException не прошли проверку на актуальность даты");
                throw new FormatException();
            }
        } catch (DateTimeException e) {
            LOGGER.error("Получаем ошибку DateTimeException");
            throw new FormatException();
        }
    }

    //    отделяем текст записи от даты
    private String createText(String string) {
        return string.substring(17);
    }

    //    проверяем актуальность даты
    private boolean checkDate(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }
}
