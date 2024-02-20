package pro.sky.telegrambot.exeption;

public class FormatException extends RuntimeException {
    public FormatException() {
        super("Ввели не корректное сообщение");
    }

}
