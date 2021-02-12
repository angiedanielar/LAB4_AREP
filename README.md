# LABORATORY - APPLICATION SERVER ARCHITECTURE WORKSHOP, OBJECT META PROTOCOLS, IOC PATTERN, REFLECTION. 🚀

## Escuela Colombiana de Ingeniería - Enterprise Architectures(AREP).

_In this laboratory, I built a web server (Apache type, which can respond to multiple non-concurrent requests.) In Java, the server can deliver html pages and PNG type images, and use the IoC framework to build web applications . From POJOS. Using the above content, I built a sample web application and deployed it on Heroku._

## Getting Started

### Prerequisites

- [Maven](https://maven.apache.org/) - Dependency Management.

- [Java 8](https://www.oracle.com/co/java/technologies/javase/javase-jdk8-downloads.html) -  Development Environment.

- [Git](https://git-scm.com/) - Version Control System.

Make sure you have this programs installed correctly and the version that we need with the following commands:

```
mvn --version
```

```
git --version
```

```
java -showversion
```

### Installing

1. Clone the repository:

```
git clone https://github.com/angiedanielar/LAB3_AREP.git
```

2. Compile the projet:

```
mvn package
```

3. Executing the program:

```
mvn exec:java -D "exec.mainClass"="edu.escuelaing.arep.App"

And put this link in your browser: http://localhost:36000/index.html
```

- Or go to the [web page]()

4. Generating the documentation:

```
mvn javadoc:javadoc
```

- [View Documentation Ubication](https://angiedanielar.github.io/LAB4_AREP/apidocs)

## Results

_jj._


![Imagen 1](resources/images/resultados.png)

## Built With

- [Maven](https://maven.apache.org/) - Dependency Management

- [Java 8](https://www.oracle.com/co/java/technologies/javase/javase-jdk8-downloads.html) -  Development Environment.

- [Git](https://git-scm.com/) - Version Control System.

- [CircleCI](https://circleci.com/) [![CircleCI](https://circleci.com/gh/circleci/circleci-docs.svg?style=svg)](https://app.circleci.com/pipelines/github/angiedanielar/LAB4_AREP) - Continuous Integration.

- [Latex](overleaf.com) - Text composition system.

- [Heroku](https://www.heroku.com/platform) - Deploy platform.

## Inform

- [View the Design Inform](https://github.com/angiedanielar/LAB4_AREP/blob/master/Inform.pdf)

## Author

- Angie Daniela Ruiz Alfonso.

## License

This project is under GNU General Public License - see the [LICENSE](LICENSE) file for details.
