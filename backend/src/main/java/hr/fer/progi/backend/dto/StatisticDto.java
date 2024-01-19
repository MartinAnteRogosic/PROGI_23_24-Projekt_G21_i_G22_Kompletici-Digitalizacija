package hr.fer.progi.backend.dto;

import hr.fer.progi.backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticDto {

   private String firstName;
   private String lastName;
   private Role role;
   private Integer numberOfScannedDocuments;
   private Integer numberOfRevisedDocuments;
   private List<LoginTime> loginTimes;
}
