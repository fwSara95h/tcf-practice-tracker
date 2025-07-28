# 🎯 TCF Roulette - Practice Tracker

[![Go Version](https://img.shields.io/badge/Go-1.21+-00ADD8?style=flat-square&logo=go&logoColor=white)](https://golang.org/)
[![SQLite](https://img.shields.io/badge/SQLite-003B57?style=flat-square&logo=sqlite&logoColor=white)](https://www.sqlite.org/)
[![French Learning](https://img.shields.io/badge/Learning-French-blue?style=flat-square&logo=🇫🇷)](https://reussir-tcfcanada.com/)
[![Boot.dev Hackathon](https://img.shields.io/badge/Boot.dev-Hackathon%202025-8B4513?style=flat-square)](https://blog.boot.dev/news/hackathon-2025/)

> A web application for randomized TCF (Test de Connaissance du Français) practice prompts. Built for Boot.dev Hackathon 2025.

![Screenshot](./auxil/app-v0-screenshot.png)

## 📖 About

TCF Roulette is a simple yet elegant web application that helps French language learners practice for the TCF exam by providing random prompts from a curated database. Built with Go, SQLite, and vanilla JavaScript, this project foray into backend development with Go.

## ✨ Current Features

- 🎲 **Random Prompt Generation** - Get randomized TCF practice prompts
- 🏷️ **Rich Task Metadata** - View task ID, category, type, format, and scenario
- 📱 **Clean, Responsive UI** - Modern gradient design built to work seamlessly on desktop and mobile
- 🗄️ **SQLite Database** - Lightweight local database for prompt storage
- 🔄 **Real-time Loading** - Smooth user experience with loading states

## 🛠️ Tech Stack

- **Backend:** Go 1.24+ with `net/http` + SQLite with `modernc.org/sqlite` (pure Go driver)
- **Frontend:** Vanilla HTML, CSS, JavaScript + CSS3 with gradients, etc

## 🚀 Quick Start

### Prerequisites
- [Go 1.21+](https://golang.org/dl/) installed
- SQLite database file with TCF prompts (`data\tcf.db`)

### Installation & Running

```bash
# Clone the repository
git clone https://github.com/fwSara95h/tcf-practice-tracker.git
cd tcf-practice-tracker

# Clean up dependencies (first time)
go mod tidy

# Start development server
go run main.go # you might need to click "Allow" on the warning that pops up before it runs

# Visit in browser
# Windows: start http://localhost:8080
# macOS: open http://localhost:8080
# Linux: xdg-open http://localhost:8080
```

**See [`auxil/deployment.md`](./auxil/deployment.md) for deployment options.**

## 📂 Project Structure

```
tcf-practice-tracker/
├── main.go              # Go server with HTTP handlers & SQLite
├── go.mod               # Go module dependencies
├── templates/
│   └── index.html       # Main HTML page
├── static/
│   ├── main.js          # Frontend JavaScript
│   └── style.css        # CSS styling
└── data/
    └── tcf.db           # SQLite database (your data)
```


## 🔮 What's Next

### 🎯 Coming Soon (v0.2-0.3)
- [ ] **🔄 Completion Tracker** - Mark prompts as completed to prevent repetition
- [ ] **🎛️ Smart Filtering** - Choose specific categories, formats, and task types  
- [ ] **📊 Progress Dashboard** - Visual statistics and completion tracking
- [ ] **⏱️ Session Management** - Timer and study session summaries
- [ ] **🌐 GitHub Pages Version** - Frontend-only deployment for easy sharing

**See our comprehensive [Feature Roadmap](./auxil/ROADMAP.md)** for detailed plans.

## 🤝 Contributing

Always open to feedback and suggestions! Feel free to:

- 🐛 Report bugs
- 💡 Suggest features
- 🔧 Submit pull requests
- 📖 Improve documentation

## 📝 License

MIT License - feel free to use this project for learning or your own TCF practice needs!

## 🙏 Acknowledgments

- **[Boot.dev](https://www.boot.dev)** for their free Python and Go tutorials, and for organizing the hackathon that motivated me to finally commit time to this project
- **[Réussir TCF](https://reussir-tcfcanada.com/)** for providing free access to sample exam tasks, updated regularly with current TCF standards
- **F. Hazrati**, an excellent French teacher who always strives to help her students succeed while keeping learning enjoyable

---

**Happy practicing! Bonne chance avec votre TCF! 🇫🇷✨**
