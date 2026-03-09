package com.iuh.kidclothes.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data // Auto generate setter & getter
@NoArgsConstructor // generate constructor with no Args
@AllArgsConstructor // generate constrctor with all Args
@Builder
@FieldDefaults(
        level = AccessLevel.PRIVATE)
public class IntrospectRequest {
    String token;

}
