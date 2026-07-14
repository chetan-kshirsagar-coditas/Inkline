from google import genai
from app.core.config import settings
from app.content.content_schema import AgentResponse
from fastapi.responses import JSONResponse
from fastapi import status

client = genai.Client(
    api_key=settings.GEMINI_API_KEY,
)

class Agent:

    @staticmethod
    def generate_output(user_query: str, role: str):

        if role == "grammar_checker":
            system_prompt = "assume you are an assistant that checks the grammatical errors and shortcomings in user's article. Your job is to suggest changes (not make any changes on your own, just suggest changes) to the reviewer. These recommendations should be not more than one small sentence and should not contain positives about the content, rather just the negatives. If the recommendations are minimal and do not require much changes, the score should be around 1, on the other hand if the recommendations are strong, the score should be around 8 or 9 or 10. The only acceptable format for output is json object with the following keys: 'recommendation': <string>, 'score': <integer>. No extra text is allowed other than a valid json object."
        elif role == "clarity_checker":
            system_prompt = "assume you are an assistant that checks the clarity in writing of user's article. Your job is to give any recommendations (not make any changes on your own, just suggest changes) to the reviewer. These recommendations should be not more than one small sentence and should not contain positives about the content, rather just the negatives. If the recommendations are minimal and do not require much changes, the score should be around 1, on the other hand if the recommendations are strong, the score should be around 8 or 9 or 10. The only acceptable format for output is json object with the following keys: 'recommendation': <string>, 'score': <integer>. No extra text is allowed other than a valid json object"
        elif role == "tone_checker":
            system_prompt = "assume you are an assistant that checks the the tone in writing of user's article. Your job is to give any recommendations (not make any changes on your own, just suggest changes) to the reviewer. These recommendations should be not more than one small sentence and should not contain positives about the content, rather just the negatives. If the recommendations are minimal and do not require much changes, the score should be around 1, on the other hand if the recommendations are strong, the score should be around 8 or 9 or 10. The only acceptable format for output is json object with the following keys: 'recommendation': <string>, 'score': <integer>. No extra text is allowed other than a valid json object"
        elif role == "final_verdict":
            system_prompt = "you will receive three reviews of some content in the following key-value manner: 'recommendation': str, 'score': int. Your task is to summarize all the three recommendations and create an average score. If the recommendations are minimal and do not require much changes, the score should be around 1, on the other hand if the recommendations are strong, the score should be around 8 or 9 or 10. The only acceptable format for output is json object with the following keys: 'recommendation': <string>, 'score': <integer>. No extra text is allowed other than a valid json object"

        response = client.interactions.create(
            model="gemini-2.5-flash",
            input = f"""
                (
                    "role": 'system',
                    "content": {system_prompt}
                ),
                (
                    "role": 'user',
                    "content": {user_query}
                )
            """,
            response_format={
                "type": "text",
                "mime_type": "application/json",
                "schema": AgentResponse.model_json_schema()
            },
        ).output_text
        raw_dict = response.split("\n")
        import json
        return json.loads(raw_dict[1] + raw_dict[2] + raw_dict[3] + raw_dict[4])
