
/**
 * Cell object
 * @author KhoiLQCE130023
 */
public class Cell {

    // constants
    private static final int SAFE_NUMBER = 0;
    private static final int MINE_NUMBER = 9;

    // variables
    private int status;
    private boolean uncovered;

    // constructors    
    public Cell() {
        status = SAFE_NUMBER;
        uncovered = false;
    }

    public Cell(int num) {
        status = num;
        uncovered = false;
    }

    // method get number in cell
    public int getStatus() {
        return status;
    }

    // method set number in cell
    public void setStatus(int newStatus) {
        status = newStatus;
    }

    // add 1 to number in cell
    public void addToStatus() {
        status = status + 1;
    }
    
    // method if uncover
    public void uncover() {
        this.uncovered = true;
    }

    public boolean isUncovered() {
        return uncovered;
    }

    // if number in cell = 0 return true
    public boolean isSafe() {
        if (status == SAFE_NUMBER) {
            return true;
        }
        return false;
    }

    // if number in cell >= 9 return true
    public boolean isMine() {
        if (status >= MINE_NUMBER) {
            return true;
        }
        return false;
    }
}
