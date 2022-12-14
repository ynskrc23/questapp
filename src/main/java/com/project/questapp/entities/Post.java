package com.project.questapp.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="post")
@Data
public class Post {
    @Id
    Long id;
    Long user_id;
    String title;
    @Lob
    @Column(columnDefinition = "text")
    String text;
}
