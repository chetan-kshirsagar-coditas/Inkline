from sqlalchemy import Column, Enum, String, func, DateTime, ForeignKey
from app.core.database import Base
import uuid
from sqlalchemy.dialects.postgresql import UUID
from enum import Enum as Enum_


class DocumentType(str, Enum_):
    PROFILE_PIC = "PROFILE_PIC"
    ATTACHMENT = "ATTACHMENT"


class Document(Base):
    __tablename__ = "documents"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    document_type = Column(Enum(DocumentType), nullable=False)
    document_url = Column(String, unique=True, nullable=False)