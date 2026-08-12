package app.searchengine;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SearchService {

    private final WebPageRepository repository;

    public SearchService(WebPageRepository repository) {
        this.repository = repository;
    }

    public Set<WebPage> search(String searchTerm) throws IOException {
        //searcMap logic here

        Map<String, WebPage> pages = repository.retrieveAll();
        Set<WebPage> results = new HashSet<WebPage>();
            for(String keyword : pages.keySet()){
                if(keyword.toLowerCase().contains(searchTerm.toLowerCase())){
                    results.add(pages.get(keyword));
                }
            }
           return results;
        }
    }