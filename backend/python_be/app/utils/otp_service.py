import random
from passlib.context import CryptContext
from app.utils.redis_service import RedisClient
from fastapi import status, HTTPException
from app.utils.email_service import EmailService
from app.user.user_repository import UserRepository
from sqlalchemy.orm import Session
from fastapi.responses import JSONResponse
from app.utils.jwt_helper import JWTHelper

password_context = CryptContext(schemes="bcrypt", deprecated="auto")


class OTPService:

    @staticmethod
    def request_otp(email: str, db: Session):
        otp = random.randint(100000, 999999)

        print(otp)

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
    
    @staticmethod
    def verify_otp(email: str, otp: int | str, db: Session):
        fetched_otp_hash = RedisClient.client().get('email')
        if not fetched_otp_hash:
            raise HTTPException(
                status_code=status.HTTP_400_BAD_REQUEST,
                detail=f"OTP Expired"
            )
        is_valid_otp = password_context.verify(str(otp), fetched_otp_hash)
        
        if not is_valid_otp:
            raise HTTPException(
                status_code = status.HTTP_400_BAD_REQUEST,
                detail=f"Invalid OTP entered."
            )
        
        user = UserRepository.get_user_by_email(email, db)

        user_details = {
            "email": user.email,
            "role": user.role
        }

        return JSONResponse(
            status_code=status.HTTP_200_OK,
            content = {
                "access_token": JWTHelper.encode_token(user_details)
            }
        )
    