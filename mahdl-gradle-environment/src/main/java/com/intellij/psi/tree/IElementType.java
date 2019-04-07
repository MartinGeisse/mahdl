package com.intellij.psi.tree;

import com.intellij.lang.Language;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AST node type.
 */
public class IElementType {

	private static final AtomicInteger INDEX_ALLOCATOR = new AtomicInteger();

	private final int index;
	private final String debugName;

	public IElementType(String debugName, Language ignored) {
		this.index = INDEX_ALLOCATOR.getAndIncrement();
		this.debugName = debugName;
	}

	public int getIndex() {
		return index;
	}

	public String getDebugName() {
		return debugName;
	}

	@Override
	public int hashCode() {
		return index;
	}

	@Override
	public String toString() {
		return debugName;
	}

}
