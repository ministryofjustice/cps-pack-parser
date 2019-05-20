package uk.gov.justice.digital.cpspackparser.ocr;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.bytedeco.javacpp.lept.pixDestroy;
import static org.bytedeco.javacpp.lept.pixRead;

@Component
@Slf4j
public class PdfToImageReader {
    public Optional<String> toText(PDDocument document) {
        return initialiseTesseract()
        .map(api -> {
            try {
                return toText(api, document);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                api.End();
            }
        });

    }

    private  Optional<TessBaseAPI> initialiseTesseract() {
        val tesseract = new TessBaseAPI();
        if (tesseract.Init("tessdata", "ENG") != 0) {
            log.error("Could not initialize tesseract.");
            return Optional.empty();
        }

        return Optional.of(tesseract);
    }

    private String toText(TessBaseAPI api, PDDocument document) throws IOException {
        log.info("Going to convert PDF to image for OCR");
        val pdfRenderer = new PDFRenderer(document);
        val startPage = previousConvictionsStartPage(document);
        val endPage = optimisePageRange(document, startPage);
        return IntStream.range(startPage, endPage)
                .mapToObj(pageNumber -> toImageFile(pdfRenderer, pageNumber))
                .map(fileName -> toText(api, fileName))
                .collect(Collectors.joining());
    }

    private int optimisePageRange(PDDocument document, int startPage) {
        // if we have found pre-cons lets just grab the first 3 pages for now to speed the process up a bit
        return startPage > 0 ? Math.min(startPage + 3, document.getNumberOfPages()) : document.getNumberOfPages();
    }

    private int previousConvictionsStartPage(PDDocument document) throws IOException {
        val pattern = Pattern.compile("Pre Cons", Pattern.MULTILINE);
        val textStripper = new PDFTextStripper();

        val pageNumber =  IntStream.range(0, document.getNumberOfPages()).filter(page -> {
            textStripper.setStartPage(page);
            textStripper.setEndPage(page);
            try {
                val matcher = pattern.matcher(textStripper.getText(document));
                return matcher.find();
            } catch (IOException e) {
                log.error("Unable to read text from PDF", e);
                return false;
            }

        }).findFirst().orElse(0);

        log.info(String.format("Previous convictions found on page %d", pageNumber));

        return pageNumber;
    }

    private File toImageFile(PDFRenderer pdfRenderer, int pageNumber) {
        log.info(String.format("Rendering PDF page %d", pageNumber));

        try {
            val imageFile = File.createTempFile("pdfToImage", ".png");
            val bim = pdfRenderer.renderImageWithDPI(pageNumber, 300, ImageType.RGB);
            ImageIOUtil.writeImage(bim, imageFile.getAbsolutePath(), 300);
            return imageFile;
        } catch (IOException e) {
            log.error("Unable to write a temporary image file", e);
            throw new RuntimeException(e);
        }
    }

    private String toText(TessBaseAPI api, File imageFile) {
        log.info(String.format("OCR processing for %s", imageFile.getAbsolutePath()));

        val image = pixRead(imageFile.getAbsolutePath());
        api.SetImage(image);
        val outText = api.GetUTF8Text();
        val text = outText.getString();
        outText.deallocate();
        pixDestroy(image);
        if (!imageFile.delete()) {
            log.error(String.format("Unable to delete %s", imageFile.getAbsolutePath()));
        }
        return text;
    }


}
