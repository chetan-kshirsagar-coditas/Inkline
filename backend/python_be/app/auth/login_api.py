from fastapi import APIRouter, Depends
from app.core.database import database
from sqlalchemy.orm import Session
from app.auth.login_schema import SignupSchema, RequestOTP
from app.auth.login_service import LoginService
from app.utils.role_checker import RoleChecker
from app.models.user import User

router = APIRouter("/auth", prefix=["auth"])

@router.post("/register")
def register(new_user: SignupSchema, db: Session = Depends(database.get_db), user: User = Depends(RoleChecker.role_checker(["ADMIN"]))):
    return LoginService.create_new_user(new_user.email, new_user.first_name, new_user.last_name, new_user.role, db)

@router.post("/request_otp")
def request_otp(email: RequestOTP, db: Session = Depends(database.get_db)):
    return 