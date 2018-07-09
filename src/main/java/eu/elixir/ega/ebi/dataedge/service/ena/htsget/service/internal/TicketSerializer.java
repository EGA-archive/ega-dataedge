package eu.elixir.ega.ebi.dataedge.service.ena.htsget.service.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import eu.elixir.ega.ebi.dataedge.dto.ena.dto.RawTicket;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class TicketSerializer extends StdSerializer<RawTicket> {

    @Value("${ega.ega.external.url}")
    protected String externalUrl;

    protected TicketSerializer() { super(RawTicket.class);}

    protected TicketSerializer(Class<RawTicket> t) {
        super(t);
    }

    protected TicketSerializer(JavaType type) {
        super(type);
    }

    protected TicketSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected TicketSerializer(StdSerializer<?> src) {
        super(src);
    }

    @Override
    public void serialize(RawTicket linkToSequence, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("htsget");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("format", linkToSequence.getFormat());
        jsonGenerator.writeArrayFieldStart("urls");

        for (String url : linkToSequence.getFtpLink()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("url", String.format("%s/sample?accesion=%s&format=%s", "localhost:8080", linkToSequence.getAccession(), linkToSequence.getFormat()));
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeStringField("md5Hash", linkToSequence.getOverallHash());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();



    }
}
