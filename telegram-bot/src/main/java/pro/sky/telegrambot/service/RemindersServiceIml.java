package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class RemindersServiceIml {
    private static NotificationTaskRepository repository;
    @Autowired
    private static TelegramBot telegramBot;

    public RemindersServiceIml(NotificationTaskRepository repository, TelegramBot telegramBot) {
        this.repository = repository;
        this.telegramBot = telegramBot;
    }


    // ищем записи по дате
    private static List<NotificationTask> searchForReminders() {
        List<NotificationTask> list = new ArrayList<>();
        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        return repository.findAllByDate(dateTime);
    }

    //  проверяем есть ли записи
    private static boolean checkReminders(List<NotificationTask> list) {
        return list.isEmpty();
    }

    //  отправляем напоминания
    public static void sendingReminders() {
        List<NotificationTask> list = searchForReminders();
        if (!checkReminders(list)) {
            list.forEach(x -> telegramBot.execute(new SendMessage(x.getIdChat(), x.getText())));
        }
    }
}
