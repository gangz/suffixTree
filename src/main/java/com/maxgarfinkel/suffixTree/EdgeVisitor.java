package com.maxgarfinkel.suffixTree;

public interface EdgeVisitor<I,S extends Iterable<I>> {

	void visit(Edge<I, S> edge);

}
