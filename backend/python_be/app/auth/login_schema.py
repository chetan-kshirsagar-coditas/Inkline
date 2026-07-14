from pydantic import BaseModel, Field, EmailStr
from app.models.user import Role

class SignupSchema(BaseModel):
    email: EmailStr = Field(...)
    first_name: str = Field(...)
    last_name: str = Field(...)
    role: Role = Field(...)
    
class RequestOTP(BaseModel):
    email: EmailStr = Field(...)

class VerifyOTP(BaseModel):
    email: EmailStr = Field(...)
    otp: int = Field(...)