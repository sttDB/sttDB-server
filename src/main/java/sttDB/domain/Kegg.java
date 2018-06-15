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

    /**
     * This attribute will replicate the three paths in one variable.
     * The reason behind this is to provide a good search when searching by descriptions.
     */
    private String description;

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
        setDescription();
    }

    public void addPath1(String path) {
        path1.add(path);
        setDescription();
    }

    public List<String> getPath2() {
        return path2;
    }

    public void setPath2(List<String> path2) {
        this.path2 = path2;
        setDescription();
    }

    public void addPath2(String path) {
        path2.add(path);
        setDescription();
    }

    public List<String> getPath3() {
        return path3;
    }

    public void setPath3(List<String> path3) {
        this.path3 = path3;
        setDescription();
    }

    public void addPath3(String path) {
        path3.add(path);
        setDescription();
    }

    public String getDescription() {
        return description;
    }

    private void setDescription() {
        if(path1 != null && path2 != null && path3 != null)
            description = path1.toString() + path2.toString() + path3.toString();
    }
}
