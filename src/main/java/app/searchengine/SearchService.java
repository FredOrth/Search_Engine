package app.searchengine;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private final WebPageRepository repository;

    public SearchService(WebPageRepository repository) {
        this.repository = repository;
    }

    public List<WebPage> search(String searchTerm) throws IOException {
        //search logic here

        List<WebPage> pages = repository.retrieveAll();
        List<WebPage> results = new ArrayList<>();
        for(WebPage page : pages ){
           if(page.getKeywords().contains(searchTerm)){
               results.add(page);
           }
        }
           return results;
        }
    }