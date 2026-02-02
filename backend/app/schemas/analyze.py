from __future__ import annotations

from pydantic import BaseModel


class AnalyzeResponse(BaseModel):
    run_id: str
    docx_path: str
    file_count: int
