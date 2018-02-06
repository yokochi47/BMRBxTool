package EDU.bmrb.starlibj;

/** Thrown when an attempt is made to alter the data when
 * the vector's type list has not been frozen yet with
 * <TT>freezeType()</TT>.
 * @see freezeType
 */
public class TypesNotFrozenYet extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}