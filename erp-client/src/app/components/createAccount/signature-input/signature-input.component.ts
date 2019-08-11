import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {CanvasComponent} from '../../canvas/canvas.component';
import {RecoveryQuestion} from '../../../models/recovery.model';

@Component({
  selector: 'erp-signature-input',
  templateUrl: './signature-input.component.html',
  styleUrls: ['./signature-input.component.scss']
})
export class SignatureInputComponent implements OnInit {

  @Output() event: EventEmitter<string> = new EventEmitter();

  @ViewChild(CanvasComponent, {static: false}) canvasChild: CanvasComponent;

  signature: string;

  constructor() {
  }

  ngOnInit() {
  }

  getSignature() {
    this.signature = this.canvasChild.getCanvasData();
    this.event.emit(this.signature);
  }

  isTouched() {
    if (!this.canvasChild) {
      return false;
    }
    return this.canvasChild.getTouched();
  }
}
