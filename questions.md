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

**9.1) A child is running up a staircase with n steps, and can hop either 1 step, 2 steps, or 3 steps at a time. Implement a method to count how many possible ways the child can run up the stairs.**

```
public int steps(int n) {
  if (n < 0) return 0;
  if (n == 0) return 1;
  return steps(n-1)+steps(n-2)+steps(n-3);
}
```
We're only interested in the *number* of combinations, so as long as we got to f(0) somehow, we count that as one valid combination. Runtime: exponential O(3^n) since each call branches out three more times.

We can do better, by memoizing.
```
public int steps(int n, int[] map) {
  if (n < 0} return 0;
  if (n == 0) return 1;
  if (map[n] != 0) {
    return map[n];
  } else {
    map[n] = steps(n-1, map)+
             steps(n-2, map)+
             steps(n-3, map);
    return map[n];
  }
}
```
Runtime ??

**9.2) A robot sits on the upper left corner of an X by Y grid. The robot can only move right & down. How many possible paths are there for the robot to go from (0,0) to (X,Y)?**

Path will have X right steps + Y down steps. Combinatorics: there are X+Y steps to get to the target, and X of them are right-steps. For example: for target (2,2) all possible paths are:
```
RRDD RDRD RDDR
DDRR DRDR DRRD
```
So, the number of total paths must be the number of ways of selecting X right-steps out of X+Y total steps. This is the binomial expression "n choose r" <img src="http://latex.codecogs.com/svg.latex?\dbinom{n}{r}=\frac{n!}{r!(n-r)!}"> and, for our problem: <img src="http://latex.codecogs.com/svg.latex?\dbinom{X+Y}{X}=\frac{(X+Y)!}{X!Y!}">

**FOLLOW UP: Certain spots are off limits, so that the robot cannot step on them. Design an algorithm to find a path from top-left to bottom-right.**

We can start from the target, and try to find our way to the beginning.
```
public boolean findPath(int x, int y, ArrayList<Point> path) {
  path.add(new Point(x, y));
  if (x == 0 && y == 0) { // we've found a path!
    return true;
  }
  
  boolean success = false;
  if (x >= 1 && isFree(x-1, y)) { // try left!
    success = findPath(x-1, y, path);
  }
  
  if (!success && y >= 1 && isFree(x, y-1) { // left didn't work out, so try up!
    success = findPath(x, y-1, path);
  }
  
  if (!success) { // neither left or up worked out, so let's discard this path
    path.remove();
  }
  
  return success;
}
```

Notice that different paths may route through some common point. This is illustrated by the fact that if we were at (X-1, Y), that the next points we would check are (X-2, Y) & (X-1, Y-1), and if we were at (X, Y-1), then the next points we would check are (X-1, Y-1) & (X, Y-2). (Common point denoted as '!' below)
```
. . . . .
. . . . .
. . . . c
. . . ! x
. . c x t
```
Since we'd be doing redundant work, we can memoize previous path explorations off that common point, effectively "marking" off portions of known failed space.
```
public void findPath(int x, int y, ArrayList<Point> path, HashMap<Point, Boolean> memo) {
  Point p = new Point(x,y);
  path.add(p);
  
  if (x == 0 && y == 0) { // we've found a path!
    return true;
  }
  
  if (memo.containsKey(p)) {
    return memo.get(p)  // we've already computed the path from this point, reuse the result
  }
  
  boolean success = false;
  if (x >= 1 && isFree(x-1, y)) { // try left!
    success = findPath(x-1, y, path);
  }
  
  if (!success && y >= 1 && isFree(x, y-1) { // left didn't work out, so try up!
    success = findPath(x, y-1, path);
  }
  
  if (!success) { // neither left or up worked out, so let's discard this path
    path.remove();
  }
  
  memo.put(p, success);
  return success;
}
```

**9.4) Write a method to return all subsets of a set**

{ A, B, C, D }
```
[]
A - AB - ABC - ABCD
         ABD
    AC - ACD
    AD
    
B - BC - BCD
    BD
    
C - CD

D
```
```
public void subset(List<String> chosen, List<String> remain) {
  if (remain.isEmpty()) { return; }
  
  System.out.println(chosen);
  for(int i=0; i<remain.length(); i++) {
    ArrayList<String> newc = new ArrayList<String>(chosen);
    ArrayList<String> newr = new ArrayList<String>();
    newc.add(s);
    if (i+1 != remain.length()) {
      newr.addAll(remain.subList(i+1, remain.length()));
    }
    subset(newc, newr);
  }
}
```
Runtime: O(2^n) because every element has the "choice" of being in the subset or not { 2 * 2 * 2 ... }

**9.5) Write a method to compute all permutations of a string**
```
public void permute(List<String> chosen, List<String> remain) {
  if (remain.isEmpty()) {
    System.out.println(chosen);
  }
  
  for(String s : remain) {
    chosen.add(s);
    ArrayList<String> newr = new ArrayList<String>(remain);
    newr.remove(s);
    permute(chosen, newr);
    chosen.remove(s);
  }
}
```

**9.6) Write an algorithm that prints all valid combinations of n-pairs of parenthesis.**

*Top-down (we don't need intermediate solutions, and only need to return complete solutions)*
```
public void parens(int open, int close, String s) {
  if (open == 0 && close == 0) {
    System.out.println(s);
  }
  
  if (open > 0) {
    parens(open-1, close, s + "(");
  }
  
  if (close != 0 && close > open) {
    parens(open, close-1, s + ")");
  }
}
```

*Bottom-up (we structure it this way to make it possible to memoize for DP-based solution)*
```
public static List<String> parens(int openleft, int closedleft) {
  List<String> results = new ArrayList<String>();

	if (openleft == 0 && closedleft == 0) {
		results.add("");
		return results;
	}

	if (openleft > 0) {
		List<String> openres = parens(openleft-1, closedleft);
		for (String s : openres) {
			results.add("(" + s);
		}
	}
	
	if (closedleft != 0 && closedleft > openleft) {
		List<String> closedres = parens(openleft, closedleft-1);
		for (String s : closedres) {
			results.add(")" + s);
		}
	}
  
  return results;
}
```

*Bottom-up w/ memoization (reusing partial solutions)*
```
private static Map<Integer, Map<Integer, List<String>>> memos = new HashMap<Integer, Map<Integer, List<String>>>();

public static List<String> parens(int openleft, int closedleft) {
	List<String> results = new ArrayList<String>();

	if (openleft == 0 && closedleft == 0) {
		results.add("");
		return results;
	}

	if (openleft > 0) {
		if (memos.get(openleft-1) == null) {
			memos.put(openleft-1, new HashMap<Integer, List<String>>());
		}
		List<String> openres;
		List<String> memoed = memos.get(openleft-1).get(closedleft);
		if (memoed == null) {
			openres = parens(openleft-1, closedleft);
			memos.get(openleft-1).put(closedleft, openres);	
		} else {
			openres = memoed;
		}
		for (String s : openres) {
			results.add("(" + s);
		}
	}
	
	if (closedleft != 0 && closedleft > openleft) {
		if (memos.get(openleft) == null) {
			memos.put(openleft, new HashMap<Integer, List<String>>());
		}
		List<String> closedres;
		List<String> memoed = memos.get(openleft).get(closedleft-1);
		if (memoed == null) {
			closedres = parens(openleft, closedleft-1);
			memos.get(openleft).put(closedleft-1, closedres);
		} else {
			closedres = memoed;
		}
		for (String s : closedres) {
			results.add(")" + s);
		}
	}
	
	return results;
}
```

**9.8) Given an infinite number of quarters, dimes, nickels, and pennies, calculate the number of ways of representing n cents.**

This avoids counting the permutations of the same coins (eg: 1+5+5 then 5+1+5), by never using larger denominations after using a smaller one (nextDenom is always the next smallest coin). The base case is when nextDenom is 1, meaning that the rest of the amount would be filled by pennies.

```
public int makeChange(int n, int denom) {
	int nextDenom;
	switch (denom) {
		case 25:
			nextDenom = 10;
		case 10:
			nextDenom = 5;
		case 5:
			nextDenom = 1;
		case 1:
			return 1;
	}
	
	int ways = 0;
	for (int i=0; i*denom <= n; i++) {
		ways += makeChange(n - i*denom, nextDenom);
	}
	
	return ways;
}
```
