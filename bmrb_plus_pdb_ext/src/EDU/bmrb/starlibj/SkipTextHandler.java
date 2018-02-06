package EDU.bmrb.starlibj;

import java.util.*;

public class SkipTextHandler {

	protected Vector < Object > skipTexts;
	protected Vector < Object > skipLineNums;

	/** This constructor initializes the handler to have no skipped
	 * values yet
	 */
	public SkipTextHandler() {

		skipTexts = new Vector < Object > ();
		skipLineNums = new Vector < Object > ();

	}

	public SkipTextHandler(SkipTextHandler copyMe) {

		skipTexts = new Vector < Object > (copyMe.skipTexts);
		skipLineNums = new Vector < Object > (copyMe.skipLineNums);

	}

	/** This <b>is</b> implemented in this class.  See the parent
	 * class "StarNode" for an explanation
	 */
	public void setSkipTexts(Vector <? > texts, Vector <? > ints) {

		if (texts != null) skipTexts.addAll(0, texts);

		if (ints != null) skipLineNums.addAll(0, ints);

	}

	/** for debugging */
	public void dumpSkipTexts() {

		System.err.println("::::DUMP OF SkipTextHandler::::");

		if (skipTexts == null) System.err.println("NULL");

		for (int i = 0; i < skipTexts.size(); i++) {

			int subLen = ((StringBuffer) skipTexts.elementAt(i)).length();

			if (subLen > 500) subLen = 500;

			System.err.println("index " + String.valueOf(i) + ": line " + ((Integer) skipLineNums.elementAt(i)).toString() + " : ");
			System.err.println("\t" + ((StringBuffer) skipTexts.elementAt(i)).substring(0, subLen));
			System.err.println("=====================================");

		}

	}

	/** This <b>is</b> implemented in this class.  See the parent
	 * class "StarNode" for an explanation
	 */
	public StringBuffer getSkipText(int i) {
		return (StringBuffer) skipTexts.elementAt(i);
	}

	/** This <b>is</b> implemented in this class.  See the parent
	 * class "StarNode" for an explanation
	 */
	public int getNumSkipTexts() {
		return skipTexts.size();
	}

	/** This <b>is</b> implemented in this class.  See the parent
	 * class "StarNode" for an explanation
	 */
	public int getSkipTextBetween(int line1, int line2, StringBuffer retString) {

		int retVal = -1;
		int sz = skipLineNums.size();

		for (int i = 0; i < sz; i++) {

			if (((Integer) skipLineNums.elementAt(i)).intValue() >= line1 && ((Integer) skipLineNums.elementAt(i)).intValue() <= line2) {

				retVal = ((Integer) skipLineNums.elementAt(i)).intValue();
				String tmpString = ((StringBuffer) skipTexts.elementAt(i)).toString();
				retString.replace(0, tmpString.length(), tmpString);

				break;
			}

		}

		return retVal;
	}
}