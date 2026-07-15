from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from app.utils.agent import Agent
from app.core.database import database
from app.content.content_schema import ContentRecommendation
from app.content.content_service import ContentService
from app.models.user import User
from app.utils.role_checker import RoleChecker

router = APIRouter(prefix="/content", tags=["content"])


@router.post("/recommendation")
def create_recommendations(article_content: ContentRecommendation, db: Session = Depends(database.get_db), user: User = Depends(RoleChecker.role_checker(["AUTHOR", "EDITOR", "ADMIN"]))):
    return ContentService.generate_recommendation(article_content.content_id, db)


@router.get("/recommendations/under_ai_review")
def get_reommendations_under_ai_review(db: Session = Depends(database.get_db), user: User = Depends(RoleChecker.role_checker(["AUTHOR", "EDITOR", "ADMIN"]))):
    return ContentService.get_all_content_under_ai_review(db)