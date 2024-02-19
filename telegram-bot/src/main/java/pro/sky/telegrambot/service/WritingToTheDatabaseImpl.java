package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.repository.NotificationTaskRepository;



@Service
public class WritingToTheDatabaseImpl implements WritingToTheDatabase{
    private NotificationTaskRepository repository;
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public WritingToTheDatabaseImpl(NotificationTaskRepository repository) {
        this.repository = repository;
    }

    public NotificationTaskRepository getRepository() {
        return repository;
    }


    public void writingToTheDatabase(String string, long idChat) {
        repository.save(MessageProcessingImpl.messageProcessing(string, idChat));
        logger.info("метод отработал");
    }

}
