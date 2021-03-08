##This is my common project dependency
###Usage
Add the repository and dependency to pom.xml
```
    <repositories>
        <repository>
            <id>hxb-maven-repo</id>
            <url>https://raw.githubusercontent.com/hexinbang/maven-repo/master/common</url>
        </repository>
    </repositories>
```

```
    <dependency>
        <groupId>com.hxb</groupId>
        <artifactId>common</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
```
#### deploy cmd
```
mvn deploy -DaltDeploymentRepository=hxb-maven-repo::default::file:/home/hxb/repo/common/
```