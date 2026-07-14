from fastapi import APIRouter, Depends
from app.core.database import database
from sqlalchemy.orm import Session
from app.auth.login_schema import SignupSchema, RequestOTP, VerifyOTP
from app.auth.login_service import LoginService
from app.utils.role_checker import RoleChecker
from app.models.user import User
from app.utils.otp_service import OTPService
from fastapi.security import OAuth2PasswordRequestForm


router = APIRouter(prefix="/auth", tags=["auth"])

@router.post("/register")
def register(new_user: SignupSchema, db: Session = Depends(database.get_db), user: User = Depends(RoleChecker.role_checker(["ADMIN"]))):

    return LoginService.create_new_user(new_user.email, new_user.first_name, new_user.last_name, new_user.role, db, user)

@router.post("/request_otp")
def request_otp(data: RequestOTP, db: Session = Depends(database.get_db)):
    return OTPService.request_otp(data.email, db)

@router.post("/login")
def login(data: OAuth2PasswordRequestForm = Depends(), db: Session = Depends(database.get_db)):
    return OTPService.verify_otp(data.username, data.password, db)

@router.post("/verify_otp")
def verify_otp(data: VerifyOTP, db: Session = Depends(database.get_db)):
    return OTPService.verify_otp(data.email, data.otp, db)

@router.get("/me")
def get_me(user: User = Depends(RoleChecker.get_current_user)):
    return LoginService.get_me(user)
    