import { Injectable } from '@angular/core';
import {
  MatDialog,
  MatDialogConfig,
  MatDialogRef,
} from '@angular/material/dialog';
import { ErrorDialogComponent } from '../dialog/error-dialog/error-dialog.component';
import { OkDialogComponent } from '../dialog/ok-dialog/ok-dialog.component';
import { ProgressSpinnerComponent } from '../dialog/progress-spinner/progress-spinner.component';

@Injectable({
  providedIn: 'root',
})
export class DialogService {
  constructor(private dialog: MatDialog) {}

  showLoadingSpinner(): MatDialogRef<ProgressSpinnerComponent> {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.closeOnNavigation = true;
    dialogConfig.panelClass = 'noBackgroundDialog';
    return this.dialog.open(ProgressSpinnerComponent, dialogConfig);
  }

  openDialog(title: string, description: string) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;
    dialogConfig.closeOnNavigation = true;
    dialogConfig.data = {
      title: title,
      description: description,
    };

    this.dialog.open(OkDialogComponent, dialogConfig);
  }

  openErrorDialog(errorMessage: string) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;
    dialogConfig.closeOnNavigation = true;
    dialogConfig.data = {
      errorMessage: errorMessage,
    };

    this.dialog.open(ErrorDialogComponent, dialogConfig);
  }
}
