package com.wodder.inventory.dtos;

public class Result<T, E> {
	private final T ok;
	private final E err;

	public Result(T ok, E err) {
		if (err != null && ok != null) {
			throw new IllegalArgumentException("Cannot create a result with both types, one must be null");
		}

		this.ok	 = ok;
		this.err = err;
	}

	public boolean isOK() {
		return ok != null;
	}

	public boolean isErr() {
		return err != null;
	}

	public T getOk() {
		return ok;
	}

	public E getErr() {
		return err;
	}
}
