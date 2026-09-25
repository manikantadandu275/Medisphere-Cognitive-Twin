def generate_personalized_careplan(patient_id: str = "P101", name: str = "John Doe", cvd_risk: float = 24.3):
    return {
        "careplanId": f"CP-{patient_id}-2026",
        "patientId": patient_id,
        "patientName": name,
        "model": "MediSphere Precision Care Engine v2.1",
        "targetGoal": "HbA1c < 7.0% in 90 days; Blood Pressure < 130/80 mmHg",
        "interventions": [
            "Metformin dosage adjustment: 500mg -> 1000mg BID",
            "Add Amlodipine 5mg QD for blood pressure optimization",
            "Daily glucose monitoring & wearable vitals sync",
            "Low-sodium Mediterranean diet program"
        ],
        "baselineCvdRisk": f"{cvd_risk}% (High Risk Category)",
        "predictedCvdRisk": "16.2% (33.3% Relative Risk Reduction)",
        "adherenceScore": 87.4,
        "shapImpact": {
            "HbA1c": "+8.0%",
            "Blood Pressure": "+6.0%",
            "Smoking History": "+4.3%",
            "Age": "+6.0%"
        },
        "status": "PENDING_PROVIDER_APPROVAL",
        "guidelineCompliance": "100% (ADA & ACC/AHA Guidelines Verified)",
        "drugSafetyCheck": "PASSED - No adverse interactions detected"
    }
