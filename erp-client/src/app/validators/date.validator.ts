import { AbstractControl } from '@angular/forms';
import * as _moment from 'moment';

const moment = _moment;

export function ValidateDate(dateControl: AbstractControl) {
  if (dateControl && dateControl.value) {
    const d = new Date(dateControl.value);

    if (!moment(d, 'MM-DD-YYYY', true).isValid()) {
        return { 'validDate': true };
    }
  }

  return null;
}