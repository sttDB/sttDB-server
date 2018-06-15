package sttDB.domain;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Kegg implements DomainInformation {

    @Id
    private String keggId;

    /**
     * Path1 is a super group of path2 and path2 is a super group of path3.
     */
    private List<String> path1;

    private List<String> path2;

    private List<String> path3;

    public Kegg() {

    }

    public Kegg(String keggId, List<String> path1, List<String> path2, List<String> path3) {
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

    public List<String> getPath1() {
        return path1;
    }

    public void setPath1(List<String> path1) {
        this.path1 = path1;
    }

    public void addPath1(String path) {
        path1.add(path);
    }

    public List<String> getPath2() {
        return path2;
    }

    public void setPath2(List<String> path2) {
        this.path2 = path2;
    }

    public void addPath2(String path) {
        path2.add(path);
    }

    public List<String> getPath3() {
        return path3;
    }

    public void setPath3(List<String> path3) {
        this.path3 = path3;
    }

    public void addPath3(String path) {
        path3.add(path);
    }
}
