package pro.sky.telegrambot.schedulerConfig;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pro.sky.telegrambot.service.RemindersServiceIml;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private RemindersServiceIml remindersService;

    public SchedulerConfig(RemindersServiceIml remindersService) {
        this.remindersService = remindersService;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void searchForReminders() {
        remindersService.sendingReminders();
    }
}
