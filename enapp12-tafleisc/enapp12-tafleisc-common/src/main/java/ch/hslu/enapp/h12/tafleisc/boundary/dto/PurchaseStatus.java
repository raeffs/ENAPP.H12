package ch.hslu.enapp.h12.tafleisc.boundary.dto;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public enum PurchaseStatus {
    Created(0),
    Payed(1),
    Submitted(2);
    
    private final int index;

    private PurchaseStatus(int index) {
        this.index = index;
    }
    
    public int getIndex() {
        return index;
    }
}
