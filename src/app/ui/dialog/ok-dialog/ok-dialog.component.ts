import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

export interface OkDialogData {
  title: string;
  description: string;
}

@Component({
  selector: 'app-ok-dialog',
  templateUrl: './ok-dialog.component.html',
  styleUrls: ['./ok-dialog.component.scss'],
})
export class OkDialogComponent implements OnInit {
  data: OkDialogData;
  constructor(
    public dialogRef: MatDialogRef<OkDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data: OkDialogData
  ) {
    this.data = data;
  }

  ngOnInit(): void {}

  close() {
    this.dialogRef.close();
  }
}
