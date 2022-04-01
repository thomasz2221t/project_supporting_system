import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Topic } from 'src/app/model/topic';
import { TopicService } from '../../service/topic.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { OkDialogComponent } from '../../ui/dialog/ok-dialog/ok-dialog.component';

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
    this.topicForm.reset();
    Object.keys(this.topicForm.controls).forEach((key) => {
      this.topicForm.get(key)?.setErrors(null);
    });
  }

  onFormSubmit() {
    this.topic.topicName = this.name?.value;
    this.topic.description = this.description?.value;
    this.topicService.createTopic(this.topic).subscribe((e) => {
      let topic: Topic = e;
      this.resetForm();
      this.openDialog(topic.topicName);
    });
  }

  openDialog(topicName: string) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;
    dialogConfig.closeOnNavigation = true;

    this.dialog.open(OkDialogComponent, {
      data: {
        title: 'Success!',
        description: `Successfully created topic named: ${topicName}`,
      },
    });
  }

  get name() {
    return this.topicForm.get('name');
  }

  get description() {
    return this.topicForm.get('description');
  }
  ngOnInit(): void {}
}
