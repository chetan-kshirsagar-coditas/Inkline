from sqlalchemy import Column, String, func, DateTime, Boolean, ForeignKey
from app.core.database import Base
import uuid
from sqlalchemy.dialects.postgresql import UUID


class Attachment(Base):
    __tablename__ = "attachments"
    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    content = Column(UUID(as_uuid=True), ForeignKey("contents.id"), nullable=False)
    attachment_url = Column(String, ForeignKey("documents.document_url"), nullable=False)
    is_public = Column(Boolean, nullable=False)
    uploaded_at = Column(DateTime, default=func.now())