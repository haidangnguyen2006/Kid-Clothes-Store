package com.iuh.kidclothes.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data // Auto generate setter & getter
@NoArgsConstructor // generate constructor with no Args
@AllArgsConstructor // generate constrctor with all Args
@Builder
@FieldDefaults(
        level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @NotNull
    String fullname;

    @NotNull
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    @NotNull
    String email;
    String phone;
    String address;
}
