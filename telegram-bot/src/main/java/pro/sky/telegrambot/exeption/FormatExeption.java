package pro.sky.telegrambot.exeption;

public class FormatExeption extends RuntimeException {
    public FormatExeption() {
        super("Ввели не корректное сообщение");
    }

}
