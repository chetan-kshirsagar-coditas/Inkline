from sqlalchemy.orm import Session
from app.user.user_repository import UserRepository
from fastapi import HTTPException, status
from app.models.user import User
from fastapi.responses import JSONResponse

class LoginService:

    @staticmethod
    def create_new_user(email: str, first_name: str, last_name: str, role: str, db: Session, current_user: User):
        existing_user = UserRepository.get_user_by_email(email, db)
        if existing_user:
            raise HTTPException(
                status_code=status.HTTP_403_FORBIDDEN,
                detail=f"User already exists."
            )
        new_user = User(
            email=email,
            first_name=first_name,
            last_name=last_name,
            role=role,
            created_by=current_user.id
        )
        return UserRepository.create_new_user(new_user, db)
    
    @staticmethod
    def get_me(user: User):
        user_details = {
            "user_id": str(user.id),
            "email": user.email,
            "first_name": user.first_name,
            "last_name": user.last_name,
            "role": user.role
        }

        return JSONResponse(
            status_code=status.HTTP_200_OK,
            content=user_details
        )