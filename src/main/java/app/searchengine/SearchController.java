package app.searchengine;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/search")
public class SearchController {

    private SearchService searchService;

    public SearchController(SearchService searchService) {
       this.searchService = searchService;
    }

    @GetMapping
    public Set<WebPage> search(@RequestParam String query) throws IOException {
        return searchService.search(query);
    }


}
