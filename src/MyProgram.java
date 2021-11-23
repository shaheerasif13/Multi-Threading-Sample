import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class MyProgram {

	private Scanner scanner;
	private String[] fileNames;
	private BST bst;
	private Vector<Vector<Vector<String>>> fileVectors; // Contains all words of input files
	private Vector<Vector<Vector<Word>>> matchingWordsVector; // Contains only matching words from files

	// Parameterized constructor
	public MyProgram(String[] fileNames) {
		this.scanner = new Scanner(System.in);
		this.fileNames = fileNames;
		this.bst = null;
		this.fileVectors = new Vector<Vector<Vector<String>>>();
		this.matchingWordsVector = new Vector<Vector<Vector<Word>>>();
	}

	// Method to display files data
	public boolean displayFilesData() {

		if (this.fileNames.length == 0) {
			System.out.println("No input files given!");
			return false;
		}

		// Displaying number and names of files
		System.out.println("Number of files: " + this.fileNames.length);

		for (int i = 0; i < this.fileNames.length; i++) {
			System.out.println("File-" + (i + 1) + ": " + this.fileNames[i]);
		}

		return true;
	}

	// Method to display menu
	public int showMenu() {
		System.out.println("\n1 - Display BST build from vocabulary file.");
		System.out.println("2 - Display vectors build from input files.");
		System.out.println("3 - View matching words and their frequencies.");
		System.out.println("4 - Search a query.");
		System.out.println("5 - Exit");

		System.out.print("\nEnter choice: ");
		int choice = scanner.nextInt();

		while (choice < 1 || choice > 5) {
			System.out.print("\nInvalid choice!\nEnter choice again: ");
			choice = scanner.nextInt();
		}

		return choice;
	}

	// Method to start threads
	public void startThreads() {

		// Creating first thread (Vocabulary)
		VocabularyThread vocabularyThread = new VocabularyThread(this.fileNames[0]);
		vocabularyThread.setPriority(10);
		vocabularyThread.start();

		try {
			vocabularyThread.join();
		}

		catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.bst = vocabularyThread.getBST();

		// Creating input file threads (Created threads count = Number of input files)
		for (int i = 1; i < this.fileNames.length; i++) {
			InputFileThread inputFileThread = new InputFileThread(this.fileNames[i], this.bst);
			inputFileThread.start();

			try {
				inputFileThread.join();
			}

			catch (InterruptedException e) {
				e.printStackTrace();
			}

			this.fileVectors.add(inputFileThread.getFileSentences());
			this.matchingWordsVector.add(inputFileThread.getMatchingWords());
		}
	}

	// Method to implement search query
	public void searchQuery() {
		scanner.nextLine();
		System.out.print("\nEnter a string: ");
		String str = scanner.nextLine();

		// Creating a vector of words in query
		Vector<String> queryWords = new Vector<String>();

		// Tokenizing sentences in input string
		StringTokenizer stringTokenizer = new StringTokenizer(str);

		while (stringTokenizer.hasMoreTokens()) {
			queryWords.add(stringTokenizer.nextToken());
		}

		// Searching query ...

		// Loop over all words in query string
		for (int i = 0; i < queryWords.size(); i++) {
			System.out.println("\nSearching " + queryWords.get(i) + " ...");

			// Loop over files
			for (int j = 0; j < this.fileVectors.size(); j++) {
				int count = 0; // Count of matching in each file

				// Loop over sentences of each file
				for (int k = 0; k < this.fileVectors.get(j).size(); k++) {

					// Loop over words of each sentence
					for (int l = 0; l < this.fileVectors.get(j).get(k).size(); l++) {

						if (queryWords.get(i).equalsIgnoreCase(this.fileVectors.get(j).get(k).get(l))) {
							count = count + 1;
						}
					}
				}

				System.out.println("Matches in file " + this.fileNames[j + 1] + ": " + count);
			}
		}
	}

	// Method to start program
	public void startProgram() {

		if (this.displayFilesData()) { // Displaying files data
			this.startThreads(); // Starting threads here

			final int DISPLAY_BST = 1;
			final int DISPLAY_VECTORS = 2;
			final int VIEW_MATCHING_WORDS = 3;
			final int SEARCH_QUERY = 4;
			final int EXIT = 5;
			int choice = -1;

			while (choice != EXIT) {
				choice = this.showMenu();

				if (choice == DISPLAY_BST) {

					if (this.bst != null) {
						System.out.println("\nBST (Level order):\n");
						this.bst.displayLevelOrder();
					}
				}

				else if (choice == DISPLAY_VECTORS) {
					System.out.println(this.fileVectors);
				}

				else if (choice == VIEW_MATCHING_WORDS) {
					System.out.println(this.matchingWordsVector);
				}

				else if (choice == SEARCH_QUERY) {
					this.searchQuery();
				}

				else if (choice == EXIT) {
					System.out.println("\nExiting ...");
				}
			}
		}
	}

	public static void main(String[] args) {
		MyProgram myProgram = new MyProgram(args);
		myProgram.startProgram();
	}
}