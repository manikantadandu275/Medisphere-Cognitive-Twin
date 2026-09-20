import { Component, signal } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
  imports: [HttpClientModule]
})
export class App {

  protected readonly title = signal('MediSphere');

  patient360 = signal<any>(null);
  cvdRisk = signal<any>(null);
  diabetesRisk = signal<any>(null);

  constructor(private http: HttpClient) {

    this.http.get('http://localhost:8081/api/patient360')
      .subscribe({
        next: (data: any) => {
          console.log('Patient 360 data:', data);

          this.patient360.set(data);

          this.loadAiResults(data);
        },
        error: (error) => {
          console.error('API Error:', error);
        }
      });
  }

  loadAiResults(patientData: any) {

    const input = {
      age: patientData.digitalTwin.age,
      heart_rate: patientData.wearableData.heartRate,
      spo2: patientData.wearableData.spo2
    };

    this.http.post('http://localhost:8081/api/ai/cvd', input)
      .subscribe({
        next: (data: any) => {
          console.log('CVD result:', data);
          this.cvdRisk.set(data);
        },
        error: (error) => {
          console.error('CVD API Error:', error);
        }
      });

    this.http.post('http://localhost:8081/api/ai/diabetes', input)
      .subscribe({
        next: (data: any) => {
          console.log('Diabetes result:', data);
          this.diabetesRisk.set(data);
        },
        error: (error) => {
          console.error('Diabetes API Error:', error);
        }
      });
  }
}