import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

public class InputFileThread extends Thread {

	private Vector<Vector<String>> fileSentences; // Contains all words of all sentences of input file
	private Vector<Vector<Word>> matchingWords; // Contains all matching words of each sentence of input file
	private BST bst;

	// Parameterized constructor
	public InputFileThread(String name, BST bst) {
		this.setName(name);
		this.fileSentences = new Vector<Vector<String>>();
		this.matchingWords = new Vector<Vector<Word>>();
		this.bst = bst;
	}

	// Method to get vector of all words of input file
	public Vector<Vector<String>> getFileSentences() {
		return this.fileSentences;
	}

	// Method to get vector of matching words of input file
	public Vector<Vector<Word>> getMatchingWords() {
		return this.matchingWords;
	}

	// Method to make vector from input file
	public void makeFileVector() {

		try {
			BufferedReader inFile = new BufferedReader(new InputStreamReader(new FileInputStream(this.getName())));
			String str = "";

			while ((str = inFile.readLine()) != null) {

				// Creating a vector of all words of file
				Vector<String> allWords = new Vector<String>();

				// Creating vector of matching words from input files
				Vector<Word> matchWords = new Vector<Word>();

				// Tokenizing sentences in input file
				StringTokenizer stringTokenizer = new StringTokenizer(str);

				while (stringTokenizer.hasMoreTokens()) {
					String currentToken = stringTokenizer.nextToken();

					allWords.add(currentToken);

					// Creating a word instance if word is in vocabulary BST
					if (this.bst.search(this.bst.getRoot(), currentToken)) {
						matchWords.add(new Word(currentToken));
					}
				}

				this.fileSentences.add(allWords);
				this.matchingWords.add(matchWords);
			}

			inFile.close();
		}

		catch (IOException E) {
			E.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.makeFileVector();
	}
}