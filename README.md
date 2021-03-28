<div align="center">
    <h1>UrlShortner</h1>
</div>

## 1. Description

<b>URL을 입력받아 짧게 줄여주고, Shortening된 URL을 입력하면 원래 URL로 리다이렉트하는 서비스</b>


## 2. Environment

- JDK 1.8
- Spring Boot 2.3.7.RELEASE
- Spring DataJPA
- Gradle 6.8
- lombok
- Mustache
- H2

## 3. Prerequisite

- profile='local', local_port=8080

## 4. Usage

1. Install

    - `$ git clone https://github.com/lombredansleau/UrlShortner.git`


2. Build

    - `$ ./gradlew build`
    - 빌드 시 'Permission dinied' 발생한 경우
        - `$. chmod +x ./gradlew` 후 다시 빌드


3. Execute

    - `$ java -jar ./build/libs/UrlShortner-1.0-SNAPSHOT.jar`
    