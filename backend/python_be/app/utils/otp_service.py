import random
from passlib.context import CryptContext
from app.utils.redis_service import RedisClient
from fastapi import status, HTTPException
from app.utils.email_service import EmailService
from app.user.user_repository import UserRepository
from sqlalchemy.orm import Session
from fastapi.responses import JSONResponse


password_context = CryptContext(schemes="bcrypt", deprecated="auto")


class OTPService:

    @staticmethod
    def request_otp(email: str, db: Session):
        otp = random.randint(100000, 999999)
        otp_hash = password_context.hash(str(otp))
        
        existing_user = UserRepository.get_user_by_email(email, db)
        if not existing_user:
            return JSONResponse(
                status_code=status.HTTP_200_OK,
                content={
                    "message": "OTP Sent Successfully"
                }
            )
        
        try: 
            RedisClient.client().set("email", otp_hash, ex=180)
        except Exception as e:
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail=f"Someting went wrong. More details: {e}"  
            )
        
        try:
            body = f"""
                    Here is your OTP code to login into Inkline:
                        
                                    {otp}
                        
                        This code is valid for 3 minutes.

                """
            EmailService.send_email(email, "Inkline | Your Login OTP", body)
        except Exception as e:
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail=f"Someting went wrong. More details: {e}"
            )
        
        return JSONResponse(
                status_code=status.HTTP_200_OK,
                content={
                    "message": "OTP Sent Successfully"
                }
            )