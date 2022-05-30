# Backlog

## Must have:
1. Users are able to create a (lecturer / regular user) account.
2. Only users who were marked as a lecturer during signup can create rooms
3. Join lecture with a link / room ID
4. Students can send questions
5. Simple interface for lecturers ("zen mode")
6. Strictly Q&A, students don't have to be able to answer each other's questions

## Should have:
7. Moderation (moderator role for TAs who can remove/edit questions, ban/timeout users)
8. Lecturers and Moderators can mark questions as answered
9. Multiple rooms at same time
10. Students can edit questions
11. Accessibility (walkthrough on how to use, enlarge text, readable color schemes etc.) -> at least a Help/Documentation feature + “I know what I’m doing”
12. Upvote other student's questions (prioritize these questions / could appear on top of the lecturer’s screen)
13. Downvote them as well to mark as spam (downvoted / spam questions should be clearly visible to the moderator so they can take action)
14. Moderators should be able to delete, edit questions

## Could have:
15. "edit mode" for the lecturer so they can moderate questions
16. Buttons to speed up or slow down the lecturer
17. Polling questions (2-10 possible answers?)
18. Question types (subject tags)
19. Students update feed (we can't use websockets, push notifications?)
20. Schedule rooms beforehand (from/to time), clearly visible to the student, they could not join or ask question before the allocated time

## Won't have:
21. Extra moderation (merging similar questions)
22. Search feature (find questions, certain words)



# User Stories

## Must have:
1. As a user, I want to be able to create a student / lecturer account so that any information about the lecture channels I partake in or have created is stored. This also includes dates and time slots for upcoming lectures that I need to attend.  
**Acceptance Criteria**: Users are able to register an account. Registration is successful if the user is able to login with the account whenever they wish and retrieve any relevant information associated with the account.  
**Failure Cases:** a)The user is unable to login, despite providing correct credentials.  
b) The user can not see all of the information associated with their account (e.g. scheduled meeting, lecture channels etc.)

2. As a lecturer I want to be the only one who can create rooms, so that confusion and creating random rooms that are not useful is avoided.  
**Problem**: Students may try to sign in as a lecturer in order to have the privilege of creating rooms too.  
**Acceptance Criteria**: All the rooms created will be specifically for the lectures and other activities proposed by lecturers.

3. As a student, I want to be able to join lectures as quickly and efficiently as possible.  
**Problem**: Going through authentication procedures is boring and time-consuming, especially if the only thing you want is to join your lecture.  
**Acceptance Criteria**: Students can join in via a chat room ID just by inputting their name, no need for a full-on logging system, unlike for lecturers.

4. As a student I want to be able to ask questions so that I can communicate my issues and questions with the lecturer.  
**Problem**: It is difficult to communicate questions to the lecturer without an easy chat function.  
**Acceptance Criteria**: Students can post questions that other students and the lecturer can see and the lecturer can answer.

5. As a lecturer, I want a zen mode (a simple version of the chat), so that I can read the messages more easily.  
**Problem**: Complicated versions of the chat might be hard to follow from a lecturer’s point of view.  
**Acceptance Criteria**: The teacher can go in zen mode.

6. As a student, I want to be sure that I only get answers from the lecturer, so that I can be sure the answer is correct and helpful.  
As a lecturer, I want to be able to skim through relevant questions only and not lose my time and patience with off-topic chatter.  
**Problem**: other students might give incorrect or incomplete answers.  
**Acceptance Criteria**: only the lecturer can answer questions. If a student has a question that can also be answered by peers, it can be asked on the StackOverflow or CSE discord instead.


## Should have:
7. As a lecturer, I want to be able to give extra privileges to certain users (TAs) in the lecture channel I own so that I do not  have to focus my attention on moderation. Those users will act as moderators and have the ability to remove/edit/merge questions as well as ban/timeout students.  
**Acceptance Criteria**: The lecturer is able to select a subset of the users and give them the moderator role. In turn those users will immediately be able to use the extra privileges described above. (The lecturer should be able to remove the moderator role if they wish).  
**Failure Cases**: a) The lecturer does not get the option to promote a user to moderator. b) The user has the role of moderator but the extra privileges are not present. c) The lecturer is unable to take away the role of moderator.

8. As a lecturer or moderator, I want to be able to mark questions as answered, so that it will be easier to know what questions are remaining and what have been already answered.  
**Problem**: Marking something as answered doesn’t actually make a difference.  
**Acceptance Criteria**: By having an “answered” button that can be also used by the moderator, the lecturer can easily go to the next question without having to scroll and read everything in order to find the new questions every time.

9. As a lecturer I would want to be able to create multiple rooms so that many different lecturers can have lectures at the same time individually.  
**Problem**: lectures for different years and programmes will want to host lectures at the same time but not have to share the same room  
**Acceptance Criteria**: Lecturers will be able to create seperate rooms for their individual lectures so they don’t overlap

10. As a student, I want to be able to edit (correct and remove) my own questions from the live chat, so that I can change my question.  
**Problem**: The student can remove student’s questions.  
**Acceptance Criteria**: The student can correct/remove their question from the chat.

11. As a vision impaired student, I still want to be able to use the application as anyone else would.  
**Problem**: a badly designed user interface could become a problem for some students.  
**Acceptance Criteria**: the application should be as accessible as possible. We should do research on common accessibility problems and ways to solve them. For example: customizable color scheme for colorblind users, the option to set a font such as OpenDyslexic for users with dyslexia. We also need to make the application as simple and intuitive as possible.

12. As a student I want to be able to indicate to the lecturer questions that are relevant to me so that I can understand the material better.  
As a lecturer I want to clearly see which questions are of most interest to the student so I can provide an answer.  
**Acceptance Criteria**: Students are able to upvote or downvote a question once based on its perceived importance.  
**Failure Cases**: a) Students are not able to cast their vote.  
b) Students can cast a vote for the same question multiple times.  
c) The lecturer is not able to see which questions are of the highest priority

13. As a lecturer, I want to see only the questions that are relevant to my lecture, so that I can focus on helping students as efficiently as possible.  
**Problem**: In an online environment, students will inevitably abuse the platform for spam in an attempt to be funny.  
**Acceptance Criteria**: Fellow students can downvote each other’s questions, making them less likely to show up in the lecturer’s question feed. This downvote button could double as marking a question as “spam”, or we could have separate options for “bad/irrelevant questions” and actual disruptive “spam”.

14. As a moderator, I want to be able to modify confusing questions and to delete aggressive/offensive ones.  
**Problem**: Some students might use the Q&A chat to express their frustrations or their off-topic opinions and distract their peers, as well as the lecturers.  
**Acceptance Criteria**: Moderators (as well as lecturers) can edit and remove questions that they consider inappropriate.


## Could have:
15. As a lecturer I want to be able to switch to edit mode and go out of zen mode so that I could use features such as edit questions, timeout users etc.  
**Problem**: Lecturer might need to moderate the chat himself.  
**Acceptance Criteria**: The lecturer is able to successfully toggle between the 2 modes.

16. As a student I want to be able to tell the lecturer to either speed up or slow down the lecture so that the lecture speed corresponds with the understanding of the students.  
**Problem**: Some students may want the lecturer to talk faster or slower during the lecture depending on their understanding.  
**Acceptance Criteria**: There will be a way for students to give the lecturers real time feedback about the speed of their lecture.

17. As a teacher, I want to be able to create pollings, so that I can interact live with the students.  
**Acceptance Criteria**: A teacher is able to create a polling, (with a question (optional) and a certain amount of answers) and is able to close it.  
As a student, I want to be able to participate in those polling and choose among the different options suggested, so that I can react to the live lecture.  
**Problem**: A student might not have the opportunity to change their vote.  
**Acceptance Criteria**: The student is able to participate in the polling and chose among the options proposed.

18. As a lecturer, I want questions to be grouped by a tag, so I can answer questions in a clear and structured way and I won’t get distracted by other questions that might not be relevant to the current topic.  
**Problem**: Hundreds of students watching a lecture and asking questions can quickly become chaotic. Especially if some students are asking questions about a previous topic, some about the current one and some about unrelated topics.  
**Acceptance Criteria**: Adding tags for the topic to a question would be one way to organise these questions. The lecturer can then filter questions based on these tags, and answer them in a structured way.

19. As a lecturer, I want to receive feed updates of the chat during a lecture session, so that it is easier for me to follow the questions being asked.  
**Acceptance Criteria**: Chat auto - scrolls when questions are asked and new questions appear sequentially.  
**Failure Cases**: The chat does not update whenever questions are asked.

20. As a lecturer, I want to be able to schedule rooms beforehand(from/to time), so that the students can see the lecture and prepare for it.  
**Problem**: You might not be able to change the time of the meeting after it is set.  
**Acceptance Criteria**: The students will be able to make a schedule ahead of time of all the lectures that take place in a specific day or week. The students should only be able to ask questions and join the lecture during the allocated time.


## Won't have:
21. As a moderator, I want to be able to merge similar or identical questions from my students, especially if those questions have already been answered so that we avoid cluttering the chat with the same few questions.  
**Problem**: Similar/identical questions might clutter the chat and diverge attention from new and relevant questions.  
**Acceptance Criteria**: Moderators can merge similar questions and keep one of them as the “representative” for the whole group of questions.

22. As a User, I want to be able to search for some keywords or questions in the live chat so that i can find previously asked questions.  
**Problem**: The chat might still update and the User will always be redirected to the newest message.  
**Acceptance Criteria**: The user is able to query a question/word and receive a pool of results based on it.
