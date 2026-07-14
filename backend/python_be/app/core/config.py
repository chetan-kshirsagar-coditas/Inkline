from pydantic_settings import BaseSettings
from pydantic import EmailStr
import uuid


class Setting(BaseSettings):

    DB_USERNAME: str
    DB_PASSWORD: str
    DB_HOST: str
    DB_PORT: str
    DB_NAME: str

    
    ADMIN_ID: uuid.UUID
    ADMIN_EMAIL: EmailStr
    ADMIN_FIRST_NAME: str
    ADMIN_LAST_NAME: str


    SECRET_KEY: str
    ALGORITHM: str
    EXPIRATION_DURATION: int


    AWS_ACCESS_KEY_ID: str
    AWS_SECRET_ACCESS_KEY: str
    REGION: str
    SES_SENDER_MAIL: str


    
    DB_URL: str
    
    model_config={
        'env_file': ".env"
    }

    
settings = Setting()