import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class VocabularyThread extends Thread {

	private BST bst;

	// Default constructor
	public VocabularyThread(String name) {
		this.setName(name);
		bst = new BST();
	}

	// Getter method for BST
	public BST getBST() {
		return this.bst;
	}

	// Method to make BST by reading file
	public void makeBST() {

		try {
			BufferedReader inFile = new BufferedReader(new InputStreamReader(new FileInputStream(this.getName())));
			String str = "";

			while ((str = inFile.readLine()) != null) {
				this.bst.setRoot(this.bst.insert(this.bst.getRoot(), str));
			}

			inFile.close();
		}

		catch (IOException E) {
			E.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.makeBST();
	}
}