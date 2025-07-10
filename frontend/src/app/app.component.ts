import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ApiService } from './api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
  simulationSteps: any;
  constructor(private api: ApiService) { }

  ngOnInit() {
    this.api.getNextSimulationStep().subscribe((res) => this.simulationSteps = res.msg );
  }
}
