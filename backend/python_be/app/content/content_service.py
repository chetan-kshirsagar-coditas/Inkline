from sqlalchemy.orm import Session
from app.utils.agent import Agent
import uuid
from app.content.content_repository import ContentRepository
from app.content.recommendations_service import RecommendationService
from fastapi import HTTPException, status
from fastapi.responses import JSONResponse
from app.models.user import User


class ContentService:

    @staticmethod
    def generate_recommendation(content_id: uuid.UUID, db: Session):
        content = ContentRepository.get_content_by_id(content_id, db)
        
        dict_generated_recommendation =  Agent.generate_output(
            user_query=f"""(
                "recommendation_1" = {Agent.generate_output(content.body, "grammar_checker")},
                "recommendation_2" = {Agent.generate_output(content.body, "clarity_checker")},
                "recommendation_3" = {Agent.generate_output(content.body, "tone_checker")}
            )""",
            role="final_verdict"
        )
        recommendation = dict_generated_recommendation.get('recommendation')
        score = dict_generated_recommendation.get('score')

        new_recommendation_record = RecommendationService.create_recommendation_record(content.id, recommendation, score, db)
        if not new_recommendation_record:
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail=f"Something went wrong. 💀"
            )
        content.content_status = "UNDER_EDITOR_REVIEW"
        db.commit()
        db.refresh(content)

        return JSONResponse(
            status_code=status.HTTP_200_OK,
            content= {
                'message': "Recomendation generated successfully"
            }
        )

        
    @staticmethod
    def get_all_content_under_ai_review(db: Session):
        return ContentRepository.get_all_content_under_ai_review(db)
