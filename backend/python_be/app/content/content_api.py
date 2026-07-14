from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from app.utils.agent import Agent
from app.core.database import database
from app.content.content_schema import ContentRecommendation


router = APIRouter(prefix="/content", tags=["content"])


@router.post("/recommendation")
def get_recommendations(article_content: ContentRecommendation, db: Session = Depends(database.get_db)):
    return Agent.generate_output(article_content, "grammar_checker")