from __future__ import annotations

from pydantic import BaseModel, Field


class PromptRequest(BaseModel):
    task: str = Field(..., min_length=5, description="User's task description")
    project_summary: str | None = Field(
        None,
        description="Optional summary of the project to include in the prompt",
    )
