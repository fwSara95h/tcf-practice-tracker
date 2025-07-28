package main

import (
	"database/sql"
	"encoding/json"
	"html/template"
	"log"
	"net/http"

	_ "modernc.org/sqlite"
)

type Task struct {
	ID       int    `json:"id"`
	Category string `json:"category"`
	TaskType string `json:"task_type"`
	TaskFormat string `json:"format_category"`
	Prompt   string `json:"prompt"`
	Scenario string `json:"scenario_category"`
}

type RandomPromptResponse struct {
	ID         int    `json:"id"`
	Category   string `json:"category"`
	TaskType   string `json:"task_type"`
	TaskFormat string `json:"task_format"`
	Prompt     string `json:"prompt"`
	Scenario   string `json:"scenario_category"`
}

var db *sql.DB

func initDB() {
	var err error
	db, err = sql.Open("sqlite", "./data/tcf.db")
	if err != nil {
		log.Fatal("Failed to open database:", err)
	}

	// Test the connection
	if err = db.Ping(); err != nil {
		log.Fatal("Failed to ping database:", err)
	}
	
	log.Println("Successfully connected to database")
}

func indexHandler(w http.ResponseWriter, r *http.Request) {
	tmpl, err := template.ParseFiles("templates/index.html")
	if err != nil {
		http.Error(w, "Error loading template", http.StatusInternalServerError)
		log.Printf("Template error: %v", err)
		return
	}

	err = tmpl.Execute(w, nil)
	if err != nil {
		http.Error(w, "Error executing template", http.StatusInternalServerError)
		log.Printf("Template execution error: %v", err)
	}
}

func randomPromptHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodGet {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}

	var task Task
	var taskFormatNull sql.NullString
	var taskScenarioNull sql.NullString
	query := "SELECT id, category, task_type, format_category, prompt, scenario_category FROM tasks ORDER BY RANDOM() LIMIT 1"
	row := db.QueryRow(query)
	
	err := row.Scan(&task.ID, &task.Category, &task.TaskType, &taskFormatNull, &task.Prompt, &taskScenarioNull)
	if err != nil {
		http.Error(w, "Error fetching random prompt", http.StatusInternalServerError)
		log.Printf("Database error: %v", err)
		return
	}

	// Handle NULL format_category
	if taskFormatNull.Valid {
		task.TaskFormat = taskFormatNull.String
	} else {
		task.TaskFormat = "Format?"
	}

	// Handle NULL scenario_category
	if taskScenarioNull.Valid {
		task.Scenario = taskScenarioNull.String
	} else {
		task.Scenario = "General"
	}
	response := RandomPromptResponse{
		ID:         task.ID,
		Category:   task.Category,
		TaskType:   task.TaskType,
		TaskFormat: task.TaskFormat,
		Prompt:     task.Prompt,
		Scenario:   task.Scenario,
	}
	
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}

func main() {
	// Initialize database
	initDB()
	defer db.Close()

	// Serve static files
	staticDir := http.Dir("./static/")
	staticFileServer := http.FileServer(staticDir)
	http.Handle("/static/", http.StripPrefix("/static/", staticFileServer))

	// Routes
	http.HandleFunc("/", indexHandler)
	http.HandleFunc("/api/random", randomPromptHandler)

	log.Println("Starting server on http://localhost:8080")
	log.Fatal(http.ListenAndServe(":8080", nil))
} 