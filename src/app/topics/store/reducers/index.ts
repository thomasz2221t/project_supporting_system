import { createEntityAdapter, EntityState } from '@ngrx/entity';
import { createReducer, on } from '@ngrx/store';
import { Topic } from '../../model/topic';
import { TopicActions } from '../action-types';

export const topicsFeatureKey = 'topics';

export interface TopicsState extends EntityState<Topic> {}

export const adapter = createEntityAdapter<Topic>();

export const initialTopicsState = adapter.getInitialState();

export const topicsReducer = createReducer(
  initialTopicsState,

  on(TopicActions.allTopicsLoaded, (state, action) =>
    adapter.addMany(action.topics, state)
  )
);

export const { selectAll } = adapter.getSelectors();
