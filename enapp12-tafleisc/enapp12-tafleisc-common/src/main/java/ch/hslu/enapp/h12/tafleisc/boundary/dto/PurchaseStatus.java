package ch.hslu.enapp.h12.tafleisc.boundary.dto;

/**
 *
 * @author Raphael Fleischlin <raphael.fleischlin@stud.hslu.ch>
 */
public enum PurchaseStatus {

    Created(0),
    Payed(1),
    Submitted(2),
    Accepted(3),
    Failed(9);
    private final int index;

    private PurchaseStatus(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static PurchaseStatus fromIndex(int index) {
        switch (index) {
            case 0:
                return Created;
            case 1:
                return Payed;
            case 2:
                return Submitted;
            case 3:
                return Accepted;
            case 9:
            default:
                return Failed;
        }
    }
}
