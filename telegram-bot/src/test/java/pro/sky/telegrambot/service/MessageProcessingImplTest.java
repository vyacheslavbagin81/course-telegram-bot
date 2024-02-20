package pro.sky.telegrambot.service;

import org.junit.jupiter.api.Test;
import pro.sky.telegrambot.exeption.FormatException;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.test_data.TestData;


import static org.junit.jupiter.api.Assertions.*;

class MessageProcessingImplTest {

    private final MessageProcessingImpl messageProcessing = new MessageProcessingImpl();


    // проверяем правильный формат, не правильный формат, с нулевым значением
    @Test
    public void messageProcessingTest_correctMessage() {
        NotificationTask result = messageProcessing.messageProcessing(TestData.CORRECT_FORMAT_MESSAGE, 1L);
        assertEquals(TestData.TASK, result);
    }

    @Test
    public void messageProcessingTest_incorrectMessage() {
        assertThrows(FormatException.class, () -> messageProcessing.messageProcessing(TestData.INCORRECT_FORMAT_MESSAGE, 1L));
    }

    @Test
    public void messageProcessingTest_emptyMessage() {
        assertThrows(FormatException.class, () -> messageProcessing.messageProcessing(TestData.EMPTY_MESSAGE, 1L));
    }
}