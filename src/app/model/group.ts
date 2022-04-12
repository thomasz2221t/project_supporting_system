import { Topic } from '../topics/model/topic';
import { Semester } from './semester';
import { Meeting } from './meeting';
import { StudentGroup } from './student-group';
import { GroupState } from './enums/groupState';

export class Group {
  id?: number;
  groupState: GroupState;

  semesterId?: number;
  topicId?: number;
  meetingListId?: number[];
  studentGroupIdList?: number[];

  constructor() {
    this.groupState = GroupState.OPEN;
  }
}
