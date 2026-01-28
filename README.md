# Project Analyzer Bot

This tool analyzes a Java project (or any source tree), exports a full DOCX document with the complete folder structure and file contents, generates structured AI prompts, and applies AI-provided JSON patch instructions back into the project.

## Features
- Full DOCX export with folder tree + file contents.
- Multithreaded file reading for large projects.
- Prompt builder for AI-assisted refactors.
- Patch application endpoint for AI-generated JSON changes.
- Web UI for uploads, prompt generation, and patch application.

## Setup
```bash
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

## Run
```bash
uvicorn backend.app:app --reload --port 8000
```
Open http://localhost:8000 in your browser.

## API Endpoints
- `POST /api/projects/analyze` (form field `path`): Analyze a server-side project path.
- `POST /api/projects/upload`: Upload a folder from the browser (uses `webkitdirectory`).
- `GET /api/projects/download?path=...`: Download the DOCX file.
- `POST /api/prompts/build`: Build the AI prompt JSON.
- `POST /api/patch/apply`: Apply AI JSON operations.

## JSON Patch Format
```json
[
  {"action": "create", "path": "src/main/java/...", "content": "..."},
  {"action": "update", "path": "README.md", "content": "..."},
  {"action": "delete", "path": "src/legacy/Old.java"}
]
```

## Notes
- Excludes common heavy directories by default: `.git`, `node_modules`, `target`, `build`.
- Binary files are noted in the DOCX export instead of embedding raw bytes.
