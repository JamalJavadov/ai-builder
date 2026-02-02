from __future__ import annotations

from fastapi import APIRouter
from fastapi.responses import JSONResponse

from ...schemas.prompt import PromptRequest

router = APIRouter()


@router.post("/build")
async def build_prompt(request: PromptRequest) -> JSONResponse:
    instructions = (
        "You are an expert software engineer. You will receive a DOCX export that "
        "contains the full project structure and source code. Based on the user's "
        "task, output a JSON array of file operations. Each operation must be an object "
        "with: action (create|update|delete), path (relative path), and content (for "
        "create/update). Paths must be relative to the project root shown in the DOCX "
        "tree (e.g., 'src/main/java/...'), and must not include absolute paths or the "
        "project folder name. Return JSON only, no markdown."
    )
    prompt_payload = {
        "task": request.task,
        "project_summary": request.project_summary,
        "output_format": "JSON",
        "instructions": instructions,
        "schema": {
            "action": "create|update|delete",
            "path": "relative/file/path.ext",
            "content": "file content for create/update",
        },
    }
    return JSONResponse(prompt_payload)
