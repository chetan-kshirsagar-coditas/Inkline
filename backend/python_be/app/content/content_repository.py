from sqlalchemy.orm import Session
import uuid
from sqlalchemy import select
from app.models.content import Content

class ContentRepository:

    @staticmethod
    def get_content_by_id(content_id: uuid.UUID, db: Session):
        return db.execute(select(Content).where(Content.id == content_id)).scalar_one_or_none()