import {MatSnackBar} from '@angular/material/snack-bar';
import {Injectable} from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class SnackbarService {

  constructor(private _snackbar: MatSnackBar) {
  }

  /**
   * Shows a snackbar with a success message
   * @param {string} message - the message to display on the snackbar
   */
  public showSuccess(message, action = 'Okay', options = {
    duration: 3000,
    panelClass: ['snackbar-success']
  }) {
    this._snackbar.open(message, action, options);
  }


  /**
   * Shows a snackbar with an error message
   * @param {string} message - the message to display on the snackbar
   * @param {string} action - the text of the Action button
   * @param {object} options - the options to include for the snackbar
   */
  public showError(message, action = 'Okay', options = {
    duration: 3000,
    panelClass: ['snackbar-error']
  }) {
    this._snackbar.open(message, action, options);
  }
}
