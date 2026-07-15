from sqlalchemy.orm import Session
from app.utils.agent import Agent


class ContentService:

    @staticmethod
    def generate_recommendation(content: str, db: Session):
        return Agent.generate_output(
            user_query=f"""(
                "recommendation_1" = {Agent.generate_output(content, "grammar_checker")},
                "recommendation_2" = {Agent.generate_output(content, "clarity_checker")},
                "recommendation_3" = {Agent.generate_output(content, "tone_checker")}
            )""",
            role="final_verdict"
        )

        
    