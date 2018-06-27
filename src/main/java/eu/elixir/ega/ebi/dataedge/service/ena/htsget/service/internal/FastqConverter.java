package eu.elixir.ega.ebi.dataedge.service.ena.htsget.service.internal;

import htsjdk.samtools.*;
import htsjdk.samtools.cram.ref.CRAMReferenceSource;
import htsjdk.samtools.cram.ref.ReferenceSource;
import htsjdk.samtools.fastq.FastqReader;
import htsjdk.samtools.util.BinaryCodec;
import htsjdk.samtools.util.FastqQualityFormat;
import org.springframework.stereotype.Service;
import picard.sam.FastqToSam;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

/**
 * Class for converting FASTQ file to required format (BAM or CRAM)
 */

@Service
public class FastqConverter {
    /**
     * Converts fastq to bam
     *
     * @param readedFastqFile inputstream from fastq file
     * @param resultingStream outputstream of converted to bam file
     * @return resultingStream
     */
    public OutputStream convertToBam(@NotNull InputStream readedFastqFile, @NotNull OutputStream resultingStream) throws IOException {
        BufferedReader buf = null;
        try {
           buf = new BufferedReader(new InputStreamReader(new GZIPInputStream(readedFastqFile)));
        }catch (ZipException e){
            buf = new BufferedReader(new InputStreamReader(readedFastqFile));
        }
        FastqReader fastqReader = new FastqReader(buf);
        SAMFileWriterFactory writerFactory = new SAMFileWriterFactory();
        SAMFileWriter samFileWriter = writerFactory.makeBAMWriter(new SAMFileHeader(), false, resultingStream);
        FastqToSam converter = new FastqToSam();
        converter.QUALITY_FORMAT = FastqQualityFormat.Standard;
        converter.makeItSo(fastqReader, null, samFileWriter);
        return resultingStream;
    }

    /**
     * converts fastq to cram
     *
     * @param inputBam        inputstream from fastq file
     * @param resultingStream outputstream of converted to cram file
     * @return resultingStream
     */
    public OutputStream convertToCram(@NotNull InputStream inputBam, @NotNull OutputStream resultingStream) {
        //TODO solve issue wit referencesource
        CRAMReferenceSource source = ReferenceSource.getDefaultCRAMReferenceSource();
        CRAMFileWriter writer = new CRAMFileWriter(resultingStream, source, new SAMFileHeader(),
                "Cramfile");
        SamReader samReader = SamReaderFactory.makeDefault().open(SamInputResource.of(inputBam));
        SAMRecordIterator recordsIterator = samReader.iterator();
        writer.setHeader(samReader.getFileHeader());
        while (recordsIterator.hasNext()) {
            SAMRecord record = recordsIterator.next();
            writer.addAlignment(record);
        }
        return resultingStream;
    }
}
