from __future__ import annotations

import os
from pathlib import Path

PROJECT_ROOT = Path(__file__).resolve().parents[3]
FRONTEND_DIR = PROJECT_ROOT / "frontend"
STORAGE_DIR = PROJECT_ROOT / "shared" / "runs"
STORAGE_DIR.mkdir(parents=True, exist_ok=True)

MAX_WORKERS = max(os.cpu_count() or 2, 4)

EXCLUDED_DIRS = {".git", ".idea", ".vscode", "node_modules", "target", "build"}
