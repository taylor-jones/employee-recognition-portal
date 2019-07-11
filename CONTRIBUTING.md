## Setup

These setup instructions are for a Maven build configuration.

In your shell profile, add the correct configurations to match the environment variables in the ```/erp/src/main/java/resource/application.properties``` file. Those environment variables are:
```bash
SPRING_JPA_HIBERNATE_DDL_AUTO
SPRING_DATASOURCE_PLATFORM
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```
Note that outside of rare circumstances, ```SPRING_JPA_HIBERNATE_DDL_AUTO``` should probably remain set to ```none```. The current dev db runs on postgres so set ```SPRING_DATASOURCE_PLATFORM``` to ```postgres```. To demonstrate, here is an example of what can be added to a ~/.bash_profile using fake credentials:

```bash
  export DB_NAME="someDbName"
  export SPRING_JPA_HIBERNATE_DDL_AUTO="none"
  export SPRING_DATASOURCE_PLATFORM="postgres"
  export SPRING_DATASOURCE_URL="jdbc:postgresql://your-host-here:your-port-here/$DB_NAME"
  export SPRING_DATASOURCE_USERNAME="your user here"
  export SPRING_DATASOURCE_USERNAME="your password here"
```

Once your environment variables have been set, open up a new CLI window, navigate to the repo's root ```/erd``` directory, and run the following:

```bash
git checkout dev/master
./mvnw spring-boot:run
```

If everything worked, you should see a list of RietzTesting objects at http://localhost:8080/testing. You can test a post to the same URI with:
```bash
curl -d '{"textField":"new", "otherTextField":"new"}' -H "Content-Type: application/json" -X POST http://localhost:8080/testing
```