# mitarbeiter-sync

Implements the synchronization of employee data between Performis and several other systems, like PSIPenta etc.
See the [specification in confluence](http://svcatlassianconfluence01:8090/display/SR/Spezifikation+MA#SpezifikationMA-AxedoSPExpert) for more details.


## Building

1. Clone this project from git repository:

   ```
   git clone http://svcatlassianbitbucket01:7990/scm/mid/mitarbeiter-sync.git
   ```

2. Navigate to the root folder of the cloned git repository and execute:

   ```
   mvn clean install
   ```

## Build Pipeline

This project can be build with Bamboo automatically. Use the following command to create or update
a build plan in Bamboo.

```
mvn -Ppublish-specs
```

**Note:** Update the credentials for Bamboo in file .credentials before running this command!

## Execution

### Prerequisites

1. Install and configure sentry.io as described [here](https://github.com/getsentry/onpremise). For every OpenShift project there should exists one project with the same name in sentry.io.

2. Obtain the DSN in Project Settings > Klient Keys (DNS) in sentry.io console.

### Execute in local Environment

1. Prepare all required settings in application.properties

2. Execute with

   ```
   mvn spring-boot:run -DWINDREAM_USERNAME=....... -DLDAP_USER=..... -DLDAP_PASSWORD=.....
   ```
   
### Execute in OpenShift

1. The following command grants 'view' access:

   ```
   oc policy add-role-to-user view --serviceaccount=default
   ```

   If the above permission is not granted, your pod may throw a message similar to the following:

   "Forbidden!Configured service account doesn't have access. Service account may have been revoked"

2. Create a ConfigMap with name `sentry.io` and a key 'SENTRY_DSN', key 'environment'. As value use the Klient Keys (DNS) for the project in sentry.io.

   ```
   oc create configmap sentry.io --from-literal=SENTRY_DSN=http://5dde06ec94984e72a0dd1832eb1bae50:205ec012ba6c48db91ccbcf65a91cd3a@192.168.200.114:9000/5 --from-literal=SENTRY_ENVIRONMENT=test
   ```

3. Create a ConfigMap for this project using the following command:

   ```
   oc create configmap mitarbeiter-sync --from-file=application.properties
   ```
   
4. Create a ConfigMap to store the last execution time using the following command:

   ```
   oc create configmap variantea-lastexecution  --from-file=variantea-lastexecution.properties
   oc create configmap varianteb-lastexecution  --from-file=varianteb-lastexecution.properties
  
   ```
   
5. Create a Secret for the Performis Connect/Accounting Service username/password using the console. The Secret has to be named `performis`.

6. Create a Secret for the Axedo DB Service username/password using the console. The Secret has to be named `axedo`.

7. Deploy the application to OpenShift

   ```
 	mvn fabric8:deploy
   ```
