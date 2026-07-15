from pydantic import BaseModel
from typing import Optional
import uuid

class ModifyUser(BaseModel):
    user_id: Optional[uuid.UUID] = None