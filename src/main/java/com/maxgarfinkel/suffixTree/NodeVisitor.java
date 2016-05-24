package com.maxgarfinkel.suffixTree;

public interface NodeVisitor<I,S extends Iterable<I>> {

	void visit(Node<I, S> node);

	void visitTerminatingEdge(Edge<I, S> edge);

}
