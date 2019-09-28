import sqlalchemy
from sqlalchemy.ext.declarative import declarative_base
Base = declarative_base()

class Table(Base):
    __tablename__ = 'data'
    date = sqlalchemy.column()