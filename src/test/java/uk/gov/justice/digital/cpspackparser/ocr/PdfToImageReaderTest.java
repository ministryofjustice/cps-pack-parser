package uk.gov.justice.digital.cpspackparser.ocr;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class PdfToImageReaderTest {
    private PdfToImageReader imageReader;

    @Before
    public void before() {
        imageReader = new PdfToImageReader();
    }

    @Test
    public void extractsTextFromPdf() throws IOException {
        try (final PDDocument document = PDDocument.load(getClass().getResourceAsStream("/imageInPdf.pdf"))) {
            assertThat(imageReader.toText(document).orElseThrow(() -> new AssertionError("Can not read PDF"))).contains("Why do we collect information?");
        }
    }
}