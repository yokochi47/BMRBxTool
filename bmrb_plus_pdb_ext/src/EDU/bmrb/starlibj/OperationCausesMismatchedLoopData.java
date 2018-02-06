package EDU.bmrb.starlibj;

/** This is thrown when an operation would have caused the loop
 * to have data that does not match the name list.  For example,
 * if there are three tag names in the loop, then there need to be
 * exactly three values in each row.
 */
public class OperationCausesMismatchedLoopData extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}