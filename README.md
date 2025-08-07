# Personal Task Manager App

The **Personal Task Manager** is a user-friendly Android app designed to help users keep track of their daily tasks. Users can add, edit, delete, and mark tasks as complete, all within a clean and modern UI. Built using **Java**, **Room Database**, **MVVM architecture**, and **Material Design**, the app ensures smooth performance and intuitive interaction.

---

## Features

- Add new tasks with title, description, date, and priority
- Edit existing tasks (description, date, and priority)
- Integrated date picker for easy date selection
- Mark tasks as completed via swipe gesture
- Delete tasks with swipe left
- View pending and completed tasks separately
- Live UI updates using LiveData
- Material Design UI with custom themes and fonts
- Persistent storage using Room Database (SQLite)

---

## Screenshots

Screenshots present in file
/PersonalTaskManagerApp/Screenshots

---

## Technologies Used

| Component           | Tech Stack                        |
|---------------------|-----------------------------------|
| Language            | Java                              |
| UI Framework        | Android SDK, XML Layouts          |
| Architecture        | MVVM (Model-View-ViewModel)       |
| Database            | Room (SQLite ORM)                 |
| UI Components       | RecyclerView, CardView, FAB       |
| Navigation          | DrawerLayout & NavigationView     |
| Tooling             | Android Studio, Gradle            |

---

## Project Structure

```plaintext
.
├── model/
│   └── Task.java               # Task entity
│
├── ui/
│   ├── AddEditTaskActivity.java
│   ├── TaskListActivity.java
│   ├── CompletedTaskActivity.java
│   └── TaskAdapter.java        # RecyclerView Adapter
│
├── viewmodel/
│   └── TaskViewModel.java
│
├── database/
│   ├── TaskDao.java
│   └── TaskDatabase.java
│
├── res/
│   ├── layout/                 # All XML files
│   ├── drawable/               # Custom backgrounds
│   ├── menu/                   # Navigation menu
│   └── values/                 # Colors, strings, themes
---


```
## How to run the file
- Clone the repo
- **bash git clone https://github.com/rzjaffery7/PersonalTaskManagerApp.git**
- Open in the Android Studio
- Build the Project
- Ensure Android Gradle Plugin and dependencies are synced
- set a real / AVD
- run the app

## Upcoming Features
- Notifications and reminders for tasks
- Task search and filtering
- Cloud sync and backup with Firebase
- Dark mode support

## Author
Rayyan Zafar Jaffery \
Github: **https://github.com/rzjaffery** \
LinkedIn: **https://www.linkedin.com/in/rayyanz7/**



