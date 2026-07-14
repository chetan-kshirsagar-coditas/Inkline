from fastapi import FastAPI, status
from fastapi.responses import JSONResponse
from app.auth.login_api import router as auth_router

app = FastAPI()

app.include_router(auth_router)

@app.post("/health")
def health():
    return JSONResponse(
        status_code = status.HTTP_200_OK,
        content=f"Working"
    )
