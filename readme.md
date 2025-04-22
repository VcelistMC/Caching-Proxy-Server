# Caching Proxy

A lightweight HTTP caching proxy server built with Spring Boot for the roadmap.sh project https://roadmap.sh/projects/caching-server.

## Overview

This application serves as an intermediary proxy that caches responses from an origin server. It intercepts HTTP GET requests, forwards them to the specified origin server, and caches the responses based on a configurable time-to-live (TTL) and allows all other HTTP methods to pass through without interception.

## Features

- **Caching Mechanism**: The proxy caches responses from the origin server using a hash of the request URI as the cache key.
- **Time-to-Live (TTL)**: Each cached response has a TTL, after which the response is considered stale and is removed from the cache.
- **Cache Invalidation**: The cache is periodically invalidated to remove stale entries.
- **HTTP Headers**: The proxy adds custom headers (`X-Cache` and `X-Cache-TTL`) to indicate whether the response was served from the cache and the TTL of the cached response.

## Project Structure

The project is structured as follows:

```
src/main/java/com/peteratef/caching_proxy/
├── CachingProxyApplication.java
├── Consts.java
├── controller/
│   └── ProxyController.java
├── service/
│   ├── ProxyService.java
│   └── client/
│       └── OriginClient.java
├── cache/
│   ├── ICache.java
│   └── TimeoutCache.java
└── model/
    └── CachedResponse.java
```

### Key Components

1. **ProxyController**: The REST controller that handles incoming GET requests and delegates them to the `ProxyService`.
2. **ProxyService**: The service layer responsible for checking the cache, forwarding requests to the origin server, and caching responses.
3. **OriginClient**: A client that forwards requests to the origin server and retrieves responses.
4. **ICache**: An abstract class that defines the basic caching operations.
5. **TimeoutCache**: A concrete implementation of `ICache` that includes TTL-based cache invalidation.
6. **CachingProxyApplication**: The main application class that initializes the Spring Boot application and validates command-line arguments.

## Requirements

- Java 11 or higher
- Maven for building the project

## Getting Started

### Run using docker

```bash
docker run -p {HOST_PORT}:{CONTAINER_PORT} peteratef/caching_proxy --port {CONTAINER_PORT} --origin {ORIGIN_URL} --ttl {TTL} 
```
Note that the container port mapped to the docker container must match the port you specify to the proxy server

### Building the Project

```bash
mvn clean install
```

### Running the Application

```bash
java -jar caching-proxy.jar --port 8080 --origin http://example.com --ttl 300000
```

#### Command Line Arguments

- `--port`: The port number on which the proxy server will listen
- `--origin`: The URL of the origin server to which requests will be forwarded
- `--ttl`: Time-to-live for cached responses in milliseconds

### Example Usage

1. **Request to the Proxy**:
   ```bash
   curl http://localhost:8080/some/resource
   ```

2. **Response Headers**:
    - `X-Cache`: Indicates whether the response was served from the cache (`HIT` or `MISS`).
    - `X-Cache-TTL`: The TTL of the cached response.


## How It Works

1. When a GET request is received, the proxy generates a hash key from the request URI
2. If a valid cached response exists for this key, it's returned with an `X-Cache: HIT` header
3. If no cache entry exists or it has expired, the request is forwarded to the origin server
4. The response from the origin server is cached and returned with an `X-Cache: MISS` header
5. Cached responses include an `X-Cache-TTL` header showing when they will expire
6. A scheduled task periodically removes stale entries from the cache

## Caching Implementation

The caching system is built on an abstract `ICache` interface with a concrete `TimeoutCache` implementation that:

- Stores responses in an in-memory HashMap
- Tracks expiration time for each cached response
- Provides methods to check for staleness
- Automatically invalidates expired cache entries
