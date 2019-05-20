package uk.gov.justice.digital.cpspackparser.parser;

import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import uk.gov.justice.digital.cpspackparser.data.api.CPSPack;
import uk.gov.justice.digital.cpspackparser.data.api.ConvictionGroup;
import uk.gov.justice.digital.cpspackparser.data.api.ConvictionSummary;
import uk.gov.justice.digital.cpspackparser.data.api.DefendantId;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

@Component
public class CPSPackParser {
    public Optional<CPSPack> extractCPSPack(String cpsPack) {
        System.err.println(cpsPack);
        val maybeDefendantId = extractDefendantId(cpsPack);
        val maybeDConvictionSummary = extractConvictionSummary(cpsPack);

        if (maybeDefendantId.isPresent() || maybeDConvictionSummary.isPresent()) {
            val builder = CPSPack.builder();
            maybeDefendantId.ifPresent(builder::defendantId);
            maybeDConvictionSummary.ifPresent(builder::convictionSummary);
            return Optional.of(builder.build());
        }
        return Optional.empty();

    }

    private static Optional<DefendantId> extractDefendantId(String cpsPack) {
        val matcher = forExpression("CRO\\s*N[A-Z0]*\\s*.\\s*([a-zA-Z0-9/]+)\\s*PNCID\\s*.\\s*([a-zA-Z0-9/]+)", cpsPack);

        if (matcher.find() && matcher.groupCount() == 2) {
            return Optional.of(DefendantId.builder().croNumber(matcher.group(1)).pncNumber(matcher.group(2)).build());
        }

        return Optional.empty();
    }

    private static Optional<ConvictionSummary> extractConvictionSummary(String cpsPack) {
        val matcher = forExpression("CONVICTION\\(S\\)\\s*.\\s*([0-9]+)\\s*OFFENCE\\(S\\)\\s*.\\s*([0-9]+)", cpsPack);

        if (matcher.find() && matcher.groupCount() == 2) {
            val builder = ConvictionSummary.builder().convictionCount(toNumber(matcher.group(1))).offenceCount(toNumber(matcher.group(2)));

            val datesMatcher = forExpression("DATE FIRST CONVICTED\\s*.\\s*([0-9]{2}\\/[0-9]{2}\\/[0-9]{2})\\s*DATE LAST CONVICTED\\s*.\\s*([0-9]{2}\\/[0-9]{2}\\/[0-9]{2})", cpsPack);

            if (datesMatcher.find() && datesMatcher.groupCount() == 2) {
                builder.dateOfFirstConviction(datesMatcher.group(1)).dateOfLastConviction(datesMatcher.group(2));

                val offenceSummaryMatcherPattern = Pattern.compile( "^.*DATE FIRST CONVICTED\\s*.\\s*[0-9]{2}/[0-9]{2}/[0-9]{2}\\s*DATE LAST CONVICTED\\s*.\\s*[0-9]{2}/[0-9]{2}/[0-9]{2}.*?$(.*?)REPRIMAND", Pattern.MULTILINE | Pattern.DOTALL);
                val offenceSummaryMatcher =  offenceSummaryMatcherPattern.matcher(cpsPack);

                if (offenceSummaryMatcher.find() && offenceSummaryMatcher.groupCount() == 1) {
                    builder.summary(extractSummaryLines(offenceSummaryMatcher.group(1)));
                }

            }
            return Optional.of(builder.build());
        }

        return Optional.empty();
    }

    private static List<ConvictionGroup> extractSummaryLines(String summaryLines) {
        val summaryLinePattern = Pattern.compile(".*?([0-9]{1,10})\\s*(.*?)\\s*([(0-9\\-)].*)");
        return Arrays.stream(summaryLines.split("\n"))
                .map(summaryLinePattern::matcher)
                .filter(Matcher::find)
                .filter(m -> m.groupCount() == 3 )
                .map(summaryLineMatcher -> ConvictionGroup
                        .builder()
                        .count(toNumber(summaryLineMatcher.group(1)))
                        .description(summaryLineMatcher.group(2))
                        .yearDates(summaryLineMatcher.group(3))
                        .build())
                .collect(toList());
    }

    private static Matcher forExpression(String expression, String text) {
        val pattern = Pattern.compile(expression, Pattern.MULTILINE);
        return pattern.matcher(text);
    }

    private static int toNumber(String text) {
        return NumberUtils.parseNumber(text.replace("o", "0").replace("O", "0"), Integer.class);
    }
}
