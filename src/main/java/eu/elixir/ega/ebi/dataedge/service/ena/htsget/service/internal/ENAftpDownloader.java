package eu.elixir.ega.ebi.dataedge.service.ena.htsget.service.internal;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import java.io.*;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;

@Service
public class ENAftpDownloader {

    public InputStream getFastqFile(String url) {

        String[] ftpServerPaths = url.split("/", 2);
//        FTPClient client = new FTPClient();
//        try {
//            client.connect(ftpServerPaths[0]);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            client.retr(ftpServerPaths[1]);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        InputStream inputStream = null;
//        try {
//            inputStream = client.getDataStream();
//            FTPReply r = client.size(ftpServerPaths[1]);
//            System.out.println(r.getReplyString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String server = ftpServerPaths[0];
//        String file = ftpServerPaths[1];
//        FTPClient ftpClient = new FTPClient();
//        InputStream fileStream = null;
//        try {
//            ftpClient.connect(server);
//            ftpClient.setFileType(FTP.STREAM_TRANSFER_MODE);
//            ftpClient.enterLocalPassiveMode();
//            fileStream = ftpClient.retrieveFileStream(file);
//        } catch (SocketException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return fileStream;
//    }
        url = "ftp://" + url;
        URL urlCon = null;
        try {
            urlCon = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection conn = null;
        try {
            conn = urlCon.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}
