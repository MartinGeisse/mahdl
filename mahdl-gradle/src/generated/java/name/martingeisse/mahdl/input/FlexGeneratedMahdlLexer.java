/* The following code was generated by JFlex 1.7.0 tweaked for IntelliJ platform */

package name.martingeisse.mahdl.input;

import name.martingeisse.mahdl.input.cm.impl.IElementType;

/**
 * This class is a scanner generated by
 * <a href="http://www.jflex.de/">JFlex</a> 1.7.0
 * from the specification file <tt>mahdl.flex</tt>
 */
public class FlexGeneratedMahdlLexer {

	/**
	 * This character denotes the end of file
	 */
	public static final int YYEOF = -1;

	/**
	 * initial size of the lookahead buffer
	 */
	private static final int ZZ_BUFFERSIZE = 16384;

	/**
	 * lexical states
	 */
	public static final int YYINITIAL = 0;

	/**
	 * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
	 * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
	 * at the beginning of a line
	 * l is of the form l = 2*k, k a non negative integer
	 */
	private static final int ZZ_LEXSTATE[] = {
		0, 0
	};

	/**
	 * Translates characters to character classes
	 * Chosen bits are [8, 6, 7]
	 * Total runtime size is 1040 bytes
	 */
	public static int ZZ_CMAP(int ch) {
		return ZZ_CMAP_A[ZZ_CMAP_Y[ZZ_CMAP_Z[ch >> 13] | ((ch >> 7) & 0x3f)] | (ch & 0x7f)];
	}

	/* The ZZ_CMAP_Z table has 136 entries */
	static final char ZZ_CMAP_Z[] = zzUnpackCMap(
		"\1\0\207\100");

	/* The ZZ_CMAP_Y table has 128 entries */
	static final char ZZ_CMAP_Y[] = zzUnpackCMap(
		"\1\0\177\200");

	/* The ZZ_CMAP_A table has 256 entries */
	static final char ZZ_CMAP_A[] = zzUnpackCMap(
		"\11\0\1\1\1\4\1\0\1\1\1\4\22\0\1\1\1\33\3\0\1\32\1\23\1\0\1\7\1\10\1\3\1\30" +
			"\1\20\1\31\1\15\1\2\1\6\1\63\6\64\2\5\1\16\1\17\1\26\1\21\1\27\1\34\1\0\6" +
			"\65\24\66\1\11\1\0\1\12\1\25\1\35\1\0\1\37\1\56\1\53\1\46\1\43\1\52\1\55\1" +
			"\62\1\41\1\66\1\60\1\50\1\44\1\36\1\45\2\66\1\51\1\54\1\40\1\47\1\42\1\61" +
			"\1\57\2\66\1\13\1\24\1\14\1\22\201\0");

	/**
	 * Translates DFA states to action switch labels.
	 */
	private static final int[] ZZ_ACTION = zzUnpackAction();

	private static final String ZZ_ACTION_PACKED_0 =
		"\1\0\1\1\1\2\1\3\1\4\2\5\1\6\1\7" +
			"\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17" +
			"\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27" +
			"\1\30\1\31\1\1\1\32\1\33\15\34\1\35\1\36" +
			"\4\0\1\37\1\40\1\41\1\42\1\43\1\44\2\34" +
			"\1\45\1\46\6\34\1\47\7\34\1\36\4\50\7\34" +
			"\1\51\7\34\1\52\1\36\1\34\1\53\2\34\1\54" +
			"\4\34\1\55\15\34\1\56\2\34\1\57\2\34\1\60" +
			"\1\61\1\62\3\34\1\63\1\64\1\34\1\65\1\66" +
			"\3\34\1\67\1\70\1\71";

	private static int[] zzUnpackAction() {
		int[] result = new int[141];
		int offset = 0;
		offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackAction(String packed, int offset, int[] result) {
		int i = 0;       /* index in packed string  */
		int j = offset;  /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			do result[j++] = value; while (--count > 0);
		}
		return j;
	}

	/**
	 * Translates a state to a row index in the transition table
	 */
	private static final int[] ZZ_ROWMAP = zzUnpackRowMap();

	private static final String ZZ_ROWMAP_PACKED_0 =
		"\0\0\0\67\0\67\0\156\0\67\0\245\0\67\0\67" +
			"\0\67\0\67\0\67\0\67\0\67\0\67\0\67\0\67" +
			"\0\67\0\334\0\67\0\67\0\67\0\67\0\u0113\0\u014a" +
			"\0\67\0\67\0\67\0\u0181\0\67\0\u01b8\0\u01ef\0\u01b8" +
			"\0\u0226\0\u025d\0\u0294\0\u02cb\0\u0302\0\u0339\0\u0370\0\u03a7" +
			"\0\u03de\0\u0415\0\u044c\0\u0483\0\u04ba\0\u04f1\0\u0528\0\u055f" +
			"\0\u0596\0\67\0\67\0\67\0\67\0\67\0\67\0\u05cd" +
			"\0\u0604\0\u063b\0\u01b8\0\u0672\0\u06a9\0\u06e0\0\u0717\0\u074e" +
			"\0\u0785\0\u01b8\0\u07bc\0\u07f3\0\u082a\0\u0861\0\u0898\0\u08cf" +
			"\0\u0906\0\u093d\0\u04f1\0\u0528\0\u055f\0\u0596\0\u0974\0\u09ab" +
			"\0\u09e2\0\u0a19\0\u0a50\0\u0a87\0\u0abe\0\u01b8\0\u0af5\0\u0b2c" +
			"\0\u0b63\0\u0b9a\0\u0bd1\0\u0c08\0\u0c3f\0\u01b8\0\67\0\u0c76" +
			"\0\u01b8\0\u0cad\0\u0ce4\0\u01b8\0\u0d1b\0\u0d52\0\u0d89\0\u0dc0" +
			"\0\u01b8\0\u0df7\0\u0e2e\0\u0e65\0\u0e9c\0\u0ed3\0\u0f0a\0\u0f41" +
			"\0\u0f78\0\u0faf\0\u0fe6\0\u101d\0\u1054\0\u108b\0\u01b8\0\u10c2" +
			"\0\u10f9\0\u01b8\0\u1130\0\u1167\0\u01b8\0\u01b8\0\u01b8\0\u119e" +
			"\0\u11d5\0\u120c\0\u01b8\0\u01b8\0\u1243\0\u01b8\0\u01b8\0\u127a" +
			"\0\u12b1\0\u12e8\0\u01b8\0\u01b8\0\u01b8";

	private static int[] zzUnpackRowMap() {
		int[] result = new int[141];
		int offset = 0;
		offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackRowMap(String packed, int offset, int[] result) {
		int i = 0;  /* index in packed string  */
		int j = offset;  /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int high = packed.charAt(i++) << 16;
			result[j++] = high | packed.charAt(i++);
		}
		return j;
	}

	/**
	 * The transition table of the DFA
	 */
	private static final int[] ZZ_TRANS = zzUnpackTrans();

	private static final String ZZ_TRANS_PACKED_0 =
		"\1\2\1\3\1\4\1\5\1\3\1\6\1\7\1\10" +
			"\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20" +
			"\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30" +
			"\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40" +
			"\1\41\1\42\1\43\1\44\1\45\1\46\1\47\2\40" +
			"\1\50\1\40\1\51\1\52\1\40\1\53\4\40\2\6" +
			"\2\40\71\0\1\54\1\55\70\0\2\6\36\0\1\56" +
			"\1\57\7\0\1\60\3\0\1\61\2\6\23\0\1\62" +
			"\66\0\1\63\4\0\1\64\61\0\1\65\5\0\1\66" +
			"\60\0\1\67\52\0\2\40\26\0\32\40\5\0\2\40" +
			"\26\0\2\40\1\70\27\40\5\0\2\40\26\0\6\40" +
			"\1\71\23\40\5\0\2\40\26\0\1\40\1\72\13\40" +
			"\1\73\14\40\5\0\2\40\26\0\6\40\1\74\23\40" +
			"\5\0\2\40\26\0\13\40\1\75\16\40\5\0\2\40" +
			"\26\0\2\40\1\76\5\40\1\77\21\40\5\0\2\40" +
			"\26\0\12\40\1\100\17\40\5\0\2\40\26\0\6\40" +
			"\1\101\1\40\1\102\21\40\5\0\2\40\26\0\6\40" +
			"\1\103\23\40\5\0\2\40\26\0\2\40\1\104\5\40" +
			"\1\105\2\40\1\106\16\40\5\0\2\40\26\0\4\40" +
			"\1\107\17\40\1\110\5\40\5\0\2\40\26\0\4\40" +
			"\1\111\25\40\4\54\1\0\62\54\3\55\1\112\63\55" +
			"\6\0\1\113\54\0\2\113\7\0\2\114\54\0\2\114" +
			"\10\0\1\115\54\0\1\115\10\0\2\116\30\0\1\116" +
			"\3\0\1\116\2\0\1\116\3\0\2\116\2\0\1\116" +
			"\4\0\3\116\6\0\2\40\26\0\3\40\1\117\26\40" +
			"\5\0\2\40\26\0\22\40\1\120\7\40\5\0\2\40" +
			"\26\0\3\40\1\121\26\40\5\0\2\40\26\0\16\40" +
			"\1\122\13\40\5\0\2\40\26\0\17\40\1\123\12\40" +
			"\5\0\2\40\26\0\3\40\1\124\26\40\5\0\2\40" +
			"\26\0\11\40\1\125\20\40\5\0\2\40\26\0\3\40" +
			"\1\126\26\40\5\0\2\40\26\0\15\40\1\127\14\40" +
			"\5\0\2\40\26\0\20\40\1\130\11\40\5\0\2\40" +
			"\26\0\17\40\1\131\12\40\5\0\2\40\26\0\1\40" +
			"\1\132\30\40\5\0\2\40\26\0\10\40\1\133\21\40" +
			"\5\0\2\40\26\0\20\40\1\134\11\40\5\0\2\40" +
			"\26\0\4\40\1\135\25\40\5\0\2\40\26\0\3\40" +
			"\1\136\26\40\2\55\1\137\1\112\63\55\5\0\2\40" +
			"\26\0\4\40\1\140\25\40\5\0\2\40\26\0\3\40" +
			"\1\141\26\40\5\0\2\40\26\0\6\40\1\142\23\40" +
			"\5\0\2\40\26\0\3\40\1\143\26\40\5\0\2\40" +
			"\26\0\6\40\1\144\23\40\5\0\2\40\26\0\14\40" +
			"\1\145\15\40\5\0\2\40\26\0\12\40\1\146\17\40" +
			"\5\0\2\40\26\0\2\40\1\147\27\40\5\0\2\40" +
			"\26\0\4\40\1\150\25\40\5\0\2\40\26\0\6\40" +
			"\1\151\23\40\5\0\2\40\26\0\17\40\1\152\12\40" +
			"\5\0\2\40\26\0\16\40\1\153\13\40\5\0\2\40" +
			"\26\0\1\40\1\154\30\40\5\0\2\40\26\0\3\40" +
			"\1\155\26\40\5\0\2\40\26\0\5\40\1\156\24\40" +
			"\5\0\2\40\26\0\14\40\1\157\3\40\1\160\11\40" +
			"\5\0\2\40\26\0\10\40\1\161\21\40\5\0\2\40" +
			"\26\0\4\40\1\162\25\40\5\0\2\40\26\0\13\40" +
			"\1\163\16\40\5\0\2\40\26\0\12\40\1\164\17\40" +
			"\5\0\2\40\26\0\17\40\1\165\12\40\5\0\2\40" +
			"\26\0\3\40\1\166\26\40\5\0\2\40\26\0\23\40" +
			"\1\167\6\40\5\0\2\40\26\0\2\40\1\170\27\40" +
			"\5\0\2\40\26\0\16\40\1\171\13\40\5\0\2\40" +
			"\26\0\6\40\1\172\23\40\5\0\2\40\26\0\15\40" +
			"\1\173\14\40\5\0\2\40\26\0\6\40\1\174\23\40" +
			"\5\0\2\40\26\0\14\40\1\175\15\40\5\0\2\40" +
			"\26\0\22\40\1\176\7\40\5\0\2\40\26\0\6\40" +
			"\1\177\23\40\5\0\2\40\26\0\13\40\1\200\16\40" +
			"\5\0\2\40\26\0\3\40\1\201\26\40\5\0\2\40" +
			"\26\0\2\40\1\202\27\40\5\0\2\40\26\0\13\40" +
			"\1\203\16\40\5\0\2\40\26\0\25\40\1\204\4\40" +
			"\5\0\2\40\26\0\2\40\1\205\27\40\5\0\2\40" +
			"\26\0\14\40\1\206\15\40\5\0\2\40\26\0\3\40" +
			"\1\207\26\40\5\0\2\40\26\0\6\40\1\210\23\40" +
			"\5\0\2\40\26\0\1\40\1\211\30\40\5\0\2\40" +
			"\26\0\16\40\1\212\13\40\5\0\2\40\26\0\14\40" +
			"\1\213\15\40\5\0\2\40\26\0\3\40\1\214\26\40" +
			"\5\0\2\40\26\0\6\40\1\215\23\40";

	private static int[] zzUnpackTrans() {
		int[] result = new int[4895];
		int offset = 0;
		offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackTrans(String packed, int offset, int[] result) {
		int i = 0;       /* index in packed string  */
		int j = offset;  /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			value--;
			do result[j++] = value; while (--count > 0);
		}
		return j;
	}

	/* error codes */
	private static final int ZZ_UNKNOWN_ERROR = 0;
	private static final int ZZ_NO_MATCH = 1;
	private static final int ZZ_PUSHBACK_2BIG = 2;

	/* error messages for the codes above */
	private static final String[] ZZ_ERROR_MSG = {
		"Unknown internal scanner error",
		"Error: could not match input",
		"Error: pushback value was too large"
	};

	/**
	 * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
	 */
	private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();

	private static final String ZZ_ATTRIBUTE_PACKED_0 =
		"\1\0\2\11\1\1\1\11\1\1\13\11\1\1\4\11" +
			"\2\1\3\11\1\1\1\11\20\1\4\0\6\11\47\1" +
			"\1\11\56\1";

	private static int[] zzUnpackAttribute() {
		int[] result = new int[141];
		int offset = 0;
		offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackAttribute(String packed, int offset, int[] result) {
		int i = 0;       /* index in packed string  */
		int j = offset;  /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			do result[j++] = value; while (--count > 0);
		}
		return j;
	}

	/**
	 * the input device
	 */
	private java.io.Reader zzReader;

	/**
	 * the current state of the DFA
	 */
	private int zzState;

	/**
	 * the current lexical state
	 */
	private int zzLexicalState = YYINITIAL;

	/**
	 * this buffer contains the current text to be matched and is
	 * the source of the yytext() string
	 */
	private CharSequence zzBuffer = "";

	/**
	 * the textposition at the last accepting state
	 */
	private int zzMarkedPos;

	/**
	 * the current text position in the buffer
	 */
	private int zzCurrentPos;

	/**
	 * startRead marks the beginning of the yytext() string in the buffer
	 */
	private int zzStartRead;

	/**
	 * endRead marks the last character in the buffer, that has been read
	 * from input
	 */
	private int zzEndRead;

	/**
	 * zzAtBOL == true <=> the scanner is currently at the beginning of a line
	 */
	private boolean zzAtBOL = true;

	/**
	 * zzAtEOF == true <=> the scanner is at the EOF
	 */
	private boolean zzAtEOF;

	/**
	 * denotes if the user-EOF-code has already been executed
	 */
	private boolean zzEOFDone;

	/* user code: */
	int yyline, yycolumn;

	/**
	 * Creates a new scanner
	 *
	 * @param in the java.io.Reader to read input from.
	 */
	public FlexGeneratedMahdlLexer(java.io.Reader in) {
		this.zzReader = in;
	}

	/**
	 * Unpacks the compressed character translation table.
	 *
	 * @param packed the packed character translation table
	 * @return the unpacked character translation table
	 */
	private static char[] zzUnpackCMap(String packed) {
		int size = 0;
		for (int i = 0, length = packed.length(); i < length; i += 2) {
			size += packed.charAt(i);
		}
		char[] map = new char[size];
		int i = 0;  /* index in packed string  */
		int j = 0;  /* index in unpacked array */
		while (i < packed.length()) {
			int count = packed.charAt(i++);
			char value = packed.charAt(i++);
			do map[j++] = value; while (--count > 0);
		}
		return map;
	}

	public final int getTokenStart() {
		return zzStartRead;
	}

	public final int getTokenEnd() {
		return getTokenStart() + yylength();
	}

	public void reset(CharSequence buffer, int start, int end, int initialState) {
		zzBuffer = buffer;
		zzCurrentPos = zzMarkedPos = zzStartRead = start;
		zzAtEOF = false;
		zzAtBOL = true;
		zzEndRead = end;
		yybegin(initialState);
	}

	/**
	 * Refills the input buffer.
	 *
	 * @return <code>false</code>, iff there was new input.
	 * @throws java.io.IOException if any I/O-Error occurs
	 */
	private boolean zzRefill() throws java.io.IOException {
		return true;
	}

	/**
	 * Returns the current lexical state.
	 */
	public final int yystate() {
		return zzLexicalState;
	}

	/**
	 * Enters a new lexical state
	 *
	 * @param newState the new lexical state
	 */
	public final void yybegin(int newState) {
		zzLexicalState = newState;
	}

	/**
	 * Returns the text matched by the current regular expression.
	 */
	public final CharSequence yytext() {
		return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
	}

	/**
	 * Returns the character at position <tt>pos</tt> from the
	 * matched text.
	 * <p>
	 * It is equivalent to yytext().charAt(pos), but faster
	 *
	 * @param pos the position of the character to fetch.
	 *            A value from 0 to yylength()-1.
	 * @return the character at position pos
	 */
	public final char yycharat(int pos) {
		return zzBuffer.charAt(zzStartRead + pos);
	}

	/**
	 * Returns the length of the matched text region.
	 */
	public final int yylength() {
		return zzMarkedPos - zzStartRead;
	}

	/**
	 * Reports an error that occured while scanning.
	 * <p>
	 * In a wellformed scanner (no or only correct usage of
	 * yypushback(int) and a match-all fallback rule) this method
	 * will only be called with things that "Can't Possibly Happen".
	 * If this method is called, something is seriously wrong
	 * (e.g. a JFlex bug producing a faulty scanner etc.).
	 * <p>
	 * Usual syntax/scanner level error handling should be done
	 * in error fallback rules.
	 *
	 * @param errorCode the code of the errormessage to display
	 */
	private void zzScanError(int errorCode) {
		String message;
		try {
			message = ZZ_ERROR_MSG[errorCode];
		} catch (ArrayIndexOutOfBoundsException e) {
			message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
		}

		throw new Error(message);
	}

	/**
	 * Pushes the specified amount of characters back into the input stream.
	 * <p>
	 * They will be read again by then next call of the scanning method
	 *
	 * @param number the number of characters to be read again.
	 *               This number must not be greater than yylength()!
	 */
	public void yypushback(int number) {
		if (number > yylength())
			zzScanError(ZZ_PUSHBACK_2BIG);

		zzMarkedPos -= number;
	}

	/**
	 * Contains user EOF-code, which will be executed exactly once,
	 * when the end of file is reached
	 */
	private void zzDoEOF() {
		if (!zzEOFDone) {
			zzEOFDone = true;

		}
	}

	/**
	 * Resumes scanning until the next regular expression is matched,
	 * the end of input is encountered or an I/O-Error occurs.
	 *
	 * @return the next token
	 * @throws java.io.IOException if any I/O-Error occurs
	 */
	public IElementType advance() throws java.io.IOException {
		int zzInput;
		int zzAction;

		// cached fields:
		int zzCurrentPosL;
		int zzMarkedPosL;
		int zzEndReadL = zzEndRead;
		CharSequence zzBufferL = zzBuffer;

		int[] zzTransL = ZZ_TRANS;
		int[] zzRowMapL = ZZ_ROWMAP;
		int[] zzAttrL = ZZ_ATTRIBUTE;

		while (true) {
			zzMarkedPosL = zzMarkedPos;

			boolean zzR = false;
			int zzCh;
			int zzCharCount;
			for (zzCurrentPosL = zzStartRead;
				 zzCurrentPosL < zzMarkedPosL;
				 zzCurrentPosL += zzCharCount) {
				zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzMarkedPosL*/);
				zzCharCount = Character.charCount(zzCh);
				switch (zzCh) {
					case '\u000B':  // fall through
					case '\u000C':  // fall through
					case '\u0085':  // fall through
					case '\u2028':  // fall through
					case '\u2029':
						yyline++;
						yycolumn = 0;
						zzR = false;
						break;
					case '\r':
						yyline++;
						yycolumn = 0;
						zzR = true;
						break;
					case '\n':
						if (zzR)
							zzR = false;
						else {
							yyline++;
							yycolumn = 0;
						}
						break;
					default:
						zzR = false;
						yycolumn += zzCharCount;
				}
			}

			if (zzR) {
				// peek one character ahead if it is \n (if we have counted one line too much)
				boolean zzPeek;
				if (zzMarkedPosL < zzEndReadL)
					zzPeek = zzBufferL.charAt(zzMarkedPosL) == '\n';
				else if (zzAtEOF)
					zzPeek = false;
				else {
					boolean eof = zzRefill();
					zzEndReadL = zzEndRead;
					zzMarkedPosL = zzMarkedPos;
					zzBufferL = zzBuffer;
					if (eof)
						zzPeek = false;
					else
						zzPeek = zzBufferL.charAt(zzMarkedPosL) == '\n';
				}
				if (zzPeek) yyline--;
			}
			zzAction = -1;

			zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

			zzState = ZZ_LEXSTATE[zzLexicalState];

			// set up zzAction for empty match case:
			int zzAttributes = zzAttrL[zzState];
			if ((zzAttributes & 1) == 1) {
				zzAction = zzState;
			}

			zzForAction:
			{
				while (true) {

					if (zzCurrentPosL < zzEndReadL) {
						zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
						zzCurrentPosL += Character.charCount(zzInput);
					} else if (zzAtEOF) {
						zzInput = YYEOF;
						break zzForAction;
					} else {
						// store back cached positions
						zzCurrentPos = zzCurrentPosL;
						zzMarkedPos = zzMarkedPosL;
						boolean eof = zzRefill();
						// get translated positions and possibly new buffer
						zzCurrentPosL = zzCurrentPos;
						zzMarkedPosL = zzMarkedPos;
						zzBufferL = zzBuffer;
						zzEndReadL = zzEndRead;
						if (eof) {
							zzInput = YYEOF;
							break zzForAction;
						} else {
							zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
							zzCurrentPosL += Character.charCount(zzInput);
						}
					}
					int zzNext = zzTransL[zzRowMapL[zzState] + ZZ_CMAP(zzInput)];
					if (zzNext == -1) break zzForAction;
					zzState = zzNext;

					zzAttributes = zzAttrL[zzState];
					if ((zzAttributes & 1) == 1) {
						zzAction = zzState;
						zzMarkedPosL = zzCurrentPosL;
						if ((zzAttributes & 8) == 8) break zzForAction;
					}

				}
			}

			// store back cached position
			zzMarkedPos = zzMarkedPosL;

			if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
				zzAtEOF = true;
				zzDoEOF();
				return null;
			} else {
				switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
					case 1: {
						return IElementType.BAD_CHARACTER;
					}
					// fall through
					case 58:
						break;
					case 2: {
						return IElementType.WHITE_SPACE;
					}
					// fall through
					case 59:
						break;
					case 3: {
						return Symbols.OP_DIVIDED_BY;
					}
					// fall through
					case 60:
						break;
					case 4: {
						return Symbols.OP_TIMES;
					}
					// fall through
					case 61:
						break;
					case 5: {
						return Symbols.INTEGER_LITERAL;
					}
					// fall through
					case 62:
						break;
					case 6: {
						return Symbols.OPENING_PARENTHESIS;
					}
					// fall through
					case 63:
						break;
					case 7: {
						return Symbols.CLOSING_PARENTHESIS;
					}
					// fall through
					case 64:
						break;
					case 8: {
						return Symbols.OPENING_SQUARE_BRACKET;
					}
					// fall through
					case 65:
						break;
					case 9: {
						return Symbols.CLOSING_SQUARE_BRACKET;
					}
					// fall through
					case 66:
						break;
					case 10: {
						return Symbols.OPENING_CURLY_BRACE;
					}
					// fall through
					case 67:
						break;
					case 11: {
						return Symbols.CLOSING_CURLY_BRACE;
					}
					// fall through
					case 68:
						break;
					case 12: {
						return Symbols.DOT;
					}
					// fall through
					case 69:
						break;
					case 13: {
						return Symbols.COLON;
					}
					// fall through
					case 70:
						break;
					case 14: {
						return Symbols.SEMICOLON;
					}
					// fall through
					case 71:
						break;
					case 15: {
						return Symbols.COMMA;
					}
					// fall through
					case 72:
						break;
					case 16: {
						return Symbols.EQUALS;
					}
					// fall through
					case 73:
						break;
					case 17: {
						return Symbols.OP_NOT;
					}
					// fall through
					case 74:
						break;
					case 18: {
						return Symbols.OP_AND;
					}
					// fall through
					case 75:
						break;
					case 19: {
						return Symbols.OP_OR;
					}
					// fall through
					case 76:
						break;
					case 20: {
						return Symbols.OP_XOR;
					}
					// fall through
					case 77:
						break;
					case 21: {
						return Symbols.OP_LESS_THAN;
					}
					// fall through
					case 78:
						break;
					case 22: {
						return Symbols.OP_GREATER_THAN;
					}
					// fall through
					case 79:
						break;
					case 23: {
						return Symbols.OP_PLUS;
					}
					// fall through
					case 80:
						break;
					case 24: {
						return Symbols.OP_MINUS;
					}
					// fall through
					case 81:
						break;
					case 25: {
						return Symbols.OP_REMAINDER;
					}
					// fall through
					case 82:
						break;
					case 26: {
						return Symbols.OP_CONDITIONAL;
					}
					// fall through
					case 83:
						break;
					case 27: {
						return Symbols.OP_CONCAT;
					}
					// fall through
					case 84:
						break;
					case 28: {
						return Symbols.IDENTIFIER;
					}
					// fall through
					case 85:
						break;
					case 29: {
						return Symbols.LINE_COMMENT;
					}
					// fall through
					case 86:
						break;
					case 30: {
						return Symbols.BLOCK_COMMENT;
					}
					// fall through
					case 87:
						break;
					case 31: {
						return Symbols.OP_EQUAL;
					}
					// fall through
					case 88:
						break;
					case 32: {
						return Symbols.OP_LESS_THAN_OR_EQUAL;
					}
					// fall through
					case 89:
						break;
					case 33: {
						return Symbols.OP_SHIFT_LEFT;
					}
					// fall through
					case 90:
						break;
					case 34: {
						return Symbols.OP_GREATER_THAN_OR_EQUAL;
					}
					// fall through
					case 91:
						break;
					case 35: {
						return Symbols.OP_SHIFT_RIGHT;
					}
					// fall through
					case 92:
						break;
					case 36: {
						return Symbols.OP_NOT_EQUAL;
					}
					// fall through
					case 93:
						break;
					case 37: {
						return Symbols.KW_IN;
					}
					// fall through
					case 94:
						break;
					case 38: {
						return Symbols.KW_IF;
					}
					// fall through
					case 95:
						break;
					case 39: {
						return Symbols.KW_DO;
					}
					// fall through
					case 96:
						break;
					case 40: {
						return Symbols.VECTOR_LITERAL;
					}
					// fall through
					case 97:
						break;
					case 41: {
						return Symbols.KW_OUT;
					}
					// fall through
					case 98:
						break;
					case 42: {
						return Symbols.KW_BIT;
					}
					// fall through
					case 99:
						break;
					case 43: {
						return Symbols.KW_TEXT;
					}
					// fall through
					case 100:
						break;
					case 44: {
						return Symbols.KW_ELSE;
					}
					// fall through
					case 101:
						break;
					case 45: {
						return Symbols.KW_CASE;
					}
					// fall through
					case 102:
						break;
					case 46: {
						return Symbols.KW_CLOCK;
					}
					// fall through
					case 103:
						break;
					case 47: {
						return Symbols.KW_NATIVE;
					}
					// fall through
					case 104:
						break;
					case 48: {
						return Symbols.KW_VECTOR;
					}
					// fall through
					case 105:
						break;
					case 49: {
						return Symbols.KW_MATRIX;
					}
					// fall through
					case 106:
						break;
					case 50: {
						return Symbols.KW_MODULE;
					}
					// fall through
					case 107:
						break;
					case 51: {
						return Symbols.KW_SIGNAL;
					}
					// fall through
					case 108:
						break;
					case 52: {
						return Symbols.KW_SWITCH;
					}
					// fall through
					case 109:
						break;
					case 53: {
						return Symbols.KW_INTEGER;
					}
					// fall through
					case 110:
						break;
					case 54: {
						return Symbols.KW_DEFAULT;
					}
					// fall through
					case 111:
						break;
					case 55: {
						return Symbols.KW_REGISTER;
					}
					// fall through
					case 112:
						break;
					case 56: {
						return Symbols.KW_CONSTANT;
					}
					// fall through
					case 113:
						break;
					case 57: {
						return Symbols.KW_INTERFACE;
					}
					// fall through
					case 114:
						break;
					default:
						zzScanError(ZZ_NO_MATCH);
				}
			}
		}
	}

}
