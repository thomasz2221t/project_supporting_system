import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Topic } from 'src/app/model/topic';
import { TopicService } from '../../service/topic.service';

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

  constructor(private topicService: TopicService) {
    this.topicForm = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.minLength(4)]),
      description: new FormControl(''),
    });
  }

  onFormSubmit() {
    this.topic.topicName = this.name?.value;
    this.topic.description = this.description?.value;
    this.topicService.createTopic(this.topic).subscribe((e) => {
      console.log(e);
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
