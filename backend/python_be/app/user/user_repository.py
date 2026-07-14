from sqlalchemy.orm import Session
from sqlalchemy import select
from app.models.user import User
from fastapi import HTTPException, status
from fastapi.responses import JSONResponse

class UserRepository:

    @staticmethod
    def get_user_by_email(email: str, db: Session):
        return db.execute(select(User).where(User.email == email)).scalar_one_or_none()
    
    @staticmethod
    def create_new_user(new_user: User, db: Session):
        try:
            db.add(new_user)
            db.commit()
            db.refresh(new_user)
        except Exception as e:
            raise HTTPException(
                status_code = status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail=f"Database Error Occured while creating new user. more details: {e}"
            )
        
    @staticmethod
    def update_profile(existing_user: User, display_name: str, bio: str, profile_url: str, db: Session):
        try:
            if bio != None:
                existing_user.bio = bio
            if display_name != None:
                existing_user.display_name = display_name
            if profile_url != None:
                existing_user.profile_picture_url = profile_url
            db.commit()
            db.refresh(existing_user)
        except Exception as e:
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail=f"Database error occured while updating user profile. more details: {e}"
            )
        
