from __future__ import annotations

from datetime import datetime
from pathlib import Path

from fastapi import APIRouter, File, Form, HTTPException, UploadFile
from fastapi.responses import FileResponse

from ...core.settings import STORAGE_DIR
from ...schemas.analyze import AnalyzeResponse
from ...services.docx_service import generate_docx

router = APIRouter()


@router.post("/analyze", response_model=AnalyzeResponse)
async def analyze_project(path: str = Form(...)) -> AnalyzeResponse:
    root_dir = Path(path).expanduser().resolve()
    if not root_dir.exists() or not root_dir.is_dir():
        raise HTTPException(status_code=400, detail="Project path not found")

    run_id = datetime.utcnow().strftime("%Y%m%d%H%M%S")
    run_dir = STORAGE_DIR / run_id
    run_dir.mkdir(parents=True, exist_ok=True)
    output_path = run_dir / "project-export.docx"

    file_count = generate_docx(root_dir, output_path)
    return AnalyzeResponse(run_id=run_id, docx_path=str(output_path), file_count=file_count)


@router.post("/upload", response_model=AnalyzeResponse)
async def analyze_upload(files: list[UploadFile] = File(...)) -> AnalyzeResponse:
    if not files:
        raise HTTPException(status_code=400, detail="No files uploaded")

    run_id = datetime.utcnow().strftime("%Y%m%d%H%M%S")
    upload_root = STORAGE_DIR / run_id / "upload"
    upload_root.mkdir(parents=True, exist_ok=True)

    for file in files:
        relative_path = Path(file.filename)
        destination = upload_root / relative_path
        destination.parent.mkdir(parents=True, exist_ok=True)
        content = await file.read()
        destination.write_bytes(content)

    output_path = STORAGE_DIR / run_id / "project-export.docx"
    file_count = generate_docx(upload_root, output_path)
    return AnalyzeResponse(run_id=run_id, docx_path=str(output_path), file_count=file_count)


@router.get("/download")
async def download_docx(path: str) -> FileResponse:
    docx_path = Path(path)
    if not docx_path.exists():
        raise HTTPException(status_code=404, detail="DOCX not found")
    return FileResponse(docx_path, filename=docx_path.name)
