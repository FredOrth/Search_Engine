package app.searchengine;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
@Repository
public class FileLoadRepository implements WebPageRepository {
	public List<WebPage> retrieveAll() throws IOException {

		List<WebPage> pages = new ArrayList<>();
		var filename = Files.readString(Paths.get("config.txt")).strip();
		try {
			List<String> lines = Files.readAllLines(Paths.get(filename));
			var lastIndex = lines.size();
			for (var i = lines.size() - 1; i >= 0; --i) {
				if (lines.get(i).startsWith("*PAGE")) {
					List<String> keywords = lines.subList(i+2,lastIndex);
					WebPage page = new WebPage(lines.get(i+1), lines.get(i).substring(6,lines.get(i).toCharArray().length), keywords);
					pages.add(page);
					lastIndex = i;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Collections.reverse(pages);
		return 	pages;
	}

}
