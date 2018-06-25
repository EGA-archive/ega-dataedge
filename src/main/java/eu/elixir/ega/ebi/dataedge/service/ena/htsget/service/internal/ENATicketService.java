package eu.elixir.ega.ebi.dataedge.service.ena.htsget.service.internal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsunsoft.http.HttpRequest;
import com.jsunsoft.http.HttpRequestBuilder;
import com.jsunsoft.http.ResponseDeserializer;
import com.jsunsoft.http.ResponseHandler;
import eu.elixir.ega.ebi.dataedge.dto.ena.dto.RawTicket;
import eu.elixir.ega.ebi.dataedge.service.ena.htsget.service.SequenceLinkService;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class ENATicketService implements SequenceLinkService {

    @Override
    public RawTicket getLinkToFile(String accession,String format) {
        //TODO move link to properties
        HttpRequest<String> request = HttpRequestBuilder.
                createGet(String.format("https://www.ebi.ac.uk/ena/portal/api/filereport?result=read_run&accession=%s&format=json",accession), String.class)
                .responseDeserializer(ResponseDeserializer.ignorableDeserializer())
                .build();
        ResponseHandler<String> responseHandler = request.execute();
        String jsonResponse = responseHandler.get();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fastq_ftp = node.findValue("fastq_ftp").textValue();
        String fastq_md5 = node.findValue("fastq_md5").textValue();
        String fastq_bytes = node.findValue("fastq_bytes").textValue();

        RawTicket link = new RawTicket();
        link.setAccession(accession);
        link.setFtpLink(fastq_ftp);
        link.setBytesInFile(fastq_bytes);
        link.setMd5Hash(fastq_md5);
        link.setFormat(format);
        return link;
    }
}
