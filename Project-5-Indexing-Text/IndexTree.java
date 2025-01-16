package index;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IndexTree {
	private IndexNode root;

	public IndexTree() {
		root = null;
	}

	public void add(String word, int lineNumber) {
		root = add(root, word, lineNumber);
	}

	// O(log n) for a balanced tree, but O(n) in the worst case for an unbalanced tree.
	private IndexNode add(IndexNode root, String word, int lineNumber) {
		if (root == null) {
			root = new IndexNode(word, lineNumber);
			return root;
		} else if (word.compareTo(root.word) == 0) {
			root.addOccurence(lineNumber);
			return root;
		} else if (word.compareTo(root.word) < 0) {
			root.left = add(root.left, word, lineNumber);
			return root;
		} else{
			root.right = add(root.right, word, lineNumber);
			return root;
		}
	}

	// O(log n) for a balanced tree, but O(n) in the worst case for an unbalanced tree.
	public boolean contains(IndexNode root, String word) {
		if (root == null) {
			return false;
		} else if (word.compareTo(root.word) == 0) {
			return true;
		} else if (word.compareTo(root.word) < 0) {
			return contains(root.left, word);
		} else {
			return contains(root.right, word);
		}
	}

	public void delete(String word) {
		root = delete(root, word);
	}

	// O(log n) for a balanced tree, but O(n) in the worst case for an unbalanced tree.
	private IndexNode delete(IndexNode root, String word) {
		if (root == null) {
			return null;
		} else if (word.compareTo(root.word) == 0) {
			if (root.left == null && root.right == null) {
				return null;
			} else if (root.right != null && root.left != null) {
				IndexNode thisNode = root.left;
				while (thisNode.right != null) {
					thisNode = thisNode.right;  // This can take up to O(n) in worst case
				}
				root.word = thisNode.word;
				root.occurences = thisNode.occurences;
				root.list = thisNode.list;
				root.left = delete(root.left, root.word);
				return root;
			} else {
				if (root.left != null) {
					return root.left;
				} else {
					return root.right;
				}
			}
		} else if (word.compareTo(root.word) < 0) {
			root.left = delete(root.left, word);
			return root;
		} else {
			root.right = delete(root.right, word);
			return root;
		}
	}

	// O(n) because it prints all nodes.
	public void printIndex() {
		printIndex(root);
	}

	// O(n) because it visits all nodes.
	private void printIndex(IndexNode root) {
		if (root == null) {
			return;
		}
		printIndex(root.left);
		System.out.println(root.toString());
		printIndex(root.right);
	}

	public static void main(String[] args) {
		IndexTree index = new IndexTree();

		String fileName = "shakespeare.txt";
		try {
			Scanner scanner = new Scanner(new File(fileName));
			int lineNumber = 0;
			while (scanner.hasNextLine()) {
				lineNumber++;
				String line = scanner.nextLine();
				String[] words = line.split("\\s+"); // O(m), where m is the number of words in the line.
				for (String word : words) { // O(m)
					word = word.toLowerCase();
					word = word.replaceAll("[^\\w]", "");
					index.add(word, lineNumber); // O(log n) for each word in a balanced tree.
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		index.printIndex();  // O(n)
		index.delete("to");  // O(log n) for a balanced tree.
		index.delete("the"); // O(log n) for a balanced tree.
		index.delete("your"); // O(log n) for a balanced tree.

		index.printIndex();  // O(n)
	}
}