package app.searchengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileLoadRepository implements WebPageRepository {
	public List<WebPage> retrieveAll() throws FileNotFoundException {
		File myFile = new File("data/config.txt");
		Scanner myScanner = new Scanner(myFile);

		myScanner.close();
		return new ArrayList<WebPage>();
	}

}
