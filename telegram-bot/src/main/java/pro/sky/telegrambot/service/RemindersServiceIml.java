package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@NoArgsConstructor
public class RemindersServiceIml implements RemindersService {
    @Autowired
    private NotificationTaskRepository repository;

    @Autowired
    private TelegramBot telegramBot;

    private final Logger LOGGER = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    public RemindersServiceIml(NotificationTaskRepository repository, TelegramBot telegramBot) {
        this.repository = repository;
        this.telegramBot = telegramBot;
    }

    //  отправляем напоминания
    @Override
    public void sendingReminders() {
        List<NotificationTask> list = searchForReminders();
        if (!checkReminders(list)) {
            list.forEach(x -> telegramBot.execute(new SendMessage(x.getIdChat(), x.getText())));
            LOGGER.info("Отправленно сообщение с напоминанием");
        }
    }

    // ищем записи по дате
    private List<NotificationTask> searchForReminders() {
        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        return repository.findAllByDate(dateTime);
    }

    //  проверяем есть ли записи
    private boolean checkReminders(List<NotificationTask> list) {
        return list.isEmpty();
    }

}
