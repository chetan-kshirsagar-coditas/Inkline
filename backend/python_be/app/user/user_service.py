from sqlalchemy.orm import Session
from app.user.user_repository import UserRepository, User
from fastapi import HTTPException, status
from app.utils.file_upload_service import S3_service
from fastapi.responses import JSONResponse
import os


class UserService:

    @staticmethod
    def update_profile(display_name: str, bio: str, profile_picture, db: Session, user: User):
        existing_user = UserRepository.get_user_by_email(user.email, db)
        if not user:
            raise HTTPException(
                status_code = status.HTTP_404_NOT_FOUND,
                detail = f"User not found"
            )
        try:
            key = S3_service.upload_file(filepath:=profile_picture.filename, profile_picture.filename, "profile_picture")
        except Exception as e:
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail=f"S3 Service error {e}"
            )
        UserRepository.update_profile(existing_user, display_name, bio, key, db)
        os.remove(filepath)

        return JSONResponse(
            status_code=status.HTTP_200_OK,
            content={
                "message": f"Profile updated successfully"
            }
        )