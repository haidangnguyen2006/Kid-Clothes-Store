package com.iuh.kidclothes.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data // Auto generate setter & getter
@NoArgsConstructor // generate constructor with no Args
@AllArgsConstructor // generate constrctor with all Args
@Builder
@FieldDefaults(
        level = AccessLevel.PRIVATE)
public class IntrospectRespone {
    boolean valid;
}
