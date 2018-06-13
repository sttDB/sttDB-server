package sttDB.domain;

import org.springframework.data.annotation.Id;

public class Kegg implements DomainInformation {

    @Id
    private String keggId;

    /**
     * Path1 is a super group of path2 and path2 is a super group of path3.
     */
    private String path1;

    private String path2;

    private String path3;

    public Kegg(String keggId, String path1, String path2, String path3) {
        this.keggId = keggId;
        this.path1 = path1;
        this.path2 = path2;
        this.path3 = path3;
    }

    public String getKeggId() {
        return keggId;
    }

    public void setKeggId(String keggId) {
        this.keggId = keggId;
    }

    public String getPath1() {
        return path1;
    }

    public void setPath1(String path1) {
        this.path1 = path1;
    }

    public String getPath2() {
        return path2;
    }

    public void setPath2(String path2) {
        this.path2 = path2;
    }

    public String getPath3() {
        return path3;
    }

    public void setPath3(String path3) {
        this.path3 = path3;
    }
}
