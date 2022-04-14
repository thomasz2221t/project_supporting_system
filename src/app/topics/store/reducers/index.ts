import { createEntityAdapter, EntityState } from '@ngrx/entity';
import { createReducer, on } from '@ngrx/store';
import { Topic } from '../../model/topic';
import { TopicActions } from '../action-types';

export const topicsFeatureKey = 'topics';

export interface TopicsState extends EntityState<Topic> {}

export const adapter = createEntityAdapter<Topic>({});

export const initialTopicsState = adapter.getInitialState();

export const topicsReducer = createReducer(
  initialTopicsState,

  on(TopicActions.allTopicsLoaded, (state, action) =>
    adapter.addMany(action.topics, state)
  ),
  on(TopicActions.loadTopicsFromObjectArray, (state, action) =>
    adapter.addMany(action.topics, state)
  ),
  on(TopicActions.deleteTopic, (state, action) =>
    adapter.removeOne(action.topicId, state)
  )
);

export const { selectAll, selectEntities } = adapter.getSelectors();
