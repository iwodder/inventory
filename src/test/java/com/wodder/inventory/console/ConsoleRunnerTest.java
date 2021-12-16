package com.wodder.inventory.console;

import com.wodder.inventory.console.handlers.*;
import com.wodder.inventory.console.menus.inventoryitems.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.internal.verification.*;
import org.mockito.junit.jupiter.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

//TODO Update this test to use mockito
@ExtendWith(MockitoExtension.class)
class ConsoleRunnerTest extends BaseMenuTest {
	private TestHandler handler;
	private RootMenu rootMenu;

	@Mock
	ConsoleMenu menu;

	@Mock
	InputHandler mockHandler;

	@Mock
	InputStream testInputStream;


	@BeforeEach
	protected void setup() {
		super.setup();
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
		runner.setMenu(menu);
		assertNotNull(runner.getMenu());
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
	void console_runner_3() throws IOException{
		when(menu.getExit()).thenReturn(false, true);
		ConsoleRunner runner = new ConsoleRunner(testInputStream, new PrintStream(new ByteArrayOutputStream()));
		runner.setMenu(menu);
		runner.start();
		verify(menu, new Times(1)).printMenu(any());
	}

	@Test
	@DisplayName("Console runner passes input and output to handle method")
	void console_runner_4() throws IOException {
		ConsoleRunner runner = new ConsoleRunner(testInputStream, new PrintStream(new ByteArrayOutputStream()));
		runner.setMenu(rootMenu);
		runner.start();
		handler.iterations = 1;
		assertTrue(handler.invokedHandledInput);
	}

	@Test
	@DisplayName("Console runner processes main loop until prompted to exit")
	void console_runner_5() throws IOException {
		ConsoleRunner runner = new ConsoleRunner(testInputStream, new PrintStream(new ByteArrayOutputStream()));
		runner.setMenu(rootMenu);
		runner.start();
		handler.iterations = 3;
		assertEquals(3, handler.handleInputCnt);
	}

	@Test
	@DisplayName("Console runner handles all exceptions from within the main loop")
	void consoleRunnerException() {
		when(menu.getExit()).thenReturn(false, true);
		doThrow(new RuntimeException("oops")).when(menu).process(any(), any(), any());
		ConsoleRunner runner = new ConsoleRunner(inputStream, out, err);
		runner.setMenu(menu);
		runner.start();
		assertTrue(true);
		assertEquals(String.format("oops%n"), baosErr.toString());
	}

	@Test
	@DisplayName("Console runner prints unknown error message when exception message is empty")
	void consoleRunnerException2() {
		when(menu.getExit()).thenReturn(false, true);
		doThrow(new RuntimeException()).when(menu).process(any(), any(), any());
		ConsoleRunner runner = new ConsoleRunner(inputStream, out, err);
		runner.setMenu(menu);
		runner.start();
		assertTrue(true);
		assertEquals(String.format("Unknown problem occurred during processing.%n"), baosErr.toString());
	}

	@Test
	@DisplayName("Console runner prints input prompt character '>'")
	void input_prompt() {
		setChars("");
		when(menu.getExit()).thenReturn(false, true);
		ConsoleRunner runner = new ConsoleRunner(inputStream, out, err);
		menu.setInputHandler(mockHandler);
		runner.setMenu(menu);
		runner.start();
		assertEquals("> ", baosOut.toString());
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
}
