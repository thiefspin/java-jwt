# JWT

This library provides you with the functionality to encode and decode JWT tokens with a range of different algorithms.

## Build status
![Build Status](https://github.com/thiefspin/java-jwt/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=master)

## Basics Usage

### Encode a payload

```java
static String KEY = "a-very-secret-key";
final String payload = "Some payload"; //This can be a json string
    
//Example 1
JwtEncodeResult result = Jwt.encode(KEY, payload);
if (result.getToken().isPresent()) {
    //Do something with token    
} 
if (result.getException().isPresent) {
    //Handle error    
}

//Example 2 (Apply function only if result was successful)
result.foreach(token -> {
    //Do something with the token    
});
```

### Decode a token
```java
static String KEY = "a-very-secret-key";
final String token = "header.payload.signature";

JwtDecodeResult result = Jwt.decode(token, KEY);

if(result.getException.isEmpty()) {
    Optional<JwtHeader> header = result.getHeader();
    Optional<String> payload = result.getPayload();
    
    //Serialize header as JSON if you wish
    String headerJson = header.toJson();
}
```