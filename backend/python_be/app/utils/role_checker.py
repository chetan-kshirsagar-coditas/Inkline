from fastapi.security import OAuth2PasswordBearer
from fastapi import Depends, HTTPException, status
from app.utils.jwt_helper import JWTHelper
from sqlalchemy.orm import Session
from app.user.user_repository import UserRepository
from app.core.database import database
from app.models.user import User



OAuth2Scheme = OAuth2PasswordBearer("/auth/login")

class RoleChecker:

    @staticmethod
    def get_current_user(token: str = Depends(OAuth2Scheme), db: Session = Depends(database.get_db)):
        payload = JWTHelper.decode_token(token)
        email = payload.get("email", None)
        print(payload)
        if not email:
            raise HTTPException(
                status_code=status.HTTP_401_UNAUTHORIZED,
                detail=f"sign in first"
            )
        current_user = UserRepository.get_user_by_email(email, db)
        return current_user
    

    @staticmethod
    def role_checker(required_roles: list[str]):
        def inner(user: User = Depends(RoleChecker.get_current_user), db: Session = Depends(database.get_db)):
            if not user:
                raise HTTPException(
                    status_code=status.HTTP_401_UNAUTHORIZED,
                    detail="User not authenticated"
                )
            user_role = user.role
            
            if user_role in required_roles:        
                return user
            raise HTTPException(
                    status_code=status.HTTP_403_FORBIDDEN,
                    detail=f"Access Denied. Your details {user.email} with roles {user_role}"
                )
        return inner