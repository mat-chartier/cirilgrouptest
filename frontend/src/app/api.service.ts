import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ApiService {
  constructor(private http: HttpClient) {}

  getNextSimulationStep() {
    return this.http.get<{ msg: string }>('http://localhost:8080/api/next');
  }
}