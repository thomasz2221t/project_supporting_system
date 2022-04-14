import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { TopicActions } from './action-types';
import { TopicService } from '../services/topic.service';
import { concatMap, map, noop, tap } from 'rxjs';
import { allTopicsLoaded } from './topics.actions';

@Injectable()
export class TopicsEffects {
  loadTopics$ = createEffect(() =>
    this.actions$.pipe(
      ofType(TopicActions.loadAllTopics),
      concatMap((action) => this.topicsService.getAllTopics(action.page)),
      map((topics) => allTopicsLoaded({ topics })),
      tap((topics) =>
        localStorage.setItem('topics', JSON.stringify(topics.topics))
      )
    )
  );
  deleteTopic$ = createEffect(() =>
    this.actions$.pipe(
      ofType(TopicActions.deleteTopic),
      concatMap((action) => this.topicsService.deleteTopic(action.topicId))
    )
  );

  constructor(private actions$: Actions, private topicsService: TopicService) {}
}
