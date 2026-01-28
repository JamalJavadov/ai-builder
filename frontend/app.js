const analyzeStatus = document.getElementById("analyzeStatus");
const analyzePathButton = document.getElementById("analyzePath");
const analyzeUploadButton = document.getElementById("analyzeUpload");
const promptOutput = document.getElementById("promptOutput");
const applyOutput = document.getElementById("applyOutput");

function setStatus(message) {
  analyzeStatus.textContent = message;
}

async function analyzeByPath() {
  const path = document.getElementById("projectPath").value.trim();
  if (!path) {
    setStatus("Enter a project path to analyze.");
    return;
  }

  setStatus("Analyzing project... this may take a while for large projects.");
  const formData = new FormData();
  formData.append("path", path);

  const response = await fetch("/api/projects/analyze", {
    method: "POST",
    body: formData,
  });
  const result = await response.json();
  if (!response.ok) {
    setStatus(`Error: ${result.detail}`);
    return;
  }

  setStatus(`DOCX ready. Files: ${result.file_count}. Downloading...`);
  window.location.href = `/api/projects/download?path=${encodeURIComponent(result.docx_path)}`;
}

async function analyzeUpload() {
  const input = document.getElementById("folderUpload");
  if (!input.files.length) {
    setStatus("Select a folder to upload.");
    return;
  }

  setStatus("Uploading files and analyzing... this may take a while.");
  const formData = new FormData();
  Array.from(input.files).forEach((file) => {
    formData.append("files", file, file.webkitRelativePath || file.name);
  });

  const response = await fetch("/api/projects/upload", {
    method: "POST",
    body: formData,
  });
  const result = await response.json();
  if (!response.ok) {
    setStatus(`Error: ${result.detail}`);
    return;
  }

  setStatus(`DOCX ready. Files: ${result.file_count}. Downloading...`);
  window.location.href = `/api/projects/download?path=${encodeURIComponent(result.docx_path)}`;
}

async function buildPrompt() {
  const task = document.getElementById("taskInput").value.trim();
  if (!task) {
    promptOutput.textContent = "Please enter a task.";
    return;
  }
  const projectSummary = document.getElementById("projectSummary").value.trim();
  const response = await fetch("/api/prompts/build", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ task, project_summary: projectSummary || null }),
  });
  const result = await response.json();
  promptOutput.textContent = JSON.stringify(result, null, 2);
}

async function applyPatch() {
  const projectRoot = document.getElementById("projectRoot").value.trim();
  if (!projectRoot) {
    applyOutput.textContent = "Enter the project root path.";
    return;
  }
  let operations;
  try {
    operations = JSON.parse(document.getElementById("jsonInput").value);
  } catch (error) {
    applyOutput.textContent = `Invalid JSON: ${error.message}`;
    return;
  }

  const response = await fetch("/api/patch/apply", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ project_root: projectRoot, operations }),
  });
  const result = await response.json();
  if (!response.ok) {
    applyOutput.textContent = `Error: ${result.detail}`;
    return;
  }
  applyOutput.textContent = JSON.stringify(result, null, 2);
}

analyzePathButton.addEventListener("click", () => analyzeByPath().catch((error) => {
  setStatus(`Error: ${error.message}`);
}));

analyzeUploadButton.addEventListener("click", () => analyzeUpload().catch((error) => {
  setStatus(`Error: ${error.message}`);
}));

document.getElementById("buildPrompt").addEventListener("click", () => {
  buildPrompt().catch((error) => {
    promptOutput.textContent = `Error: ${error.message}`;
  });
});

document.getElementById("applyPatch").addEventListener("click", () => {
  applyPatch().catch((error) => {
    applyOutput.textContent = `Error: ${error.message}`;
  });
});
