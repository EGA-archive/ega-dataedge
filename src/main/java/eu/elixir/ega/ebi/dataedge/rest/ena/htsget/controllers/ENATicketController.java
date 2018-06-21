package eu.elixir.ega.ebi.dataedge.rest.ena.htsget.controllers;

import eu.elixir.ega.ebi.dataedge.dto.ena.dto.LinkToSequence;
import eu.elixir.ega.ebi.dataedge.service.ena.htsget.service.internal.ENATicketService;
import eu.elixir.ega.ebi.dataedge.service.ena.htsget.service.internal.ENAftpDownloader;
import eu.elixir.ega.ebi.dataedge.service.ena.htsget.service.internal.FastqConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequestMapping("/ga4gh")
public class ENATicketController {

    private ENAftpDownloader downloader;
    private ENATicketService linkService;
    private FastqConverter converter;

    @Autowired
    ENATicketController(ENAftpDownloader downloader, ENATicketService linkService, FastqConverter converter){
        this.downloader = downloader;
        this.linkService = linkService;
        this.converter = converter;
    }
    @RequestMapping("/sample/{Biosample_ID}")
    public Object getTicket(@PathVariable String biosampleID,
                            @RequestParam(name = "format", required = false, defaultValue = "BAM") String format) {
        LinkToSequence link = linkService.getLinkToFile(biosampleID);
        InputStream inputStream = downloader.getFastqFile(link.getFtpLink().get(0));
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OutputStream outputStream = converter.convertToBam(reader);

        return null;
    }


}
