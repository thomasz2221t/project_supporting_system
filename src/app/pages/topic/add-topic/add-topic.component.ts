import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Topic } from 'src/app/model/topic';
import { TopicService } from '../../../service/topic.service';
import {
  MatDialog,
  MatDialogConfig,
  MatDialogRef,
} from '@angular/material/dialog';
import { OkDialogComponent } from '../../../ui/dialog/ok-dialog/ok-dialog.component';
import { ErrorDialogComponent } from 'src/app/ui/dialog/error-dialog/error-dialog.component';
import { ProgressSpinnerComponent } from 'src/app/ui/dialog/progress-spinner/progress-spinner.component';

@Component({
  selector: 'app-add-topic',
  templateUrl: './add-topic.component.html',
  styleUrls: ['./add-topic.component.scss'],
})
export class AddTopicComponent implements OnInit {
  topic: Topic = {
    topicName: '',
    description: '',
  };
  topicForm: FormGroup;

  constructor(private topicService: TopicService, public dialog: MatDialog) {
    this.topicForm = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.minLength(4)]),
      description: new FormControl(''),
    });
  }

  resetForm() {
    this.name?.setValue('');
    this.description?.setValue('');
    Object.keys(this.topicForm.controls).forEach((key) => {
      this.topicForm.get(key)?.setErrors(null);
    });
    this.topicForm.markAsUntouched();
  }

  showLoadingSpinner(): MatDialogRef<ProgressSpinnerComponent> {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.closeOnNavigation = true;
    dialogConfig.panelClass = 'noBackgroundDialog';
    return this.dialog.open(ProgressSpinnerComponent, dialogConfig);
  }

  onFormSubmit() {
    const dialogRef = this.showLoadingSpinner();
    this.topic.topicName = this.name?.value;
    this.topic.description = this.description?.value;
    this.topicService.createTopic(this.topic).subscribe({
      next: (res) => {
        let topic: Topic = res;
        dialogRef.close();
        this.resetForm();
        this.openDialog(topic.topicName);
      },
      error: (err) => {
        dialogRef.close();
        this.openErrorDialog(err);
      },
    });
  }

  openDialog(topicName: string) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;
    dialogConfig.closeOnNavigation = true;
    dialogConfig.data = {
      title: 'Success!',
      description: `Successfully created topic named: ${topicName}`,
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
  get name() {
    return this.topicForm.get('name');
  }

  get description() {
    return this.topicForm.get('description');
  }
  ngOnInit(): void {}
}
