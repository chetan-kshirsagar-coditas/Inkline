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

    @property
    def DB_URL(self):
        return f"postgresql+psycopg2://{self.DB_USERNAME}:{self.DB_PASSWORD}@{self.DB_HOST}:{self.DB_PORT}/{self.DB_NAME}"
    
    model_config={
        'env_file': ".env"
    }

    
settings = Setting()