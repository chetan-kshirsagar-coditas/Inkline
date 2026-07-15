from fastapi import APIRouter
from app.user.user_schema import ModifyUser


router = APIRouter(prefix="/users", tags = ["users/roles"])

@router.patch("/update_role")
def update_user_role(upadtes: ModifyUser):
    pass