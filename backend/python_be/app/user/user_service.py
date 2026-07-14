from sqlalchemy.orm import Session
from app.user.user_repository import UserRepository, User
from fastapi import HTTPException, status


class UserService:

    @staticmethod
    def update_profile(display_name: str, bio: str, profile_picture, db: Session, user: User):
        existing_user = UserRepository.get_user_by_email(user.email, db)
        if not user:
            raise HTTPException(
                status_code = status.HTTP_404_NOT_FOUND,
                detail = f"User not found"
            )
        profile_url = "abcd"
        
        UserRepository.update_profile(existing_user, display_name, bio, profile_url, db)