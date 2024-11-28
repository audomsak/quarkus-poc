package com.redhat.app.poc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;

@Entity
@ApplicationScoped
@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "log")

public class LogMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @JsonProperty
    @Column(name = "logger_name")
    private String loggerName;

    @JsonProperty
    @Column(name = "level")
    private String level;

    @JsonProperty
    @Column(name = "message")
    private String message;

    @JsonProperty
    @Column(name = "process_name")
    private String processName;

    @JsonProperty
    @Column(name = "timestamp")
    private String timestamp;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LogMessage{" +
                "id=" + id +
                ", loggerName='" + loggerName + '\'' +
                ", level='" + level + '\'' +
                ", message='" + message + '\'' +
                ", processName='" + processName + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
