import { MatTableDataSource } from '@angular/material/table';
import { createFeatureSelector, createSelector } from '@ngrx/store';
import { map, pipe } from 'rxjs';
import { TopicsState } from './reducers';
import * as fromTopics from './reducers/index';

export const selectTopicsState = createFeatureSelector<TopicsState>('topics');

export const selectAllTopics = createSelector(
  selectTopicsState,
  fromTopics.selectAll
);
