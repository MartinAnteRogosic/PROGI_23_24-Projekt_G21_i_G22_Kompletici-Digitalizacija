package hr.fer.progi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class StatisticDto {

    private long timeOfLogin;
    private long numberOfDocuments;
}
