package uk.gov.justice.digital.cpspackparser.data.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CPSPack {
    private DefendantId defendantId;
    private ConvictionSummary convictionSummary;
}
