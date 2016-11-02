package com.maxgarfinkel.suffixTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Utils {

	/**
	 * Appends a SequenceTerminal element to a supplied array.
	 * 
	 * @param sequence
	 *            The sequence to which we are applying the terminating object.
	 * @param terminatingObject
	 *            The instance of the terminating object.
	 * @return A new sequence with an extra element at the end containing the
	 *         terminating object.
	 */
	public static <I, S extends Iterable<I>> Object[] addTerminalToSequence(S sequence,
			SequenceTerminal<S> terminatingObject) {

		ArrayList<Object> list = new ArrayList<Object>();
		for (I item : sequence)
			list.add(item);

		Object[] newSequence = new Object[list.size() + 1];

		int i = 0;
		for (; i < list.size(); i++)
			newSequence[i] = list.get(i);
		newSequence[i] = terminatingObject;
		return newSequence;
	}

	public static <T, S extends Iterable<T>> String printTreeForGraphViz(SuffixTree<T, S> tree) {
		return printTreeForGraphViz(tree, true, true);
	}
	public static <T, S extends Iterable<T>> String printTreeForGraphViz(SuffixTree<T, S> tree,boolean printSuffixLinks) {
		return printTreeForGraphViz(tree, true, false);
	}
	/**
	 * Generates a .dot format string for visualizing a suffix tree.
	 * 
	 * @param tree
	 *            The tree for which we are generating a dot file.
	 * @return A string containing the contents of a .dot representation of the
	 *         tree.
	 */
	public static <T, S extends Iterable<T>> String printTreeForGraphViz(SuffixTree<T, S> tree,
			boolean printSuffixLinks, boolean printIncomingEdgeLinks) {
		LinkedList<Node<T, S>> stack = new LinkedList<Node<T, S>>();
		stack.add(tree.getRoot());
		Map<Node<T, S>, Integer> nodeMap = new HashMap<Node<T, S>, Integer>();
		nodeMap.put(tree.getRoot(), 0);
		int nodeId = 1;

		StringBuilder sb = new StringBuilder(
				"\ndigraph suffixTree{\n node [shape=circle, label=\"\", fixedsize=true, width=0.1, height=0.1]\n");

		while (stack.size() > 0) {
			LinkedList<Node<T, S>> childNodes = new LinkedList<Node<T, S>>();
			for (Node<T, S> node : stack) {
				for (Edge<T, S> edge : node) {
					int id = nodeId++;
					if (edge.isTerminating()) {
						childNodes.push(edge.getTerminal());
						nodeMap.put(edge.getTerminal(), id);
					}

					sb.append(nodeMap.get(node)).append(" -> ").append(id).append(" [label=\"");

					for (T item : edge) {
						sb.append(item.toString());
					}
					sb.append("\"];\n");
				}
			}
			stack = childNodes;
		}
		if (printSuffixLinks) {
			// loop again to find all suffix links.
			sb.append("edge [color=red]\n");
			for (Map.Entry<Node<T, S>, Integer> entry : nodeMap.entrySet()) {
				Node n1 = entry.getKey();
				int id1 = entry.getValue();
				if (n1.hasSuffixLink()) {
					Node n2 = n1.getSuffixLink();
					Integer id2 = nodeMap.get(n2);
					sb.append(id1).append(" -> ").append(id2).append(" ;\n");
				}
			}
		}
		if (printIncomingEdgeLinks){
			for (Node<T, S> node:nodeMap.keySet()){
				int id1 = nodeMap.get(node);
				if (node.getIncomingEdge()!=null){
					if (node.getIncomingEdge().getParentNode()!=null){
						int id2 = nodeMap.get(node.getIncomingEdge().getParentNode());
						sb.append(id1).append("->").append(id2).append("[style=dashed];\n");
					}
				}
			}
		}
		sb.append("}");
		return (sb.toString());
	}
}
