import { Topic } from './topic';
import { Semester } from './semester';
import { Meeting } from './meeting';
import { StudentGroup } from './student-group';
import { GroupState } from './enums/groupState';

export interface Group {
  id?: number;
  groupState: GroupState;

  semesterId?: number;
  topicId?: number;
  meetingListId?: number[];
  studentGroupIdList?: number[];
}
