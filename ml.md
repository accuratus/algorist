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
  - Quadratic Features: derive new non-linear features by combining feature pairs. Eg: People with a Master degree in Business (Quadratic feature over Education + Occupation) make much more money than people with just "Masters" or "Business" degrees. Helps capture this "cross feature" relationship.
* Domain-specific transformations (eg: text)
  - Text features:
    - N-grams, contiguous subsequences of n words - to capture multi-word concepts. 2-grams of Black Hawk Down: {Black_Hawk, Hawk_Down}. "Bank" can have different meanings based on context: river bank, financial bank. N-grams help compensate for this ambiguity.
    - Parts of speech/Ontology tagging: Focus on words with specific roles 
    - Stop-words removal/Stemming: Helps focus on semantics 
    - Lowercasing, punctuation removal: Helps standardize syntax
    - Cutting off very high/low percentiles: Reduces feature space without substantial loss in predictive power
    - TF-IDF normalization: Corpus wide normalization of word frequency

####Feature Selection
* Often,  “Less is More“
  - Better generalization behavior, useful to prevent "overfitting"
  - More robust parameter estimates with smaller number of non-redundant features

####Parameter Tuning
* Loss function
  - Squared: regression, classification
  - Hinge: classification only, more robust to outliers
  - Logistic: classification only, better for skewed class distributions
* Number of passes
  - More passes: better fit on training data, but diminishing returns
* Regularization
  - Prevent overfitting by constraining weights to be small
* Learning parameters (e.g. decay rate)
  - Decaying too aggressively: algorithm never reaches optimum
  - Decaying too slowly: algorithm bounces around, never converges to optimum
* Use **k-fold cross-validation** to evaluate model performance for a given parameter setting
  - Randomly split training data into k parts
  - Train models on k training sets, each containing k-1 parts
  - Test each model on remaining part (not used for training)
  - Average k model performance scores

####Evaluation Metrics (Classification)
    Confusion matrix for binary classification
    ------------------------------------------------
    |                 | Actual true | Actual false |
    | Predicted true  | TP          | FP           |
    | Predicted false | FN          | TN           |
    ------------------------------------------------

* Precision = TP/(TP+FP): How correct are you on the ones you predicted +1?
* Recall = TP/(TP+FN): What fraction of actual +1's did you correctly predict?
* True Positive Rate (TPR) = Recall
* False Positive Rate (FPR) = FP/(FP+TN): What fraction of -1's did you wrongly predict?

**AUC: Area under ROC curve**, plots TPR vs. FPR for different thresholds. A perfect model has AUC=1, random chance has AUC=0.5

![roc curve comparison](http://gim.unmc.edu/dxtests/roccomp.jpg)

Example: Imagine fraud classification of orders. x-axis (FPR) represents %cumulative non-frauds, where y-axis (TPR) represents %cumulative frauds. You want to capture as large %cumulative frauds with as few %cumulative non-frauds as possible. Deciding where this point lies is an operational/business decision - the user has to choose.

Example: Imagine email spam detection. On the X-axis is the percentage of good emails that are going to be affected by our action – in this case, sidelining emails into spam folder. On the y-axis is the percentage of spam we are capturing. That knee in the ROC curve is a great tradeoff point. If we set our operating cutoff at that point, we’ll get a good *balance between the good population we impact, and the bads we capture*. Of course, if there are different costs to sidelining a population and capturing the bads, we can shift that operating point to the left or to the right.

####Evaluation Metrics (Regression)
* Root Mean Square Error (RMSE)
* Mean Absolute Percent Error (MAPE)

#### Handling Imbalanced Datasets
Many applications have skewed class distribution (e.g. clicks vs non-clicks). The majority class may dominate, class boundary cannot be learned effectively

Strategies include:
* Downsampling: Downsample examples from majority class
* Oversampling: Assign higher importance weights to examples from minority class

####Overall Modelling Guidelines
* **The more training examples, the better**
  - Large training sets lead to better generalization to unseen examples
* **The more features, the better**
  - Invest time in feature engineering to construct features with signal
* **Evaluate model performance on separate test set**
  - Tune model parameters on separate validation set (and not test set)
* **Pay attention to training data quality**
  - Garbage in Garbage out, Remove outliers, target leakers
* **Select evaluation metrics that reflect business objectives**
  - AUC may not always be appropriate, Log-likelihood, Precision@K
* **Retrain models periodically**
  - Ensure training data distribution is in sync with test data distribution
