# SpaceFlight News

SpaceFlight News is a sample app that shows articles
from [SpaceFlight News API](https://api.spaceflightnewsapi.net/v4/docs/)

## Demo

![video](docs/Demo%20Video.webm)

### Prerequisites

Use the latest Android Studio Hedgehog | 2023.1.1 Patch 1.
App compile sdk and target sdk is 34 with min sdk 28

### App Architecture

App is using Clean Code Architecture with single module and MVVM for presentation layer.
The app is very small and simple containing only one feature with on screen, that it why I choose to
go with single module to reduce unnecessary complexity but the app is separated into packages with
scalability in-mind (Modularization is one step away), once more features are required the packages
can move in its own module.

#### Packages Structure

all these packages can be moved to their own modules when the app scales-up to help develop other
features faster and easier.

##### Network package

Contain all basic networking setup(Retrofit, OkHttp and Kotlinx Serialization Json), using hilt to
inject these dependencies also containing paging base network model(DTO) that is used for all paging
responses from SpaceFlight News API, Like Articles, Blogs and Reports.

#### Design System Package

Contain all design system theming and component for SpaceFlight News App.

- Theming like colors, typography and app theme.
- Base Design Component that can be used in different screens and features:
    - paging error/loading handling.
    - full screen loading.
    - loading item that can be used in the ind of list.
    - top app bar.

#### Common Package

containing extensions function and interfaces that can be used by any feature.

- DateExtensions: date conversion function that convert:
    - date from server string to LocalDateTime that can be use in business logic
    - from LocalDateTime to user readable string format.
- Mapper interface that can be use to map models from one layer to another if needed.

#### Articles Package (Feature Package)

Articles containing 3 packages for all 3 architecture layers, when app scale-up this can be moved to
its own module or spilt to 2 or 3 modules depending on future needs and dependencies between
features.

- Data Layer: contain all data accessing code.
    - Article DTO.
    - fetching article pages from API.
    - prepare pagingData and flows.
    - mapping Articles DTO to Article model.
- Domain (Business) Layer: contain all business logic.
    - contract with data layer.
    - usecase for the business use cases.
    - Article model(Entity).
- Presentation Layer: using MVVM architecture
    - ViewModel is responsible of executing usecase the provide data to the user or to execute
      user/app events.
    - Compose is Used to show UI.

### External Library used

- Hilt: for dependency injection
- Compose: for UI
- Coroutines: for concurrency/asynchronous work
- Retrofit: for networking
- Kotlinx Serialization: for model serialization/deserialization
- Jetpack Paging 3: for handling pagination
- MockK: for mocking dependencies in unit test
- turbine: for Coroutine flow testing

### Tools

- Kover: test coverage tool to make sure that the unit test covers all business code.
    - run ```koverHtmlReportDebug``` to generate coverage report, coverage report can be found under
      app/build/coverageReport/index.html
- Detekt: a static code analysis tool to make sure that the code style is kept clean and readable.
    - run ```detekt``` gradle task to analysis the code
