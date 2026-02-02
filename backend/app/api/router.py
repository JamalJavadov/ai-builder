from __future__ import annotations

from fastapi import APIRouter

from .routes import health, patch, projects, prompts

api_router = APIRouter()
api_router.include_router(health.router, tags=["health"])
api_router.include_router(projects.router, prefix="/projects", tags=["projects"])
api_router.include_router(prompts.router, prefix="/prompts", tags=["prompts"])
api_router.include_router(patch.router, prefix="/patch", tags=["patch"])
