from pydantic import BaseModel, Field 
import uuid

class ContentRecommendation(BaseModel):
    content_id: uuid.UUID = Field(...)

class AgentResponse(BaseModel):
    recommendation: str = Field(..., description="contains one line definition of the negatives about the content")
    score : int = Field(..., ge=0, le=10, description="contains the recommendation score 1 being a weak recommendation and 10 being strong recommendation")