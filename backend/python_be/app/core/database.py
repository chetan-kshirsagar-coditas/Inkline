from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, DeclarativeBase
from app.core.config import settings

engine = create_engine(url=settings.DB_URL)
session = sessionmaker(bind=engine, autoflush=False, autocommit=False)

class Base(DeclarativeBase):
    pass
    

class Database:

    @staticmethod
    def get_db():
        db = session()
        try:
            yield db
        finally:
            db.close()

database = Database()