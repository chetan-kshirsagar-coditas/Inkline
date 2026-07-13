from sqlalchemy import Column, String, Text, Enum, func, DateTime, ForeignKey
from app.core.database import Base
import uuid
from sqlalchemy.dialects.postgresql import UUID
from enum import Enum as Enum_

class ContentStatus(str, Enum_):
    DRAFT= "DRAFT"
    UNDER_AI_REVIEW= "UNDER_AI_REVIEW"
    UNDER_EDITOR_REVIEW= "UNDER_EDITOR_REVIEW"
    CHANGES_REQUESTED= "CHANGES_REQUESTED"
    APPROVED = "APPROVED"
    PUBLISHED = "PUBLISHED"


class Content(Base):
    __tablename__ = "contents"
    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    title = Column(String, nullable=False)
    body = Column(Text, nullable=False)
    category = Column(UUID(as_uuid=True), default=None)
    author = Column(UUID(as_uuid=True), ForeignKey("users.id"), nullable=False)
    created_at = Column(DateTime, default=func.now())
    submitted_at = Column(DateTime, default=None)
    content_status = Column(Enum(ContentStatus), default="DRAFT")