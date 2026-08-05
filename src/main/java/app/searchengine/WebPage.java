package app.searchengine;

import java.util.List;

public class WebPage {

	private String title;
	private String url;
	private List<String> keywords;

	public WebPage(String title, String url, List<String> keywords) {

		this.url = url;
		this.title = title;
		this.keywords = keywords;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public String getTitle() {
		return title;
	}
	public String getUrl() {
		return url;
	}
}
