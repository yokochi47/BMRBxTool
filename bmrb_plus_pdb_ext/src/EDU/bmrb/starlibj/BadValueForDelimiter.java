package EDU.bmrb.starlibj;

/** The value string is not syntactically correct for the
 * kind of delimiter in use, for example, a string with
 * whitespace cannot be without quotes, or a string with
 * multiple lines in it is only allowed as a semicolon-delimted
 * string.
 */
public class BadValueForDelimiter extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String msg;

	/** @param val The string value that violated the syntax
	 * @param delim integer for the type of value that was being
	 * attempted.
	 */
	public BadValueForDelimiter(String val, short delim) {

		super();

		msg = "The value '" + val + "' is not a valid ";

		if (delim == DataValueNode.NON) msg = msg + "Non-delimited value.";
		else if (delim == DataValueNode.DOUBLE) msg = msg + "doublequote-delimited value.";
		else if (delim == DataValueNode.SINGLE) msg = msg + "singlequote-delimited value.";
		else if (delim == DataValueNode.FRAMECODE) msg = msg + "framcode value.";
		else if (delim == DataValueNode.SEMICOLON) msg = msg + "semicolon-delimited value.";
		else msg = msg + "<unspecified type of value>";

	}

	public String getMessage() {
		return msg;
	}
}