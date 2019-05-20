package uk.gov.justice.digital.cpspackparser.data.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConvictionGroup {
    private int count;
    private String description;
    private String yearDates;
}
