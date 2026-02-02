from __future__ import annotations

from pathlib import Path

from fastapi import APIRouter, HTTPException
from fastapi.responses import JSONResponse

from ...schemas.apply import ApplyRequest
from ...services.patch_service import apply_operations

router = APIRouter()


@router.post("/apply")
async def apply_patch(request: ApplyRequest) -> JSONResponse:
    root_dir = Path(request.project_root).expanduser().resolve()
    if not root_dir.exists() or not root_dir.is_dir():
        raise HTTPException(status_code=400, detail="Project root not found")

    try:
        results = apply_operations(root_dir, request.operations)
    except ValueError as exc:
        raise HTTPException(status_code=400, detail=str(exc)) from exc

    return JSONResponse({"status": "ok", "results": results})
