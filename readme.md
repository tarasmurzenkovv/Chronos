**How to run application?**
1. cd into cloned repository
2. execute ./gradlew build -Dskip.tests=true
2. cd into ./build/libs/
3. execute java -jar *.jar -Dspring.datasource.url=jdbc uri of DB -Dspring.datasource.username=db user name -Dspring.datasource.password=db password
