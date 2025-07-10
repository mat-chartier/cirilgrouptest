import { Component } from '@angular/core';
import { ApiService } from './api.service';
import { NgClass, NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [NgFor, NgIf, NgClass],
})
export class AppComponent {
  title = 'frontend';
  board: LandState[][] | null = null;
  constructor(private api: ApiService) { }

  startSimulation(): void {
    this.api.getNextSimulationStep(null).subscribe({
      next: (res: any) => {
        this.board = res.states;
        if (res.cellsStillBurning) {
          window.setTimeout(() => {
            this.getNextSimulationStep();
          }, 1000);
        }
      },
      error: (err: any) => {
        console.error('Erreur lors du démarrage de la simulation:', err);
      }
    });
  }

  getNextSimulationStep(): void {
    this.api.getNextSimulationStep(this.board).subscribe({
      next: (res: any) => {
        this.board = res.states;
        if (res.cellsStillBurning) {
          window.setTimeout(() => {
            this.getNextSimulationStep();
          }, 500);
        }
      },
      error: (err: any) => {
        console.error('Erreur lors de la récupération du prochain état de la simulation:', err);
      }
    });
  }
}

export enum LandState {
  ALIVE = "ALIVE",
  BURNING = "BURNING",
  BURNED = "BURNED"
}
