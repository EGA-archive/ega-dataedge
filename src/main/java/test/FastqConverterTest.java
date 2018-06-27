package test;

import eu.elixir.ega.ebi.dataedge.service.ena.htsget.service.internal.FastqConverter;
import org.junit.jupiter.api.Test;

import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.GZIPInputStream;

import static org.junit.jupiter.api.Assertions.*;

class FastqConverterTest {

    @Test
    public void converterTest() throws IOException {
        FastqConverter converter = new FastqConverter();
        File file1 = new File("C:\\Users\\dilsc\\Desktop\\ega-dataedge\\Test100k.fastq.gz");
        File expectedFile = new File("C:\\Users\\dilsc\\Desktop\\ega-dataedge\\test100k_expected.bam");
        FileInputStream fileIn = new FileInputStream(file1);
       // GZIPInputStream stream = new GZIPInputStream(fileIn);
        FileOutputStream bamStream = new FileOutputStream("C:\\Users\\dilsc\\Desktop\\ega-dataedge\\test100k.bam");
        converter.convertToBam(fileIn,bamStream);
        File file2 = new File("C:\\Users\\dilsc\\Desktop\\ega-dataedge\\test100k.bam");
        System.out.println(file2.length());

        assertTrue(fileEquals(expectedFile,file2));
    }

    @Test
    public void cramConverterTest1() throws IOException {
        FastqConverter converter = new FastqConverter();
        File file1 = new File("C:\\Users\\dilsc\\Desktop\\ega-dataedge\\test100k_expected.bam");
        File expectedFile = new File("C:\\Users\\dilsc\\Desktop\\ega-dataedge\\test100k.cram");
        FileInputStream fileIn = new FileInputStream(file1);
        FileOutputStream fos = new FileOutputStream("C:\\Users\\dilsc\\Desktop\\ega-dataedge\\test100k_test.cram");
        converter.convertToCram(fileIn,fos);
        fos.flush();
        fos.close();
        File file2 = new File("C:\\Users\\dilsc\\Desktop\\ega-dataedge\\test100k_test.cram");
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