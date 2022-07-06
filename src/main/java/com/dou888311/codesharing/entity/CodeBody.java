package com.dou888311.codesharing.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CodeBody {

    @Id
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private String id;
    private String code;
    private String date;
    private long time; // in seconds
    private int views; // in times

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private boolean alwaysVisible = false;

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime creationTime;
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private boolean viewsLimited = false;
}
