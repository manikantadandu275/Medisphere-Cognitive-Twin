from fastapi import FastAPI
from pydantic import BaseModel

from cvd_prediction import predict_cvd_risk
from diabetes_prediction import predict_diabetes_risk

app = FastAPI(title="MediSphere AI Service")


class PatientData(BaseModel):
    age: int
    heart_rate: float
    spo2: float


@app.get("/")
def home():
    return {"message": "MediSphere AI Service is running"}


@app.post("/predict/cvd")
def predict_cvd(data: PatientData):
    result = predict_cvd_risk(
        age=data.age,
        heart_rate=data.heart_rate,
        spo2=data.spo2
    )

    return result
@app.post("/predict/diabetes")
def predict_diabetes(data: PatientData):
    result = predict_diabetes_risk(
        age=data.age,
        heart_rate=data.heart_rate,
        spo2=data.spo2
    )

    return result

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="127.0.0.1", port=8000)