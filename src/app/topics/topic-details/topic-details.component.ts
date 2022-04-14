import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Store, select } from '@ngrx/store';
import { TopicsState } from '../store/reducers';
import {
  Observable,
  tap,
  first,
  filter,
  map,
  shareReplay,
  noop,
  finalize,
} from 'rxjs';
import { Topic } from '../model/topic';
import { selectAllTopics } from '../store/topics.selectors';

@Component({
  selector: 'app-topic-details',
  templateUrl: './topic-details.component.html',
  styleUrls: ['./topic-details.component.scss'],
})
export class TopicDetailsComponent implements OnInit {
  loading$: Observable<boolean>;
  topic: Topic;

  constructor(
    private route: ActivatedRoute,
    private store: Store<TopicsState>,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (typeof Number(id) != 'number') this.router.navigateByUrl('/');
    let numId: number = +id;
    this.loading$ = this.store.pipe(
      select(selectAllTopics),
      map((topics) => {
        const topic = topics.find((topic) => topic.id == numId);
        this.topic = topic;
        return !!topic;
      }),
      shareReplay()
    );
  }
}
