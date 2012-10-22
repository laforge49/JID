# JID

Fast deserialization/reserialization is critical for databases. The key is to
deserialize only what you need and to keep the serialized data for items that have
not been changed for reserialization after an update.

Application are built by subclassing existing JID actors and then composing more
complex structures from them. The JID project includes a number of actors which
support different types of persistent data.

| Entries | Balanced List Jid | Balanced Map Jid |
| ------- | ----------------- | ---------------- |
| 1,000 | .014 ms | .027 ms |
| 10,000 | .035 ms | .056 ms |
| 100,000 | .24 ms | .48 ms |
| 1,000,000 | 2.9 ms | 6.9 ms |

Times given above were for updating a byte array containing a serialized list or table,
i.e. the time to deserialize, insert a new value (in the case of a list) and reserialize.
(Tests performed on an i7-3770 @ 3.40 GHz)

License: [LGPL](http://www.gnu.org/licenses/lgpl-2.1.txt)

Dependencies: Java 6, Maven 2, [JActor](https://github.com/laforge49/JActor)

Available on [The (Maven) Central Repository](http://search.maven.org/#search|ga|1|org.agilewiki),
so you just need to add this to your POM file:
```
<dependency>
      <groupId>org.agilewiki.jid</groupId>
      <artifactId>jid</artifactId>
      <version>2.0.0-RC4</version>
</dependency>
```
(Be sure to update the version number appropriately, of course.)

| Links |
| --------------- |
| [Releases](https://freecode.com/projects/jid/releases) |
| [Announcements](https://freecode.com/projects/jid/announcements) |
| [Downloads](https://sourceforge.net/projects/jactor/files/) |
| [API](http://jactor.sourceforge.net/) |
| VCS: [Files](https://github.com/laforge49/JID/), [Commits](https://github.com/laforge49/JID/commits/master) |
| [Issues](https://github.com/laforge49/JID/issues/) |
| [free(code)](http://freecode.com/projects/jid/) |
| Google group: [agilewikidevelopers](http://groups.google.com/group/agilewikidevelopers/) |

## Documentation
1. [The problem with Java Serialization](https://www.ibm.com/developerworks/mydeveloperworks/blogs/jactor/entry/the_problem_with_java_serialization53?lang=en)

    There are a number of problems with Java serialization and numerous alternatives have been developed.
    But my focus here is on a particular use case, databases, and a single issue, performance...
    [more](https://www.ibm.com/developerworks/mydeveloperworks/blogs/jactor/entry/the_problem_with_java_serialization53?lang=en)

1. [Introducing JID](https://www.ibm.com/developerworks/mydeveloperworks/blogs/jactor/entry/introducing_jid14?lang=en)

    Java Incremental Deserialization/reserialization (JID) provides a near-ideal solution for updating serialized data structures...
    [more](https://www.ibm.com/developerworks/mydeveloperworks/blogs/jactor/entry/introducing_jid14?lang=en)

## Wiki
1.  [Jid Trees](https://github.com/laforge49/JID/wiki/Jid-Trees)
1.  [Jid Factories](https://github.com/laforge49/JID/wiki/JidFactories)
1.  [Applications](https://github.com/laforge49/JID/wiki/Applications)
1.  [Serialized Data](https://github.com/laforge49/JID/wiki/Serialized-Data)

## Dependent Projects
*   [JActor Sockets](https://github.com/laforge49/JASocket)
*   [File Persistence](https://github.com/laforge49/JFile)

## Contact
*   email:   laforge49@gmail.com
*   twitter: [@laforge49](https://twitter.com/laforge49)
*   web:     [JActor Consulting, plc](http://jactorconsulting.com)
