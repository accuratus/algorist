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

####Linear Models
An important class of models parameterized by weights W. eg. 
  F(X) = 5∙age + 0.0003∙income

Training: Learn weights W that minimize loss.

Prediction:
  * Regression: Y= W∙X
  * Classification: if W∙X > threshold T then Y = +1 else Y = -1

Overfitting problem: Model fits training data well (low training error) but does not generalize well to unseen data (poor test error). Complex models with large #parameters capture not only good patterns (that generalize) but also noisy ones.

####Bias-Variance Tradeoff
Bias: Difference between average model prediction and true target value

Variance: Variation in predictions across different training data samples
![bias vs variance](http://www.kdnuggets.com/2012/09/bias-vs-variance.jpg)

* Simple models with small #parameters have high bias and low variance
  - E.g. Linear models with few features
  - Reduce bias by increasing model complexity (adding more features, decreasing regularization)
* Complex models with large #parameters have low bias and high variance
  - E.g. Linear models with large number of sparse features
  - Reduce variance by increasing training data and decreasing model complexity (feature selection, aggressive regularization)

####Data Preparation
* Transform data to appropriate input format
  - CSV format, headers specifying column names and data types
  - Filter XML/HTML from text 
* Split data into train and test files
  - Training data used to learn models
  - Test data used to evaluate model performance 
* Randomly shuffle data
  - Speeds convergence of online training algorithms 
* Feature scaling (for numeric attributes)
  - Subtract mean and divide by standard deviation -> zero mean, unit variance
  - Speeds convergence of gradient-based training algorithms

####Data Visualization & Analysis
**A better understanding of data allows for better feature engineering & modelling.**

The idea is to identify features (with signal) that are correlated with target.
* Feature and target summaries
  - Feature and target data distribution, histograms 
  - Identify outliers in data, detect skew in feature/class distribution
* Feature-Target correlation
  - Correlation measures like mutual information, Pearson’s correlation coefficient
  - Class distribution conditioned on feature values, scatter plots 
  - Identify features with predictive power, target leakers

####Feature Engineering
**Constructing new features with predictive power from raw data can boost model performance.**

There are many types of feature transformations:
* Non-linear feature transformations for linear models 
  - Numeric Value Binning: introduces non-linearity into linear models. Binning strategies include: equal size ranges, equal number of examples.
  - Quadratic Features: derive new non-linear features by combining feature pairs.
* Domain-specific transformations (eg: text)
  - Text features:
    - N-grams, contiguous subsequences of n words - to capture multi-word concepts. 2-grams of Black Hawk Down: {Black_Hawk, Hawk_Down}. "Bank" can have different meanings based on context: river bank, financial bank. N-grams help compensate for this ambiguity.
    - Parts of speech/Ontology tagging: Focus on words with specific roles 
    - Stop-words removal/Stemming: Helps focus on semantics 
    - Lowercasing, punctuation removal: Helps standardize syntax
    - Cutting off very high/low percentiles: Reduces feature space without substantial loss in predictive power
    - TF-IDF normalization: Corpus wide normalization of word frequency
