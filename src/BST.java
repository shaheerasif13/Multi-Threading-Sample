import java.util.LinkedList;
import java.util.Queue;

public class BST {

	// Node class for BST
	public class Node {

		public String data;
		public Node leftChild;
		public Node rightChild;

		// Parameterized constructor
		public Node(String data) {
			this.data = data;
			this.leftChild = null;
			this.rightChild = null;
		}
	}

	private Node root;

	// Default constructor
	public BST() {
		this.root = null;
	}

	// Method to check if BST is empty or not
	public boolean isEmpty() {
		return this.root == null;
	}

	// Method to get root of BST
	public Node getRoot() {
		return this.root;
	}

	// Method to set root of BST
	public void setRoot(Node root) {
		this.root = root;
	}

	// Method to insert in BST
	public Node insert(Node root, String newData) {

		if (root == null) {
			root = new Node(newData);
		}

		// If new value is greater, insert at right child
		else if (newData.compareTo(root.data) > 0) {
			root.rightChild = insert(root.rightChild, newData);
		}

		// If new value is less, insert at left child
		else if (newData.compareTo(root.data) < 0) {
			root.leftChild = insert(root.leftChild, newData);
		}

		else {
			System.out.println("Value already in BST!");
		}

		return root;
	}

	// Method to search in BST
	public boolean search(Node root, String key) {

		if (root == null) {
			return false;
		}

		if (key.compareTo(root.data) > 0) {
			return this.search(root.rightChild, key);
		}

		if (key.compareTo(root.data) < 0) {
			return this.search(root.leftChild, key);
		}

		return true;
	}

	// Method to display BST (Level order)
	public void displayLevelOrder() {

		if (this.isEmpty()) {
			System.out.println("BST is empty!");
			return;
		}

		// Creating a queue for storing nodes
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(this.root);

		while (!queue.isEmpty()) {

			// Number of nodes in current level
			int nodeCount = queue.size();

			// Displaying all nodes of current level
			while (nodeCount != 0) {
				Node currentNode = queue.peek();
				queue.remove();
				System.out.print(currentNode.data + " ");

				if (currentNode.leftChild != null) {
					queue.add(currentNode.leftChild);
				}

				if (currentNode.rightChild != null) {
					queue.add(currentNode.rightChild);
				}

				nodeCount = nodeCount - 1;
			}

			System.out.println();
		}
	}
}