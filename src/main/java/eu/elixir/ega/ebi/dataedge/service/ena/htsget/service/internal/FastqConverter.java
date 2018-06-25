package eu.elixir.ega.ebi.dataedge.service.ena.htsget.service.internal;

import htsjdk.samtools.*;
import htsjdk.samtools.fastq.FastqReader;
import htsjdk.samtools.fastq.FastqRecord;
import htsjdk.samtools.util.FastqQualityFormat;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import picard.sam.FastqToSam;

import java.io.*;

@Service
public class FastqConverter {

    public OutputStream convertToBam(InputStream readedFastqFile){
        BufferedReader buf = new BufferedReader(new InputStreamReader(readedFastqFile));
       // BufferedReader buf1 = new BufferedReader(new InputStreamReader(readedFastqFile));
        FastqReader fastqReader = new FastqReader(buf);
        //FastqReader read = new FastqReader(buf1);
        //FastqReader fastqReader = new FastqReader(readedFastqFile,false);
        SAMFileWriterFactory writerFactory = new SAMFileWriterFactory();
        OutputStream resultingStream = new ByteArrayOutputStream();
        SAMFileWriter samFileWriter = writerFactory.makeBAMWriter(new SAMFileHeader(),false,resultingStream);
        FastqToSam converter = new FastqToSam();
        converter.QUALITY_FORMAT= FastqQualityFormat.Standard;//FastqToSam.determineQualityFormat(fastqReader,null,null);
        converter.makeItSo(fastqReader,null,samFileWriter);
        return resultingStream;
    }

    public OutputStream convertToCram(InputStream inputBam) {
        CRAMFileReader reader = new CRAMFileReader(null,inputBam);
        //reader.
        return null;
    }
}
