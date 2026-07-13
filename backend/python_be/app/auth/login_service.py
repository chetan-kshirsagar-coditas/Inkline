from sqlalchemy.orm import Session
from app.user.user_repository import UserRepository
from fastapi import HTTPException, status
from app.models.user import User


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
            created_by=current_user.user_id
        )