from __future__ import annotations

import shutil
from pathlib import Path

from ..schemas.apply import ApplyOperation
from ..utils.filesystem import safe_join


def apply_operations(root_dir: Path, operations: list[ApplyOperation]) -> list[str]:
    messages: list[str] = []
    for operation in operations:
        action = operation.action.lower()
        normalized_path = Path(operation.path).as_posix().lstrip("./")
        if normalized_path.startswith(f"{root_dir.name}/"):
            raise ValueError(
                f"Path must be relative to project root. Remove leading '{root_dir.name}/'."
            )
        destination = safe_join(root_dir, normalized_path)
        if action in {"create", "update"}:
            destination.parent.mkdir(parents=True, exist_ok=True)
            if operation.content is None:
                raise ValueError(f"Missing content for {action} {normalized_path}")
            destination.write_text(operation.content, encoding="utf-8")
            messages.append(f"{action}: {normalized_path}")
        elif action == "delete":
            if destination.exists():
                if destination.is_dir():
                    shutil.rmtree(destination)
                else:
                    destination.unlink()
                messages.append(f"deleted: {normalized_path}")
            else:
                messages.append(f"skipped (missing): {normalized_path}")
        else:
            raise ValueError(f"Unknown action: {operation.action}")
    return messages
