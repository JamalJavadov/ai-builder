from __future__ import annotations

import os
from concurrent.futures import ThreadPoolExecutor, as_completed
from datetime import datetime
from pathlib import Path
from typing import Iterable

from ..core.settings import EXCLUDED_DIRS, MAX_WORKERS


def is_binary_file(path: Path) -> bool:
    try:
        with path.open("rb") as handle:
            chunk = handle.read(2048)
        return b"\x00" in chunk
    except OSError:
        return True


def iter_files(root_dir: Path) -> Iterable[Path]:
    for base, dirs, files in os.walk(root_dir):
        dirs[:] = [d for d in dirs if d not in EXCLUDED_DIRS]
        for filename in files:
            yield Path(base) / filename


def build_tree(root_dir: Path) -> str:
    lines: list[str] = []
    root_dir = root_dir.resolve()
    prefix_map: dict[Path, str] = {root_dir: ""}

    for base, dirs, files in os.walk(root_dir):
        base_path = Path(base)
        prefix = prefix_map.get(base_path, "")
        entries = sorted(dirs) + sorted(files)
        for index, name in enumerate(entries):
            path = base_path / name
            connector = "└── " if index == len(entries) - 1 else "├── "
            lines.append(f"{prefix}{connector}{name}")
            if path.is_dir():
                extension = "    " if index == len(entries) - 1 else "│   "
                prefix_map[path] = prefix + extension
    return "\n".join(lines)


def read_file_text(path: Path) -> str:
    if is_binary_file(path):
        return "<binary file omitted>"
    try:
        return path.read_text(encoding="utf-8", errors="replace")
    except OSError as exc:
        return f"<unable to read file: {exc}>"


def gather_file_records(root_dir: Path) -> list[dict[str, str]]:
    file_paths = list(iter_files(root_dir))
    records: list[dict[str, str]] = []
    with ThreadPoolExecutor(max_workers=MAX_WORKERS) as executor:
        futures = {executor.submit(read_file_text, path): path for path in file_paths}
        for future in as_completed(futures):
            path = futures[future]
            relative_path = path.relative_to(root_dir).as_posix()
            stat = path.stat()
            content = future.result()
            records.append(
                {
                    "path": relative_path,
                    "size": str(stat.st_size),
                    "modified": datetime.fromtimestamp(stat.st_mtime).isoformat(),
                    "content": content,
                }
            )
    records.sort(key=lambda item: item["path"])
    return records


def safe_join(root_dir: Path, relative_path: str) -> Path:
    destination = (root_dir / relative_path).resolve()
    if root_dir not in destination.parents and destination != root_dir:
        raise ValueError("Invalid path outside project root")
    return destination
