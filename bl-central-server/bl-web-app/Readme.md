# bridge-logistics-webapp

## How to start

-   Via the runnable package
```bash
java -jar jetty-runner.jar --port 8080 target/bl-web-app-0.0.0-SNAPSHOT.war
```

-   Via the maven plugin
```bash
mvn jetty:run
```

## Hints & Tricks

-  [Serializing/Deserializing in Java 14](https://github.com/FasterXML/jackson-future-ideas/issues/46)

```text
ObjectMapper objectMapper = new ObjectMapper();
JacksonAnnotationIntrospector implicitRecordAI = new JacksonAnnotationIntrospector() {
    @Override
    public String findImplicitPropertyName(AnnotatedMember m) {
        if (m.getDeclaringClass().isRecord()) {
            if (m instanceof AnnotatedParameter parameter) {
            return m.getDeclaringClass().getRecordComponents()[parameter.getIndex()].getName();
        }
        if (m instanceof AnnotatedMember member) {
            for (RecordComponent recordComponent : m.getDeclaringClass().getRecordComponents()) {
                if (recordComponent.getName().equals(member.getName())) {
                    return member.getName();
                }
            }
        }
    }
    return super.findImplicitPropertyName(m);
}
};
objectMapper.setAnnotationIntrospector(implicitRecordAI);
```

## References

-   [Program for distance between two points on earth](https://www.geeksforgeeks.org/program-distance-two-points-earth/)
-   [Support for record types in JDK 14 #46](https://github.com/FasterXML/jackson-future-ideas/issues/46)
-   [jetty://](https://www.eclipse.org/jetty/documentation/current/index.html)

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
