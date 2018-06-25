package eu.elixir.ega.ebi.dataedge.rest.ena.htsget.controllers;

import eu.elixir.ega.ebi.dataedge.dto.ena.dto.RawTicket;
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

    private ENATicketService linkService;

    @Autowired
    ENATicketController(ENATicketService linkService){
        this.linkService = linkService;
    }

    @RequestMapping("sample/{Biosample_ID}")
    public RawTicket getTicket(@PathVariable String biosampleID,
                               @RequestParam(name = "format", required = false, defaultValue = "BAM") String format) {
        return linkService.getLinkToFile(biosampleID,format);
    }

}
