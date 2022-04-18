# k8s-manager

## Try it Out

### Deploy K8s resources
Deploy the `nginx-deployment` and `nginx-service`

### Run the application

#### Install Gradle locally
SDKMan or Brew are fine options.  I prefer SDK Man

#### Run the app
From the root of the project:
```bash
./gradlew clean bootRun
```

### Make some calls

#### Pick a REST client to use
I like Insomnia better than Postman.  Feel free to import the Insomnia calls from the `./sample-requests` directory

`cURL` is a fine option, too.  There are three example calls in the `./sample-requests` directory
