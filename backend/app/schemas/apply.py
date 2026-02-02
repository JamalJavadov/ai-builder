from __future__ import annotations

from pydantic import BaseModel, Field


class ApplyOperation(BaseModel):
    action: str = Field(..., description="create|update|delete")
    path: str = Field(..., description="Relative path inside the project")
    content: str | None = Field(None, description="New file contents for create/update")


class ApplyRequest(BaseModel):
    project_root: str = Field(..., description="Absolute path to project root")
    operations: list[ApplyOperation]
