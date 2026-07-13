from fastapi import FastAPI, status
from fastapi.responses import JSONResponse


app = FastAPI()

@app.post("/health")
def health():
    return JSONResponse(
        status_code = status.HTTP_200_OK,
        content=f"Working"
    )
