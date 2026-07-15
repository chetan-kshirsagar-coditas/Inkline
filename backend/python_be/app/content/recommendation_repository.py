from sqlalchemy.orm import Session
from app.models.ai_recommendation import AiRecommendation
from fastapi import HTTPException, status


class RecommendationRepository:

    @staticmethod
    def create_new_recommendation(new_recommendation: AiRecommendation, db: Session):
        try:
            db.add(new_recommendation)
            db.commit()
            db.refresh(new_recommendation)
        except Exception as e:
            raise HTTPException(
                status_code = status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail = f"Database error occured while creating new ai reommendation. more details: {e}"
            )
        return new_recommendation    
    