from pydantic import BaseModel, Field
from typing import Optional

class ProfileCreate(BaseModel):
    display_name: str = Field(...)
    bio: Optional[str] = None
    