package app.searchengine;

import java.io.IOException;
import java.util.Map;

public interface WebPageRepository {
	Map<String, WebPage> retrieveAll() throws IOException;

}
