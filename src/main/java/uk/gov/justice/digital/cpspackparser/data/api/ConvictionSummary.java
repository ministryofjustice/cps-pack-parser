package uk.gov.justice.digital.cpspackparser.data.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ConvictionSummary {
    private int convictionCount;
    private int offenceCount;
    private String dateOfFirstConviction;
    private String dateOfLastConviction;
    private List<ConvictionGroup> summary;
}
