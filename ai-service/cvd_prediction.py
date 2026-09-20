def predict_cvd_risk(age, heart_rate, spo2):
    risk_score = 0

    if age >= 60:
        risk_score += 2
    elif age >= 45:
        risk_score += 1

    if heart_rate > 100:
        risk_score += 2
    elif heart_rate > 90:
        risk_score += 1

    if spo2 < 94:
        risk_score += 2
    elif spo2 < 96:
        risk_score += 1

    if risk_score >= 4:
        risk = "High"
    elif risk_score >= 2:
        risk = "Moderate"
    else:
        risk = "Low"

    return {
        "risk": risk,
        "risk_score": risk_score
    }


# Test the prediction
result = predict_cvd_risk(
    age=21,
    heart_rate=78,
    spo2=98
)

print(result)