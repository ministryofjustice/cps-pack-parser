package uk.gov.justice.digital.cpspackparser.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import uk.gov.justice.digital.cpspackparser.data.api.CPSPack;
import uk.gov.justice.digital.cpspackparser.ocr.PdfToImageReader;
import uk.gov.justice.digital.cpspackparser.parser.CPSPackParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class ParserService {

    private final CPSPackParser cpsPackParser;
    private final PdfToImageReader pdfToImageReader;

    public ParserService(CPSPackParser cpsPackParser, PdfToImageReader pdfToImageReader) {
        this.cpsPackParser = cpsPackParser;
        this.pdfToImageReader = pdfToImageReader;
    }

    public Optional<CPSPack> parse(InputStream cpsPackInputStream) throws IOException {
        try (val document = PDDocument.load(cpsPackInputStream)) {
            return toText(document)
                    .map(cpsPackParser::extractCPSPack)
                    .orElseGet(() -> pdfToImageReader.toText(document).flatMap(cpsPackParser::extractCPSPack));
        } catch (IOException e) {
            log.error("Unable to ready PDF", e);
            throw e;
        }
    }



    private static Matcher forExpression(String expression, String text) {
        val pattern = Pattern.compile(expression, Pattern.MULTILINE);
        return pattern.matcher(text);
    }



    private Optional<String> toText(PDDocument document)  {
        try {
            val documentText = new PDFTextStripper().getText(document);
            val matcher = forExpression("PRINT OF PNC RECORD", documentText);

            if (matcher.find()) {
                log.info("Previous convictions found as text in document");
                return Optional.of(documentText);
            }
        } catch (IOException e) {
            log.error("Unable to read text from  PDF", e);
        }

        return Optional.empty();
    }
}
