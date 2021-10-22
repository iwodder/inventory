package com.wodder.inventory.console.menus.inventory;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

public abstract class BaseMenuTest {
	protected PrimitiveIterator.OfInt chars;
	protected PrintStream out;
	protected PrintStream err;
	protected ByteArrayOutputStream baosOut;
	protected ByteArrayOutputStream baosErr;
	protected Scanner in;
	protected InputStream inputStream;
	protected boolean success;

	@BeforeEach
	protected void setup() {
		baosOut = new ByteArrayOutputStream();
		baosErr = new ByteArrayOutputStream();
		out = new PrintStream(baosOut);
		err = new PrintStream(baosErr);
		inputStream = new TestInputStream();
		in = new Scanner(inputStream);
	}

	public void setChars(String userInput) {
		if (userInput == null) {
			userInput = System.lineSeparator();
		}
		if (!userInput.endsWith(System.lineSeparator())) {
			userInput = userInput + System.lineSeparator();
		}
		chars = userInput.chars().iterator();
	}

	class TestInputStream extends InputStream {

		@Override
		public int read() throws IOException {
			if (chars.hasNext()) {
				return chars.next();
			} else {
				return -1;
			}
		}
	}
}
