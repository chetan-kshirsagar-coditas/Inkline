from sqlalchemy import Column, String, func, DateTime, ForeignKey
from app.core.database import Base
import uuid
from sqlalchemy.dialects.postgresql import UUID

class User(Base):
    __tablename__ = "users"

    user_id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    email = Column(String, unique=True, nullable=False)
    first_name = Column(String, nullable=False)
    last_name = Column(String, nullable=False)
    created_at = Column(DateTime, default=func.now)
    created_by = Column(UUID(as_uuid=True), ForeignKey("users.user_id"), nullable=False)