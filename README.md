# PlayDecisionTreeDocker
#### Extends PlayDecisionTree by creating the Docker image to make random forest classification model predict from the mleap compressed, efficient packaged model. 
#### The Mleap zip file is also present in the repp
#### Add this to the docker directory set up by calling the sbt docker:stage command and modify the Dockerfile to  include the mleap package and set its location in the application.conf configuration
