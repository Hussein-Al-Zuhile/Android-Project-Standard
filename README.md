# Android Projects Standard

## About the Project

The **Android Projects Standard** is a showcase project for Android development.
It follows the **Clean Architecture** pattern with the **MVI (Model-View-Intent)**
It contains codebase to build your project on it, and an example for login use-case.

### Key Features:

1. **Clean Architecture**
2. **MVI (Model-View-Intent)**
3. **Koin Dependency Injection**
4. **Compose UI**
5. **Compose Navigation**
6. **Ktor HTTP Client**
7. **HiveMQ MQTT Client** (Codebase only)
8. **DataStore for caching**
9. **Paging3 for pagination** (Codebase only)
10. **Coil for image loading** (No example for this in the project but it's easy to find on internet)
11. **Accompanist for permissions** (No example for this in the project but it's easy to find on internet)

---

## Project Structure and Description

The project is structured to enforce separation of concerns and maintainability, following Clean Architecture
principles.
Each layer has a specific responsibility:

- **Data Layer**: Manages data retrieval and wrap it in
  a [Result<T>](app/src/main/java/com/hussein/androidprojectstandard/domain/base/Result.kt).
- **Domain Layer**: Contains use cases and business models.
- **Presentation Layer**: Handles the UI events and manage the state in viewmodel in unidirectional way.
    - For Events that doesn't require domain and data layer interaction it will be handled in the viewmodel.
    - Otherwise it will be as the following image:
      ![](/images/user%20interaction%20flow.jpeg)

The following is a detailed overview of each layer:

### Layers:

1. **data**: Handles data-related operations.

    - **base**: Contains core configurations and utilities
        - [`BaseMQTTClient`](app/src/main/java/com/hussein/androidprojectstandard/data/base/BaseMQTTClient.kt):
          MQTT client setup and API definitions
        - [
          `BasePagingDataSource`](app/src/main/java/com/hussein/androidprojectstandard/data/base/BasePagingDataSource.kt):
        - [`BaseRemoteService`](app/src/main/java/com/hussein/androidprojectstandard/data/base/BaseRemoteService.kt):
          Placeholder for HTTP service abstraction
        - [`BaseRepository`](app/src/main/java/com/hussein/androidprojectstandard/data/base/BaseRepository.kt):
          Unified handler for HTTP/MQTT requests, wrapping results in
          a `Result<T>`

    - **dataSource**: Includes:
        - `Http` services for API requests.
        - `ApiResources`: Hierarchical HTTP API structure.
        - `HttpClient`: Configures Ktor client.
        - `BackofficeMqttClient`: Handles MQTT connections.
    - **model**: Request and response models.
    - **repository**: Main repository for all data-layer interactions.

2. **di**: Dependency Injection setup using Koin (`MainModule`).

3. **domain**: Contains business logic.
    - **base**:
        - [`BaseUseCase<T>`](app/src/main/java/com/hussein/androidprojectstandard/domain/base/BaseUseCase.kt):
          Encapsulates logic in flows, emitting states like `Loading`, `Success`,
          or `Failure`
        - [`Result<T>`](app/src/main/java/com/hussein/androidprojectstandard/domain/base/Result.kt):
          A sealed class representing state transitions
    - **model**:
      Business models
    - **usecase**:
      Includes HTTP and MQTT use-cases

4. **presentation**: Handles UI-related logic and components.
    - **base**: Core reusable components
        - [`BaseUIState`](app/src/main/java/com/hussein/androidprojectstandard/presentation/base/BaseUIState.kt):
          Abstract state for screens, includes `isLoading` and `errorMessage`
        - [
          `BaseViewModel`](app/src/main/java/com/hussein/androidprojectstandard/presentation/base/BaseViewModel.kt)<StateEvent>:
          Manages state and events for the view
        - [
          `DataStoreManager`](app/src/main/java/com/hussein/androidprojectstandard/presentation/base/DataStoreManager.kt):
          Manages caching with `DataStore`
        - [
          `MessageSnackbar`](app/src/main/java/com/hussein/androidprojectstandard/presentation/base/MessageSnackbar.kt):
          Composable for displaying messages
        - [
          `PreviewAnnotations`](app/src/main/java/com/hussein/androidprojectstandard/presentation/base/PreviewAnnotations.kt):
          Reusable preview setups for UI
        - [
          `StateEvent`](app/src/main/java/com/hussein/androidprojectstandard/presentation/base/StateEvent.kt):
          Interface for one-time UI events
    - [**main**](app/src/main/java/com/hussein/androidprojectstandard/presentation/main): Parent activity
      containing global message channels.
    - [**login**](app/src/main/java/com/hussein/androidprojectstandard/presentation/login): Login functionality
      with token management.
    - [**home**](app/src/main/java/com/hussein/androidprojectstandard/presentation/home): Just mimicking home with
      logout button

    - [**theme**](app/src/main/java/com/hussein/androidprojectstandard/presentation/theme):Custom theme generated with
      the [Material Theme Builder](https://material-foundation.github.io/material-theme-builder/).

---

## Events Handling Structure

Each screen follows a standardized **State Management** pattern with the following components:

1. **Composable Screen**:  
   Accepts `State` (current screen data) and `OnEvent` (user interaction handler).

2. **State**:  
   Encapsulates all the updatable data in the screen.

3. **Event**:  
   Represents all possible user actions in the screen.

4. **StateEvent**:  
   Defines one-time actions, such as navigation or displaying messages.

5. **ViewModel**:
    - Manages the state and event handling for the screen.
    - Uses a backing channel:  
      `protected _stateEventChannel: Channel<StateEvent>`
    - Exposes a public event channel for observing one-time events:  
      `public stateEventChannel: ReceiveChannel<StateEvent>`.

6. **Initial events**:
    - When you have an event you want to call in the first time entering the screen, it will be in a
      LaunchedEffect(Unit).
    - These Initial events sometimes handled inside the screen composable and sometimes in the
      NavHost composable definition of that screen (It should be unified in the future).

This structure ensures consistency, scalability, and maintainability.

---