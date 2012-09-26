# JID

Fast deserialization/reserialization is critical for databases. The key is to
deserialize only what you need and to keep the serialized data for items that have
not been changed for reserialization after an update.

Application are built by subclassing existing JID actors and then composing more
complex structures from them. The JID project includes a number of actors which
support different types of persistent data.

License: [LGPL](http://www.gnu.org/licenses/lgpl-2.1.txt)

Dependencies: Java 6, Maven 2, [JActor](https://github.com/laforge49/JActor)

Available on [The (Maven) Central Repository](http://search.maven.org/#search|ga|1|org.agilewiki),
so you just need to add this to your POM file:
```
<dependency>
      <groupId>org.agilewiki.jid</groupId>
      <artifactId>jid</artifactId>
      <version>1.5.0</version>
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

## Deserialization / Update / Reserialization

JID can deserialize/update/reserialize a map of 10,000 entries in .9 milliseconds,
where deserialization/reserialization timings are largely independent of the size
and complexity of the table entries. That's 90 nanoseconds per table entry, where
the entries are actor key / actor value pairs.

### Test Environment
```
i7-3770 @ 3.40 GHz
ASUS P8Z77-M Motherboard
16 GB Corsair DDR3 1600 MHz RAM
Vertex 3 SATA III SSD
```
## Documentation
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
