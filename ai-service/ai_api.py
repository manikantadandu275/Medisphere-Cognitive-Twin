from fastapi import FastAPI
from pydantic import BaseModel

from cvd_prediction import predict_cvd_risk
from diabetes_prediction import predict_diabetes_risk
from careplan_generator import generate_personalized_careplan
from anomaly_detector import detect_vitals_anomaly

app = FastAPI(title="MediSphere AI Service")


class PatientData(BaseModel):
    age: int
    heart_rate: float
    spo2: float


class VitalsData(BaseModel):
    heart_rate: float
    spo2: float
    temperature: float
    context: str = "At rest"



@app.get("/")
def home():
    return {
        "message": "MediSphere AI Service is running"
    }


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


@app.get("/careplan")
def get_careplan(patient_id: str = "P101"):
    return generate_personalized_careplan(patient_id=patient_id)


@app.post("/detect-anomaly")
def detect_anomaly(vitals: VitalsData):
    return detect_vitals_anomaly(
        heart_rate=vitals.heart_rate,
        spo2=vitals.spo2,
        temperature=vitals.temperature,
        context=vitals.context
    )


@app.get("/shap")
def get_shap():

    return {
        "model": "CVD-Risk-v3.2",
        "federated_round": 47,
        "features": [
            {
                "name": "HbA1c (+8%)",
                "value": "7.2%",
                "shap_value": 0.08
            },
            {
                "name": "Blood Pressure (+6%)",
                "value": "138/88 mmHg",
                "shap_value": 0.06
            },
            {
                "name": "Age (+6%)",
                "value": 58,
                "shap_value": 0.06
            },
            {
                "name": "Heart Rate",
                "value": 78,
                "shap_value": -0.04
            },
            {
                "name": "SpO2",
                "value": 98,
                "shap_value": -0.01
            }
        ]
    }


@app.get("/federated")
def get_federated():

    client1_data = [78, 80, 76]
    client2_data = [82, 79, 81]

    client1_average = sum(client1_data) / len(client1_data)
    client2_average = sum(client2_data) / len(client2_data)

    global_average = (
        client1_average + client2_average
    ) / 2

    return {
        "status": "CONVERGED",
        "current_round": 47,
        "global_accuracy": 91.4,
        "participating_hospitals": [
            "Mayo Clinic Health System",
            "Johns Hopkins Medicine",
            "Stanford Health Care"
        ],
        "client1_average": client1_average,
        "client2_average": client2_average,
        "global_average": global_average
    }


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(
        app,
        host="127.0.0.1",
        port=8000
    )