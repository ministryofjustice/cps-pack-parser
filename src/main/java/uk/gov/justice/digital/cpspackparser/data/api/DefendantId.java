package uk.gov.justice.digital.cpspackparser.data.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DefendantId {
    private String croNumber;
    private String pncNumber;
}
