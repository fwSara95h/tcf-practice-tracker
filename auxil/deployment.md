
# 🚀 Deployment Options

## 1. Local Production Build

**Windows:**
```powershell
# Build executable
go build -o tcf-tracker.exe

# Run production server
.\tcf-tracker.exe
```

**macOS/Linux:**
```bash
# Build executable
go build -o tcf-tracker

# Run production server
./tcf-tracker
```

## 2. Cloud Deployment

### **Heroku**
```bash
# Create Procfile
echo "web: ./tcf-tracker" > Procfile

# Deploy
git push heroku main
```

### **Railway**
```bash
# Just push to Git - Railway auto-detects Go
git push origin main
```

### **DigitalOcean App Platform**
1. Upload your code to GitHub
2. Connect DigitalOcean to your repository
3. Select Go as runtime
4. Deploy automatically

### **VPS/Traditional Server**
```bash
# Build for Linux (if developing on Windows/Mac)
GOOS=linux GOARCH=amd64 go build -o tcf-tracker

# Upload tcf-tracker + templates/ + static/ + data/ to server
# Run with systemd or similar service manager
```

## 3. Docker Deployment
```dockerfile
FROM golang:1.24-alpine AS builder
WORKDIR /app
COPY . .
RUN go build -o tcf-tracker

FROM alpine:latest
RUN apk --no-cache add ca-certificates
WORKDIR /root/
COPY --from=builder /app/tcf-tracker .
COPY --from=builder /app/templates ./templates
COPY --from=builder /app/static ./static
COPY --from=builder /app/data ./data
CMD ["./tcf-tracker"]
```
