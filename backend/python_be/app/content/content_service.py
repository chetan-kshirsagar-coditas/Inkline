from sqlalchemy.orm import Session
from app.utils.agent import Agent
import uuid
from app.content.content_repository import ContentRepository
from app.content.recommendations_service import RecommendationService



class ContentService:

    @staticmethod
    def generate_recommendation(content_id: uuid.UUID, db: Session):
        content = ContentRepository.get_content_by_id(content_id, db).body
        
        dict_generated_recommendation =  Agent.generate_output(
            user_query=f"""(
                "recommendation_1" = {Agent.generate_output(content, "grammar_checker")},
                "recommendation_2" = {Agent.generate_output(content, "clarity_checker")},
                "recommendation_3" = {Agent.generate_output(content, "tone_checker")}
            )""",
            role="final_verdict"
        )
        recommendation = dict_generated_recommendation.get('recommendation')
        score = dict_generated_recommendation.get('score')

        return RecommendationService.create_recommendation_record()


        
