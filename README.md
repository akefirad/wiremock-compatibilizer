# Wiremock Compatibilizer (1.58 -> 2.5.1)
It makes packages compiled with WireMock version 1.58 forward compatible with WireMock version 2.5.1.

To build the library:
- Java7: Append `-Djava.version=1.7 -Dmaven.compiler.executable=/path/to/target/javac` (both are optional if `JAVA_HOME` points to JDK 7)
- Java8: Append `-Djava.version=1.8 -Dmaven.compiler.executable=/path/to/target/javac` (the second argument is optional if `JAVA_HOME` points to JDK 8)

How to use:
- See `com.akefirad.wiremock.compatiblizer.samples` package for how to use it.
- See `wiremock-base-version-tests` and `wiremock-latest-version-tests` projects for verification.


