package eu.elixir.ega.ebi.dataedge.dto.ena.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LinkToSequence {
    private List<String> ftpLink;
    private List<String> bytesInFile;
    private List<String> md5Hash;

    public List<String> getFtpLink() {
        return ftpLink;
    }

    public void setFtpLink(String ftpLink) {
        this.ftpLink = Arrays.asList(ftpLink.split(";"));
    }

    public List<String> getBytesInFile() {
        return bytesInFile;
    }

    public void setBytesInFile(String bytesInFile) {
        this.bytesInFile = Arrays.asList(bytesInFile.split(";"));
    }

    public List<String> getMd5Hash() {
        return md5Hash;
    }

    public void setMd5Hash(String md5Hash) {
        this.md5Hash =  Arrays.asList(md5Hash.split(";"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkToSequence that = (LinkToSequence) o;
        return bytesInFile == that.bytesInFile &&
                Objects.equals(ftpLink, that.ftpLink) &&
                Objects.equals(md5Hash, that.md5Hash);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ftpLink, bytesInFile, md5Hash);
    }
}
