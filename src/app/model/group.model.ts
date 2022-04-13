import { GroupState } from './enums/groupState';

export interface Group {
  id?: number;
  groupState: GroupState;

  semesterId?: number;
  topicId?: number;
  meetingListId?: number[];
  studentGroupIdList?: number[];
}
