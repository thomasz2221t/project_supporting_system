import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Topic } from '../model/topic';
import { TopicsState } from '../store/reducers';
import { deleteTopic } from '../store/topics.actions';

@Component({
  selector: 'app-topic-card',
  templateUrl: './topic-card.component.html',
  styleUrls: ['./topic-card.component.scss'],
})
export class TopicCardComponent implements OnInit {
  @Input() topic: Topic;
  constructor(private router: Router, private store: Store<TopicsState>) {}

  showTopicDetails() {
    this.router.navigateByUrl(`/topic/${this.topic.id}`);
  }

  deleteTopic() {
    this.store.dispatch(deleteTopic({ topicId: `${this.topic.id}` }));
  }

  ngOnInit(): void {}
}
