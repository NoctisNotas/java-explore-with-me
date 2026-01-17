package ru.practicum.explorewithme.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @CreationTimestamp
    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommentStatus status;

    @Column(name = "moderator_comment", length = 500)
    private String moderatorComment;
}