package test;

import eu.elixir.ega.ebi.dataedge.service.ena.htsget.service.internal.FastqConverter;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.zip.GZIPInputStream;

import static org.junit.jupiter.api.Assertions.*;

class FastqConverterTest {

    @Test
    public void converterTest() throws IOException {
        FastqConverter converter = new FastqConverter();
        File file1 = new File("C:\\Users\\dilsc\\Desktop\\ega-dataedge\\Test100k.fastq.gz");
        File expectedFile = new File("C:\\Users\\dilsc\\Desktop\\ega-dataedge\\test100k_bam.bam");
        FileInputStream fileIn = new FileInputStream(file1);
        InputStream stream = new GZIPInputStream(fileIn);
        ByteArrayOutputStream bamStream = (ByteArrayOutputStream) converter.convertToBam(stream);
        FileOutputStream fos = new FileOutputStream("C:\\Users\\dilsc\\Desktop\\ega-dataedge\\test100k.bam");
        fos.write(bamStream.toByteArray());
        File file2 = new File("C:\\Users\\dilsc\\Desktop\\ega-dataedge\\test100k.bam");
        assertTrue(fileEquals(expectedFile,file2));
    }

    public boolean fileEquals(File file1, File file2) throws IOException {
        boolean areFilesIdentical = true;
        FileInputStream fis1 = new FileInputStream(file1);
        FileInputStream fis2 = new FileInputStream(file2);
        int i1 = fis1.read();
        int i2 = fis2.read();
        while (i1 != -1) {
            if (i1 != i2) {
                areFilesIdentical = false;
                break;
            }
            i1 = fis1.read();
            i2 = fis2.read();
        }
        fis1.close();
        fis2.close();
        return areFilesIdentical;
    }
}