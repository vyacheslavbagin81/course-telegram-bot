package pro.sky.telegrambot.model;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "id_chat")
    private long idChat;
    @Column
    private String text;
    @Column
    private LocalDateTime date;

    public NotificationTask(long id, long idChat, String text, LocalDateTime date) {
        this.id = id;
        this.idChat = idChat;
        this.text = text;
        this.date = date;
    }

    public NotificationTask(long idChat, String text, LocalDateTime date) {
        this.idChat = idChat;
        this.text = text;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return id == that.id && idChat == that.idChat && Objects.equals(text, that.text) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idChat, text, date);
    }
}
