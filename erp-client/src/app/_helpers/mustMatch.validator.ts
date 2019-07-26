import { FormGroup } from '@angular/forms';

/**
 * Validator that checks if two fields match
 */
export function MustMatch(controlNameA: string, controlNameB: string) {
  return (formGroup: FormGroup) => {
    const controlA = formGroup.controls[controlNameA];
    const controlB = formGroup.controls[controlNameB];

    // If an error has already been found, stop processing.
    if (controlB.errors && !controlB.errors.mustMatch) {
      return;
    }

    // If validation fails, set the error. Otherwise, clear it.
    if (controlA.value !== controlB.value) {
      controlB.setErrors({ mustMatch: true });
    } else {
      controlB.setErrors(null);
    }
  }
}