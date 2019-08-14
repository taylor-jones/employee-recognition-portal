import { AbstractControl } from '@angular/forms';

export function ValidateEmail(emailControl: AbstractControl) {
  if (!emailControl || !emailControl.value) {
    return { 'validEmail': true };
  }

  if (emailControl && emailControl.value) {
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailControl.value)) {
      return { 'validEmail': true }
    }
  }

  return null;
}