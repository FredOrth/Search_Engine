package app.searchengine;

import java.io.FileNotFoundException;
import java.util.List;

public interface WebPageRepository {
	List<WebPage> retrieveAll() throws FileNotFoundException;

}
