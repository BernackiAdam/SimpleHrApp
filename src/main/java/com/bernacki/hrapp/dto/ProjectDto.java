package com.bernacki.hrapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectDto {

    @NotNull(message = "Title is required")
    @Size(min=1, message = "Title is required")
    private String title;

    @NotNull(message = "Select project type")
    @Size(min=1, message = "Select project type")
    private String projectType;

    @NotNull(message = "Select client")
    @Min(value = 1, message = "Select client")
    private int clientId;

    @NotNull(message = "Description is required")
    @Size(min=1, message = "Description is required")
    private String description;

    @NotNull(message = "Select starting phase")
    @Size(min=1, message = "Select starting phase")
    private String startingPhase;

}
