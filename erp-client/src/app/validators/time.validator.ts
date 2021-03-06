import { AbstractControl } from '@angular/forms';
import * as _moment from 'moment';

const moment = _moment;

export function ValidateTime(timeControl: AbstractControl) {
  if (timeControl && timeControl.value) {
    const formatted = moment(timeControl.value, ["h:mm A"]).format('HH:mm');
    if (!moment(formatted, 'HH:mm', true).isValid()) {
      return { 'validTime': true };
    }
  }

  return null;
}