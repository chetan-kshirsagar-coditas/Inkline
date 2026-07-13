from sqlalchemy import Column, String, func, DateTime, ForeignKey
from app.core.database import Base
import uuid
from sqlalchemy.dialects.postgresql import UUID

class Category(Base):
    __tablename__ = "categories"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    category_name = Column(String, nullable=False)
    created_by = Column(UUID(as_uuid=True), ForeignKey("users.id"), nullable=False)
    created_at = Column(DateTime, default=func.now())
