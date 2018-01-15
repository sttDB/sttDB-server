package sttDB.service.interproServices;

public class LineItems {

    public String trinityID;
    public String interproId;
    public String description;

    public LineItems(String trinityID, String interproId, String description) {
        this.trinityID = trinityID;
        this.interproId = interproId;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LineItems lineItems = (LineItems) o;

        if (trinityID != null ? !trinityID.equals(lineItems.trinityID) : lineItems.trinityID != null) return false;
        if (interproId != null ? !interproId.equals(lineItems.interproId) : lineItems.interproId != null) return false;
        return description != null ? description.equals(lineItems.description) : lineItems.description == null;
    }

    @Override
    public int hashCode() {
        int result = trinityID != null ? trinityID.hashCode() : 0;
        result = 31 * result + (interproId != null ? interproId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}