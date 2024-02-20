package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exeption.FormatException;
import pro.sky.telegrambot.service.WritingToTheDatabaseImpl;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private WritingToTheDatabaseImpl writing;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (update.message().text().equals("/start") ||
                    update.message().text().equals("start") ||
                    update.message().text().equals("/info") ||
                    update.message().text().equals("info")) {
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Введите запись для того что бы сделать напоминание в виде дд.мм.гггг чч:мм сообщение"));
            } else if (!update.message().text().isBlank()) {
                try {
                    writing.writingToTheDatabase(update.message().text(),
                            update.message().chat().id());
                } catch (FormatException e) {
                    telegramBot.execute(new SendMessage(update.message().chat().id(), e.getMessage()));
                }
            }

        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
