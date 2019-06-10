# cfj

An example of calling Clojure from Java by defining a set of Java interfaces,
then using the Clojure Java API to start invoking Clojure code that implements
those interfaces.

## Setup

This is a stock lein template, it only adds the :java-src attribute to define
Java source, which will be compiled first. Notably, there is no Clojure
AOT or compilation required (the Clojure Java API has the hooks we need).

Files:

* java/cfj/Event.java - Event interface
* java/cfj/Support.java - static class for API users
* src/cfj/core.clj - functions implementing the interface

## Build

lein will compile first the Java source, and then the Clojure src (which is a
no op, as no AOT is needed). Both compiled Java and Clojure source will be
packaged into a jar.

We can do the whole shebang with just the uberjar task:

```
$ lein uberjar
Compiling 3 source files to /Users/alex/tmp/clojure-from-java/target/classes
Created /Users/alex/tmp/clojure-from-java/target/cfj-0.1.0-SNAPSHOT.jar
Created /Users/alex/tmp/clojure-from-java/target/cfj-0.1.0-SNAPSHOT-standalone.jar
```

## Test from Java

As of Java 9, there is now a Java shell, kind of like a poor REPL, but
serviceable here - note that you need to include the uberjar on the
classpath (it has the project code + Clojure).

First we import the Event interface (defined in Java) and the Support
class, which uses the Clojure Java API to load and run Clojure functions.
Then we use Support.createEvent() to cause our Clojure code to be loaded,
and exercise it with the Java API.

```
amac:clojure-from-java alex$ jshell -class-path target/cfj-0.1.0-SNAPSHOT-standalone.jar
|  Welcome to JShell -- Version 12
|  For an introduction type: /help intro

jshell> import cfj.Event;

jshell> import cfj.Support;

jshell> Event e = Support.createEvent("abc");
e ==> cfj.core.EventImpl@4d2a576e

jshell> e.getTimestamp();
$4 ==> Sun Jun 09 23:08:03 CDT 2019

jshell> e.getName();
$5 ==> "abc"
```

Here, the Event e is a Clojure record instance (could have been an anonymous reify too).
The getTimestamp() and getName() methods are Clojure code. Java users don't know or care.

## License

Copyright © 2019 Alex Miller

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
