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

http://www.research.ibm.com/software/IBMResearch/multimedia/IJCNN2013.corelet-language.pdf


Why Object-Oriented Methodology?

From the perspective of a language designer, objectoriented programming (OOP) is the ideal method for implementing corelets for at least three reasons.

First, by deﬁnition, a corelet encapsulates all the details of a TrueNorth program except for external inputs and outputs. Encapsulation is also a fundamental feature of OOP. Therefore, corelet encapsulation can be guaranteed by deﬁning a corelet class and then instantiating corelets as objects from this class.

Second, all corelets must use similar data structures and operations, and must be accessed by users in similar ways. This similarity can be achieved by another fundamental feature of OOP, inheritance, which allows the underlying data structures and operations to be deﬁned once for an abstract class and then passed down to abstract subclasses derived from it, as well as to object instances of the class.

Third, we need to invoke operations such as "decompose" (to translate them into a TrueNorth program) and “verify” (to
ensure that they are correct and consistent with respect to TrueNorth) on all corelets. Each operation is named homogeneously across mutiple corelets, but can be heterogeneously deﬁned for different corelets. These operations can be implemented by polymorphism - another fundamental feature of OOP.


**Difference between HashMap & Hashtable?**
HashMap is not synchronized, Hashtable is. Hashtable doesn't allow null keys or values.
