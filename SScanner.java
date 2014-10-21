

import java.io.BufferedReader;
import java.io.FileReader;

public class SScanner {

	private int charclass;
	private char[] lexeme = new char[100];
	private char nextchar;
	private int lexlen;
	private int token;
	private static int nexttoken;
	private final int LETTER = 0;
	private final int DIGIT = 1;
	private final int UNKNOWN = 99;
	private int int_lit = 10;
	private int ident = 11;
	private int assign_op = 20;
	private int add_op = 21;
	private int sub_op = 22;
	private int mult_op = 23;
	private int div_op = 24;
	private int left_paren = 25;
	private int right_paren = 26;
	private String filedata = "";
	private static int charindex;
	private static final char EOF = 'E';

	public static void main(String[] args) {

		SScanner sss = new SScanner();
		if (args.length >= 1) {
			sss.fileRead(args[0]);
			do {
				sss.lex();
				// charindex++;
			} while (nexttoken != EOF);

		} else {
			System.out.println("Please give the input file");
		}
	}

	private void addChar() {

		if (lexlen <= 98) {

			lexeme[lexlen++] = nextchar;
			lexeme[lexlen] = 0;
			charindex++;

		} else {
			System.out.println("Error :lexeme is too long");
		}
	}

	private void getChar() {
		if ((nextchar = getc(filedata)) != EOF) {
			if (isalpha(nextchar))
				charclass = LETTER;
			else if (isdigit(nextchar))
				charclass = DIGIT;
			else
				charclass = UNKNOWN;

		} else
			charclass = EOF;
	}

	private char getc(String filedata) {
	

		if (charindex < filedata.length())

			return filedata.charAt(charindex);
		else
			return EOF;
	}

	private void fileRead(String inputfile) {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(inputfile));
			String stline = null;
			while ((stline = br.readLine()) != null) {
				filedata += stline;
			}

		}

		catch (Exception e) {
			e.printStackTrace();

		}
	}

	private boolean isalpha(char c) {

		return Character.isAlphabetic((int) c);
	}

	private boolean isdigit(char c) {

		return Character.isDigit(c);
	}

	private boolean isspace(char c) {
		return Character.isSpaceChar(c);
	}

	private void getNonBlank() {
		while (isspace(nextchar))
			getChar();
	}

	private int lookup(char ch) {
		switch (ch) {
		case '(':
			addChar();

			nexttoken = left_paren;
			break;
		case ')':
			addChar();
			nexttoken = right_paren;
			break;
		case '+':
			addChar();
			nexttoken = add_op;
			break;
		case '-':
			addChar();
			nexttoken = sub_op;
			break;
		case '*':
			addChar();
			nexttoken = mult_op;
			break;
		case '/':
			addChar();
			nexttoken = div_op;
			break;
		default:
			addChar();
			nexttoken = EOF;
			break;
		}
		return nexttoken;
	}

	private int lex() {

		lexlen = 0;
		getNonBlank();
		switch (charclass) {
		case LETTER:

			addChar();
			getChar();
			while (charclass == LETTER || charclass == DIGIT) {
				addChar();
				getChar();
			}
			nexttoken = ident;
			break;
		case DIGIT:
			addChar();
			getChar();
			while (charclass == DIGIT) {

				addChar();
				getChar();
			}
			break;
		case UNKNOWN:
			lookup(nextchar);
			getChar();
			break;
		case EOF:
			nexttoken = EOF;
			lexeme[0] = 'E';
			lexeme[1] = 'o';
			lexeme[2] = 'F';
			lexeme[3] = 0;

		}
		System.out.println(lexeme);
		return nexttoken;
	}

}
