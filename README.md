# Spring PetClinic Sample Application [![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml)[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml)

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/spring-projects/spring-petclinic) [![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=7517918)

## Understanding the Spring Petclinic application with a few diagrams

See the presentation here:  
[Spring Petclinic Sample Application (legacy slides)](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application?slide=20)

> **Note:** These slides refer to a legacy, pre–Spring Boot version of Petclinic and may not reflect the current Spring Boot–based implementation.  
> For up-to-date information, please refer to this repository and its documentation.


## Run Petclinic locally

Spring Petclinic is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/) or [Gradle](https://spring.io/guides/gs/gradle/).
Java 17 or later is required for the build, and the application can run with Java 17 or newer.

You first need to clone the project locally:

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
```
If you are using Maven, you can start the application on the command-line as follows:

```bash
./mvnw spring-boot:run
```
With Gradle, the command is as follows:

```bash
./gradlew bootRun
```

You can then access the Petclinic at <http://localhost:8080/>.

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

You can, of course, run Petclinic in your favorite IDE.
See below for more details.

## Building a Container

There is no `Dockerfile` in this project. You can build a container image (if you have a docker daemon) using the Spring Boot build plugin:

```bash
./mvnw spring-boot:build-image
```

## In case you find a bug/suggested improvement for Spring Petclinic

Our issue tracker is available [here](https://github.com/spring-projects/spring-petclinic/issues).

## Database configuration

In its default configuration, Petclinic uses an in-memory database (H2) which
gets populated at startup with data. The h2 console is exposed at `http://localhost:8080/h2-console`,
and it is possible to inspect the content of the database using the `jdbc:h2:mem:<uuid>` URL. The UUID is printed at startup to the console.

A similar setup is provided for MySQL and PostgreSQL if a persistent database configuration is needed. Note that whenever the database type changes, the app needs to run with a different profile: `spring.profiles.active=mysql` for MySQL or `spring.profiles.active=postgres` for PostgreSQL. See the [Spring Boot documentation](https://docs.spring.io/spring-boot/how-to/properties-and-configuration.html#howto.properties-and-configuration.set-active-spring-profiles) for more detail on how to set the active profile.

You can start MySQL or PostgreSQL locally with whatever installer works for your OS or use docker:

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.5
```

or

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:18.1
```

Further documentation is provided for [MySQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt)
and [PostgreSQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/postgres/petclinic_db_setup_postgres.txt).

Instead of vanilla `docker` you can also use the provided `docker-compose.yml` file to start the database containers. Each one has a service named after the Spring profile:

```bash
docker compose up mysql
```

or

```bash
docker compose up postgres
```

## Test Applications

At development time we recommend you use the test applications set up as `main()` methods in `PetClinicIntegrationTests` (using the default H2 database and also adding Spring Boot Devtools), `MySqlTestApplication` and `PostgresIntegrationTests`. These are set up so that you can run the apps in your IDE to get fast feedback and also run the same classes as integration tests against the respective database. The MySql integration tests use Testcontainers to start the database in a Docker container, and the Postgres tests use Docker Compose to do the same thing.

## Compiling the CSS

There is a `petclinic.css` in `src/main/resources/static/resources/css`. It was generated from the `petclinic.scss` source, combined with the [Bootstrap](https://getbootstrap.com/) library. If you make changes to the `scss`, or upgrade Bootstrap, you will need to re-compile the CSS resources using the Maven profile "css", i.e. `./mvnw package -P css`. There is no build profile for Gradle to compile the CSS.

## Working with Petclinic in your IDE

### Prerequisites

The following items should be installed in your system:

- Java 17 or newer (full JDK, not a JRE)
- [Git command line tool](https://help.github.com/articles/set-up-git)
- Your preferred IDE
  - Eclipse with the m2e plugin. Note: when m2e is available, there is a m2 icon in `Help -> About` dialog. If m2e is
  not there, follow the installation process [here](https://www.eclipse.org/m2e/)
  - [Spring Tools Suite](https://spring.io/tools) (STS)
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - [VS Code](https://code.visualstudio.com)

### Steps

1. On the command line run:

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

1. Inside Eclipse or STS:

    Open the project via `File -> Import -> Maven -> Existing Maven project`, then select the root directory of the cloned repo.

    Then either build on the command line `./mvnw generate-resources` or use the Eclipse launcher (right-click on project and `Run As -> Maven install`) to generate the CSS. Run the application's main method by right-clicking on it and choosing `Run As -> Java Application`.

1. Inside IntelliJ IDEA:

    In the main menu, choose `File -> Open` and select the Petclinic [pom.xml](pom.xml). Click on the `Open` button.

    - CSS files are generated from the Maven build. You can build them on the command line `./mvnw generate-resources` or right-click on the `spring-petclinic` project then `Maven -> Generates sources and Update Folders`.

    - A run configuration named `PetClinicApplication` should have been created for you if you're using a recent Ultimate version. Otherwise, run the application by right-clicking on the `PetClinicApplication` main class and choosing `Run 'PetClinicApplication'`.

1. Navigate to the Petclinic

    Visit [http://localhost:8080](http://localhost:8080) in your browser.

## Looking for something in particular?

|Spring Boot Configuration | Class or Java property files  |
|--------------------------|---|
|The Main Class | [PetClinicApplication](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
|Properties Files | [application.properties](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources) |
|Caching | [CacheConfiguration](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## Interesting Spring Petclinic branches and forks

The Spring Petclinic "main" branch in the [spring-projects](https://github.com/spring-projects/spring-petclinic)
GitHub org is the "canonical" implementation based on Spring Boot and Thymeleaf. There are
[quite a few forks](https://spring-petclinic.github.io/docs/forks.html) in the GitHub org
[spring-petclinic](https://github.com/spring-petclinic). If you are interested in using a different technology stack to implement the Pet Clinic, please join the community there.

## Interaction with other open-source projects

One of the best parts about working on the Spring Petclinic application is that we have the opportunity to work in direct contact with many Open Source projects. We found bugs/suggested improvements on various topics such as Spring, Spring Data, Bean Validation and even Eclipse! In many cases, they've been fixed/implemented in just a few days.
Here is a list of them:

| Name | Issue |
|------|-------|
| Spring JDBC: simplify usage of NamedParameterJdbcTemplate | [SPR-10256](https://github.com/spring-projects/spring-framework/issues/14889) and [SPR-10257](https://github.com/spring-projects/spring-framework/issues/14890) |
| Bean Validation / Hibernate Validator: simplify Maven dependencies and backward compatibility |[HV-790](https://hibernate.atlassian.net/browse/HV-790) and [HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: provide more flexibility when working with JPQL queries | [DATAJPA-292](https://github.com/spring-projects/spring-data-jpa/issues/704) |

## Contributing

The [issue tracker](https://github.com/spring-projects/spring-petclinic/issues) is the preferred channel for bug reports, feature requests and submitting pull requests.

For pull requests, editor preferences are available in the [editor config](.editorconfig) for easy use in common text editors. Read more and download plugins at <https://editorconfig.org>. All commits must include a __Signed-off-by__ trailer at the end of each commit message to indicate that the contributor agrees to the Developer Certificate of Origin.
For additional details, please refer to the blog post [Hello DCO, Goodbye CLA: Simplifying Contributions to Spring](https://spring.io/blog/2025/01/06/hello-dco-goodbye-cla-simplifying-contributions-to-spring).

## License

The Spring PetClinic sample application is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).

## Feature Flags (Custom Implementation)

This repository includes a simple feature-flag system implemented without external libraries (no FF4J, Togglz, or similar).

### How to Run with Feature Flags

The application uses an on-disk H2 database at `./data/petclinic` by default (persistent across restarts):

```bash
./mvnw spring-boot:run
```

Once started, access the app at <http://localhost:8080/>.

### Feature Flag Storage & Persistence

- **Database**: `feature_flags` table in H2 file-based database (`./data/petclinic`)
- **Persistence**: Flags persist across application restarts
- **Schema**: Located in [src/main/resources/db/h2/schema.sql](src/main/resources/db/h2/schema.sql)

### Flagged Features & Implementation Details

| Feature Flag Name | What It Controls | Code Location | Endpoints Affected |
|---|---|---|---|
| `add_pet` | Controls creation of new pets | [PetController.java](src/main/java/org/springframework/samples/petclinic/owner/PetController.java) | `GET /owners/{ownerId}/pets/new`<br/>`POST /owners/{ownerId}/pets/new` |
| `add_visit` | Controls creation of new visits for pets | [VisitController.java](src/main/java/org/springframework/samples/petclinic/owner/VisitController.java) | `GET /owners/{ownerId}/pets/{petId}/visits/new`<br/>`POST /owners/{ownerId}/pets/{petId}/visits/new` |
| `owner_search` | Controls owner search & list functionality | [OwnerController.java](src/main/java/org/springframework/samples/petclinic/owner/OwnerController.java) | `GET /owners/find`<br/>`GET /owners` |

### API Documentation: Feature Flag Management

**Base URL**: `http://localhost:8080/api/feature-flags`

#### 1. List All Flags
```bash
GET /api/feature-flags
```
**Response** (200 OK):
```json
[
  {
    "id": 1,
    "name": "add_pet",
    "flagType": "BOOLEAN",
    "enabled": true,
    "whitelist": null,
    "blacklist": null,
    "percentage": null
  }
]
```

#### 2. Get Flag by Name
```bash
GET /api/feature-flags/add_pet
```
**Response** (200 OK): Returns single flag object or 404 if not found.

#### 3. Create Flag
```bash
POST /api/feature-flags
Content-Type: application/json

{
  "name": "add_pet",
  "flagType": "BOOLEAN",
  "enabled": true
}
```
**Response** (201 Created): Returns created flag with assigned ID.

**Examples for different flag types**:

- **Boolean flag** (simple on/off):
```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{"name":"add_pet","flagType":"BOOLEAN","enabled":true}' \
  http://localhost:8080/api/feature-flags
```

- **Whitelist flag** (only listed principals can access):
```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{"name":"feature_beta","flagType":"WHITELIST","whitelist":"alice,bob,charlie"}' \
  http://localhost:8080/api/feature-flags
```

- **Blacklist flag** (all except listed principals can access):
```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{"name":"deprecated_feature","flagType":"BLACKLIST","enabled":true,"blacklist":"admin,testuser"}' \
  http://localhost:8080/api/feature-flags
```

- **Percentage flag** (gradual rollout — e.g., 20% of users):
```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{"name":"new_feature","flagType":"PERCENTAGE","percentage":20}' \
  http://localhost:8080/api/feature-flags
```

- **Global Disable flag** (always disabled, regardless of settings):
```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{"name":"maintenance_mode","flagType":"GLOBAL_DISABLE"}' \
  http://localhost:8080/api/feature-flags
```

#### 4. Update Flag
```bash
PUT /api/feature-flags/{id}
Content-Type: application/json

{
  "name": "add_pet",
  "flagType": "BOOLEAN",
  "enabled": false
}
```
**Response** (200 OK): Returns updated flag.

**Example** (disable a flag):
```bash
curl -X PUT -H "Content-Type: application/json" \
  -d '{"name":"add_pet","flagType":"BOOLEAN","enabled":false}' \
  http://localhost:8080/api/feature-flags/1
```

#### 5. Delete Flag
```bash
DELETE /api/feature-flags/{id}
```
**Response** (204 No Content): Flag deleted successfully.

**Example**:
```bash
curl -X DELETE http://localhost:8080/api/feature-flags/1
```

### Feature Flag Type Reference

| Type | Description | Use Case | Key Fields |
|---|---|---|---|
| **BOOLEAN** | Simple on/off feature | Enable/disable entire feature | `enabled` |
| **WHITELIST** | Only listed principals can access | Beta features, early adopters | `whitelist` (comma-separated) |
| **BLACKLIST** | All except listed principals can access | Temporary exclusions | `blacklist`, `enabled` |
| **PERCENTAGE** | Gradual rollout by percentage | Canary deployments, A/B tests | `percentage` (0-100) |
| **GLOBAL_DISABLE** | Always disabled (ignores other settings) | Emergency off-switch | N/A |

### Helper Functions

Feature checks can be performed from any controller or service:

```java
import org.springframework.samples.petclinic.feature.FeatureFlags;

// Check if feature is enabled
if (FeatureFlags.isEnabled("add_pet")) {
    // feature is enabled
}

// Check with principal (for whitelist/blacklist/percentage)
if (FeatureFlags.isEnabled("add_pet", "alice")) {
    // alice can access this feature
}
```

### Design Decisions & Assumptions

1. **No External Library**: Feature flag system built from scratch without FF4J, Togglz, or similar libraries (per requirements).
2. **No Authentication**: Management API endpoints (`/api/feature-flags/*`) are intentionally unauthenticated for this exercise. In production, add proper authorization.
3. **Case-Insensitive Matching**: Whitelist/blacklist values use case-insensitive string matching (e.g., "alice" matches "ALICE").
4. **Deterministic Percentage Rollout**: Uses hash of principal name for consistent bucketing (same user always gets same result). Anonymous/null principals are bucketed randomly.
5. **File-Based H2 Database**: Uses on-disk H2 at `./data/petclinic` for persistence; flags survive application restarts.
6. **SQL Initialization**: `spring.sql.init.mode=always` ensures schema is created on first run.
7. **Direct Helper Calls**: Controllers perform explicit `FeatureFlags.isEnabled()` checks and throw HTTP 403 when disabled (no AOP required after removing spring-boot-starter-aop to avoid dependency conflicts).

### Video Walkthrough

Loom video demonstrating the feature flag system:  
**[Paste your Loom link here]**

### Repository & Submission

To submit your work:
1. Push this repository to GitHub
2. Record and upload a Loom walkthrough (showing app features, flag API, and toggling flags)
3. Submit repo URL + Loom link to the form: https://forms.gle/kt3mEfWZ1v1z9LJL8
