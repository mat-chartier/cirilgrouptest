import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ApiService {
  constructor(private http: HttpClient) {}

  getNextSimulationStep(states: any[][] | null = []): any {
    return this.http.post<{ msg: string }>('http://localhost:8080/api/next', { states });
  }
}