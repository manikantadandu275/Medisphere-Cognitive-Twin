import shap
import numpy as np

print("MediSphere SHAP Explanation")
print("---------------------------")

# Example patient data
features = ["Age", "Heart Rate", "SpO2"]
values = np.array([21, 78, 98])

# Simple example model
def model(x):
    return x[:, 0] * 0.01 + x[:, 1] * 0.02 - x[:, 2] * 0.01

# Create SHAP explainer
background = np.array([
    [20, 75, 98],
    [30, 80, 97],
    [40, 85, 96]
])

explainer = shap.Explainer(model, background)

# Explain patient data
shap_values = explainer(values.reshape(1, -1))

print("Patient values:")
for feature, value in zip(features, values):
    print(feature, ":", value)

print("\nSHAP values:")
for feature, value in zip(features, shap_values.values[0]):
    print(feature, ":", round(float(value), 4))

print("\nSHAP explanation completed successfully.")