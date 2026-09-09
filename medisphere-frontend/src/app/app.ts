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

  patient360: any;

  constructor(private http: HttpClient) {
    this.http.get('http://localhost:8081/api/patient360')
      .subscribe({
        next: (data) => {
          console.log('Patient 360 data:', data);
          this.patient360 = data;
        },
        error: (error) => {
          console.error('API Error:', error);
        }
      });
  }
}