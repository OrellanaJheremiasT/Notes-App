# 📝 Notes App

[![Java](https://img.shields.io/badge/Java-11+-blue)](https://www.java.com/) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Issues](https://img.shields.io/github/issues/OrellanaJheremiasT/Pomodoro-Clock)](https://github.com/OrellanaJheremiasT/Pomodoro-Clock/issues)  [![Stars](https://img.shields.io/github/stars/OrellanaJheremiasT/Pomodoro-Clock?style=social)](https://github.com/OrellanaJheremiasT/Pomodoro-Clock/stargazers)  


A **console-based Notes Application** in Java to easily create, view, edit, and delete text notes on your local machine.

---

## 🚀 Features

- 🆕 Create new notes with custom filenames.
- 📄 View notes list with **pagination** (5 notes per page).
- ✏️ Edit existing notes.
- 🗑️ Delete notes safely with confirmation.
- ⬅️➡️ Navigate between pages of notes.
- 💾 Persistent storage in a folder of your choice.

---

## 🛠️ Getting Started

### Prerequisites

- Java 11 or higher installed.
- Terminal / Command Prompt access.

### Installation & Run

1. Clone the repository:

```bash
git clone https://github.com/yourusername/Notes-App.git
cd Notes-App
```

2. Compile the program:

```bash
javac Notes.java
```

3. Run the application:

```bash
java Notes
```

---

## 🎯 Usage

1. Enter the folder path to store your notes.
2. **Main Menu:**
   - Create new note
   - View notes list
   - Exit
3. **Notes List:**
   - Select note (1–5) to view/edit/delete.
   - Next / Previous page for more notes.
   - Return to main menu.

> Notes are stored as `.txt` files. Existing notes require confirmation to overwrite.

---

## 📂 Project Structure

```
Notes-App/
├─ Notes.java        # Main program
├─ README.md         # Project documentation
└─ [User Notes Folder] # Stores your notes
```

---

## 🤝 Contributing

Contributions are welcome! Fork, improve, and submit a pull request.

---

## ⚖️ License

This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.
