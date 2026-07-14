from sqlalchemy import Column, Text, func, DateTime, ForeignKey, Integer
from app.core.database import Base
import uuid
from sqlalchemy.dialects.postgresql import UUID


class AiRecommendation(Base):
    __tablename__ = "ai_recommendation"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    content = Column(UUID(as_uuid=True), ForeignKey("contents.id"), nullable=False)
    suggestions = Column(Text, nullable=False)
    score = Column(Integer, nullable=False)
    created_at = Column(DateTime, default=func.now())