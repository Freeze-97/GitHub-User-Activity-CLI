# GitHub User Activity CLI (Java)

A simple **command-line Java application** that fetches and displays a GitHub user's **recent public activity** using the [GitHub REST API](https://docs.github.com/en/rest).  

## Features
- Fetches recent public GitHub activity for any username  
- Parses and displays a clean, readable summary in the terminal  
- Handles errors (invalid username, connection issues, etc.)  
- Uses only **core Java classes** (`HttpURLConnection`, `BufferedReader`, `URI`, etc.)

## Requirements
- **Java 20** or later  
- Internet connection (to reach the GitHub API)

##  How to Run

### 1. Compile
```bash
javac GitHubUserActivity.java
```

### 2. Run
Provide a GitHub username as a command-line argument:
```bash
java GitHubUserActivity <username>
```

### Example
```bash
java GitHubUserActivity torvalds
```

### Output
```bash
- Pushed to torvalds/linux
- Created something in torvalds/subsurface
- Starred torvalds/test-repo
```

