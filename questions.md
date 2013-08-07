### questions

**Given a list of points, find the k closest points to a given point**

Calculate euclidean distance between given point and all other points, throw Pair(Point p, double distance) into a min heap, poll k times.
*Tip: don't actually have to bother with sqrt() for distance. raw squared distance measure is enough.*

Runtime O(n) distance calc + O(n log n) heap insertions + O(k log n) polls (find is constant, delete-min is log n) = O(n log n)

**7.7) Design an algorithm to find the kth number such that the only prime factors are 3, 5, and 7.**

Numbers whose prime factors are only 3, 5, 7 have the form:
`3^a * 5^b * 7^c`

```
3^0 * 5^0 * 7^0 = 1
3^1 * 5^0 * 7^0 = 3  = 1*3
3^0 * 5^1 * 7^0 = 5  = 1*5
3^0 * 5^0 * 7^1 = 7  = 1*7

3^2 * 5^0 * 7^0 = 9  = 3*3
3^1 * 5^1 * 7^0 = 15 = 3*5
3^1 * 5^0 * 7^1 = 21 = 3*7
...

5*3 = 15 (already in list)
5*5 = 25
5*7 = 35

7*3 = 21 (already in list)
7*5 = 35 (already in list)
7*7 = 49

9*3 = 27
9*5 = 45
9*7 = 63
```

We can express every number as `{3, 5, 7} * (previous numbers in the list)` This means that A_k is `{3, 5, 7} * (some value in {A_1, ... , A_k-1}` and A_k will be the smallest "new" number that can be formed by multiplying each value in the list by 3,5,7.

Basic:
Multiply 3,5,7 against list. We can stop multiplying for a given prime once we find a value greater than the existing max. Now we just need to compare "candidate" numbers across the three primes.
```
A_4 = {3, 5, 7, 3*3}
A_5 = {3, 5, 7, 9, 3*5}
A_6 = {3, 5, 7, 9, 15, 3*7}
A_7 = {3, 5, 7, 9, 15, 21, (options > 21: 3*9, 5*5, 7*5 ... 5*5=25)}
A_8 = {3, 5, 7, 9, 15, 21, 25, (options > 25: 3*9, 5*7, 7*5 ... 3*9=27)}
```
runtime?

Better:
Generate candidate numbers off of the 1 ... kth number (every time we remove).
```
0th itr: {3, 5, 7}
1st itr (A_1): Remove 3, {5, 7, 3*3, 3*5, 3*7}
2nd itr (A_2): Remove 5, {7, 9, 15, 21, 5*3, 5*5, 5*7}
3rd itr (A_3): Remove 7, {9, 15, 21, 15, 25, 35, 7*3, 7*5, 7*7}
...
```
We ensure that we'll always add the smallest 'next' number, because we're always 'discovering' the next numbers off of the removed minimum (... 7*3, 7*5, 7*7 are larger at the end of the 3rd itr, but we'll still discover 9*3 because we're calculating off of minimum).


**8.2) Design a call center with three levels of employees: respondent, manager, and director. Incoming call must first be allocated to a free respondent. If respondent can't handle, he must escalate to manager. If manager isn't free or not able to handle, call must escalate to director.**

```
public class CallHandler {
  List<List<Call>> callQueues;
  List<List<Employee>> freeQueues;

  public void dispatchCall(Call call) {
    Employee e = getNextFree();
    if (e == null) {
      // no available emps, add to call queue
    }
    e.receiveCall(call);
  }
}

private Employee getNextFree() { 
  // poll respondent free queue ...
}

public class Call {
  private Caller caller;
  private Rank minRank;
}

public enum Rank {
  Respondent(0), Manager(1), Director(2);
  private final int r;
  Rank(int r) { this.r = r }
}

// no need to instantiate Employee directly
public abstract class Employee {
  private Call currentCall;
  private Rank rank;
  
  public void receiveCall(Call c) { 
    this.currentCall = c;
    if (rank < c.minRank) {
      // can't handle this call, need to escalate, then free ourself
      escalate(c);
      ...
    }
  }
  
  private void escalate(Call c) {
    // add to call queue for next level rank
  }
}

public class Respondent extends Employee {
  public Respondent() { rank = Rank.Respondent; }
} ...
```
