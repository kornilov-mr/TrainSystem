# Train System Internship task

## Task description
A railway system consists of several stations connected by one-way tracks.

Each station is associated with two cargo types:

One type that is unloaded when a train arrives.

One type that is loaded before a train departs.

All trains start from the same initial station, carrying no cargo. Trains may follow any valid route along the tracks.

Determine, for each station, which cargo types might be on a train when it arrives. A cargo type is considered possible if there is at least one route from the initial station that brings it to the station.

#### Additionally
If the cargo type is set to -1, it means that the station doesn't load or unload any cargo.
### Input Example

* *[simple_train_system](src/main/resources/simple_train_system.txt)*
* *[simple_train_system_with_a_loop](src/main/resources/simple_train_system_with_loop.txt)*

## How to run
Solution is a kotlin project with entry point in *[Main.kt](src/main/kotlin/Main.kt)*

Run gradle task of the gradle build file *[build.gradle.kts](build.gradle.kts)*

## Tests
There are 5 tests cases in the project.

