Client: ![Client coverage](https://gitlab.ewi.tudelft.nl/cse1105/2019-2020/organisation/repository-template/badges/master/coverage.svg?job=client-test)
Server: ![Server coverage](https://gitlab.ewi.tudelft.nl/cse1105/2019-2020/organisation/repository-template/badges/master/coverage.svg?job=server-test)


# Starting template

This README will need to contain a description of your project, how to run it, how to set up the development environment, and who worked on it.
This information can be added throughout the course, except for the names of the group members.
Add your own name (do not add the names for others!) to the section below.

## Description of project

This is the application that we developed for the OOP Project course this year. It is a Q&A application designed to aid students, teachers and assistants during lectures.

The app functions on the concept of Q&A rooms (very similar to the more traditional chat rooms). Users (usually lecturers) can create such Q&A rooms, where students can ask questions about the ongoing lecture. The application doesn't require user login, but it will ask all users to choose a username when first started, such that everyone has a better understanding of the questions and discussions.

The creator of a room will have access to 2 codes, one for students (with the most basic privileges) and one that grants moderator privileges to anyone that has access to it.

The main difference between a Q&A room and a traditional chat room is that in the Q&A room only the lecturer and any of the assigned moderators can answer and otherwise edit questions. Students can only post questions and up/down-vote them.

Some of the notable features that the application contains are:
- upvoting and downvoting questions for prioritising the questions that the students consider to be the most relevant/important;
- editing privileges for moderators (such as modifying/deleting questions and banning users);
- zen mode for a distraction-free experience on the lecturer/moderator side;
- exporting questions to a file on the computer;

## Group members

| 📸 | Name | Email |
|---|---|---|
| ![](https://eu.ui-avatars.com/api/?name=JW&length=4&size=50&color=DDD&background=777&font-size=0.325) | Jocelyn Woods | j.j.woods@student.tudelft.nl |
| ![](https://i.imgur.com/Uv7yJcC.jpg) | Sam van der Kris | s.vanderkris@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=VI&length=4&size=50&color=DDD&background=777&font-size=0.325) | Vlad Iftimescu | V.G.Iftimescu@student.tudelft.nl |
| ![Imgur](https://i.imgur.com/oqTf0I1.jpg) | Nikolay Lalev | N.T.Lalev@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=JW&length=4&size=50&color=DDD&background=777&font-size=0.325) | Jort Vincenti | J.Vincenti@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=SS&length=4&size=50&color=DDD&background=777&font-size=0.325) | Sorana Stan | S.A.Stan@student.tudelft.nl |

<!-- Instructions (remove once assignment has been completed -->
<!-- - Add (only!) your own name to the table above (use Markdown formatting) -->
<!-- - Mention your *student* email address -->
<!-- - Preferably add a recognisable photo, otherwise add your GitLab photo -->
<!-- - (please make sure the photos have the same size) -->

## How to run it

1) Clone the repository into a local repository on your machine.

2) Make sure you have gradle installed

3) Run `gradle :server:run` to start the server. This will automatically install all dependencies as well.

4) Run `gradle :client:run` to start the client

Congratulations! You have just managed to run the app!

## Dependencies
(All of these dependencies are handled automatically by gradle, so there is no need to install anything manually)

- `Gradle` for adding all the dependencies to the project;
- `Project Lombok` for the class code cleanup;
- `JUnit` for running unit tests;
- `GSON` for the JSON communication between client and server;
- `OkHttp` for the http request/response handling.

## How to contribute to it

Anyone can contribute to the project by providing some additional code and functionalities that would improve the user experience and the overall quality of the app.

One notable contribution that could be provided right away would be changing the database that the app uses from an in-memory database (the project currently uses the H2 database) to a disk memory one (for example, to PostgreSQL).

Any other contributions are welcome, but the members of the development team have to give their explicit approval before merging said contributions into the live version of the project.
