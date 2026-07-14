from groq import Groq
from app.core.config import settings
from fastapi import HTTPException, status
from fastapi.responses import JSONResponse

client = Groq(
    api_key=settings.GROQ_API_KEY
)


class GroqAgent:

    @staticmethod
    def generate_output(user_query: str, role: str):
        tries = 0
        while (tries <= 3):
            if role == "grammar_checker":
                system_prompt = "assume you are an assistant that checks the grammatical errors and shortcomings in user's article. Your job is to suggest changes (not make any changes on your own, just suggest changes) to the reviewer. If the recommendations are minimal and do not require much changes, the score should be around 1, on the other hand if the recommendations are strong, the score should be around 8 or 9 or 10. The only acceptable format for output is json object with the following keys: 'recommendation': <string>, 'score': <integer>. No extra text is allowed other than a valid json object"
            elif role == "charity_checker":
                system_prompt = "assume you are an assistant that checks the clarity in writing of user's article. Your job is to give any recommendations (not make any changes on your own, just suggest changes) to the reviewer. If the recommendations are minimal and do not require much changes, the score should be around 1, on the other hand if the recommendations are strong, the score should be around 8 or 9 or 10. The only acceptable format for output is json object with the following keys: 'recommendation': <string>, 'score': <integer>. No extra text is allowed other than a valid json object"
            elif role == "tone_checker":
                system_prompt = "assume you are an assistant that checks the the tone in writing of user's article. Your job is to give any recommendations (not make any changes on your own, just suggest changes) to the reviewer. If the recommendations are minimal and do not require much changes, the score should be around 1, on the other hand if the recommendations are strong, the score should be around 8 or 9 or 10. The only acceptable format for output is json object with the following keys: 'recommendation': <string>, 'score': <integer>. No extra text is allowed other than a valid json object"
            
            response = client.chat.completions.create(
                messages = [
                    {
                        "role": "system",
                        "content": system_prompt
                    },
                    {
                        "role": "user",
                        "content": user_query
                    }
                ]
            ).choices[0].message.content
            if (response[0] != '{') and (response[-1] != '}'):
                return response
            tries += 1
        return None