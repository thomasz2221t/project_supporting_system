import { createAction, props } from '@ngrx/store';
import { Topic } from '../model/topic';

export const loadAllTopics = createAction(
  '[Topics Resolver] Load Next Topics Page',
  props<{ page: number }>()
);

export const allTopicsLoaded = createAction(
  '[Load Topics Effect] Next Page Topics Loaded',
  props<{ topics: Topic[] }>()
);
export const loadTopicsFromObjectArray = createAction(
  '[Page reload] Load Topics from Local Storage',
  props<{ topics: Topic[] }>()
);

export const deleteTopic = createAction(
  '[Topic] Delete topic',
  props<{ topicId: string }>()
);
