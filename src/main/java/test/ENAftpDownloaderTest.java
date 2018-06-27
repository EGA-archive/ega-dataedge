package test;

import eu.elixir.ega.ebi.dataedge.service.ena.htsget.service.internal.ENAFtpDownloader;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ENAftpDownloaderTest {
    /**
     * For running this test you need to put into root folder of project ERR1777637_1.fastq.gz
     from ftp.sra.ebi.ac.uk/vol1/fastq/ERR177/007/ERR1777637/ERR1777637_1.fastq.gz
     **/
    @Test
    public void downloadingTest1() throws IOException {
        String pathToExpectedFile = "C:\\Users\\dilsc\\Desktop\\ega-dataedge\\ERR1777637_1.fastq.gz";
        File expectedFile = new File(pathToExpectedFile);
        ENAFtpDownloader downloader = new ENAFtpDownloader();
        InputStream receivedStream = downloader.getFastqFile("ftp.sra.ebi.ac.uk/vol1/fastq/ERR177/007/ERR1777637/ERR1777637_1.fastq.gz");
        File downloadedFile = writeToFile(receivedStream);
        assertTrue(fileEquals(expectedFile,downloadedFile));
    }

    public File writeToFile(InputStream inputStream){
        OutputStream outputStream = null;
        File file = new File("C:\\Users\\dilsc\\Desktop\\ega-dataedge\\test-ERR1777637_1.fastq.gz");
        try {

            // write the inputStream to a FileOutputStream
            outputStream =
                    new FileOutputStream(file);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            System.out.println("Done!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return file;
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



    public static boolean isSame( InputStream input1,
                                  InputStream input2 ) throws IOException {
        boolean error = false;
        try {
            byte[] buffer1 = new byte[1024];
            byte[] buffer2 = new byte[1024];
            try {
                int numRead1 = 0;
                int numRead2 = 0;
                while (true) {
                    numRead1 = input1.read(buffer1);
                    numRead2 = input2.read(buffer2);
                    if (numRead1 > -1) {
                        if (numRead2 != numRead1) return false;
                        // Otherwise same number of bytes read
                        if (!Arrays.equals(buffer1, buffer2)) return false;
                        // Otherwise same bytes read, so continue ...
                    } else {
                        // Nothing more in stream 1 ...
                        return numRead2 < 0;
                    }
                }
            } finally {
                input1.close();
            }
        } catch (IOException e) {
            error = true; // this error should be thrown, even if there is an error closing stream 2
            throw e;
        } catch (RuntimeException e) {
            error = true; // this error should be thrown, even if there is an error closing stream 2
            throw e;
        } finally {
            try {
                input2.close();
            } catch (IOException e) {
                if (!error) throw e;
            }
        }
    }

}