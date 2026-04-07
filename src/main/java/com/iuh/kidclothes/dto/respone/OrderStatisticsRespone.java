package com.iuh.kidclothes.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderStatisticsRespone {
    Double totalAmount;
    LocalDateTime createdAt;
}
