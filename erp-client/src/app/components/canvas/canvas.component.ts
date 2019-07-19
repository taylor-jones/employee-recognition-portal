import { Component, Input, OnInit } from '@angular/core';
import { CanvasService } from '../../services/canvas/canvas.service';

@Component({
  selector: 'erp-canvas',
  templateUrl: './canvas.component.html',
  styleUrls: ['./canvas.component.scss'],
  providers: [ CanvasService ]
})
export class CanvasComponent implements OnInit {

  private canvasHTML;
  private canvasContext;
  private mouseIsDown: boolean = false;
  unlocked: boolean = true;
  
  @Input() width: number;
  @Input() height: number;

  constructor(private canvasService: CanvasService) { }

  ngOnInit() {
    this.initContext();
  }

  private initContext(): void {
    this.canvasHTML = document.getElementById('canvas');
    this.canvasContext = this.canvasHTML.getContext('2d');
    this.canvasContext.lineWidth = 3;
  }

  // Lock or unlock the canvas
  toggleLock(): void {
    this.unlocked ? this.unlocked = false : this.unlocked = true;
  }

  // On mouse down, prep the path start
  penDown(e): void {
    if (this.unlocked) {
      this.mouseIsDown = true;
      this.canvasContext.beginPath();
      this.canvasContext.moveTo(e.offsetX, e.offsetY);
    }
  }

  // On mouse drag, append mouse locations to the canvas
  penMove(e): void {
    if (this.mouseIsDown && this.unlocked) {
      this.canvasContext.lineTo(e.offsetX, e.offsetY);
      this.canvasContext.stroke();
    }
  }

  // On mouse up, stop drawing
  penUp(): void {
    this.mouseIsDown = false;
  }

  // TR: Still need to test dynamic width and height using parent component to pass input
  clearCanvas(): void {
    this.canvasContext.clearRect(0, 0, 500, 300);
  }

  canvasToData() {
    console.log(this.canvasHTML.toDataURL());
    this.canvasService.addSignature(this.canvasHTML.toDataURL().toString()).subscribe(
      (ok) => {console.log(ok)},
      (error) => {console.log(error)}
    );
  }

  // TR: Generate a quick scribble. Still need to test dynamic width and height using parent component to pass input
  randomPath(): void {
    let randCount = Math.floor(Math.random() * 50);
    this.canvasContext.beginPath();
    this.canvasContext.moveTo(
      Math.floor( Math.random() * 500 ),
      Math.floor( Math.random() * 300 )
    );
    let i = 0;
    while (i < randCount) {
      this.canvasContext.lineTo(
        Math.floor( Math.random() * 500 ),
        Math.floor( Math.random() * 300 )
      );
      i++;
    }
    this.canvasContext.stroke();
  }

}
