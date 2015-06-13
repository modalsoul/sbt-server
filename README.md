sbt-server
============

sbt-server is a sbt plugin which provide simple http service.

Requirements
------------

* sbt 0.13.x

Setup
-----

### Using Plugin on GitHub

Add lines to your `project/plugins.sbt`

```scala
lazy val root = project.in(file(".")).dependsOn(githubRepo)

lazy val githubRepo = uri("git://github.com/modalsoul/sbt-server.git")
```

Usage
-----
### server task

    > server

Default http port number is 9000.

You can use 

`http://localhost:9000/`


### Configuration
#### Change port number
For example use 9001 port.

Use ```-p``` or ```-port``` parameter

    > server -p 9001
    or
    > server -port 9001

#### Change base directory
For example use ./tmp as base directory

Use ```-b``` or ```-base``` parameter

    > server -b tmp
    or
    > server -base tmp


