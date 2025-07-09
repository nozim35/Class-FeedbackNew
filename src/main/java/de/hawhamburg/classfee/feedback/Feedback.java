package de.hawhamburg.classfee.feedback;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String content;

    private String author; // z. B. Benutzername oder "Anonym"

    private LocalDateTime createdAt;

    private Long moduleId;

    private String moduleName;


    // --- Konstruktoren ---
    public Feedback() {}

    public Feedback(String content, String author, LocalDateTime createdAt, Long moduleId) {
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.moduleId = moduleId;
    }

    // --- Getter/Setter ---
    public Long getId() { return id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) { this.author = author; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }

    public String getModuleName() { return moduleName; }
    public void setModuleName(String moduleName) { this.moduleName = moduleName; }

    public String getFormattedDate() {
        return createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm 'Uhr'"));
    }

}