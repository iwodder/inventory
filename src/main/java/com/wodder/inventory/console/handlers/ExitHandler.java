package com.wodder.inventory.console.handlers;

import java.io.*;
import java.util.*;

public class ExitHandler extends InputHandler {

	@Override
	public void handleInput(Scanner in, PrintStream out, PrintStream err) {
		menu.getParentMenu().exitMenu();
	}
}
