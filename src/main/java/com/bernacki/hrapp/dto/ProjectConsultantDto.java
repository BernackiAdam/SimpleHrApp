package com.bernacki.hrapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectConsultantDto {

    @NotNull(message = "First name is required")
    @Size(min=1, message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(min=1, message = "Last name is required")
    private String lastName;

    @NotNull(message = "Email is required")
    @Size(min=1, message = "Email is required")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email format is invalid")
    private String email;

    @NotNull(message = "Telephone number is required")
    @Size(min=1, message = "Telephone number is required")
    @Pattern(regexp = "^[0-9]{9}", message = "Invalid telephone number format")
    private String telephoneNumber;

    @NotNull(message = "Project is required")
    @Min(value = 0, message = "Project is required")
    private int projectId;
}
