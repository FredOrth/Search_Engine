# Search Engine REST API

A work-in-progress search engine backend written in Java and exposed through a REST API using Spring Boot.

The project started as a smaller search engine used for teaching introductory object-oriented programming. I am now redesigning it as a more complete backend application, with a focus on clean architecture, API design, modular persistence, testing, and gradually more advanced information retrieval techniques.

> **Status:** Actively under development.

## Background

The original version of the search engine was intentionally small and focused on fundamental programming concepts such as classes, collections, file processing, and object-oriented design.

That implementation is preserved in the [`legacy`](./legacy) directory.

The current version is a redesign rather than simply adding HTTP endpoints to the original application. The goal is to build a backend service where the search implementation, web layer, and data source can evolve independently.

## Architecture

The application is structured around clearly separated responsibilities:

```text
HTTP Client
     |
     v
REST Controller
     |
     v
Service / Application Layer
     |
     v
Search Domain
     |
     v
Repository / Data Source
```

The intention is to keep framework-specific concerns separate from the core search functionality.

### REST Layer

The REST layer is responsible for the HTTP boundary of the application:

- Receiving requests
- Validating input
- Mapping requests to application operations
- Returning structured responses
- Handling HTTP-specific errors and status codes

Controllers are deliberately kept thin and delegate application logic to the service layer.

### Service Layer

The service layer coordinates the application's use cases and connects the REST API with the underlying search domain.

This keeps application behaviour separate from both HTTP handling and persistence.

### Search Domain

The core search functionality is designed independently of Spring and the underlying data source.

The long-term goal is to evolve this layer into a more complete information retrieval system including:

- Inverted indexing
- Term-frequency statistics
- Relevance scoring
- Ranking and sorting
- More efficient query evaluation

Potential ranking models include TF-IDF and related scoring approaches such as BM25.

### Data Source and Persistence

The application currently works with input files, allowing the search functionality to be developed without requiring external infrastructure.

Persistence is intentionally hidden behind an abstraction so that the rest of the application does not depend directly on where the data comes from.

Conceptually:

```text
                  Search Domain
                       |
                       v
                  Repository API
                   /          \
                  /            \
         File-based source   PostgreSQL
             (current)        (planned)
```

The architecture is already prepared for PostgreSQL to replace or supplement the file-based implementation without requiring the REST or search layers to be redesigned.

This also makes it possible to test the search functionality against lightweight local data while using persistent storage in a production-oriented configuration.

## Initial Design Decisions

### Keep controllers thin

Controllers deal with HTTP concerns rather than search or business logic.

Requests are validated and passed to application services, which makes the individual layers easier to test and change independently.

### Keep the search engine independent of the web framework

The search implementation should not need to know whether it is called from a REST endpoint, a command-line application, or another interface.

Spring provides infrastructure around the search engine rather than defining its internal design.

### Keep persistence replaceable

The search functionality depends on an abstraction rather than directly on files or PostgreSQL.

This allows the current file-based data source to be replaced by a database-backed implementation without changing the rest of the application.

### Introduce complexity when it solves a real problem

The application is currently a modular monolith rather than a collection of microservices.

For the current scope, clear internal boundaries provide the benefits needed without introducing unnecessary distributed-system complexity.

### Design the API around use cases

The public REST API represents operations that clients need to perform rather than exposing the internal Java object model directly.

This allows internal implementation details to evolve without unnecessarily changing the external API.

## Search Engine Roadmap

The current search implementation will gradually be replaced with a more efficient retrieval pipeline.

A planned query flow looks roughly like this:

```text
Documents
    |
    v
Tokenization / Normalization
    |
    v
Inverted Index
    |
    v
Query Processing
    |
    v
Candidate Documents
    |
    v
Relevance Scoring
    |
    v
Ranking
    |
    v
Top Search Results
```

### Inverted Index

Instead of scanning every document for each query, terms will be mapped to the documents in which they occur.

For example:

```text
java    -> [doc1, doc4, doc8]
spring  -> [doc2, doc4]
search  -> [doc1, doc2, doc7]
```

This should significantly reduce the amount of work required for each search.

### Relevance Scoring

Matching documents will eventually receive a relevance score rather than being treated as equally important.

Possible approaches include:

- TF-IDF
- BM25
- Field weighting
- Term frequency
- Document frequency

This will allow results to be ranked according to how well they match the query.

### Ranking and Top-K Retrieval

Rather than fully sorting every matching document, the search engine can eventually retrieve only the highest-scoring results required by the client.

For large result sets, a bounded priority queue / heap can be used to maintain the best `k` results without sorting the complete set.

## Future Optimizations

As the dataset and search functionality grow, several further optimizations can be explored.

### Precomputed Index Statistics

Values such as document frequency, inverse document frequency, and document length statistics can be calculated during indexing rather than repeatedly during queries.

This moves expensive work away from query time.

### Efficient Posting Lists

Posting lists in the inverted index can be maintained in sorted order, making intersections between multiple search terms significantly more efficient.

Later optimizations could include:

- Skip pointers
- Compressed posting lists
- Delta encoding of document IDs

### Query Result Caching

Frequently repeated searches could be cached to avoid repeating identical retrieval and ranking work.

Caching would need an invalidation strategy as the index changes.

### Incremental Indexing

Instead of rebuilding the complete index whenever documents change, individual documents could be added, updated, or removed incrementally.

### Parallel Index Construction

Index creation is naturally divisible across documents and could be parallelized for larger datasets.

Partial indexes could be constructed concurrently and merged afterwards.

### Database Optimization

Once PostgreSQL is introduced, database-specific optimizations can be investigated, including:

- Appropriate database indexes
- Batch inserts
- Connection pooling
- Query analysis
- Transaction boundaries

The database is intended primarily as persistent storage; search-specific structures can remain optimized for retrieval rather than forcing every search operation through relational queries.

### Pagination

The REST API will eventually support paginated results so that clients do not need to retrieve a complete result set for every query.

### Performance Measurement

As optimizations are introduced, benchmarks can be added for:

- Index construction time
- Query latency
- Memory consumption
- Number of indexed documents
- Ranking performance
- File-backed versus database-backed implementations

This makes it possible to evaluate optimizations empirically rather than assuming that a more complicated implementation is necessarily faster.

## Technologies

- **Java**
- **Spring Boot**
- **Spring MVC**
- **REST / HTTP**
- **Gradle**
- **PostgreSQL** — planned persistence
- **JUnit** — testing

Planned additions include:

- Spring Data JPA
- Database migrations
- Expanded integration testing
- API documentation
- Inverted indexing
- Relevance scoring and ranking

## What I'm Exploring

The project is primarily an exercise in practical backend and search-engine engineering.

Some of the questions I am exploring are:

- How should search functionality be exposed through a REST API?
- How can framework-specific code remain separate from core application logic?
- How can storage implementations be replaced without affecting the rest of the system?
- What data structures make full-text search efficient?
- How should matching documents be scored and ranked?
- Which work can be moved from query time to indexing time?
- How should the system change as the dataset grows?
- Which optimizations actually improve measurable performance?

## Current Status

The application currently uses a file-based data source while the REST and application architecture are being developed.

The next major steps are:

1. Continue refining the REST API and application structure
2. Introduce PostgreSQL persistence
3. Implement an inverted index
4. Add relevance scoring and ranking
5. Improve query processing
6. Add performance benchmarks
7. Explore indexing and retrieval optimizations
