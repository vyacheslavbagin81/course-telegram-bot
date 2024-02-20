package pro.sky.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface NotificationTaskRepository extends CrudRepository<NotificationTask, Long>, PagingAndSortingRepository<NotificationTask, Long> {
    List<NotificationTask> findAllByDate(LocalDateTime dateTime);
}
