from sqlalchemy.orm import Session
import uuid
from sqlalchemy import select, and_
from app.models.content import Content

class ContentRepository:

    @staticmethod
    def get_content_by_id(content_id: uuid.UUID, db: Session):
        return db.execute(select(Content).where(and_(Content.id == content_id, Content.content_status == "UNDER_AI_REVIEW"))).scalar_one_or_none()
    
    @staticmethod
    def get_all_content_under_ai_review(db: Session):
        return db.execute(select(Content).where(Content.content_status == "UNDER_AI_REVIEW")).scalars().all()
        