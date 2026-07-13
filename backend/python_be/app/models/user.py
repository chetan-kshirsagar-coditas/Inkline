from sqlalchemy import Column, String, Enum, func, DateTime, ForeignKey
from app.core.database import Base
import uuid
from sqlalchemy.dialects.postgresql import UUID
from enum import Enum as Enum_


class Role(str, Enum_):
    ADMIN = "ADMIN"
    AUTHOR = "AUTHOR"
    EDITOR = "EDITOR"


class User(Base):
    __tablename__ = "users"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    email = Column(String, unique=True, nullable=False)
    first_name = Column(String, nullable=False)
    last_name = Column(String, nullable=False)
    role = Column(Enum(Role), nullable=False)
    profile_picture_url = Column(String, nullable=True)
    created_by = Column(UUID(as_uuid=True), ForeignKey("users.id"), nullable=False)
    created_at = Column(DateTime, default=func.now())