import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { TopicActions } from './action-types';
import { TopicService } from '../services/topic.service';
import { concatMap, map } from 'rxjs';
import { allTopicsLoaded } from './topics.actions';

@Injectable()
export class TopicsEffects {
  loadTopics$ = createEffect(() =>
    this.actions$.pipe(
      ofType(TopicActions.loadAllTopics),
      concatMap((action) => this.topicsService.getAllTopics()),
      map((topics) => allTopicsLoaded({ topics }))
    )
  );

  constructor(private actions$: Actions, private topicsService: TopicService) {}
}
