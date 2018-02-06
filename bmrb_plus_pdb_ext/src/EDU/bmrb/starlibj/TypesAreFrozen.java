package EDU.bmrb.starlibj;

/** Thrown when an attempt is made to alter the list of
 * allowed types after the type list was frozen with
 * <TT>freezeType()</TT>
 * @see freezeType
 */
public class TypesAreFrozen extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}