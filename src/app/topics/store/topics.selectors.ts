import { createFeatureSelector, createSelector } from '@ngrx/store';
import { filter } from 'rxjs';
import { TopicsState } from './reducers';
import * as fromTopics from './reducers/index';

export const selectTopicsState = createFeatureSelector<TopicsState>('topics');

export const selectAllTopics = createSelector(
  selectTopicsState,
  fromTopics.selectAll
);
