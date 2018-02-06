package EDU.bmrb.starlibj;

import java.util.*;

/** Thrown when an attempt is made to insert an element into
 * the vector that is not one of the types that was designated
 * with <TT>addType()</TT>
 * @see addType
 */
public class WrongElementType extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String msg;

	/** Give the vector of legal classes, and the class that
	 * was being attempted, so the error message can be
	 * descriptive.
	 * @param classes = vector of full string names for class.
	 * @param attempt = string name of the attempted class.
	 */
	public WrongElementType(Vector <? > classes, String attempt) {

		super();

		msg = "Attempt to insert object of type " + System.getProperty("line.separator") +
				"\t" + attempt + System.getProperty("line.separator") +
				" when only the following types allowed:" + System.getProperty("line.separator");

		for (int i = 0; i < classes.size(); i++)
			msg = msg + "\t" + ((String) classes.elementAt(i)) + System.getProperty("line.separator");

	}

	/** The message that will be printed
	 */
	public String getMessage() {
		return msg;
	}
}