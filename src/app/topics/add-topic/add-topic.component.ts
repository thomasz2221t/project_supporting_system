import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Topic } from 'src/app/topics/model/topic';
import { DialogService } from 'src/app/shared/services/dialog.service';
import { TopicService } from '../services/topic.service';

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

  constructor(
    private topicService: TopicService,
    private dialogService: DialogService
  ) {
    this.topicForm = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.minLength(4)]),
      description: new FormControl('', Validators.maxLength(3000)),
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

  onFormSubmit() {
    this.topic.topicName = this.name?.value;
    this.topic.description = this.description?.value;
    this.topicService.createTopic(this.topic).subscribe({
      next: (res) => {
        let topic: Topic = res;
        this.resetForm();
        this.dialogService.openDialog(
          'Success!',
          `Successfully created topic named: ${res.topicName}`
        );
      },
      error: (err) => {
        this.dialogService.openErrorDialog(err);
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
