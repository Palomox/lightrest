# LightRest

Simple jetty-based library to create a light REST api.

Some of the benefits of this lib are that creating a REST api is as easy as creating a class and annotating it, and that it supports any kind of auth, although it requires the user to code an adapter for it. The biggest advantage, and the reason I made it is because you can restrict rest methods with Google Zanzibar relations requirements. You have to write an implementation for the implementation you're using, though

## Using LightRest

[![Maven Central](https://img.shields.io/maven-central/v/ga.palomox.lightrest/lightrest.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22ga.palomox.lightrest%22%20AND%20a:%22lightrest%22)

LightRest is published on maven central, so using it is as easy as adding it to your build system as a maven dependency:

```md
groupId: ga.palomox.lightrest
artifactId: lightrest
version: Indicated with a badge on top of this section
```

This means, in maven, you have to add the following to your pom.xml

```xml
<dependency>
  <groupId>ga.palomox.lightrest</groupId>
  <artifactId>lightrest</artifactId>
  <version>{Indicated with a badge on top of this section}</version>
</dependency>
```

or, with gradle, this to your build.gradle

```groovy
dependencies{
	implementation 'ga.palomox.lightrest:lightrest:{Version ndicated with a badge on top of this section}'
}
```
