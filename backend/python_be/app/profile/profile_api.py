from fastapi import APIRouter, Depends, UploadFile, HTTPException, status
from sqlalchemy.orm import Session
from app.utils.role_checker import RoleChecker
from app.core.database import database
from app.user.user_service import UserService
from app.profile.profile_schema import ProfileCreate
from app.models.user import User



router = APIRouter(prefix="/profile", tags=["profile"])

@router.patch("/set_profile")
async def set_profile(display_name: str, bio: str, profile_picture: UploadFile, db: Session = Depends(database.get_db), user: User = Depends(RoleChecker.role_checker(["ADMIN", "AUTHOR", "EDITOR"]))):
    if profile_picture.filename.split('.')[-1] not in ["png", "jpg", "jpeg"]:
        raise HTTPException(
            status_code = status.HTTP_400_BAD_REQUEST,
            detail=f"Invalid File type"
        )
    
    with open(profile_picture.filename, "wb") as f:
            content = await profile_picture.read()
            f.write(content)
    return UserService.update_profile(display_name, bio, profile_picture, db, user)