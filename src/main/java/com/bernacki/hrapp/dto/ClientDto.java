package com.bernacki.hrapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientDto {

    @NotNull(message = "Company name is required")
    @Size(min=1, message = "Company name is required")
    private String name;

    @NotNull(message = "Company address is required")
    @Size(min=1, message = "Company address is required")
    private String address;

}
