package uk.gov.justice.digital.cpspackparser.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.gov.justice.digital.cpspackparser.data.api.CPSPack;
import uk.gov.justice.digital.cpspackparser.service.ParserService;

import java.io.IOException;

@RestController
@Slf4j
public class ParserController {

    private final ParserService parserService;

    @Autowired
    public ParserController(ParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping("/api/parse")
    public ResponseEntity<CPSPack> uploadFile(
            @RequestParam("file") MultipartFile uploadfile) {
        log.info(String.format("Received call to parse CPS Pack %s", uploadfile.getOriginalFilename()));

        try {
            return parserService.parse(uploadfile.getInputStream()).map(this::ok).orElseGet(this::badPdf);
        } catch (IOException e) {
            log.debug("Unable to process file", e);
            return error();
        }

    }

    private ResponseEntity<CPSPack> ok(CPSPack cpsPack) {
        return new ResponseEntity<>((cpsPack), HttpStatus.OK);
    }
    private ResponseEntity<CPSPack> badPdf() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    private ResponseEntity<CPSPack> error() {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
