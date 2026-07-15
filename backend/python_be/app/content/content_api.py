from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from app.utils.agent import Agent
from app.core.database import database
from app.content.content_schema import ContentRecommendation
from app.content.content_service import ContentService

router = APIRouter(prefix="/content", tags=["content"])


@router.post("/recommendation")
def create_recommendations(article_content: ContentRecommendation, db: Session = Depends(database.get_db)):
    return ContentService.generate_recommendation(article_content, db)