package pro.sky.telegrambot.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.repository.NotificationTaskRepository;


@Service
@NoArgsConstructor
public class WritingToTheDatabaseImpl implements WritingToTheDatabase {
    @Getter
    @Autowired
    private NotificationTaskRepository repository;
    @Autowired
    private MessageProcessingImpl messageProcessing;

    public WritingToTheDatabaseImpl(NotificationTaskRepository repository, MessageProcessingImpl messageProcessing) {
        this.repository = repository;
        this.messageProcessing = messageProcessing;
    }

    private final Logger LOGGER = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public WritingToTheDatabaseImpl(NotificationTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void writingToTheDatabase(String string, long idChat) {
        repository.save(messageProcessing.messageProcessing(string, idChat));
        LOGGER.info("данные из сообщения занесены в БД");
    }

}
