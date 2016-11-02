package com.github.gangz.suffixtree.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.maxgarfinkel.suffixTree.SuffixTree;
import com.maxgarfinkel.suffixTree.Utils;
import com.maxgarfinkel.suffixTree.Word;

public class TestSuffixTree {

	@Test
	public void printToDot() {
		Word word = new Word("BANANA");
		SuffixTree<Character, Word> tree = new SuffixTree<Character, Word>(word);
		String dotOfSuffixTree = Utils.printTreeForGraphViz(tree);
		System.out.println(dotOfSuffixTree);
	}

}
