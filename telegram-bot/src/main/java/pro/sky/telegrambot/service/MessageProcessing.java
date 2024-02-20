package pro.sky.telegrambot.service;

import pro.sky.telegrambot.model.NotificationTask;

public interface MessageProcessing {

    NotificationTask messageProcessing(String string, Long idChat);
}
