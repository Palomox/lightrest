# LightRest

Simple jetty-based library to create a light REST api.

Some of the benefits of this lib are that creating a REST api is as easy as creating a class and annotating it, and that it supports any kind of auth, although it requires the user to code an adapter for it. The biggest advantage, and the reason I made it is because you can restrict rest methods with Google Zanzibar relations requirements. You have to write an implementation for the implementation you're using, though

## Using LightRest

LightRest is published on maven central, so using it is as easy as adding it to your maven or gradle buildfile:

```
groupId: ga.palomox.lightrest
artifactId: lightrest
version: 1.0.0
```
