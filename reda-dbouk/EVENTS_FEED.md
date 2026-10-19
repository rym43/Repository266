# Campus Events feed — Reda Dbouk

## What I added (workshop steps)

| Step | File(s) | Checkpoint |
|---|---|---|
| 1. Manifest permission + dependencies | `AndroidManifest.xml` (INTERNET), `libs.versions.toml` + `app/build.gradle.kts` (Retrofit 2.11.0, Gson converter) | App compiles |
| 2. EventDto + CampusApi | `EventDto.kt`, `CampusApi.kt` | `getEvents()` returns `List<EventDto>` (a Kotlin type) |
| 3. EventsRepository | `EventsRepository.kt`, `CampusEvent.kt` | `EventDto.toCampusEvent()` maps DTO to `CampusEvent` |
| 4. EventsViewModel | `EventsViewModel.kt`, `EventsUiState.kt` | `Loading` becomes `Content` or `Error` |
| 5. Render the state | `EventsScreen.kt`, `MainActivity.kt` (Events / Tasks tabs) | List, Retry button and error message are visible |

Data comes from [`events.json`](events.json), loaded over the network from GitHub's raw URL.

## Success criterion: what happens when I tap **Refresh**

1. **UI (`EventsScreen`)**: the Refresh button calls `onRefresh`, which calls `viewModel.refresh()`. The screen does no work itself; it only sends the event up.
2. **ViewModel (`EventsViewModel`)**: sets the state to `Loading` (a spinner shows), then starts a coroutine in `viewModelScope` and asks the repository for events.
3. **Repository (`EventsRepository`)**: calls the API, then maps every `EventDto` to a `CampusEvent` (cleans the date, turns a missing location into "Location TBA"). The UI never sees raw server data.
4. **Network (`CampusApi` + Retrofit)**: sends `GET .../events.json` over HTTP (allowed by the INTERNET permission), and Gson turns the JSON into `List<EventDto>`.
5. **Back up the chain**: the repository returns `List<CampusEvent>`, and the ViewModel sets `Content(events)`. If anything failed (no internet, bad JSON), it catches the exception and sets `Error(message)` instead.
6. **UI again**: `collectAsStateWithLifecycle()` sees the new state, and Compose redraws the screen with either the list or the error message and a **Retry** button.
