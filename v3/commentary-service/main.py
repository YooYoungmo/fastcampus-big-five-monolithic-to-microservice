import os
from fastapi import FastAPI
from openai import OpenAI
from pydantic import BaseModel
from sqlalchemy import Column, Integer, Text, create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

########################################################################################################
# SQLite 파일 데이터베이스 Setup
engine = create_engine('sqlite:///commentary.db', echo=True)

# DB Model 선언
Base = declarative_base()

class Commentary(Base):
    __tablename__ = 'commentaries'
    id = Column(Integer, primary_key=True)
    comments = Column(Text)

Commentary.__table__.create(bind=engine, checkfirst=True)

# LLM 클라이언트 선언
client = OpenAI(api_key=os.environ["SOLAR_API_KEY"], base_url="https://api.upstage.ai/v1/solar")

# Rest API 서버
app = FastAPI()

# API Request DTO
class BigFiveEvaluation(BaseModel):
    evaluationId: int
    extraversion: str
    neuroticism: str
    conscientiousness: str
    agreeableness: str
    openness: str

########################################################################################################
# REST API
@app.post("/api/commentaries")
def create_commentary(item: BigFiveEvaluation):
    # 1. Solar LLMs 에 질의
    chat_result = client.chat.completions.create(
        model="solar-1-mini-chat",
        messages=[
            {"role": "system", "content": f"""
            최근에 Big Five personality traits 검사 결과를 받았어. 외향성(extraversion)이 {item.extraversion},
            신경성(neuroticism)이 {item.neuroticism}, 성실성(conscientiousness)이 {item.conscientiousness},
            친화성(agreeableness)이 {item.agreeableness}, 개방성(openness)이 {item.openness} 이라는 거야.
            그런데 이 결과를 어떻게 해석해야 할지 모르겠어.
            각 성격 요인별 강점을 살리고, 약점을 보완하는 긍정적인 방향으로 삶을 개선할 수 있는 구체적인 제안을 해줘.
            나에 대해 스스로 잘 알고 싶어 그래서 요약본과 각 요인별 자세하게 해석해주었으면해
            내가 지향하는 방향과 다른 결과 때문에 내가 스스로 잘 하고 있는지 걱정이 드네.
            """},
        ],
    )
    
    # 2. 결과를 SQLite DB에 저장
    Session = sessionmaker(bind=engine)
    session = Session()
    
    commentary = Commentary(id=item.evaluationId, comments=chat_result.choices[0].message.content)
    session.add(commentary)
    session.commit()
    
    # 해설 반환
    return {"comments": chat_result.choices[0].message.content}