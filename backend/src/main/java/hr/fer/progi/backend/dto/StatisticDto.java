package hr.fer.progi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@AllArgsConstructor
public class StatisticDto {

   private Integer numberOfScannedDocuments;
   private Integer numberOfRevisedDocuments;
   private List<LoginTime> loginTimes;
}
