### prep
http://steve-yegge.blogspot.com/2008/03/get-that-job-at-google.html


**OOP Principles**

Encapsulation (information hiding)
* An object's data & logic is hidden from other objects, such that the only thing an object knows about another, is it's interface.

Inheritance
* Ability of a new class to be created, from an existing class by extending it.

Polymorphism
* The ability for one type to express some sort of contract, and for other types to implement that contract (whether through class inheritance or not) in different ways, each according to their own purpose. Code using that contract should not have to care about which implementation is involved, only that the contract will be obeyed.
* Declaring a uniform interface that isn't type aware, leaving implementation details to concrete types that implement the interface.

**Streaming Algorithms, Sketch, Probalistic Data Structures**

* http://en.wikipedia.org/wiki/Streaming_algorithm
* http://lkozma.net/blog/sketching-data-structures/
* http://en.wikipedia.org/wiki/Bloom_filter

**Database / Cache Servers**
* **Redis**: *In-memory, non-relational database*. Stores Strings, lists, sets, hashes, sorted sets
* **memcached**: *In-memory, key-value cache*. Stores Mapping of keys to values
* **MySQL**: *Relational database*. Stores Databases of tables of rows, views over tables, spatial and third-party extensions
* **MongoDB**: *On-disk non-relational document store*. Stores Databases of tables of schema-less BSON documents
