/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.intellij;

import com.google.common.collect.ImmutableList;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import name.martingeisse.mahdl.common.input.psi.Token;
import name.martingeisse.mahdl.gradle.CompilationErrors;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PsiBuilderImpl implements PsiBuilder {

	private final String sourcePath;
	private final ImmutableList<Token> tokens;
	private int tokenIndex = 0;
	private final List<Marker> markerStack = new ArrayList<>();
	private final ASTNode rootNode;
	private boolean hasErrors = false;

	public PsiBuilderImpl(String sourcePath, ImmutableList<Token> tokens, IElementType rootElementType) {
		this.sourcePath = sourcePath;
		this.tokens = tokens;
		this.rootNode = new ASTNode(rootElementType, 0, 0);
	}

	@Override
	public void advanceLexer() {
		if (tokenIndex < tokens.size()) {
			tokenIndex++;
		}
	}

	@Override
	public IElementType getTokenType() {
		return (tokenIndex < tokens.size() ? tokens.get(tokenIndex).getElementType() : null);
	}

	@Override
	public Marker mark() {
		if (tokenIndex == tokens.size()) {
			throw new IllegalStateException("cannot mark at EOF");
		}
		MarkerImpl marker = new MarkerImpl(tokens.get(tokenIndex));
		markerStack.add(marker);
		return marker;
	}

	@Override
	public void error(String message) {
		int errorTokenIndex = eof() ? (tokens.size() - 1) : tokenIndex;
		int row = tokens.get(errorTokenIndex).getRow();
		CompilationErrors.reportError(sourcePath, row, message);
	}

	@Override
	public boolean eof() {
		return (tokenIndex == tokens.size());
	}

	@Override
	public ASTNode getTreeBuilt() {
		return rootNode;
	}

	public final class MarkerImpl implements Marker {

		private final Token startToken;
		private final List<ASTNode> unlinkedChildNodes = new ArrayList<>();

		public MarkerImpl(Token startToken) {
			this.startToken = startToken;
		}

		@Override
		public void rollbackTo() {
			int index = markerStack.lastIndexOf(this);
			if (index == -1) {
				throw new IllegalStateException("this marker is not active");
			}
			while (markerStack.size() >= index) {
				markerStack.remove(markerStack.size() - 1);
			}
		}

		@Override
		public void done(IElementType type) {
			if (getTopOfStack() != this) {
				throw new IllegalStateException("nested markers are active");
			}
			markerStack.remove(markerStack.size() - 1);
			if (markerStack.isEmpty()) {
				rootNode.createChild(startToken);
			} else {
				MarkerImpl parentMarker = (MarkerImpl) markerStack.get(markerStack.size() - 1);
				ASTNode node = parentMarker.createUnlinkedChildNode(startToken);
				for (ASTNode unlinkedChildNode : unlinkedChildNodes) {
					node.createChild(unlinkedChildNode.getElementType(), unlinkedChildNode.getRow(), unlinkedChildNode.getColumn());
				}
			}
		}

		private Marker getTopOfStack() {
			int size = markerStack.size();
			return (size == 0 ? null : markerStack.get(size - 1));
		}

		private ASTNode createUnlinkedChildNode(Token childToken) {
			ASTNode node = new ASTNode(childToken);
			unlinkedChildNodes.add(node);
			return node;
		}

	}

}
