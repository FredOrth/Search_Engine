# Search Engine REST API

A work-in-progress search engine backend written in Java and exposed through a REST API using Spring Boot.

The project started as a smaller search engine used for teaching introductory object-oriented programming. I am now rebuilding it as a more complete backend application with a focus on clean architecture, modular persistence, testing, and more advanced search techniques.

> **Status:** Actively under development.

## Architecture

The application is structured around separated responsibilities:

```text
HTTP Client
    |
    v
REST Controller
    |
    v
Service Layer
    |
    v
Search Domain
    |
    v
Repository / Data Source
```

The core search logic is kept independent of Spring and of the underlying storage mechanism.

The application currently works with file-based input, while the architecture is prepared for a PostgreSQL-backed implementation through the same persistence boundary.

```text
Search Domain
     |
     v
Repository Interface
   /         \
Files      PostgreSQL
```

## Design Goals

- Keep controllers thin and focused on HTTP concerns
- Separate application logic from framework-specific code
- Keep persistence replaceable
- Use a modular monolith rather than adding unnecessary distributed complexity
- Design the REST API around use cases rather than exposing internal Java objects directly

## Search Roadmap

The search implementation will gradually be expanded with:

- Inverted indexing
- Query processing
- Relevance scoring using TF-IDF, BM25, or similar approaches
- Ranking and sorting
- Top-k retrieval
- Incremental indexing
- Query caching
- Performance benchmarking

A future search flow will look roughly like:

```text
Documents
    |
    v
Tokenization
    |
    v
Inverted Index
    |
    v
Query Processing
    |
    v
Scoring
    |
    v
Ranking
    |
    v
Top Results
```

## Planned Optimizations

As the project grows, I plan to explore:

- Precomputed index statistics
- Efficient posting lists
- Bounded heaps for top-k results
- Parallel index construction
- Incremental index updates
- Database indexing and batch operations
- Query-result caching
- Pagination
- Measuring query latency, indexing time, and memory usage

## Technologies

- Java
- Spring Boot
- Spring MVC
- REST / HTTP
- Gradle
- PostgreSQL
- JUnit
