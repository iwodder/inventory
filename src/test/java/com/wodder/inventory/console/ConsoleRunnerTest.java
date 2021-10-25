package com.wodder.inventory.console;

import com.wodder.inventory.console.handlers.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleRunnerTest {
	private TestHandler handler;
	private RootMenu rootMenu;

	@BeforeEach
	void setup() {
		handler = new TestHandler();
		rootMenu = new RootMenu("Root Menu", handler);
		handler.setMenu(rootMenu);
	}

	@Test
	@DisplayName("Console runner can start")
	void console_runner() {
		ConsoleRunner runner = new ConsoleRunner();
		runner.start();
		assertTrue(runner.running);
	}

	@Test
	@DisplayName("Console runner accepts a rootmenu")
	void console_runner_1() {
		ConsoleRunner runner = new ConsoleRunner();
		runner.setRootMenu(new RootMenu("Root Menu", handler));
		assertNotNull(runner.getRootMenu());
	}

	@Test
	@DisplayName("Console runner requires InputStream and PrintStream")
	void console_runner_2() {
		ConsoleRunner runner = new ConsoleRunner(System.in, System.out);
		assertNotNull(runner.getInput());
		assertNotNull(runner.getOutput());
	}

	@Test
	@DisplayName("Console runner prints menu on start")
	void console_runner_3() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ConsoleRunner runner = new ConsoleRunner(new TestInputStream(), new PrintStream(baos));
		handler.iterations = 1;
		runner.setRootMenu(rootMenu);
		runner.start();
		assertNotEquals("", baos.toString());
	}

	@Test
	@DisplayName("Console runner passes input and output to handle method")
	void console_runner_4() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ConsoleRunner runner = new ConsoleRunner(new TestInputStream(), new PrintStream(baos));
		runner.setRootMenu(rootMenu);
		runner.start();
		handler.iterations = 1;
		assertTrue(handler.invokedHandledInput);
	}

	@Test
	@DisplayName("Console runner processes main loop until prompted to exit")
	void console_runner_5() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ConsoleRunner runner = new ConsoleRunner(new TestInputStream(), new PrintStream(baos));
		runner.setRootMenu(rootMenu);
		runner.start();
		handler.iterations = 3;
		assertEquals(3, handler.handleInputCnt);
	}

	private static class TestHandler extends InputHandler {
		boolean invokedHandledInput = false;
		int handleInputCnt = 0;
		int iterations = 1;

		@Override
		public void handleInput(Scanner input, PrintStream out, PrintStream err) {
			invokedHandledInput = true;
			if (handleInputCnt++ > iterations) {
				menu.setExit(true);
			}
			assertNotNull(input);
			assertNotNull(out);
		}
	}

	private static class TestInputStream extends InputStream {

		@Override
		public int read() throws IOException {
			return 0;
		}
	}
}
