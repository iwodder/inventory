package com.wodder.inventory.console;

import java.io.*;
import java.util.*;

@FunctionalInterface
public interface InputHandler {

	void handleInput(Scanner input, PrintStream out);
}
