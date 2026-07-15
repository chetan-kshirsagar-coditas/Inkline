from sqlalchemy.orm import Session
import uuid
from app.content.content_repository import ContentRepository
from fastapi import HTTPException, status
from app.models.ai_recommendation import AiRecommendation
from app.content.recommendation_repository import RecommendationRepository


class RecommendationService:
    
    @staticmethod
    def create_recommendation_record(content_id: uuid.UUID, recommendation: str, score: int, db: Session):
        existing_content = ContentRepository.get_content_by_id(content_id, db)
        if not existing_content:
            raise HTTPException(
                status_code = status.HTTP_403_NOT_FOUND,
                detail= f"Content not found"
            )
        new_recommendation = AiRecommendation(
            
            content = existing_content.id,
            suggestions=recommendation,
            score=score
        )

        return RecommendationRepository.create_new_recommendation(new_recommendation, db)