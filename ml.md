####Why learn?

* **Learn it when you can’t code it**
  - Complex tasks where deterministic solution don’t suffice 
  - e.g. speech recognition, handwriting recognition 
* **Learn it when you can’t scale it**
  - Repetitive task needing human-like expertise (e.g. recommendations, spam & fraud detection) 
  - Speed, scale of data, number of data points
* **Learn it when you need to adapt/personalize**
  - e.g. personalized product recommendations, stock predictions
* **Learn it when you can’t track it**
  - e.g. automated driving, fraud detection (input-solution mapping changes dynamically) 

####Supervised Learning
**Training:** Given training examples {(Xi, Yi)} where Xi is the feature vector and Yi the target variable, learn a function F to best fit the training data (i.e.,  Yi ≈ F(Xi) for all i)

**Prediction:** Given a new sample X with unknown Y, predict Y using F(X)

#####Classification problem: if target variable Y is categorical

*Model types: Logistic Regression, Decision Trees, Random Forests, Support Vector Machines, Naïve Bayes, etc.*

Examples:
  * Web page classification (e-commerce site? blog?) (Binary)
  * Product categorization (Multi-class)


#####Regression problem: if target variable Y is numeric (ordinal/real-valued)

*Model types: Linear Regression, Regression Trees, Kernel Regression, etc.*

Examples:
  * Forecasting demand

