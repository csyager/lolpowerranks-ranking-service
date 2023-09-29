# LoL Powerrankings Service

This service is the backend to the LoL powerrankings project.  It serves as an interface for querying the datastores for ranking information.

The API contract for the competition can be found [here](https://docs.google.com/document/d/1Klodp4YqE6bIOES026ecmNb_jS5IOntRqLv5EmDAXyc/edit).


## Setup
Add a file `application-local.yaml` to the `src/main/resources` directory, and add the following:

```
amazon:
  aws:
    accesskey: { access key }
    secretkey: { secret key }
```

## Running the service locally
To start the local development server run `./gradlew bootRunLocal`.  This will use the local Spring profile configured in the previous step to interface with the datastores on AWS.