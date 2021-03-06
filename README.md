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


#### Configuration
##### Change port number
For example use 9001 port.

Use ```-p``` or ```-port``` parameter

    > server -p 9001
    or
    > server -port 9001

##### Change base directory
For example use ./tmp as base directory

Use ```-b``` or ```-base``` parameter

    > server -b tmp
    or
    > server -base tmp

### mock task
#### Add mock

    > mock -a <URI> <RESPONSE|RESOURCE_FILE_PATH>
    or
    > mock -add <URI> <RESPONSE|RESOURCE_FILE_PATH>

You can add mock-up routing.

For example, ```mock -a /api/foobar.json {"foo":"bar"}```

`http://localhost:9000/api/foorbar.json`

return 

```json
{
    "foo" : "bar"
}
```

For example, you have a file like this

```./tmp/users.json
[{"name": "John Smith", "age": 33}, {"name": "Justin Randall Timberlake", "age": 34}]
```

and use mock command

```mock -a /api/users ./tmp/users.json```

`http://localhost:9000/api/users`

return

```json
[
    {
        "name": "John Smith", 
        "age": 33
    }, 
    {
        "name": "Justin Randall Timberlake", 
        "age": 34
    }
]
```

#### List all mock

    > mock -l
    or
    > mock -list

You can show all mock

For example,

```
/ : sbt-server is running.
/api/foobar.json {"foo":"bar"}
/api/users ./tmp/users.json
/hello/world : Hello world
```