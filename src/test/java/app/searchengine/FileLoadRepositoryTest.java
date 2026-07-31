package app.searchengine;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileLoadRepositoryTest {

	@Test
	void fileFound() throws Exception {

		WebPageRepository files = new FileLoadRepository();
		assertNotNull(files);
		var pages = files.retrieveAll();
		Assertions.assertNotNull(files);
		Assertions.assertFalse(pages.isEmpty());
	}
}
